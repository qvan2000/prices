package ru.aor_m.site.service

import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ru.aor_m.site.dto.UserDetailsImpl
import ru.aor_m.site.repository.AccountRepository

@Service
class UserDetailsServiceImpl(): UserDetailsService {

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Transactional
    override fun loadUserByUsername(userName: String?): UserDetails {
        val accountOptional = accountRepository.getByUserName(userName!!)
        if (accountOptional.isPresent) {
            val account = accountOptional.get()
            return UserDetailsImpl(account)
        } else
            throw EntityNotFoundException("Account not found!")
    }

}