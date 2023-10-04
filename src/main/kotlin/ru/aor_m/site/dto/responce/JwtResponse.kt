package ru.aor_m.site.dto.responce

import ru.aor_m.site.enums.RoleEnum
import java.util.*

data class JwtResponse (

    val token: String,
    val type: String,
    val id: UUID,
    val userName: String,
    val email: String,
    val roles: RoleEnum
    
)