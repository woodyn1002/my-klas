package me.woodyn.myklas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableJpaRepositories
@EnableAsync
class MyKlasApplication

fun main(args: Array<String>) {
    runApplication<MyKlasApplication>(*args)
}
