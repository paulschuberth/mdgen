package de.pschuberth.mdgen

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileOutputStream
import kotlin.test.assertEquals

class ReadmeTest : MdTestCase() {

    @Test
    fun `README is up to date`() {
        // Arrange
        val readmeFile = File("README.md")

        // Act
        generateReadmeContent(md)

        // Assert
        val actualReadmeContent = fileContent(readmeFile)
        assertEquals(fileContent(), actualReadmeContent, "README.md is not up to date!")
    }

    @Disabled("Run this to update the README.md")
    @Test
    fun `Create README`() {
        val readme = File("README.md")
        val readmeOutput = FileOutputStream(readme)
        val readmeMd = Md(readmeOutput)
        generateReadmeContent(readmeMd)
    }

    private fun generateReadmeContent(md: Md) {
        md.start {
            section {
                +"About"
                paragraph("This is a tiny Kotlin DSL for creating Markdown files.")

                section {
                    +"Features"
                    paragraph("The DSL currently supports")
                    list {
                        -"Headings"
                        -"Lists"
                        -"Code Blocks"
                    }

                    section {
                        +"Headings"
                        paragraph("Headings can be created like by using the `section()` method")
                        code("kotlin") {
                            """
                            import de.pschuberth.mdgen.Md

                            val md = Md(FileOutputStream(File("README.md")))

                            md.start {
                                section {
                                    +"This is a heading with one #"
                                }
                            }
                            """.trimIndent()
                        }
                        paragraph(
                            "Nesting `section()` methods automatically increases the number of # used for the heading\n" +
                                "The following results in a # Heading and a ## Sub-Heading"
                        )

                        code("kotlin") {
                            """
                            import de.pschuberth.mdgen.Md

                            val md = Md(FileOutputStream(File("README.md")))

                            md.start {
                                section {
                                    +"Heading"
                                    section {
                                        +"Sub-Heading"
                                    }
                                }
                            }
                            """.trimIndent()
                        }

                        paragraph("Headings will be put on the same level if their `section()` methods are called after one-another.")
                        code("kotlin") {
                            """
                            import de.pschuberth.mdgen.Md

                            val md = Md(FileOutputStream(File("README.md")))

                            md.start {
                                section {
                                    +"Heading"
                                }
                                section {
                                    +"Another Heading"
                                }
                            }
                            """.trimIndent()
                        }
                    }
                    section {
                        +"Lists"
                        paragraph(
                            "Lists can be created using the `list()` method. By default ordered lists start at\n" +
                                "index 1 and their elements are added with the unary plus operator.\n" +
                                "Unordered list elements are added using the unaryMinus operator and use the asterisk (*)."
                        )
                        code("kotlin") {
                            """
                           import de.pschuberth.mdgen.Md

                           val md = Md(FileOutputStream(File("README.md")))

                           md.start {
                               list {
                                    +"First element"
                                    +"Second element"
                                    +"Third element"
                               }
                               list {
                                   -"Foo"
                                   -"Bar"
                                   -"Baz"
                               }
                           }
                            """.trimIndent()
                        }
                        paragraph("Running the code above results in the following Markdown.")
                        list {
                            +"First element"
                            +"Second element"
                            +"Third element"
                        }
                        list {
                            -"Foo"
                            -"Bar"
                            -"Baz"
                        }
                    }

                    section {
                        +"Code Blocks"
                        paragraph(
                            "At the moment this DSL only supports code fenced code blocks using three\n" +
                                "backticks. A optional parameter can be used to specify the language of the code block."
                        )
                        code("kotlin") {
                            """
                            println("Hello World")
                            """.trimIndent()
                        }
                        paragraph("The listing above is created by a this code.")
                        code("kotlin") {
                            """
                            code("kotlin") {
                                ""${'"'}
                                println("Hello World")
                                ""${'"'}.trimIndent()
                            }
                            """.trimIndent()
                        }
                        paragraph("Other languages can be specfied by passing them in the first parameter.")
                        code("rust") {
                            """
                            fn main() {
                                println!("Hello World!");
                            }
                            """.trimIndent()
                        }
                        paragraph("The listing above is created by a this code.")
                        code("kotlin") {
                            """
                            code("rust") {
                                ""${'"'}
                                fn main() {
                                    println!("Hello World!");
                                }
                                ""${'"'}.trimIndent()
                            }
                            """.trimIndent()
                        }
                    }
                    paragraph(
                        "This README.md is generated by the DSL as well. Take a look at\n" +
                            "[ReadmeTest.kt](https://github.com/paulschuberth/mdgen/blob/main/src/test/kotlin/de/pschuberth/mdgen/ReadmeTest.kt) for details."
                    )
                }
            }
        }
    }
}
