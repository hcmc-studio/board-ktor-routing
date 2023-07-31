plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
    id("io.ktor.plugin") version "2.3.2"
}

group = "studio.hcmc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":kotlin-protocol-extension"))
    implementation(project(":exposed-transaction-extension"))
    implementation(project(":ktor-routing-extension"))

    implementation(project(":data-domain"))
    implementation(project(":data-transfer-object"))
    implementation(project(":data-value-object"))
    implementation(project(":exposed-entity"))
    implementation(project(":ktor-service"))

    implementation("io.ktor:ktor-server-core-jvm:2.3.2")
    implementation("io.ktor:ktor-server-resources:2.3.2")
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")
}

kotlin {
    jvmToolchain(17)
}