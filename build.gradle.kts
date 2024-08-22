import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.20"
    `java-library`
    `maven-publish`
    id("org.jetbrains.kotlinx.kover") version "0.8.3"
    id("org.jetbrains.dokka") version "1.9.20"
    id("ca.cutterslade.analyze") version "1.10.0"
}

group = "de.pschuberth"
// x-release-please-start-version
version = "0.2.23"
// x-release-please-end

java {
    withJavadocJar()
    withSourcesJar()

    targetCompatibility = JavaVersion.VERSION_1_8
}

kover {
    reports {
        total {
            xml {
                onCheck = true
            }
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.20")
    implementation("org.jetbrains:annotations:24.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0")
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
