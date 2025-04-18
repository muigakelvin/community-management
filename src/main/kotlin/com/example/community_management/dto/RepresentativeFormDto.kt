package com.example.community_management.dto

data class RepresentativeFormDto(
    val groupName: String,
    val representativeName: String,
    val representativeIdNumber: String,
    val representativePhone: String,
    val communityName: String,
    val landSize: String,
    val sublocation: String,
    val location: String,
    val fieldCoordinator: String,
    val dateSigned: String,
    val signedLocal: String,
    val signedOrg: String,
    val witnessLocal: String,
    val loiDocument: String,
    val mouDocument: String,
    val gisDetails: String,
    val members: List<MemberDto>
)

data class MemberDto(
    val memberIdNumber: String,
    val memberName: String,
    val memberPhoneNumber: String,
    val titleNumber: String
)