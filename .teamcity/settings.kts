import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildFeatures.merge
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.exec
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2018_2.projectFeatures.dockerRegistry
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.finishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2018.2"

project {

    buildType(Release)
    buildType(Deploy)
    buildType(Build)

    features {
        dockerRegistry {
            id = "PROJECT_EXT_2"
            name = "Docker Registry"
            url = "http://docker:5000"
        }
        feature {
            id = "PROJECT_EXT_9"
            type = "IssueTracker"
            param("secure:password", "")
            param("name", "vsoshyn/task-management")
            param("pattern", """#(\d+)""")
            param("authType", "anonymous")
            param("repository", "https://github.com/vsoshyn/task-management")
            param("type", "GithubIssues")
            param("secure:accessToken", "")
            param("username", "")
        }
    }
}

object Build : BuildType({
    name = "Build"

    vcs {
        root(AbsoluteId("Root_HttpsGithubComVsoshynTaskManagementRefsHeadsDevelop"))
    }

    steps {
        gradle {
            tasks = "clean update test -Penv=uat"
            buildFile = ""
            gradleParams = "-Dorg.gradle.daemon=true -Dorg.gradle.parallel=true"
        }
    }

    triggers {
        vcs {
        }
    }
})

object Deploy : BuildType({
    name = "Deploy"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    vcs {
        root(DslContext.settingsRoot)

        cleanCheckout = true
        showDependenciesChanges = true
    }

    triggers {
        finishBuildTrigger {
            buildType = "${Release.id}"
            successfulOnly = true
            branchFilter = "+:tags"
        }
    }
})

object Release : BuildType({
    name = "Release"

    params {
        checkbox("env.RELEASE_TYPE", "", label = "Major release:", description = "If yes then the version will be X.0", display = ParameterDisplay.PROMPT,
                  checked = "major", unchecked = "minor")
        text("env.NEXT_DEVELOP_VERSION", "%build.vcs.number.1%", display = ParameterDisplay.HIDDEN, allowEmpty = true)
        text("env.RELEASE_VERSION", "%build.vcs.number.1%", display = ParameterDisplay.HIDDEN, allowEmpty = true)
    }

    vcs {
        root(AbsoluteId("Root_HttpsGithubComVsoshynTaskManagementRefsHeadsDevelop"))
    }

    steps {
        gradle {
            name = "Build artifact"
            tasks = "clean bootDistTar -Penv=prod -x test"
            buildFile = ""
            gradleParams = "-Dorg.gradle.daemon=true -Dorg.gradle.parallel=true"
        }
        exec {
            name = "Prepare release version"
            path = "release/set-prod-version.sh"
            arguments = "%env.RELEASE_TYPE%"
        }
        dockerCommand {
            name = "Build docker image"
            commandType = build {
                source = path {
                    path = "backend/Dockerfile"
                }
                namesAndTags = "localhost:5000/task-manager:%env.RELEASE_VERSION%"
                commandArgs = "--pull -q"
            }
        }
        exec {
            name = "Prepare next develop version"
            path = "release/set-next-dev-version.sh"
        }
        dockerCommand {
            name = "Publish docker image"
            commandType = push {
                namesAndTags = "localhost:5000/task-manager:%env.RELEASE_VERSION%"
            }
            param("dockerfile.path", "backend/Dockerfile")
        }
        script {
            name = "Publish to Git"
            scriptContent = """
                git push --all git@github.com:vsoshyn/task-management.git
                git push --tags git@github.com:vsoshyn/task-management.git
                
                git checkout master
                git merge develop
                git push git@github.com:vsoshyn/task-management.git
            """.trimIndent()
        }
    }

    features {
        merge {
            enabled = false
            branchFilter = "+:develop"
            destinationBranch = "master"
            commitMessage = "[Auto merge of release %env.RELEASE_VERSION%]"
        }
    }
})
