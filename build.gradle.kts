plugins {
	id("com.android.library")
	id("kotlin-android")
	id("org.jetbrains.dokka") version "1.9.10"
	id("maven-publish")
}

android {
	namespace = "dev.frozenmilk.dairy.curdled"
	compileSdk = 29

	defaultConfig {
		minSdk = 24
		//noinspection ExpiredTargetSdkVersion
		targetSdk = 28

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		consumerProguardFiles("consumer-rules.pro")
	}

	compileSdk = 29

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8

		kotlin {
			compilerOptions {
				freeCompilerArgs.add("-Xjvm-default=all")
			}
		}
	}
}

repositories {
	maven("https://repo.dairy.foundation/releases")
}

dependencies {
	//noinspection GradleDependency
	implementation("androidx.appcompat:appcompat:1.2.0")
	testImplementation(testFixtures(project(":Core")))

	compileOnly(project(":Core"))
	compileOnly("dev.frozenmilk.dairy:CachingHardware:1.0.0")
	compileOnly("org.firstinspires.ftc:RobotCore:10.0.0")
	compileOnly("org.firstinspires.ftc:Hardware:10.0.0")
	compileOnly("org.firstinspires.ftc:FtcCommon:10.0.0")
}

publishing {
	publications {
		register<MavenPublication>("release") {
			groupId = "dev.frozenmilk.dairy"
			artifactId = "Curdled"
			version = "0.0.0"

			afterEvaluate {
				from(components["release"])
			}
		}
	}
	repositories {
		maven {
			name = "Calcified"
			url = uri("${project.buildDir}/release")
		}
	}
}
