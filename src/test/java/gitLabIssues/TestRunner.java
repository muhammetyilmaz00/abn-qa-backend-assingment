package gitLabIssues;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "html:target/cucumber-report.html",
                "json:target/cucumber.json"
        },
        features = {"src/test/resources/features"},
        glue = {"gitLabIssues/stepDefs", "gitLabIssues.utils"},
        publish = true,
        tags = "@GitLabIssues"
)

/**
 * This configuration ensures that Cucumber runs the specified features, applies the specified tags, and associates step definitions and utility classes using the defined glue packages.
 * Test reports will be generated in HTML and JSON formats
 */
public class TestRunner {
}