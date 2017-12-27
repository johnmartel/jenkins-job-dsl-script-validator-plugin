package ca.coglinc.plugins.jenkins.jobdsl.validator

import ca.coglinc.plugins.configuration.ProjectConfigurer
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.internal.artifacts.dsl.DefaultRepositoryHandler
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

        then:
        Configuration jenkinsConfiguration = project.configurations.getByName('jenkins')
        !jenkinsConfiguration.visible
        jenkinsConfiguration.transitive
    }

    void 'Adds the Jenkins repository'() {
        when:
        configurer.configure(project)

        then:
        project.repositories.findByName('jenkins') != null
    }

    void 'Adds an alternate artifact URL to the jcenter repository'() {
        when:
        configurer.configure(project)

        then:
        MavenArtifactRepository jcenterRepository = (MavenArtifactRepository) project
            .repositories.findByName(DefaultRepositoryHandler.DEFAULT_BINTRAY_JCENTER_REPO_NAME)
        jcenterRepository != null
        jcenterRepository.artifactUrls.find { URI uri ->
            'https://repo.jenkins-ci.org/public/'.equalsIgnoreCase(uri.toString())
        } != null
    }
}
