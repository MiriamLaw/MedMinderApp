const fs = require("fs-extra")
const path = require("path")

// Define paths
const sourceBuildDir = path.resolve(__dirname, "../build")
const targetDir = path.resolve(__dirname, "../../backend/src/main/resources/static")

// Ensure the target directory exists
fs.ensureDirSync(targetDir)

// Copy the build files to the Spring Boot static resources directory
fs.copySync(sourceBuildDir, targetDir, { overwrite: true })

console.log("Successfully copied React build files to Spring Boot static resources!")
