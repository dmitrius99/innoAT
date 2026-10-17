import org.gradle.api.tasks.testing.Test

plugins {
    `java-library`
    id("io.qameta.allure")
}

dependencies {
    api(project(":common"))
    api("io.rest-assured:rest-assured:5.5.6")
    api("org.assertj:assertj-core:3.27.7")
    api("io.qameta.allure:allure-rest-assured:2.29.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.qameta.allure:allure-junit5:2.29.1")
}

allure {
    version.set("2.29.0")
    adapter {
        autoconfigure.set(true)
        autoconfigureListeners.set(true)
        frameworks {
            junit5 { adapterVersion.set("2.29.1") }
        }
    }
}

tasks.register<Test>("smokeApi") {
    group = "verification"
    description = "Запускает API-тесты с тегом Smoke"
    useJUnitPlatform { includeTags("Smoke") }
}
