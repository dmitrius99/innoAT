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
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "failed", "skipped")
        showStandardStreams = true
    }
}

//Задача запускает все тесты в проекте.
tasks.register("runAllTests") {
    dependsOn(tasks.test)
    finalizedBy("testRunIsOver")
}

//После прогона вывод записи
tasks.register("testRunIsOver") {
    doLast {
        println("Test run is over")
    }
}
