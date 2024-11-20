package com.relogging.server.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.relogging.server.security.filter.AuthenticationExceptionFilter
import com.relogging.server.security.handler.JwtAccessDeniedHandler
import com.relogging.server.security.handler.JwtAuthenticationEntryPoint
import com.relogging.server.security.jwt.filter.JwtFilter
import com.relogging.server.security.jwt.provider.TokenProvider
import com.relogging.server.security.oauth.handler.OAuthAuthenticationSuccessHandler
import com.relogging.server.security.oauth.service.CustomOAuthUserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Value("\${cors-allow-url.front}")
    private var frontUrl: String,
    @Value("\${cors-allow-url.local}")
    private var localUrl: String,
    private val customOAuthUserService: CustomOAuthUserService,
    private val oAuthAuthenticationSuccessHandler: OAuthAuthenticationSuccessHandler,
    private val tokenProvider: TokenProvider,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val objectMapper: ObjectMapper,
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
                authorize(HttpMethod.GET, "/api/newsArticles/**", permitAll)
                authorize(HttpMethod.GET, "/api/ploggingEvents/**", permitAll)
                authorize(HttpMethod.GET, "/api/ploggingMeetups/**", permitAll)
                authorize(HttpMethod.POST, "/api/auth/login", permitAll)
                authorize(HttpMethod.POST, "/api/auth/temp", permitAll)
                authorize(anyRequest, authenticated)
            }
            exceptionHandling {
                authenticationEntryPoint = jwtAuthenticationEntryPoint
                accessDeniedHandler = jwtAccessDeniedHandler
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(JwtFilter(tokenProvider))
            addFilterBefore<JwtFilter>(AuthenticationExceptionFilter(objectMapper))
//            oauth2Login {
//                userInfoEndpoint { userService = customOAuthUserService }
//                authenticationSuccessHandler = oAuthAuthenticationSuccessHandler
//                authorizationEndpoint {
//                    authorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository
//                }
//            }
        }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = setOf(frontUrl, localUrl).toList()
        configuration.allowedMethods = listOf("POST", "GET", "DELETE", "PUT")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)

        return source
    }
}
