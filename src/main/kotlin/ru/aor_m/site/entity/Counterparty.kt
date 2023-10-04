package ru.aor_m.site.entity

import jakarta.persistence.*
import java.util.*

@Entity(name = "counterparty")
class Counterparty {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null

    @Column(name = "name")
    var name: String = ""

}