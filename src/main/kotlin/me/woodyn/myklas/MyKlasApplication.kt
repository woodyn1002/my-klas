package me.woodyn.myklas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class MyKlasApplication

fun main(args: Array<String>) {
    runApplication<MyKlasApplication>(*args)
}
