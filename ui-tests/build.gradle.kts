import org.gradle.api.tasks.testing.Test

plugins { id("io.qameta.allure") }

dependencies {
    testImplementation(project(":common"))
    testImplementation(project(":api-tests"))
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.codeborne:selenide:7.17.0")
    testImplementation("org.assertj:assertj-core:3.27.7")
    testImplementation("io.qameta.allure:allure-junit5:2.29.1")
    testImplementation("io.qameta.allure:allure-selenide:2.29.1")
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

tasks.register<Test>("smokeUi") {
    group = "verification"
    description = "Запускает UI-тесты с тегом Smoke"
    useJUnitPlatform { includeTags("Smoke") }
}
