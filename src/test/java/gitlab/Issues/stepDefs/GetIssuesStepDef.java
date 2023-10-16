package gitlab.Issues.stepDefs;

import com.google.gson.reflect.TypeToken;
import gitlab.Issues.pojo.Error;
import gitlab.Issues.pojo.Issue;
import gitlab.Issues.requests.CallService;
import gitlab.Issues.utils.BaseTest;
import gitlab.Issues.utils.ContextStore;
import gitlab.Issues.utils.LogUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;

public class GetIssuesStepDef extends BaseTest {
    CallService callService = new CallService(requestSpecification);
    CallService callServiceWithInvalidToken = new CallService(requestSpecificationWithInvalidToken);

    @Given("I request the list of all issues")
    public void getAllIssues() {
        LogUtils.logInfo("Requesting issues from the project...");

        // sends a GET request to the specified endpoint to retrieve a list of issues
        Response response = callService.executeGetRequest(ISSUES_ENDPOINT, false, false);

        // stores the response for further use in the test
        ContextStore.put("response", response);

        LogUtils.logInfo("Get issues from project successfully completed!");
    }

    @Then("I should see the issue types are {} and author id's are not null")
    public void checkIssueTypesAndAuthorIds(String issueType) {
        // retrieves the stored response from the context store, allowing you to access the previously stored response data for validation or further use in the test
        Response response = ContextStore.get("response");

        // deserializes the response data into a list of Issue objects using the TypeToken and makes it accessible as the issues variable
        List<Issue> issues = response.as(new TypeToken<List<Issue>>() {}.getType());

        //  iterates through each Issue in the issues list and asserts that the issueType matches the type of each issue
        for (Issue issue : issues) {
            Assert.assertEquals("Issue type does not match!", issueType, issue.getType());
            Assert.assertNotNull("Author id is null!", issue.getAuthor().getId());
        }

        LogUtils.logInfo("Issue types and author id's are successfully verified!");
    }

    @When("I request issues specific to the project")
    public void getProjectIssues() {
        LogUtils.logInfo("Requesting issues specific to the project...");

        // sends a GET request to the specific project's issues endpoint to retrieve a list of issues
        Response response = callService.executeGetRequest(PROJECTS_ENDPOINT + PROJECT_ID + ISSUES_ENDPOINT, false, false);

        // stores the response for further use in the test
        ContextStore.put("response", response);

        LogUtils.logInfo("Retrieving issues specific to the project successfully completed!");
    }

    @Given("I request issues specific to the group")
    public void getGroupIssues() {
        LogUtils.logInfo("Requesting issues specific to the group...");

        // sends a GET request to the specific group's issues endpoint to retrieve a list of issues
        Response response = callService.executeGetRequest(GROUPS_ENDPOINT + GROUP_ID + ISSUES_ENDPOINT, false, false);

        // stores the response for further use in the test
        ContextStore.put("response", response);

        LogUtils.logInfo("Retrieving issues specific to the group successfully completed!");
    }

    @When("I send a get request to retrieve issues from the {} id {}")
    public void getInvalidIssues(String entity, String id) {
        // Set the endpoint based on the entity, default to projects
        String endpoint = PROJECTS_ENDPOINT;

        if (entity.equals("project")) {
            LogUtils.logInfo("Requesting issues specific to the project...");
            endpoint = PROJECTS_ENDPOINT;
        } else if (entity.equals("group")) {
            LogUtils.logInfo("Requesting issues specific to the group...");
            endpoint = GROUPS_ENDPOINT;
        }

        // Send a GET request to the specified entity's issues endpoint to retrieve a list of issues
        Response response = callService.executeGetRequest(endpoint + id + ISSUES_ENDPOINT, false, true);

        // Store the response for further use in the test
        ContextStore.put("response", response);

        LogUtils.logInfo("Retrieving issues from an invalid " + entity + " successfully sent!");
    }


    @When("I request the list of all issues without valid token")
    public void getIssuesWithInvalidToken() {
        LogUtils.logInfo("Requesting issues without token from the project...");

        // sends a GET request with an invalid token to the specified endpoint to retrieve a list of issues and prints response body
        Response response = callServiceWithInvalidToken.executeGetRequest(ISSUES_ENDPOINT, false, true);

        // stores the response for further use in the test
        ContextStore.put("response", response);

        LogUtils.logInfo("Get issues from project successfully completed!");
    }

    @Then("Response body {} should be {}")
    public void responseBodyMessageShouldBe(String field, String value) {
        // retrieves the stored response from the context store
        Response response = ContextStore.get("response");

        // deserializes the response data into an Error object
        Error errorModel = response.as(Error.class);

        // asserts the value of the specified field in the Error object
        if (field.equals("message"))
            Assert.assertEquals(value, errorModel.getMessage());
        else if (field.equalsIgnoreCase("error"))
            Assert.assertEquals(value, errorModel.getError());

        LogUtils.logInfo("Response body is: " + response.getBody().asString());

        LogUtils.logInfo("Response body message is successfully verified!");
    }

    @Then("I should see the opened and closed issues")
    public void iShouldSeeTheOpenedAndClosedIssues() {
        // retrieves the stored response from the context store
        Response response = ContextStore.get("response");

        // deserializes the response data into a list of Issue objects
        List<Issue> issues = response.as(new TypeToken<List<Issue>>() {}.getType());

        int openIssuesCount = 0;
        int closedIssuesCount = 0;

        // iterates through each Issue in the issues list, checks their state, and increments the appropriate count
        for (Issue issue : issues) {
            if (issue.getState().equalsIgnoreCase("opened")) openIssuesCount++;
            else if (issue.getState().equalsIgnoreCase("closed")) closedIssuesCount++;

            Assert.assertEquals("ISSUE", issue.getType());
        }

        LogUtils.logInfo("There are " + issues.size() + " issues in the project!");
        LogUtils.logInfo("Open Issues: " + openIssuesCount);
        LogUtils.logInfo("Closed Issues: " + closedIssuesCount);

        LogUtils.logInfo("Issues are successfully verified!");
    }

}
