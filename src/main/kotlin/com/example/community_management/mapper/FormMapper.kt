package com.example.community_management.mapper

import com.example.community_management.dto.MemberDto
import com.example.community_management.dto.RepresentativeFormDto
import com.example.community_management.dto.UnifiedRecordDto
import com.example.community_management.entity.MainTableEntity
import com.example.community_management.entity.MemberEntity
import java.time.LocalDate
import java.util.Base64

fun mapRepresentativeFormToMainTableEntity(dto: RepresentativeFormDto): MainTableEntity {
    return MainTableEntity(
        groupName = dto.groupName,
        representativeName = dto.representativeName,
        representativeIdNumber = dto.representativeIdNumber,
        representativePhone = dto.representativePhone,
        landSize = dto.landSize.toDoubleOrNull(),
        communityName = dto.communityName,
        sublocation = dto.sublocation,
        location = dto.location,
        fieldCoordinator = dto.fieldCoordinator,
        dateSigned = LocalDate.parse(dto.dateSigned),
        signedLocal = dto.signedLocal,
        signedOrg = dto.signedOrg,
        witnessLocal = dto.witnessLocal,
        loiDocumentPath = null,
        mouDocumentPath = null,
        gisDetailsPath = null
    )
}

fun mapToMemberEntities(mainTable: MainTableEntity, members: List<MemberDto>): List<MemberEntity> {
    return members.map { member ->
        MemberEntity(
            mainTable = mainTable,
            memberIdNumber = member.memberIdNumber,
            memberName = member.memberName,
            memberPhoneNumber = member.memberPhoneNumber,
            titleNumber = member.titleNumber
        )
    }
}

fun MainTableEntity.toUnifiedRecordDto(): UnifiedRecordDto {
    return UnifiedRecordDto(
        id = this.id,
        communityMember = this.communityMember,
        idNumber = this.idNumber,
        phoneNumber = this.phoneNumber,
        landSize = this.landSize?.toString(),
        communityName = this.communityName,
        groupName = this.groupName,
        representativeName = this.representativeName,
        representativeIdNumber = this.representativeIdNumber,
        representativePhone = this.representativePhone,
        sublocation = this.sublocation,
        location = this.location,
        fieldCoordinator = this.fieldCoordinator,
        dateSigned = this.dateSigned?.toString(),
        signedLocal = this.signedLocal,
        signedOrg = this.signedOrg,
        witnessLocal = this.witnessLocal,
        loiDocument = this.loiDocumentPath,
        mouDocument = this.mouDocumentPath,
        gisDetails = this.gisDetailsPath,
        source = this.source,
        members = this.members.map { member ->
            MemberDto(
                memberIdNumber = member.memberIdNumber,
                memberName = member.memberName,
                memberPhoneNumber = member.memberPhoneNumber,
                titleNumber = member.titleNumber
            )
        }
    )
}