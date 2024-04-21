
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val postgresql_driver_version: String by project
val exposed_version: String by project

plugins {
    kotlin("jvm") version "1.9.23"
    id("io.ktor.plugin") version "2.3.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
}

group = "com.example"
version = "0.0.1"

ktor{
    fatJar {
        archiveFileName.set("onerep.jar")
    }

    docker{
        jreVersion.set(JavaVersion.VERSION_17)
        localImageName.set("onerep-docker-image")
        imageTag.set("0.0.1-preview")
    }
}

tasks.shadowJar {
    // Inclui o arquivo .env na raiz do JAR
    from(".") {
        include(".env")
    }
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")


    implementation("org.postgresql:postgresql:$postgresql_driver_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")

    implementation("at.favre.lib:bcrypt:0.10.2")

    implementation(kotlin("stdlib"))
    implementation("io.github.cdimascio:dotenv-kotlin:6.2.2")
}
