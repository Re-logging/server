package com.relogging.server.oauth

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class OAuth2UserService : DefaultOAuth2UserService() {
    private val restTemplate = RestTemplate()

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User: OAuth2User = super.loadUser(userRequest)

        println("Attributes: ${oAuth2User.attributes}")

        return oAuth2User
    }
}
