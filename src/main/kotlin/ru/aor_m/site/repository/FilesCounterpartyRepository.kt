package ru.aor_m.site.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.aor_m.site.entity.FilesCounterparty
import java.util.*

interface FilesCounterpartyRepository: JpaRepository<FilesCounterparty, UUID> {

    fun findByName(name: String): Optional<FilesCounterparty>

}