import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    antlr
}

group = "me.qaz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(files(
        // Get the jar at https://github.com/QazCetelic/Neatlin/releases
        "$projectDir/../../Kotlin/Neatlin/build/libs/Neatlin-jvm-0.3.3.jar",
        // Get the jar at https://github.com/QazCetelic/Kwing/releases
        "$projectDir/../../JSwing/Kwing/build/libs/Kwing-0.1.0.jar"
    ))
    antlr("org.antlr:antlr4:4.9.3")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.generateGrammarSource {
    // Allows importing of grammar
    arguments.addAll(listOf("-lib", "src/main/antlr/"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}