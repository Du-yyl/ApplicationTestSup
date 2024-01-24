import com.google.protobuf.gradle.proto
import java.text.SimpleDateFormat
import java.time.LocalDateTime

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // HILT
    id("com.google.dagger.hilt.android") version "2.44"
    kotlin("kapt")

    // 启用 proto 插件
    id("com.google.protobuf") version ("0.9.4")

    // ksp
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply true
//    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.applicationtest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.applicationtest"
        minSdk = 23
        // 目标版本
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
//    启用 DataBinding
    dataBinding {
        enable = true
    }
//    启用 ViewBinding
    viewBinding {
        enable = true
    }

//    指定 proto 配置路径
    sourceSets {
        named("main") {
            proto {
                srcDir("src/main/proto")
            }
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // =============================================================================================
    //                          viewModels
    // 使用示例：
    //  val viewModel by viewModels<MainViewModule>()
    // =============================================================================================
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    // =============================================================================================
    //  HILT
    // =============================================================================================
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")

    // ===================Splitties===============================================================
    implementation("com.louiscad.splitties:splitties-fun-pack-android-base:3.0.0")

    // ===================JSON GSON 转换=============================================================
    implementation("com.google.code.gson:gson:2.10")

    // ===================lottie 动画库=====================================================================
    implementation("com.airbnb.android:lottie-compose:6.3.0")

    // ===================提示组件===================================================================
    implementation("com.github.GrenderG:Toasty:1.5.2")

    // ===================流式布局===================================================================
    implementation("com.nex3z:flow-layout:1.3.3")


}

dependencies {
    val lifecycleVersion = "2.7.0"

    // 添加以下依赖用于支持Android Architecture Components和Data Binding
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion")
    implementation("androidx.databinding:databinding-runtime:8.1.0")
}

// dataStore
dependencies {
    val datastoreVersion = "1.0.0"

    // =====Preferences DataStore  通过键值对存储数据，不保证类型安全======================
    implementation("androidx.datastore:datastore-preferences:$datastoreVersion")
    implementation("androidx.datastore:datastore-preferences-core:$datastoreVersion")

    // ======Typed DataStore 通过Protocol-Buffers定义存储数据类型以及结构，保证类型安全========
    implementation("androidx.datastore:datastore:$datastoreVersion")
    implementation("androidx.datastore:datastore-core:$datastoreVersion")
    // 定义 proto 数据
    implementation("com.google.protobuf:protobuf-javalite:3.21.6")

}

// Room Database
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.21")

//    本地数据解析
    implementation(files("libs\\SQLiteStudioRemote.jar"))

    val roomVersion = "2.6.1"

    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$roomVersion")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")

    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$roomVersion")
}

// Allow references to generated code【允许生成代码】(HILT 使用)
kapt {
    correctErrorTypes = true
}

//3. 配置协议缓冲区
protobuf {
    // 设置 protoc 的版本
    protoc {
        artifact = "com.google.protobuf:protoc:3.10.0"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}


