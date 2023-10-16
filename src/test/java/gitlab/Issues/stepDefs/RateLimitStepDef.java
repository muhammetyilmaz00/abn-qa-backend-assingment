package gitlab.Issues.stepDefs;

import gitlab.Issues.requests.CallService;
import gitlab.Issues.utils.BaseTest;
import gitlab.Issues.utils.LogUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class RateLimitStepDef extends BaseTest {

    public static RequestSpecification requestSpecificationWithoutToken = RestAssured.given();
    static CallService callServiceWithoutToken = new CallService(requestSpecificationWithoutToken);

    @When("I send {int} unauthenticated get requests with {} concurrent requests to retrieve projects")
    public void iSendUnauthenticatedGetRequestsToRetrieveProjects(int numberOfRequests, int concurrentRequests) {
        LogUtils.logInfo("Sending " + numberOfRequests + " unauthenticated get requests to retrieve projects...");

        int requestCount = 0;

        // create an executor service
        ExecutorService executorService = Executors.newFixedThreadPool(concurrentRequests);

        long startTime = System.currentTimeMillis();

        // send the requests
        while (requestCount < numberOfRequests) {

            if (System.currentTimeMillis() - startTime >= 60000) break;

            for (int i = 0; i < concurrentRequests; i++) {
                executorService.execute(new APITestRunnable());
                requestCount++;
            }
        }

        // shuts down the executor service
        executorService.shutdown();

        // wait for all threads to finish
        while (!executorService.isTerminated()) {}

        // get the elapsed time
        long currentTime = System.currentTimeMillis();

        System.out.println("Elapsed time: " + (currentTime - startTime) + " milliseconds");
        System.out.println("Elapsed time: " + (currentTime - startTime) / 1000 + " seconds");
    }

    static class APITestRunnable implements Runnable {

        // create a boolean flag to indicate whether the thread is running
        private static AtomicBoolean isRunning = new AtomicBoolean(false);

        @Override
        public void run() {
            // send a GET request to the project endpoint without token
            Response response = callServiceWithoutToken.executeGetRequest(PROJECTS_ENDPOINT, false, false);

            int statusCode = response.getStatusCode();

            // check if the response status code is 429
            if (statusCode == 429) {
                isRunning.set(true);
                System.out.println("Response Code: " + statusCode);
                Assert.assertNotNull("Response body is null!", response.getBody().asString());
                LogUtils.logInfo("429 response received!");
            }
        }
    }

    @Then("I should receive 429 Too Many response")
    public void iShouldReceive429Response() {
        if(APITestRunnable.isRunning.get()) {
            LogUtils.logInfo("429 response received!");
        }else
            Assert.fail("429 response not received!");
    }

}
