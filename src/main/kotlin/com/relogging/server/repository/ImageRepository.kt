package com.relogging.server.repository

import com.relogging.server.entity.Image
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository : JpaRepository<Image, Long>
