package com.example.community_management.mapper

import com.example.community_management.dto.AddFormDialogDto
import com.example.community_management.dto.MemberDto
import com.example.community_management.dto.RepresentativeFormDto
import com.example.community_management.entity.MainTableEntity
import com.example.community_management.entity.MemberEntity
import java.time.LocalDate

// Function for mapping AddFormDialogDto to MainTableEntity
fun mapAddFormToMainTableEntity(dto: AddFormDialogDto): MainTableEntity {
    return MainTableEntity(
        communityMember = dto.communityMember,
        idNumber = dto.idNumber,
        phoneNumber = dto.phoneNumber,
        landSize = "${dto.landSize} acres",
        communityName = dto.communityName,
        sublocation = dto.sublocation,
        location = dto.location,
        fieldCoordinator = dto.fieldCoordinator,
        dateSigned = LocalDate.parse(dto.dateSigned),
        signedLocal = dto.signedLocal,
        signedOrg = dto.signedOrg,
        witnessLocal = dto.witnessLocal,
        loiDocument = dto.loiDocument,
        mouDocument = dto.mouDocument,
        gisDetails = dto.gisDetails,
        source = dto.source
    )
}

// Function for mapping RepresentativeFormDto to MainTableEntity
fun mapRepresentativeFormToMainTableEntity(dto: RepresentativeFormDto): MainTableEntity {
    return MainTableEntity(
        groupName = dto.groupName,
        representativeName = dto.representativeName,
        representativeIdNumber = dto.representativeIdNumber,
        representativePhone = dto.representativePhone,
        landSize = "${dto.landSize} acres",
        communityName = dto.communityName,
        sublocation = dto.sublocation,
        location = dto.location,
        fieldCoordinator = dto.fieldCoordinator,
        dateSigned = LocalDate.parse(dto.dateSigned),
        signedLocal = dto.signedLocal,
        signedOrg = dto.signedOrg,
        witnessLocal = dto.witnessLocal,
        loiDocument = dto.loiDocument,
        mouDocument = dto.gisDetails
    )
}

// Function for mapping MemberDto to MemberEntity
fun mapToMemberEntities(mainTable: MainTableEntity, members: List<MemberDto>): List<MemberEntity> {
    return members.map { member ->
        MemberEntity(
            mainTable = mainTable, // Pass the MainTableEntity object
            memberIdNumber = member.memberIdNumber,
            memberName = member.memberName,
            memberPhoneNumber = member.memberPhoneNumber,
            titleNumber = member.titleNumber
        )
    }
}