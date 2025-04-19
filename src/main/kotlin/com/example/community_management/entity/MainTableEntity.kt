package com.example.community_management.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "MainTable")
data class MainTableEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null, // Changed to var for mutability during updates

    @Column(name = "community_member", length = 255)
    var communityMember: String? = null,

    @Column(name = "id_number", length = 255)
    var idNumber: String? = null,

    @Column(name = "phone_number", length = 255)
    var phoneNumber: String? = null,

    @Column(name = "land_size")
    var landSize: Double? = null,

    @Column(name = "community_name", length = 255)
    var communityName: String? = null,

    @Column(name = "group_name", length = 255)
    var groupName: String? = null,

    @Column(name = "representative_name", length = 255)
    var representativeName: String? = null,

    @Column(name = "representative_id_number", length = 255)
    var representativeIdNumber: String? = null,

    @Column(name = "representative_phone", length = 255)
    var representativePhone: String? = null,

    @Column(name = "sublocation", length = 255)
    var sublocation: String? = null,

    @Column(name = "location", length = 255)
    var location: String? = null,

    @Column(name = "field_coordinator", length = 255)
    var fieldCoordinator: String? = null,

    @Column(name = "date_signed")
    var dateSigned: LocalDate? = null,

    @Column(name = "signed_local", length = 255)
    var signedLocal: String? = null,

    @Column(name = "signed_org", length = 255)
    var signedOrg: String? = null,

    @Column(name = "witness_local", length = 255)
    var witnessLocal: String? = null,

    @Column(name = "source", length = 255)
    var source: String? = null,

    // File path fields (mutable)
    @Column(name = "loi_document_path", length = 255)
    var loiDocumentPath: String? = null,

    @Column(name = "mou_document_path", length = 255)
    var mouDocumentPath: String? = null,

    @Column(name = "gis_details_path", length = 255)
    var gisDetailsPath: String? = null,



    // One-to-many relationship with MemberEntity
    @OneToMany(mappedBy = "mainTable", cascade = [CascadeType.ALL], orphanRemoval = true)
    val members: MutableList<MemberEntity> = mutableListOf()
)