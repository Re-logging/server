package com.relogging.server.domain.comment.repository

import com.relogging.server.domain.comment.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long>
