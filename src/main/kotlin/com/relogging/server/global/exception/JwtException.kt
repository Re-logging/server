package com.relogging.server.global.exception

class JwtException(val jwtErrorCode: JwtErrorCode) : RuntimeException()
