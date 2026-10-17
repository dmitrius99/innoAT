import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test

plugins {
    id("io.qameta.allure") version "2.12.0" apply false
}

group = "ru.learninno"
version = "1.0-SNAPSHOT"

allprojects {
    repositories { mavenCentral() }
}

subprojects {
    apply(plugin = "java")

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
        testLogging {
            events("passed", "failed", "skipped")
            showStandardStreams = true
        }
    }
}

tasks.register("smokeApi") {
    group = "verification"
    description = "Запускает Smoke-прогон API-автотестов"
    dependsOn(":api-tests:smokeApi")
}
tasks.register("smokeUi") {
    group = "verification"
    description = "Запускает Smoke-прогон UI-автотестов"
    dependsOn(":ui-tests:smokeUi")
}
tasks.register("smoke") {
    group = "verification"
    description = "Запускает общий Smoke-прогон API- и UI-автотестов"
    dependsOn("smokeApi", "smokeUi")
}


