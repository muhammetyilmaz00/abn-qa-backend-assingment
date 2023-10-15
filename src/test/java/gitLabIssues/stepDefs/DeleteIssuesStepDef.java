package gitLabIssues.stepDefs;

import com.google.gson.reflect.TypeToken;
import gitLabIssues.pojo.Issue;
import gitLabIssues.requests.CallService;
import gitLabIssues.utils.BaseTest;
import gitLabIssues.utils.ContextStore;
import gitLabIssues.utils.LogUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.List;

public class DeleteIssuesStepDef extends BaseTest {

    CallService callService = new CallService(requestSpecification);


    @And("I delete the created issue")
    public void deleteTheCreatedIssue() {
        LogUtils.logInfo("Deleting the issueId " + ContextStore.get("issueId") + " from the project...");

        // sends a DELETE request to the specific project's issue endpoint to delete the issue
        Response response = callService.executeDeleteRequest(PROJECTS_ENDPOINT + PROJECT_ID + ISSUES_ENDPOINT, ContextStore.get("issueId").toString(),false,false);

        // stores the response for further use in the test
        ContextStore.put("response", response);

        LogUtils.logInfo("Deleting the issueId " + ContextStore.get("issueId") + " from the project successfully completed!");
    }

    @Then("I check if the issue is deleted from the list of all issues")
    public void checkIfTheIssueIsDeletedFromTheListOfAllIssues() {
        LogUtils.logInfo("Checking if the issue is deleted from the list of all issues...");

        // sends a GET request to the specified endpoint to retrieve a list of issues
        Response response = callService.executeGetRequest(ISSUES_ENDPOINT, false, false);

        // deserializes the response data into a list of Issue objects using the TypeToken and makes it accessible as the issues variable
        List<Issue> issues = response.as(new TypeToken<List<Issue>>() {}.getType());

        // iterates through each Issue in the issues list and asserts that the issueId does not exist
        for (Issue issue : issues) {
            Assert.assertNotEquals(ContextStore.get("issueId"), issue.getId());
        }

        LogUtils.logInfo("Verification is successfully completed!");
    }

    @When("I send a delete request with {}")
    public void iSendADeleteRequestWith(String issueId) {
        LogUtils.logInfo("Deleting the issueId " + issueId + " from the project...");

        // sends a DELETE request without issue id to the specific project's issue endpoint to delete the issue
        Response response = callService.executeDeleteRequest(PROJECTS_ENDPOINT + PROJECT_ID + ISSUES_ENDPOINT, issueId,true,true);

        // stores the response for further use in the test
        ContextStore.put("response", response);

        LogUtils.logInfo("Deleting the issueId " + issueId + " from the project successfully sent!");
    }

    @And("I delete all of created issue")
    public void iDeleteAllOfCreatedIssue() {
        int count = 0;
        List<String> issueIds = ContextStore.get("issueIds");

        // sends a DELETE request to the specific project's issue endpoint to delete the issue
        for (String issueId : issueIds) {
            Response response = callService.executeDeleteRequest(PROJECTS_ENDPOINT + PROJECT_ID + ISSUES_ENDPOINT, issueId,true,true);
            Assert.assertEquals(204, response.getStatusCode());
            count++;
        }

        if(count == issueIds.size()){
            LogUtils.logInfo("Deleting all of created issue successfully completed!");
        }else{
            LogUtils.logInfo("Deleting all of created issue failed!");
            Assert.fail("Deleting all of created issue failed!");
        }
    }
}
