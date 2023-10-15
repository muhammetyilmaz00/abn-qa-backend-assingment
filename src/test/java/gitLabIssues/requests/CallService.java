package gitLabIssues.requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class CallService {

    private final RequestSpecification requestSpecification;

    public CallService(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    /**
     * This method performs a GET request to a specified endpoint.
     * It can log both the request details and the response body if needed for debugging or analysis.
     * The boolean flags printReq and printResBody control whether these logs are generated or not.
     * The method returns the response from the GET request for further processing
     */
    public Response executeGetRequest(String endpoint, boolean printReq, boolean printResBody) {
        RequestSpecification requestSpec = given(requestSpecification);

        if (printReq)
            requestSpec.log().uri().log().body();

        Response response = requestSpec
                .when()
                .get(endpoint);

        if (printResBody)
            response.then().log().body();

        return response;
    }

    /**
     * This method is for making a POST request to a specified endpoint with optional logging of request details and the response body.
     * It takes a Map of query parameters, and the boolean flags printReq and printResBody determine whether to log request details and response body.
     * The method returns the response from the POST request for further processing.
     */
    public Response executePostRequest(String endpoint, Map<String, Object> queryParams, boolean printReq, boolean printResBody) {
        RequestSpecification requestSpec = given(requestSpecification);

        if (printReq)
            requestSpec.log().uri().log().body();

        Response response = requestSpec
                .queryParams(queryParams)
                .when()
                .post(endpoint);

        if (printResBody)
            response.then().log().body();

        return response;
    }

    /**
     * This method is for making a PUT request to a specified endpoint with optional logging of request details and the response body.
     * It takes a Map of query parameters, and the boolean flags printReq and printResBody determine whether to log request details and response body.
     * The method returns the response from the PUT request for further processing.
     */
    public Response executePutRequest(String endpoint, Map<String, Object> queryParams, boolean printReq, boolean printResBody) {
        RequestSpecification requestSpec = given(requestSpecification);

        if (printReq)
            requestSpec.log().uri().log().body();

        Response response = requestSpec
                .queryParams(queryParams)
                .when()
                .put(endpoint);

        if (printResBody)
            response.then().log().body();

        return response;
    }

    /**
     * This method is used to send a DELETE request to a specified endpoint, which includes an issueId as a path parameter.
     * It returns the response from the DELETE request, allowing further processing if needed.
     */
    public Response executeDeleteRequest(String endpoint, String issueId, boolean printReq, boolean printResBody) {
        RequestSpecification requestSpec = given(requestSpecification);

        if (printReq)
            requestSpec.log().uri().log().body();

        Response response =  requestSpec
                .when()
                .delete(endpoint + issueId);

        if (printResBody)
            response.then().log().body();
        return response;
    }
}
