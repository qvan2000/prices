package ru.aor_m.site.entity

import jakarta.persistence.*
import ru.aor_m.site.enums.RoleEnum
import java.util.UUID


@Entity
@Table(name = "account")
class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    var id: UUID? = null

    @Column(name = "user_name")
    var userName: String? = null

    @Column(name = "email")
    var email: String? = null

    @Column(name = "password")
    var password: String? = null

    @Column(name = "role")
    var role: RoleEnum? = null
}