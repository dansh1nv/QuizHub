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
    ":data:settings",

    ":domain:quizList",
    ":domain:quizDetails",
    ":domain:profile",
    ":domain:settings",

    ":features:quizList",
    ":features:quizDetails",
    ":features:profile",
    ":features:settings"
)
