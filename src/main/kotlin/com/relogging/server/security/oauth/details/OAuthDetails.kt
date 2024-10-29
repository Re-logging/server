package com.relogging.server.security.oauth.details

import com.relogging.server.domain.user.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

class OAuthDetails(
    private val user: User,
    private val attributes: MutableMap<String, Any>,
) : UserDetails, OAuth2User {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(user.role.value))
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return this.attributes
    }

    override fun getPassword(): String {
        return ""
    }

    override fun getUsername(): String {
        return this.user.email
    }

    override fun getName(): String {
        return this.user.name
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
