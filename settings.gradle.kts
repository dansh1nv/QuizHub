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

include(
    ":app",
    ":core",
    ":navigation",
    ":database",
    ":quizapi",
    ":common",
    ":designSystem",

    ":data-quiz-list",
    ":data-quiz-details",
    ":data-profile",

    ":domain-quiz-list",
    ":domain-quiz-details",
    ":domain-profile",

    ":features:quiz-list",
    ":features:quiz-details",
    ":features:profile",
)
