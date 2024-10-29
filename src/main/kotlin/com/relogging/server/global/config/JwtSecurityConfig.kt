package com.relogging.server.global.config

import com.relogging.server.global.security.jwt.filter.JwtFilter
import com.relogging.server.global.security.jwt.service.JwtService
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class JwtSecurityConfig(
    private val jwtService: JwtService
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
    override fun configure(builder: HttpSecurity) {
        builder.addFilterBefore(
            JwtFilter(jwtService),
            UsernamePasswordAuthenticationFilter::class.java
        )
    }
}
