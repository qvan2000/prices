package ru.aor_m.site.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.aor_m.site.entity.Account
import java.util.*

interface AccountRepository: JpaRepository<Account, UUID> {

    fun getByUserName(userName: String): Optional<Account>

    fun existsByUserName(userName: String): Boolean

    fun existsByEmail(email: String): Boolean
}