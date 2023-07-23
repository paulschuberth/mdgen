import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    `java-library`
    `maven-publish`
    id("org.jetbrains.kotlinx.kover") version "0.7.2"
    id("org.jetbrains.dokka") version "1.8.20"
    id("ca.cutterslade.analyze") version "1.9.1"
}

group = "de.pschuberth"
// x-release-please-start-version
version = "0.2.18"
// x-release-please-end

java {
    withJavadocJar()
    withSourcesJar()

    targetCompatibility = JavaVersion.VERSION_1_8
}

koverReport {
    defaults {
        xml {
            onCheck = true
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
    implementation("org.jetbrains:annotations:24.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

publishing {
    repositories {
        maven {
            name = "GitHub"
            url = uri("https://maven.pkg.github.com/paulschuberth/mdgen")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("mdgen") {
            from(components["java"])
        }
    }
}
tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}
