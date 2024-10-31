package com.relogging.server.security.config

import com.relogging.server.security.jwt.provider.TokenProvider
import com.relogging.server.security.oauth.handler.OAuthAuthenticationSuccessHandler
import com.relogging.server.security.oauth.service.CustomOAuthUserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Value("\${cors-allow-url.front}")
    private val frontUrl: String,
    private val customOAuthUserService: CustomOAuthUserService,
    private val oAuthAuthenticationSuccessHandler: OAuthAuthenticationSuccessHandler,
    private val tokenProvider: TokenProvider,
) {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            httpBasic { disable() }
            csrf { disable() }
            cors { }
            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
            authorizeRequests {
                authorize("/swagger-ui/**", permitAll)
                authorize("/v3/api-docs/**", permitAll)
                authorize("/swagger-resources/**", permitAll)
                authorize(anyRequest, authenticated)
            }
            oauth2Login {
                userInfoEndpoint { userService = customOAuthUserService }
                authenticationSuccessHandler = oAuthAuthenticationSuccessHandler
            }
            apply { JwtSecurityConfig(tokenProvider) }
        }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf(frontUrl)
        configuration.allowedMethods = listOf("POST", "GET", "DELETE", "PUT")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)

        return source
    }
}
