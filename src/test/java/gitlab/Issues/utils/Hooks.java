package gitlab.Issues.utils;

import gitlab.Issues.stepDefs.DeleteIssuesStepDef;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.core.config.Configurator;

import static io.restassured.RestAssured.reset;

public class Hooks {
    private static Scenario scenario;

    public static String getScenarioName() {
        return scenario.getName();
    }

    /**
     * It is executed before each test case.
     * It initializes the test environment by calling the setUp() method from the BaseTest class.
     * It also sets the current scenario, configures the logging properties, and logs the start of the scenario
     */
    @Before
    public static void init(Scenario scenario) {
        BaseTest.setUp();
        Hooks.scenario = scenario;

        // Load Log4j configuration file
        Configurator.initialize(null, "src/test/resources/config/log4j2.xml");

        LogUtils.logInfo("Started Scenario: " + getScenarioName());
    }

    /**
     * It resets the RestAssured values and logs an info message with the name of the scenario
     */
    @After
    public static void tearDown() {
        reset();
        LogUtils.logInfo("Finished Scenario: " + getScenarioName());
    }

    // Delete the created issue if @DeleteIssue annotation is present
    @After(value = "@DeleteIssue")
    public static void deleteIssue() {
        DeleteIssuesStepDef deleteIssuesStepDef = new DeleteIssuesStepDef();
        deleteIssuesStepDef.deleteTheCreatedIssue();
    }
}
