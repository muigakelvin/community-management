package com.example.community_management.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "MainTable")
data class MainTableEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val communityMember: String? = null,
    val idNumber: String? = null,
    val phoneNumber: String? = null,
    val landSize: String? = null,
    val communityName: String? = null,
    val groupName: String? = null,
    val representativeName: String? = null,
    val representativeIdNumber: String? = null,
    val representativePhone: String? = null,
    val sublocation: String? = null,
    val location: String? = null,
    val fieldCoordinator: String? = null,
    val dateSigned: LocalDate? = null,
    val signedLocal: String? = null,
    val signedOrg: String? = null,
    val witnessLocal: String? = null,
    val loiDocument: String? = null,
    val mouDocument: String? = null,
    val gisDetails: String? = null,
    val source: String? = null
)