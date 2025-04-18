package com.example.community_management.dto

data class AddFormDialogDto(
    val communityMember: String,
    val idNumber: String,
    val phoneNumber: String,
    val landSize: String,
    val communityName: String,
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
    val source: String,
    val members: List<Map<String, Any>> = emptyList()
)