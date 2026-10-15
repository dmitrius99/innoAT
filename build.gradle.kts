import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.compile.JavaCompile
import java.util.Properties

plugins {
    id("java")
    id("io.qameta.allure") version "2.12.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.27.0")
    testImplementation("com.codeborne:selenide:7.17.0")
    // Source: https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
    implementation("com.fasterxml.jackson.core:jackson-core:2.22.1")
    implementation("io.rest-assured:rest-assured:5.5.6")
    testImplementation("org.assertj:assertj-core:3.27.7")
    testImplementation("io.qameta.allure:allure-junit5:2.29.1")
    testImplementation("io.qameta.allure:allure-selenide:2.29.1")
    testImplementation("io.qameta.allure:allure-rest-assured:2.29.1")
}

allure {
    version.set("2.29.0")
    adapter {
        autoconfigure.set(true)
        autoconfigureListeners.set(true)
        frameworks {
            junit5 {
                adapterVersion.set("2.29.1")
            }
        }
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform {
        includeTags("homework")
    }
    testLogging {
        events("passed", "failed", "skipped")
        showStandardStreams = true
    }
}

tasks.withType<Test>().configureEach {
    doFirst {
        val config = Properties()
        file("src/test/resources/test.properties").inputStream().use { input -> config.load(input) }

        println("Конфигурация запуска тестов:")
        println("  URL стенда: ${config.getProperty("stand.url")}")
        println("  URL API: ${config.getProperty("api.url")}")
        println("  Тайм-аут поиска элементов: ${config.getProperty("element.timeout.ms")} мс")
        println("  Режим логирования: ${config.getProperty("logging.mode")}")
        println("  Имя стартового товара: ${config.getProperty("starter.product.name")}")
        println("  Цена стартового товара: ${config.getProperty("starter.product.price")}")
    }
}

tasks.register("runAllTests") {
    dependsOn(tasks.test)
    finalizedBy("testRunIsOver")
}

tasks.register("testRunIsOver") {
    doLast {
        println("Test run is over")
    }
}

tasks.register<Zip>("archiveAllureReport") {
    group = "verification"
    description = "Собирает ZIP-архив Allure-отчёта после UI- и API-тестов"
    dependsOn("allureReport")
    from(layout.buildDirectory.dir("reports/allure-report/allureReport"))
    archiveFileName.set("allure-report-ui-api.zip")
    destinationDirectory.set(layout.buildDirectory.dir("reports"))
}

tasks.register<Test>("runErrorTests") {
    description = "Запускает только тесты из AssertionsTest"
    useJUnitPlatform()

    filter {
        includeTestsMatching("AssertionsTest")
    }

    finalizedBy("testRunIsOver")
}

tasks.register<Test>("API") {
    description = "Запускает API-тесты"
    useJUnitPlatform {
        includeTags("ApiTests")
    }
    testLogging {
        events("passed", "failed", "skipped")
        showStandardStreams = true
    }
}

tasks.register<Test>("testApiTask1") {
    description = "Запускает API-тесты"
    useJUnitPlatform {
        includeTags("ApiTestsTask1")
    }
    testLogging {
        events("passed", "failed", "skipped")
        showStandardStreams = true
    }
}

tasks.register<Test>("allureTests") {
    description = "Запускает UI- и API-сценарии, включённые в Allure-отчёт"
    useJUnitPlatform {
        includeTags("AllureTests")
    }
    testLogging {
        events("passed", "failed", "skipped")
        showStandardStreams = true
    }
}


