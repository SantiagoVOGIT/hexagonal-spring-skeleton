plugins {
    java
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    id("com.diffplug.spotless") version "6.25.0"
    id("com.github.ben-manes.versions") version "0.42.0"
}

group = "com.company.project"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {
    sourceSets {
        main {
            java.srcDir("main")
            resources.srcDir("main/resources")
        }
        test {
            java.srcDir("test")
            resources.srcDir("test/resources")
        }
    }

    tasks.bootJar {
        enabled = false
    }

    tasks.jar {
        enabled = true
    }
}

sourceSets {
    main {
        java.srcDirs("app/main")
        resources.srcDir("app/main/resources")
    }
    test {
        java.srcDirs("app/test")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.bootJar {
    mainClass.set("com.company.project.MainApplication")
}

spotless {
    java {
        googleJavaFormat()
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
    }
}