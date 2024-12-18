plugins {
	java
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
	id("org.barfuin.gradle.jacocolog") version "3.1.0" // for labs
}

group = "com.sergosoft"
version = "0.0.2"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

// Minimum coverage for labs
extra["minimumCoveragePerFile"] = 0.8
extra["filesExcludedFromCoverage"] = listOf(
	"**/*MarketApplication.*",
	"**/config/*Configuration.*"
)

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

// for labs
apply(from = "${rootProject.projectDir}/gradle/test.gradle")
apply(from = "${rootProject.projectDir}/gradle/jacoco.gradle")

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.projectlombok:lombok-mapstruct-binding:0.2.0")
	implementation("org.mapstruct:mapstruct:1.6.2")

	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")

	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.6.2")

	testAnnotationProcessor("org.mapstruct:mapstruct-processor:1.6.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// WireMock for testing with Jetty 11 support
	testImplementation ("org.wiremock:wiremock-jetty12:3.9.2")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
