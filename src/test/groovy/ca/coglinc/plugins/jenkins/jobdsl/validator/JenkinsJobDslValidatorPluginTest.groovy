package ca.coglinc.plugins.jenkins.jobdsl.validator

import ca.coglinc.plugins.tasks.ValidateJenkinsJobDslTaskConfigurer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class JenkinsJobDslValidatorPluginTest extends Specification {
    private Plugin<Project> plugin
    private JenkinsDependencyConfigurer jenkinsDependencyConfigurer
    private ValidateJenkinsJobDslTaskConfigurer taskConfigurer
    private Project project

    void setup() {
        jenkinsDependencyConfigurer = Spy(JenkinsDependencyConfigurer)
        taskConfigurer = Spy(ValidateJenkinsJobDslTaskConfigurer)

        plugin = new JenkinsJobDslValidatorPlugin(jenkinsDependencyConfigurer, taskConfigurer)

        project = ProjectBuilder.builder().build()
    }

    void 'Configures jenkins dependency'() {
        when:
        plugin.apply(project)

        then:
        1 * jenkinsDependencyConfigurer.configure(project)
    }

    void 'Configures jenkins verification task'() {
        when:
        plugin.apply(project)

        then:
        1 * taskConfigurer.configure(project)
    }
}
