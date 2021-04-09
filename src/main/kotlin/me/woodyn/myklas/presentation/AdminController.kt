package me.woodyn.myklas.presentation

import me.woodyn.myklas.service.AdminService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AdminController(
    private val adminService: AdminService
) {

    @PostMapping("/admin/clear")
    fun clear() =
        adminService.clear()

}