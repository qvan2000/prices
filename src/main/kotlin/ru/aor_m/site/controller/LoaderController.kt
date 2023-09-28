package ru.aor_m.site.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import ru.aor_m.site.service.LoaderService

@RestController
class LoaderController {

    @Autowired
    private lateinit var loaderService: LoaderService

    @GetMapping("/loadnewfile")
    fun loadNewFile() {
        loaderService.loadFromRegardToDB()
    }
}