package ca.coglinc.plugins.jenkins.jobdsl.validator

import ca.coglinc.plugins.tasks.ValidateJenkinsJobDslTaskConfigurer
import groovy.transform.PackageScope
import org.gradle.api.Plugin
import org.gradle.api.Project

import javax.annotation.Nonnull
import javax.inject.Inject

class JenkinsJobDslValidatorPlugin implements Plugin<Project> {

    @Nonnull
    private final JenkinsDependencyConfigurer jenkinsDependencyConfigurer

    @Nonnull
    private final ValidateJenkinsJobDslTaskConfigurer validateJobDslTaskConfigurer

    @Inject
    @SuppressWarnings('GroovyUnusedDeclaration')
    JenkinsJobDslValidatorPlugin() {
        this(new JenkinsDependencyConfigurer(), new ValidateJenkinsJobDslTaskConfigurer())
    }

    @PackageScope
    JenkinsJobDslValidatorPlugin(@Nonnull JenkinsDependencyConfigurer jenkinsDependencyConfigurer,
                                 @Nonnull ValidateJenkinsJobDslTaskConfigurer validateJobDslTaskConfigurer) {
        this.jenkinsDependencyConfigurer = jenkinsDependencyConfigurer
        this.validateJobDslTaskConfigurer = validateJobDslTaskConfigurer
    }

    @Override
    void apply(Project project) {
        jenkinsDependencyConfigurer.configure(project)
        validateJobDslTaskConfigurer.configure(project)
    }
}
