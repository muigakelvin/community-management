package com.example.community_management.service

import com.example.community_management.dto.AddFormDialogDto
import com.example.community_management.dto.RepresentativeFormDto
import com.example.community_management.entity.MainTableEntity
import com.example.community_management.mapper.mapAddFormToMainTableEntity
import com.example.community_management.mapper.mapRepresentativeFormToMainTableEntity
import com.example.community_management.mapper.mapToMemberEntities
import com.example.community_management.repository.MainTableRepository
import com.example.community_management.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class FormService(
    private val mainTableRepository: MainTableRepository,
    private val memberRepository: MemberRepository
) {

    fun processAddForm(addForm: AddFormDialogDto) {
        val mainTableEntity = mapAddFormToMainTableEntity(addForm)
        mainTableRepository.save(mainTableEntity)
    }

    fun processRepresentativeForm(representativeForm: RepresentativeFormDto) {
        val mainTableEntity = mapRepresentativeFormToMainTableEntity(representativeForm)
        val savedMainTable = mainTableRepository.save(mainTableEntity)

        // Map members and associate them with the saved MainTableEntity
        val memberEntities = mapToMemberEntities(savedMainTable, representativeForm.members)
        memberRepository.saveAll(memberEntities)
    }
}