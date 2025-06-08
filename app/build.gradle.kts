plugins {
    id("application")

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

    // junit
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

application  {
    mainClass = "hexlet.code.App"
}