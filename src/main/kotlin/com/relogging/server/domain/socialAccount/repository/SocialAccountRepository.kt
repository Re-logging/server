package com.relogging.server.domain.socialAccount.repository

import com.relogging.server.domain.socialAccount.entity.SocialAccount
import org.springframework.data.jpa.repository.JpaRepository

interface SocialAccountRepository : JpaRepository<SocialAccount, Long>
