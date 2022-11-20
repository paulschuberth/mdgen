import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    `java-library`
    `maven-publish`
}

group = "de.pschuberth"
// x-release-please-start-version
version = "0.2.0"
// x-release-please-end

java {
    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "mdgen"
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("mdgen")
                description.set("A Kotlin DSL to generate basic Markdown files")
                url.set("https://github.com/paulschuberth/mdgen")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("paulschuberth")
                        name.set("Paul Schuberth")
                        email.set("github@pschuberth.de")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/paulschuberth/mdgen")
                    url.set("https://github.com/paulschuberth/mdgen")
                }
            }
        }
    }
    repositories {
        maven {
            name = "GitHub"
            url = uri("https://maven.pkg.github.com/paulschuberth/mdgen")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}
