package com.relogging.server.domain.image.repository

import com.relogging.server.domain.image.entity.Image
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository : JpaRepository<Image, Long>
