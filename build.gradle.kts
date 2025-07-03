plugins {
    java
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "io.github.jongminchung"
version = "0.0.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}


val querydsl = "7.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly("com.mysql:mysql-connector-j")

    compileOnly("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    // QueryDSL
    implementation("io.github.openfeign.querydsl:querydsl-core:${querydsl}")
    implementation("io.github.openfeign.querydsl:querydsl-jpa:$querydsl")
    annotationProcessor("io.github.openfeign.querydsl:querydsl-apt:$querydsl:jpa")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}


sourceSets {
    main {
        java {
            srcDirs("src/main/java", "${layout.buildDirectory}/generated")
        }
    }
}
