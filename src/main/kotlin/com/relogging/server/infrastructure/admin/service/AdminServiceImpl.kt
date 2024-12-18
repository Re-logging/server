package com.relogging.server.infrastructure.admin.service

import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import com.relogging.server.infrastructure.admin.entity.Admin
import com.relogging.server.infrastructure.admin.repository.AdminRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminServiceImpl(
    private val adminRepository: AdminRepository,
) : AdminService {
    @Transactional
    override fun findById(id: Long): Admin {
        return adminRepository.findById(id).orElseThrow {
            throw GlobalException(GlobalErrorCode.ADMIN_NOT_FOUND)
        }
    }

    @Transactional
    override fun findAll(): List<Admin> {
        return adminRepository.findAll()
    }

    @Transactional
    override fun deleteById(id: Long) {
        val admin = findById(id)
        adminRepository.delete(admin)
    }
}
