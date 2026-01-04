val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version = "0.41.1" // или актуальная версия
val h2_version = "2.2.224"
val koin_version = "4.1.1"
plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.serialization") version "2.2.21" // ОБЯЗАТЕЛЬНО для JSON
    id("io.ktor.plugin") version "3.3.2"
}

group = "danis.galimullin"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}
repositories {
    mavenCentral()
}

dependencies {

    implementation(platform("io.insert-koin:koin-bom:$koin_version"))
    // Koin for Ktor
    implementation("io.insert-koin:koin-ktor")
    // SLF4J Logger
    implementation("io.insert-koin:koin-logger-slf4j")


    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")

    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")

    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")

    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-core:3.3.2")
    implementation("io.ktor:ktor-server-host-common:3.3.2")
    implementation("io.ktor:ktor-server-status-pages:3.3.2")
    implementation("io.ktor:ktor-server-core:3.3.2")


    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")


    implementation("org.postgresql:postgresql:42.7.7")
    implementation("org.jetbrains.exposed:exposed-core:${exposed_version}")
    implementation("org.jetbrains.exposed:exposed-dao:${exposed_version}")
    implementation("org.jetbrains.exposed:exposed-jdbc:${exposed_version}")

    implementation("com.h2database:h2:${h2_version}")

    implementation("io.ktor:ktor-server-auth")
    implementation("io.ktor:ktor-server-auth-jwt")
    implementation("org.mindrot:jbcrypt:0.4")

}