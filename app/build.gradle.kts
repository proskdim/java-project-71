plugins {
    id("application")
    id("io.freefair.lombok") version "8.13.1"
    id("org.sonarqube") version "6.2.0.5505"

    jacoco
    checkstyle
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //picocli
    implementation("info.picocli:picocli:4.7.7")

    // jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.19.0")

    // junit
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport { reports { xml.required.set(true) } }

sonar {
    properties {
        property("sonar.projectKey", "proskdim_java-project-71")
        property("sonar.organization", "proskdim")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

application  {
    mainClass = "hexlet.code.App"
}