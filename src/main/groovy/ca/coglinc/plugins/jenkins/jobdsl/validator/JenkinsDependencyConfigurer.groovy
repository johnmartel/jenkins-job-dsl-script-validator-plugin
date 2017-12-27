package ca.coglinc.plugins.jenkins.jobdsl.validator

import ca.coglinc.plugins.configuration.ProjectConfigurer
import groovy.transform.PackageScope
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.DependencySet

import javax.annotation.Nonnull

@PackageScope
class JenkinsDependencyConfigurer implements ProjectConfigurer {
    private static final String JENKINS_TEST_HARNESS_VERSION = '2.8'
    private static final String JENKINS_WAR_VERSION = '2.89.2'
    private static final String JOB_DSL_PLUGIN_VERSION = '1.66'
    private static final String STRUCTS_PLUGIN_VERSION = '1.6'

    @Override
    void configure(@Nonnull Project project) {
        Configuration configuration = createJenkinsConfiguration(project)
        configureDefaultJenkinsDependency(project, configuration)
    }

    private Configuration createJenkinsConfiguration(Project project) {
        Configuration jenkinsConfiguration = project.configurations.create('jenkins')
        jenkinsConfiguration.visible = false
        jenkinsConfiguration.transitive = true
        jenkinsConfiguration.description = 'The Jenkins dependencies for this project'

        return jenkinsConfiguration
    }

    private void configureDefaultJenkinsDependency(Project project, Configuration configuration) {
        configuration.defaultDependencies(new Action<DependencySet>() {
            @Override
            void execute(DependencySet dependencies) {
                dependencies.with {
                    add(
                        project.dependencies.create(
                            "org.jenkins-ci.main:jenkins-test-harness:${JENKINS_TEST_HARNESS_VERSION}"
                        )
                    )
                    add(
                        project.dependencies.create("org.jenkins-ci.main:jenkins-war:${JENKINS_WAR_VERSION}")
                    )
                    add(
                        project.dependencies.create(
                            "org.jenkins-ci.main:jenkins-war:${JENKINS_WAR_VERSION}:war-for-test@jar"
                        )
                    )
                    add(
                        project.dependencies.create("org.jenkins-ci.plugins:job-dsl:${JOB_DSL_PLUGIN_VERSION}")
                    )
                    add(
                        project.dependencies.create("org.jenkins-ci.plugins:job-dsl:${JOB_DSL_PLUGIN_VERSION}@jar")
                    )
                    add(
                        project.dependencies.create("org.jenkins-ci.plugins:structs:${STRUCTS_PLUGIN_VERSION}@jar")
                    )
                }
            }
        })
    }
}
