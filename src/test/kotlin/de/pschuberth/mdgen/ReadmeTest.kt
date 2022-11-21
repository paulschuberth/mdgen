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

    @Test
    @Disabled("Run this to update the README.md")
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
                        -"Sub-Headings"
                        -"Lists"
                        -"Code Blocks"
                    }
                }
            }
        }
    }
}
