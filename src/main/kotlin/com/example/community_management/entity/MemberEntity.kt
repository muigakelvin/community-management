package com.example.community_management.entity

import jakarta.persistence.*

@Entity
@Table(name = "MembersTable")
data class MemberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "mainTableId", nullable = false)
    val mainTable: MainTableEntity? = null,

    val memberIdNumber: String,
    val memberName: String,
    val memberPhoneNumber: String,
    val titleNumber: String
)