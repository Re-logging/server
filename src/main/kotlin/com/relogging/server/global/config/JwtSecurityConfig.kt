package com.relogging.server.global.config

import com.relogging.server.global.security.jwt.service.JwtService
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain

@Configuration
class JwtSecurityConfig(
    private val jwtService: JwtService
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
    override fun configure(builder: HttpSecurity?) {
        super.configure(builder)
    }
}
