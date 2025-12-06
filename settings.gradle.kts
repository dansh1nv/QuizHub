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
    ":database",
    ":quizapi",
    ":common",
    ":designSystem",

    ":data:quizList",
    ":data:quizDetails",
    ":data:profile",

    ":domain:quizList",
    ":domain:quizDetails",
    ":domain:profile",

    ":features:quizList",
    ":features:quizDetails",
    ":features:profile",
    ":features:settings"
)
