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
    val witnessLocal: String,
    val signedLocal: String,
    val signedOrg: String,
    val dateSigned: String, // Date as a string (e.g., "YYYY-MM-DD")
    val loiDocument: String?, // Base64-encoded file
    val mouDocument: String?, // Base64-encoded file
    val gisDetails: String?, // Base64-encoded file
    val source: String?, // Added the source field here
    val members: List<MemberDto>
)


data class MemberDto(
    val memberIdNumber: String,
    val memberName: String,
    val memberPhoneNumber: String,
    val titleNumber: String
)