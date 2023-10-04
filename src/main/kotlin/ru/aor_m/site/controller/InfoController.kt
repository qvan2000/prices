package ru.aor_m.site.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import ru.aor_m.site.dto.responce.MessageResponse
import ru.aor_m.site.repository.AccountRepository
import java.util.UUID

@RestController
class InfoController {

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @GetMapping("/getuser/{id}")
    fun getUser(@PathVariable id: UUID): ResponseEntity<*> {
        return  ResponseEntity.ok<Any>(MessageResponse(accountRepository.getReferenceById(id).userName!!))
    }
}