package ca.coglinc.plugins.test

import org.gradle.api.Task

import javax.annotation.Nonnull

/**
 * Provides utility methods for verifying a {@link Task} instance.
 */
class TaskVerification {

    @Nonnull
    private final Task task

    TaskVerification(@Nonnull Task task) {
        this.task = task
    }

    /**
     * Determines if the task is in the given {@code group}.
     */
    boolean existsInGroup(@Nonnull String group) {
        return task.group == group
    }

    /**
     * Determines if the task depends on {@code dependencyName}.
     */
    boolean dependsOn(@Nonnull String dependencyName) {
        Set<Task> dependencies = task.taskDependencies.getDependencies(task)
        return dependencies.find {
            (it instanceof Task && it.name == dependencyName) || (it instanceof String && it == dependencyName)
        }
    }
}
