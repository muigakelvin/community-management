package com.example.community_management.repository

import com.example.community_management.entity.MainTableEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MainTableRepository : JpaRepository<MainTableEntity, Long>