import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.0"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm")
	kotlin("plugin.spring") version "1.4.10"
}

group = "pl.zycienakodach"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven("https://repo.spring.io/milestone")
	maven("https://repo.spring.io/snapshot")
	maven("https://dl.bintray.com/arrow-kt/arrow-kt/")
	maven("https://oss.jfrog.org/artifactory/oss-snapshot-local/")
}

val arrowVersion = "0.11.0"
val timeTravelerVersion = "0.1.15"

dependencies {
	implementation(project(":domain"))
	//implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")


	implementation("org.springframework.fu:spring-fu-kofu:0.5.0-SNAPSHOT")


	implementation("pl.zycienakodach:kt-time-traveler-spring-boot-starter:$timeTravelerVersion")
	implementation("pl.zycienakodach:kt-time-traveler-core:$timeTravelerVersion")
	testImplementation("pl.zycienakodach:kt-time-traveler-test:$timeTravelerVersion")

	implementation("io.arrow-kt:arrow-fx:$arrowVersion")
	implementation("io.arrow-kt:arrow-optics:$arrowVersion")
	implementation("io.arrow-kt:arrow-syntax:$arrowVersion")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	//runtimeOnly("io.r2dbc:r2dbc-postgresql")
	//runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.test {
	useJUnitPlatform()
	testLogging {
		events("passed", "skipped", "failed")
	}
}
