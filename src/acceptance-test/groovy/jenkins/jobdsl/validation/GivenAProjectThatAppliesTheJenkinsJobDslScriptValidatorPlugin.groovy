package jenkins.jobdsl.validation

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import spock.lang.Unroll

class GivenAProjectThatAppliesTheJenkinsJobDslScriptValidatorPlugin extends Specification {

    @Rule
    final TemporaryFolder projectDir = new TemporaryFolder()

    @Unroll
    void 'Can apply plugin to project with Gradle version #gradleVersion'() {
        given:
        File buildFile = projectDir.newFile('build.gradle')
        buildFile << """
            plugins {
                id 'ca.coglinc.jenkins.jobdsl.validator'
            }

            validateJenkinsJobDsl {
                source 'jenkins_job.groovy'
            }
        """

        projectDir.newFile('jenkins_job.groovy')

        when:
        BuildResult result = GradleRunner.create()
            .withProjectDir(projectDir.root)
            .withGradleVersion(gradleVersion)
            .forwardOutput()
            .withPluginClasspath()
            .withArguments('validateJenkinsJobDsl')
            .build()

        then:
        result.task(':validateJenkinsJobDsl').outcome == TaskOutcome.SUCCESS

        where:
        gradleVersion << ['4.0', '4.1', '4.2', '4.3', '4.4', '4.4.1']
    }
}
