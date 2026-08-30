import org.gradle.api.tasks.testing.Test

plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // Source: https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
    implementation("com.fasterxml.jackson.core:jackson-core:2.22.1")
    implementation("io.rest-assured:rest-assured:5.5.6")
    testImplementation("org.assertj:assertj-core:3.27.7")
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

tasks.register("runAllTests") {
    dependsOn(tasks.test)
    finalizedBy("testRunIsOver")
}

tasks.register("testRunIsOver") {
    doLast {
        println("Test run is over")
    }
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


