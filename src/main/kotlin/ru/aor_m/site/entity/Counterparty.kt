package ru.aor_m.site.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity(name = "counterparty")
class Counterparty {

    @Id
    var id: UUID? = null

    @Column(name = "name")
    var name: String = ""

}