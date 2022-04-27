import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    id("io.gitlab.arturbosch.detekt") version "1.18.0"
    id("org.jmailen.kotlinter") version "3.6.0"
}

group = "com.sia"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

allprojects {
    repositories {
        mavenCentral()

        maven("https://repo.osgeo.org/repository/release/")
        //maven("http://download.osgeo.org/webdav/geotools/")
        maven("https://plugins.gradle.org/m2/")
        google()
        mavenCentral()
        maven {
            url = uri("https://pkgs.dev.azure.com/si-analytics/_packaging/backend-core/maven/v1")
            name = "backend-core"
            credentials {
                username = "si-analytics"
                password = "kzvlhdsgjmiznfchsrr3odsgrxhudacbauxnyyarnu45wjrynjta"
            }
        }
    }

    apply(plugin = "idea")
    apply(plugin = "kotlin")
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jmailen.kotlinter")
    apply(plugin = "io.spring.dependency-management")

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        implementation("si-analytics:inference-core:0.1.2")

        testImplementation("io.kotest:kotest-assertions-core:5.1.0")
        testImplementation("io.kotest:kotest-runner-junit5-jvm:5.1.0")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.0")


        testImplementation("io.mockk:mockk:1.12.3")
    }

    tasks {
        formatKotlinMain {
            exclude(listOf("**/**GrpcKt.kt"))
        }

        lintKotlinMain {
            exclude(listOf("**/**GrpcKt.kt"))
        }
        compileKotlin {
            dependsOn(formatKotlin)
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "11"
            }
        }
        compileTestKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "11"
            }
        }
        test {
            useJUnitPlatform()
        }
    }

    kotlinter {
        ignoreFailures = false
        indentSize = 4
        reporters = arrayOf("checkstyle", "plain")
        experimentalRules = false
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }


}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.springframework.boot:spring-boot-starter:2.6.4")
}