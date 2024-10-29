package com.relogging.server.global.config

import com.relogging.server.global.security.jwt.service.JwtService
import com.relogging.server.global.security.oauth.handler.OAuthAuthenticationSuccessHandler
import com.relogging.server.global.security.oauth.service.PrincipalOAuthUserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Value("\${cors-allow-url.front}")
    private val frontUrl: String,
    private val principalOAuthUserService: PrincipalOAuthUserService,
    private val oAuthAuthenticationSuccessHandler: OAuthAuthenticationSuccessHandler,
    private val jwtService: JwtService
) {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            httpBasic { disable() }
            csrf { disable() }
            cors { }
//            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
//            authorizeRequests {
//                authorize("/**", permitAll)
//            }
            oauth2Login {
                userInfoEndpoint { userService = principalOAuthUserService }
                authenticationSuccessHandler = oAuthAuthenticationSuccessHandler
            }
            apply { JwtSecurityConfig(jwtService) }

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
