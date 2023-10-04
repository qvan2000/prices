package ru.aor_m.site.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.aor_m.site.config.JwtUtils
import ru.aor_m.site.dto.UserDetailsImpl
import ru.aor_m.site.dto.request.LoginRequest
import ru.aor_m.site.dto.request.SignupRequest
import ru.aor_m.site.dto.responce.JwtResponse
import ru.aor_m.site.dto.responce.MessageResponse
import ru.aor_m.site.entity.Account
import ru.aor_m.site.enums.RoleEnum
import ru.aor_m.site.repository.AccountRepository

@RestController
@RequestMapping("/api/auth")
class AuthController {
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Autowired
    private lateinit var encoder: PasswordEncoder

    @Autowired
    private lateinit var jwtUtils: JwtUtils

    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<*> {
        val authentication = authenticationManager
            .authenticate(UsernamePasswordAuthenticationToken(loginRequest.userName, loginRequest.password))
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)
        val userDetails = authentication.principal as UserDetailsImpl
        val roles = RoleEnum.valueOf(authentication.authorities.first().authority)
        return ResponseEntity
            .ok<Any>(JwtResponse(
                jwt,
                "Bearer",
                userDetails.getId(),
                userDetails.username,
                userDetails.getEmail(),
                roles))
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody signUpRequest: SignupRequest): ResponseEntity<*> {
        if (accountRepository.existsByUserName(signUpRequest.username)) {
            return ResponseEntity.badRequest().body<Any>(MessageResponse("Error: Username is already taken!"))
        }
        if (accountRepository.existsByEmail(signUpRequest.email)) {
            return ResponseEntity.badRequest().body<Any>(MessageResponse("Error: Email is already in use!"))
        }
        val account = Account().apply {
            this.userName = signUpRequest.username
            this.email = signUpRequest.email
            this.password = encoder.encode(signUpRequest.password)
        }
        account.role = RoleEnum.ROLE_USER
        accountRepository.save(account)
        return ResponseEntity.ok<Any>(MessageResponse("User registered successfully!"))
    }
}