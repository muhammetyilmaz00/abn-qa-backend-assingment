package gitLabIssues.stepDefs;

import com.google.gson.reflect.TypeToken;
import gitLabIssues.pojo.Issue;
import gitLabIssues.requests.CallService;
import gitLabIssues.utils.BaseTest;
import gitLabIssues.utils.ContextStore;
import gitLabIssues.utils.LogUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateIssuesStepDef extends BaseTest {

    CallService callService = new CallService(requestSpecification);

    public Response updateIssue(Integer issueId, Map<String, Object> queryParams, boolean printReq, boolean printResBody) {
        // sends a PUT request to the specific project's issue endpoint to update the issue and returns the response
        return callService.executePutRequest(PROJECTS_ENDPOINT + PROJECT_ID + ISSUES_ENDPOINT + issueId, queryParams, printReq, printResBody);
    }

    @Then("I update the created issue with description {}")
    public void updateTheCreatedIssue(String description) {
        // initializes a map which is "description" to the issue
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("description", description);

        LogUtils.logInfo("Updating the issueId " + ContextStore.get("issueId") + "...");

        // calls updateIssue method and stores the response
        Response response = updateIssue(ContextStore.get("issueId"), queryParams, false, false);

        // stores the response for further use in the test
        ContextStore.put("response", response);

        LogUtils.logInfo("Updating the issueId " + ContextStore.get("issueId") + " successfully completed!");
    }

    @When("I update the issue id {}")
    public void updateTheIssueId(Integer issueId, DataTable dataTable) {
        LogUtils.logInfo("Updating the issueId " + issueId + "...");

        // converts the DataTable to a Map
        Map<String, Object> queryParams = dataTable.asMap(String.class, Object.class);

        // calls updateIssue method and stores the response
        Response response = updateIssue(issueId, queryParams, false, true);

        // stores the response for further use in the test
        ContextStore.put("response", response);

        LogUtils.logInfo("Updating the issueId " + issueId + " request successfully sent!");
    }

    @When("I send an update request with {}")
    public void sendUpdateRequestWithInvalidId(String issueId) {
        LogUtils.logInfo("Updating the issueId " + issueId + "...");

        // initializes a map which is "description" to the issue
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("description", "new description");

        // sends a PUT request with invalid id to the specific project's issue endpoint to update the issue and returns the response
        Response response = callService.executePutRequest(PROJECTS_ENDPOINT + PROJECT_ID + ISSUES_ENDPOINT + issueId, queryParams, false, true);

        // stores the response for further use in the test
        ContextStore.put("response", response);

        LogUtils.logInfo("Updating the issueId " + issueId + " request successfully sent!");
    }


    @And("I verify the issue description has been changed to {}")
    public void iVerifyTheIssueDescriptionHasBeenChanged(String description) {
        // retrieves the stored response from the context store, allowing you to access the previously stored response data for validation or further use in the test
        Response response = ContextStore.get("response");

        // deserializes the response data into an Issue object
        Issue issue = response.as(Issue.class);

        // asserts the description of the issue
        Assert.assertEquals(description, issue.getDescription());

        LogUtils.logInfo("The description of the issueId " + ContextStore.get("issueId") + " successfully verified!");
    }

    @When("I change the state of the issue to {}")
    public void iChangeTheStateOfTheIssueTo(String state) {
        LogUtils.logInfo("Changing the state of the issue to " + state + "...");

        // initializes a map which is "state_event" to the issue
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("state_event", state);

        LogUtils.logInfo("Updating the issueId " + ContextStore.get("issueId") + "...");

        // calls updateIssue method and stores the response
        Response response = updateIssue(ContextStore.get("issueId"), queryParams, false, false);

        // stores the response for further use in the test
        ContextStore.put("response", response);

        LogUtils.logInfo("Changing the state of the issueId " + ContextStore.get("issueId") + " successfully completed!");
    }

    @And("I verify the response for the state {}")
    public void iVerifyTheResponseForTheStateClosed(String state) {
        // retrieves the stored response from the context store
        Response response = ContextStore.get("response");

        // deserializes the response data into an Issue object
        Issue issue = response.as(Issue.class);

        // asserts the state of the issue
        Assert.assertEquals(state, issue.getState());

        LogUtils.logInfo("The state of the issueId " + ContextStore.get("issueId") + " successfully verified!");
    }

    @Then("The issue should be in a {} state")
    public void theIssueShouldBeInAState(String state) {
        LogUtils.logInfo("Checking if the issue is in a " + state + " state...");

        // retrieves the stored response from the context store
        Response response = ContextStore.get("response");

        // deserializes the response data into a list of Issue objects
        List<Issue> issues = response.as(new TypeToken<List<Issue>>() {}.getType());

        // iterates through each Issue in the issues list and checks if any issue has the same iid (issue ID) as the one stored in the context
        // If a match is found, it asserts that the state of that issue matches the expected state
        for (Issue issue : issues) {
            if (issue.getIid() == ContextStore.get("issueId"))
                Assert.assertEquals(state, issue.getState());
        }

        LogUtils.logInfo("Verification is successfully completed!");
    }
}
