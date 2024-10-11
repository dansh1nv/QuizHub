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

rootProject.name = "QuizHub"
include(":app")
include(":database")
include(":quizapi")
include(":features:quiz-list")
include(":features:quiz-details")
include(":features:profile")
include(":data-quiz-list")
include(":common")
include(":data-profile")
include(":data-quiz-details")
include(":domain-quiz-list")
include(":domain-quiz-details")
include(":domain-profile")
include(":designSystem")
include(":buildConfig")
include(":navigation")
