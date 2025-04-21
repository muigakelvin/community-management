package com.example.community_management.service

import com.example.community_management.dto.AddFormDialogDto
import com.example.community_management.dto.MemberDto
import com.example.community_management.dto.RepresentativeFormDto
import com.example.community_management.dto.UnifiedRecordDto
import com.example.community_management.entity.MainTableEntity
import com.example.community_management.entity.MemberEntity
import com.example.community_management.mapper.toUnifiedRecordDto
import com.example.community_management.repository.MainTableRepository
import com.example.community_management.repository.MemberRepository
import org.hibernate.Hibernate
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.util.Base64

@Service
class FormService(
    private val mainTableRepository: MainTableRepository,
    private val memberRepository: MemberRepository
) {
    private val logger = LoggerFactory.getLogger(FormService::class.java)

    // Process AddFormDialog
    fun processAddFormDialog(addFormDialog: AddFormDialogDto): UnifiedRecordDto {
        logger.info("Processing AddFormDialog data: $addFormDialog")
        // Decode base64 files into temporary files
        val loiDocument = addFormDialog.loiDocument?.let { decodeBase64ToFile(it, "loi.pdf") }
        val mouDocument = addFormDialog.mouDocument?.let { decodeBase64ToFile(it, "mou.pdf") }
        val gisDetails = addFormDialog.gisDetails?.let { decodeBase64ToFile(it, "gis.gpx") }
        try {
            // Map DTO to Entity
            val mainTableEntity = mapAddFormDialogToMainTableEntity(addFormDialog).apply {
                this.loiDocumentPath = loiDocument?.absolutePath
                this.mouDocumentPath = mouDocument?.absolutePath
                this.gisDetailsPath = gisDetails?.absolutePath
            }
            // Save the main table entity
            val savedMainTable = mainTableRepository.save(mainTableEntity)
            logger.info("AddFormDialog submitted successfully with ID: ${savedMainTable.id}")
            // Return the saved entity as a DTO
            return savedMainTable.toUnifiedRecordDto()
        } catch (e: Exception) {
            logger.error("Error processing AddFormDialog: ${e.message}")
            throw RuntimeException("Failed to process AddFormDialog")
        } finally {
            // Clean up temporary files
            listOf(loiDocument, mouDocument, gisDetails).forEach { file ->
                file?.delete()
            }
        }
    }

    // Process RepresentativeForm
    @Transactional
    fun processRepresentativeForm(representativeForm: RepresentativeFormDto): UnifiedRecordDto {
        logger.info("Processing RepresentativeForm data: $representativeForm")
        // Decode base64 files into temporary files
        val loiDocument = representativeForm.loiDocument?.let { decodeBase64ToFile(it, "loi.pdf") }
        val mouDocument = representativeForm.mouDocument?.let { decodeBase64ToFile(it, "mou.pdf") }
        val gisDetails = representativeForm.gisDetails?.let { decodeBase64ToFile(it, "gis.gpx") }
        try {
            // Create the main table entity
            val mainTable = mapRepresentativeFormToMainTableEntity(representativeForm).apply {
                this.loiDocumentPath = loiDocument?.absolutePath
                this.mouDocumentPath = mouDocument?.absolutePath
                this.gisDetailsPath = gisDetails?.absolutePath
            }
            // Map the members and set them
            val members = mapToMemberEntities(mainTable, representativeForm.members)
            mainTable.members.addAll(members) // Add all mapped members to the main table
            // Save the main table entity (this will cascade save the members due to CascadeType.ALL)
            val savedMainTable = mainTableRepository.save(mainTable)
            // Log the number of members added
            logger.info("Members count after saving: ${savedMainTable.members.size}")
            // Return the saved entity as a DTO
            return savedMainTable.toUnifiedRecordDto()
        } finally {
            // Clean up temporary files
            listOf(loiDocument, mouDocument, gisDetails).forEach { file ->
                file?.delete()
            }
        }
    }

    // Read All Records
    fun getAllRecords(): List<UnifiedRecordDto> {
        logger.info("Fetching all records")
        val entities = mainTableRepository.findAll()
        return entities.map { it.toUnifiedRecordDto() }
    }

    // Read Single Record by ID
    fun getRecordById(id: Long): UnifiedRecordDto {
        logger.info("Fetching record by ID: $id")
        val entity = mainTableRepository.findById(id)
            .orElseThrow { RuntimeException("Record not found with ID: $id") }
        Hibernate.initialize(entity.members) // Force initialization of lazy-loaded members
        return entity.toUnifiedRecordDto()
    }

    // Update Operation
    fun updateRecord(id: Long, updatedForm: RepresentativeFormDto): UnifiedRecordDto {
        logger.info("Updating record with ID: $id")
        val existingEntity = mainTableRepository.findById(id)
            .orElseThrow { RuntimeException("Record not found with ID: $id") }
        // Decode base64 files into temporary files
        val loiDocument = updatedForm.loiDocument?.let { decodeBase64ToFile(it, "loi.pdf") }
        val mouDocument = updatedForm.mouDocument?.let { decodeBase64ToFile(it, "mou.pdf") }
        val gisDetails = updatedForm.gisDetails?.let { decodeBase64ToFile(it, "gis.gpx") }
        try {
            // Update the existing entity
            val updatedEntity = mapRepresentativeFormToMainTableEntity(updatedForm).apply {
                this.id = existingEntity.id
                this.loiDocumentPath = loiDocument?.absolutePath ?: existingEntity.loiDocumentPath
                this.mouDocumentPath = mouDocument?.absolutePath ?: existingEntity.mouDocumentPath
                this.gisDetailsPath = gisDetails?.absolutePath ?: existingEntity.gisDetailsPath
            }
            // Save the updated entity
            val savedEntity = mainTableRepository.save(updatedEntity)
            // Update members
            memberRepository.deleteAllByMainTableId(id) // Delete old members
            val memberEntities = mapToMemberEntities(savedEntity, updatedForm.members)
            memberRepository.saveAll(memberEntities)
            logger.info("Record updated successfully with ID: $id")
            // Return the updated entity as a DTO
            return savedEntity.toUnifiedRecordDto()
        } finally {
            // Clean up temporary files
            listOf(loiDocument, mouDocument, gisDetails).forEach { file ->
                file?.delete()
            }
        }
    }

    // Delete Operation
    fun deleteRecord(id: Long) {
        logger.info("Deleting record with ID: $id")
        if (!mainTableRepository.existsById(id)) {
            throw RuntimeException("Record not found with ID: $id")
        }
        mainTableRepository.deleteById(id)
        logger.info("Record deleted successfully with ID: $id")
    }

    // Helper function to decode base64 strings into temporary files
    private fun decodeBase64ToFile(base64String: String, fileName: String): File {
        logger.debug("Decoding base64 string to file: $fileName")
        val decodedBytes = Base64.getDecoder().decode(base64String.split(",").last())
        val file = File.createTempFile(fileName, null)
        FileOutputStream(file).use { fos -> fos.write(decodedBytes) }
        return file.apply {
            deleteOnExit() // Ensure the file is deleted when the JVM exits
        }
    }

    // Mapper function for AddFormDialogDto
    private fun mapAddFormDialogToMainTableEntity(dto: AddFormDialogDto): MainTableEntity {
        return MainTableEntity(
            communityMember = dto.communityMember,
            idNumber = dto.idNumber,
            phoneNumber = dto.phoneNumber,
            landSize = dto.landSize.toDoubleOrNull(),
            communityName = dto.communityName,
            sublocation = dto.sublocation,
            location = dto.location,
            fieldCoordinator = dto.fieldCoordinator,
            dateSigned = LocalDate.parse(dto.dateSigned),
            signedLocal = dto.signedLocal,
            signedOrg = dto.signedOrg,
            witnessLocal = dto.witnessLocal,
            loiDocumentPath = null, // Set later in processAddFormDialog
            mouDocumentPath = null, // Set later in processAddFormDialog
            gisDetailsPath = null, // Set later in processAddFormDialog
            source = dto.source
        )
    }

    // Mapper function for RepresentativeFormDto
    private fun mapRepresentativeFormToMainTableEntity(dto: RepresentativeFormDto): MainTableEntity {
        return MainTableEntity(
            groupName = dto.groupName,
            representativeName = dto.representativeName,
            representativeIdNumber = dto.representativeIdNumber,
            representativePhone = dto.representativePhone,
            communityMember = dto.communityMember,
            landSize = dto.landSize.toDoubleOrNull(),
            communityName = dto.communityName,
            sublocation = dto.sublocation,
            location = dto.location,
            fieldCoordinator = dto.fieldCoordinator,
            dateSigned = LocalDate.parse(dto.dateSigned),
            signedLocal = dto.signedLocal,
            signedOrg = dto.signedOrg,
            witnessLocal = dto.witnessLocal,
            loiDocumentPath = null, // Set later in processRepresentativeForm
            mouDocumentPath = null, // Set later in processRepresentativeForm
            gisDetailsPath = null, // Set later in processRepresentativeForm
            source = dto.source
        )
    }

    // Mapper function to convert MemberDto to MemberEntity
    private fun mapToMemberEntities(mainTableEntity: MainTableEntity, members: List<MemberDto>): List<MemberEntity> {
        return members.map { memberDto ->
            MemberEntity(
                mainTable = mainTableEntity, // Associate the member with the main table
                memberIdNumber = memberDto.memberIdNumber,
                memberName = memberDto.memberName,
                memberPhoneNumber = memberDto.memberPhoneNumber,
                titleNumber = memberDto.titleNumber
            )
        }
    }
}