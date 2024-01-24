pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

buildscript {
    repositories {
        // 阿里云云效仓库：https://maven.aliyun.com/mvn/guide
        maven(url = "https://maven.aliyun.com/repository/public")
        maven(url = "https://maven.aliyun.com/repository/google")
        // 华为开源镜像：https://mirrors.huaweicloud.com/
        maven(url = "https://repo.huaweicloud.com/repository/maven/")
        // JitPack 远程仓库：https://jitpack.io
        maven(url = "https://jitpack.io")
    }
}


dependencyResolutionManagement {
    repositories {
        // JitPack 远程仓库：https://jitpack.io
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "ApplicationTest"
include(":app")
