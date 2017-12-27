package ca.coglinc.plugins.tasks

import groovy.transform.PackageScope
import org.gradle.api.tasks.SourceTask
import org.gradle.api.tasks.TaskAction
import org.junit.runner.Result
import spock.util.EmbeddedSpecRunner

@PackageScope
class ValidateJenkinsJobDslTask extends SourceTask {

    @TaskAction
    void run() {
        StringBuilder allTests = new StringBuilder()
        source.forEach { File file ->
            allTests.append("""
                void 'Validate Jenkins Job DSL script ${file.absolutePath}'() {
                    given:
                    JenkinsJobManagement jobManagement = new JenkinsJobManagement(System.out, [:], new File('.'))
            
                    when:
                    new DslScriptLoader(jobManagement).runScript(${file.text})
            
                    then:
                    noExceptionThrown()
                }
                
            """)
        }

        EmbeddedSpecRunner runner = new EmbeddedSpecRunner(throwFailure: false)
        String validationTestSource = """
            import javaposse.jobdsl.dsl.DslScriptLoader
            import javaposse.jobdsl.plugin.JenkinsJobManagement
            import org.junit.ClassRule
            import org.jvnet.hudson.test.JenkinsRule
            import spock.lang.Shared
            import spock.lang.Specification
            
            class JobScriptsSpec extends Specification {
                @Shared
                @ClassRule
                JenkinsRule jenkinsRule = new JenkinsRule()
            
                ${allTests.toString()}
            }
        """
        Result result = runner.runWithImports(validationTestSource)
        result.properties.each { k, v -> println "$k: $v" }
    }
}
