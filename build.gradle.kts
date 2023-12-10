import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Property

plugins {
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
	id("nu.studer.jooq") version "8.2.1"
	id("org.flywaydb.flyway") version "9.22.3"
	kotlin("jvm") version "1.9.20"
	kotlin("plugin.spring") version "1.9.20"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	jooqGenerator("org.postgresql:postgresql:42.7.0")
	implementation("org.flywaydb:flyway-core:9.22.3")
	implementation("org.postgresql:postgresql:42.7.0")
	implementation("io.jsonwebtoken:jjwt-api:0.12.3")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web:3.2.0")
	implementation("org.springframework.boot:spring-boot-starter-jooq:3.2.0")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")
	implementation("javax.xml.bind:jaxb-api:2.3.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

flyway {
	url = "jdbc:postgresql://localhost:5432/test"
	user = "postgres"
	password = "postgres"
	schemas = arrayOf("public")
}

jooq {
	version.set("3.18.7")

	configurations {
		create("main") {
			generateSchemaSourceOnCompilation.set(true)

			jooqConfiguration.apply {
				logging = Logging.WARN
				jdbc.apply {
					driver = "org.postgresql.Driver"
					url = "jdbc:postgresql://localhost:5432/test"
					user = "postgres"
					password = "postgres"
				}
				generator.apply {
					name = "org.jooq.codegen.DefaultGenerator"
					database.apply {
						name = "org.jooq.meta.postgres.PostgresDatabase"
						inputSchema = "public"
						forcedTypes.addAll(listOf(
								ForcedType().apply {
									name = "varchar"
									includeExpression = ".*"
									includeTypes = "JSONB?"
								},
								ForcedType().apply {
									name = "varchar"
									includeExpression = ".*"
									includeTypes = "INET"
								}
						))
					}

					strategy.name = "org.jooq.codegen.example.JPrefixGeneratorStrategy"

					target.apply {
						packageName = "test.db.example"
						directory = "build/generated-src/jooq/main"  // default (can be omitted)
					}
				}
			}
		}
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
