package com.example.community_management.repository

import com.example.community_management.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<MemberEntity, Long> {
    fun deleteAllByMainTableId(mainTableId: Long)
}