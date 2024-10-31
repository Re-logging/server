package com.relogging.server.security.details

import com.relogging.server.domain.user.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

class PrincipalDetails(
    val user: User,
) : UserDetails, OAuth2User {
    private lateinit var attributes: MutableMap<String, Any>

    constructor(user: User, attributes: MutableMap<String, Any>) : this(user) {
        this.attributes = attributes
    }

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
