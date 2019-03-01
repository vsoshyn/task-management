package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs

object Build : BuildType({
    name = "Build"

    vcs {
        root(HttpsGithubComVsoshynTaskManagementRefsHeadsMaster)
    }
    steps {
        gradle {
            tasks = "clean build"
            buildFile = ""
        }
    }
    triggers {
        vcs {
        }
    }
})
