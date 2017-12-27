package ca.coglinc.plugins.tasks

import ca.coglinc.plugins.configuration.ProjectConfigurer
import org.gradle.api.Project
import org.gradle.api.Task

import javax.annotation.Nonnull

class ValidateJenkinsJobDslTaskConfigurer implements ProjectConfigurer {

    @Override
    void configure(@Nonnull Project project) {
        Task validateJenkinsJobDsl = createValidateJenkinsJobDslTask(project)
        setCheckDependsOnValidateJenkinsJobDsl(project, validateJenkinsJobDsl)
    }

    private Task createValidateJenkinsJobDslTask(Project project) {
        Task task = project.tasks.create(name: 'validateJenkinsJobDsl', type: ValidateJenkinsJobDslTask) {
            group = 'Verification'
            description = 'Validates Jenkins DSL scripts in this project.'
        }

        return task
    }

    private void setCheckDependsOnValidateJenkinsJobDsl(Project project, Task validateJenkinsJobDslTask) {
        if (project.tasks.findByName('check') != null) {
            project.tasks.check.dependsOn validateJenkinsJobDslTask
        }
    }
}
