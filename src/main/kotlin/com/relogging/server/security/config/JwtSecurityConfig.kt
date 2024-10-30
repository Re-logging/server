package com.relogging.server.security.config

import com.relogging.server.security.jwt.filter.JwtFilter
import com.relogging.server.security.jwt.provider.TokenProvider
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class JwtSecurityConfig(
    private val tokenProvider: TokenProvider,
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
    override fun configure(builder: HttpSecurity) {
        builder.addFilterBefore(
            JwtFilter(tokenProvider),
            UsernamePasswordAuthenticationFilter::class.java,
        )
    }
}
