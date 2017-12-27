package ca.coglinc.plugins.jenkins.jobdsl.validator

import ca.coglinc.plugins.configuration.ProjectConfigurer
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class JenkinsDependencyConfigurerTest extends Specification {
    private ProjectConfigurer configurer
    private Project project

    void setup() {
        configurer = new JenkinsDependencyConfigurer()
        project = ProjectBuilder.builder().build()
    }

    void 'Adds jenkins configuration to project'() {
        when:
        configurer.configure(project)

        then:
        project.configurations.getByName('jenkins') != null
    }

    void 'Jenkins configuration is not visible and transitive'() {
        when:
        configurer.configure(project)

        Configuration jenkinsConfiguration = project.configurations.getByName('jenkins')

        then:
        !jenkinsConfiguration.visible
        jenkinsConfiguration.transitive
    }
}
