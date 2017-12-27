package ca.coglinc.plugins.jenkins.jobdsl.validator

import ca.coglinc.plugins.configuration.ProjectConfigurer
import ca.coglinc.plugins.tasks.ValidateJenkinsJobDslTaskConfigurer
import ca.coglinc.plugins.test.TaskVerification
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class ValidateJenkinsJobDslTaskConfigurerTest extends Specification {
    private Project project
    private ProjectConfigurer configurer

    void setup() {
        project = ProjectBuilder.builder().build()
        configurer = new ValidateJenkinsJobDslTaskConfigurer()
    }

    void 'Adds validateJenkinsJobDsl task to the project'() {
        when:
        this.configurer.configure(this.project)

        then:
        TaskVerification taskVerification = new TaskVerification(this.project.tasks.validateJenkinsJobDsl)
        taskVerification.existsInGroup('Verification')
    }

    void 'Adds a dependency on validateJenkinsJobDsl to check'() {
        given:
        project.tasks.create('check')

        when:
        configurer.configure(project)

        then:
        TaskVerification taskVerification = new TaskVerification(project.tasks.check)
        taskVerification.dependsOn('validateJenkinsJobDsl')
    }
}
