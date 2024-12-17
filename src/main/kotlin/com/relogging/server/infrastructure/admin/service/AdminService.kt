package com.relogging.server.infrastructure.admin.service

import com.relogging.server.infrastructure.admin.entity.Admin

interface AdminService {
    fun findById(id: Long): Admin

    fun findAll(): List<Admin>

    fun deleteById(id: Long)
}
