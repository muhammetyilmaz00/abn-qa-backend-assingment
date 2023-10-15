package gitLabIssues.stepDefs;

import gitLabIssues.pojo.Issue;
import gitLabIssues.requests.CallService;
import gitLabIssues.utils.BaseTest;
import gitLabIssues.utils.ContextStore;
import gitLabIssues.utils.IssueUtils;
import gitLabIssues.utils.LogUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class CreateIssuesStepDef extends BaseTest {

    CallService callService = new CallService(requestSpecification);

    @When("I create a new issue in the project with the following parameters")
    public void createANewIssueWithParameters(DataTable dataTable) {
        LogUtils.logInfo("Creating a new issue in the project with the parameters...");

        // converts the DataTable to a Map
        Map<String, Object> keyValueMap = dataTable.asMap(String.class, Object.class);

        // stores the keyValueMap for further use in the test
        ContextStore.put("keyValueMap", keyValueMap);

        // sends a POST request to the specific project's issues endpoint to create a new issue
        Response response = callService.executePostRequest(PROJECTS_ENDPOINT + PROJECT_ID + ISSUES_ENDPOINT, keyValueMap, false, false);

        // stores the response for further use in the test
        ContextStore.put("response", response);

        // retrieves the newly created issue
        Issue issue = response.as(Issue.class);

        // stores the issueId for further use in the test
        ContextStore.put("issueId", issue.getIid());

        LogUtils.logInfo("Creating a new issue in the project successfully completed!");
    }

    @Then("Response status code should be {}")
    public void responseStatusShouldBe(int statusCode) {
        // retrieves the stored response from the context store
        Response response = ContextStore.get("response");

        LogUtils.logInfo("Response status code is: " + response.getStatusCode());

        // asserts the status code
        Assert.assertEquals(statusCode, response.getStatusCode());

        LogUtils.logInfo("Response status code is successfully verified!");
    }

    @And("Response content type should be {}")
    public void responseStatusShouldBe(String contentType) {
        // retrieves the stored response from the context store
        Response response = ContextStore.get("response");

        LogUtils.logInfo("Response content type is: " + response.contentType());

        // asserts the status code
        Assert.assertEquals(contentType, response.contentType());

        LogUtils.logInfo("Response content type is successfully verified!");
    }

    @Then("I verify the response for the expected parameters")
    public void iVerifyTheResponseForTheExpectedParameters() {
        LogUtils.logInfo("Verifying the response for the expected parameters...");

        // retrieves the stored keyValueMap from the context store
        Map<String, Object> keyValueMap = ContextStore.get("keyValueMap");

        // retrieves the stored response from the context store
        Response response = ContextStore.get("response");

        // deserializes the response data into an Issue object
        Issue issue = response.as(Issue.class);

        // iterates through each key-value pair in the keyValueMap
        for (Map.Entry<String, Object> entry : keyValueMap.entrySet()) {
            String key = entry.getKey();
            Object expectedValue = entry.getValue();

            // retrieves the value from the Issue object using the key
            Object actualValue = IssueUtils.retrieveValueFromIssue(issue, key);

            // asserts the value
            Assert.assertEquals("Assertion for key: " + key, expectedValue, actualValue);
        }

        LogUtils.logInfo("Response parameters are successfully verified!");
    }

    @And("I create a new issue in the project with title {}")
    public void createANewIssueWithTitleAndLabel(String title) {
        // initializes a map which is "title" to the issue
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("title", title);

        LogUtils.logInfo("Creating a new issue in the project...");

        // sends a POST request to the specific project's issues endpoint to create a new issue
        Response response = callService.executePostRequest(PROJECTS_ENDPOINT + PROJECT_ID + ISSUES_ENDPOINT, queryParams, false, false);

        // stores the response for further use in the test
        ContextStore.put("response", response);

        // retrieves the newly created issue
        Issue issue = response.as(Issue.class);

        // stores the issueId for further use in the test
        ContextStore.put("issueId", issue.getIid());

        LogUtils.logInfo("Creating a new issue in the project successfully completed!");
    }

    @And("I verify the response for title {} and state {}")
    public void iVerifyTheResponseForTitleAndLabel(String title, String state) {
        LogUtils.logInfo("Verifying the response for title " + title + " and state " + state + "...");
        // retrieves the stored response from the context store
        Response response = ContextStore.get("response");

        // deserializes the response data into an Issue object
        Issue issue = response.as(Issue.class);

        // asserts the title
        Assert.assertEquals(title, issue.getTitle());

        // asserts the state
        Assert.assertEquals(state, issue.getState());

        LogUtils.logInfo("Response parameters are successfully verified!");
    }
}
