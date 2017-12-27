# jenkins-job-dsl-script-validator

![Build status](https://travis-ci.org/johnmartel/jenkins-job-dsl-script-validator-plugin.svg?branch=master)

A Gradle plugin that allows validating your Jenkins Job DSL scripts in a project.

## Building

Simply run `./gradlew clean check`

## Releasing

To release a new version, simply run `./gradlew -Prelease=true tag`

To bump the version number (this project uses [semantic versioning](http://semver.org)), simply add the following
to a commit message, on a single line, with no leading whitespace:

- `[bump major]`
- `[bump minor]`
- `[bump patch]`

This will autobump the version number. For more, see https://github.com/vivin/gradle-semantic-build-versioning#automatic-bumping-based-on-commit-messages.

## Using

### Installing

Simply apply the plugin: using the [plugins DSL](https://docs.gradle.org/current/userguide/plugins.html#sec:plugins_block):

```groovy
plugins {
    id 'ca.coglinc.jenkins.jobdsl.validator' version '1.0.0'
}
```
  
### Task

This plugin adds a new task to the project: `validateJenkinsJobDsl`, which is a subclass of
[SourceTask](https://docs.gradle.org/current/javadoc/org/gradle/api/tasks/SourceTask.html) and can be configured as follows:

```groovy
validateJenkinsJobDsl {
    source 'jenkins_job.groovy'
}
```

To run the task, simply the usual `./gradlew validateJenkinsJobDsl` and you are set.

### Configuration

This plugin adds a new configuration named `jenkins` to the project, which can be used to define the dependencies to the required jenkins tooling.
The default dependencies are:

```groovy
dependencies {
    // Jenkins test harness dependencies
    jenkins 'org.jenkins-ci.main:jenkins-test-harness:2.8'
    jenkins 'org.jenkins-ci.main:jenkins-war:2.89.2'
    jenkins "org.jenkins-ci.main:jenkins-war:2.89.2:war-for-test@jar"
    
    // Job DSL plugin including plugin dependencies
    jenkins "org.jenkins-ci.plugins:job-dsl:1.66"
    jenkins "org.jenkins-ci.plugins:job-dsl:1.66@jar"
    jenkins 'org.jenkins-ci.plugins:structs:1.6@jar'
}
```

## References

The following were used to develop this plugin, many thanks to their authors and the community behind them:

- [Jenkins Job DSL Plugin article on testing DSL scripts](https://github.com/jenkinsci/job-dsl-plugin/wiki/Testing-DSL-Scripts)
- [Jenkins Job DSL Plugin article on IDE support](https://github.com/jenkinsci/job-dsl-plugin/wiki/IDE-Support)
- [The Jenkins Test Harness](https://github.com/jenkinsci/jenkins-test-harness)
- [The Jenkins Job DSL plugin API](https://jenkinsci.github.io/job-dsl-plugin/)
