package ca.coglinc.plugins.configuration

import org.gradle.api.Project

import javax.annotation.Nonnull

/**
 * This abstract class provides an interface for helping plugins to configure a {@link Project}.
 *
 * Configuration can consist in actions such as adding configurations, defining extensions, creating tasks, setting task
 * dependencies, etc.
 */
interface ProjectConfigurer {

    /**
     * Configures the project.
     *
     * @param project The project to be configured by this configurer
     */
    void configure(@Nonnull Project project)
}
