package com.relogging.server.security.jwt.provider

import com.relogging.server.domain.user.entity.Role
import com.relogging.server.global.exception.GlobalErrorCode
import com.relogging.server.global.exception.GlobalException
import com.relogging.server.security.service.PrincipalDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class TokenProvider(
    @Value("\${jwt.secret}")
    private val secretKey: String,
    @Value("\${jwt.validity-in-seconds}")
    val accessExpirationTime: Long,
    @Value("\${jwt.validity-in-seconds-refresh}")
    val refreshExpirationTime: Long,
    private val principalDetailsService: PrincipalDetailsService,
) {
    private val key: Key by lazy {
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secretKey))
    }

    fun createAccessToken(
        userId: Long,
        role: Role,
    ): String {
        return this.createToken(userId, role, this.refreshExpirationTime)
    }

    fun createRefreshToken(
        userId: Long,
        role: Role,
    ): String {
        return this.createToken(userId, role, this.accessExpirationTime)
    }

    private fun createToken(
        userId: Long,
        role: Role,
        expirationTime: Long,
    ): String {
        val claims: Claims = Jwts.claims()
        claims["sub"] = userId
        claims["role"] = role

        val now = Date()
        val expirationDate = Date(now.time + expirationTime)

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(this.key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val token: String = request.getHeader("Authorization") ?: return null
        if (!token.startsWith("Bearer ")) {
            return null
        }
        return token.substring(7)
    }

    fun validateToken(token: String): Boolean {
        try {
            return Jwts.parserBuilder().setSigningKey(this.key).build()
                .parseClaimsJws(token).body.expiration.after(Date())
        } catch (e: SignatureException) {
            throw GlobalException(GlobalErrorCode.JWT_INVALID_TOKEN)
        } catch (e: MalformedJwtException) {
            throw GlobalException(GlobalErrorCode.JWT_MALFORMED_TOKEN)
        } catch (e: ExpiredJwtException) {
            throw GlobalException(GlobalErrorCode.JWT_EXPIRED_TOKEN)
        } catch (e: UnsupportedJwtException) {
            throw GlobalException(GlobalErrorCode.JWT_UNSUPPORTED_TOKEN)
        } catch (e: IllegalArgumentException) {
            throw GlobalException(GlobalErrorCode.INTERNAL_SERVER_ERROR)
        }
    }

    fun getUserId(token: String): String {
        return Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token)
            .body.subject
    }

    fun getAuthentication(token: String): Authentication {
        val userId: String = this.getUserId(token)
        val userDetails: UserDetails = this.principalDetailsService.loadUser(userId)
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }
}
