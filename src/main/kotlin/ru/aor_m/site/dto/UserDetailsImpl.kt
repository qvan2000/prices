package ru.aor_m.site.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.aor_m.site.entity.Account
import java.util.*

class UserDetailsImpl(get: Account) : UserDetails {

    var account: Account = get
    
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = mutableListOf<GrantedAuthority>()
        authorities.add(SimpleGrantedAuthority(account.role!!.name))
        return authorities
    }

    fun getId(): UUID {
        return account.id!!
    }

    fun getEmail(): String {
        return account.email!!
    }

    override fun getPassword(): String {
        return account.password!!
    }

    override fun getUsername(): String {
        return account.userName!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}