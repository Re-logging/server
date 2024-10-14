package com.relogging.server.entity

import jakarta.persistence.*

@Entity
@Table(name = "TEST")
class Test(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") val id: Long
)
