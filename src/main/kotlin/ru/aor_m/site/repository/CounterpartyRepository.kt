package ru.aor_m.site.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.aor_m.site.entity.Counterparty
import java.util.*

interface CounterpartyRepository: JpaRepository<Counterparty, UUID> {

    fun getByName(name: String): Optional<Counterparty>

}