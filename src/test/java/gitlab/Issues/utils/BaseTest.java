package gitlab.Issues.utils;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

public class BaseTest {
    public static RequestSpecification requestSpecification;
    public static RequestSpecification requestSpecificationWithInvalidToken;
    public static final String ISSUES_ENDPOINT = "/issues/";
    public static final String PROJECTS_ENDPOINT = "/projects/";
    public static final String GROUPS_ENDPOINT = "/groups/";
    public static String PROJECT_ID ;
    public static String GROUP_ID ;

    public static void setUp() {
        baseURI = PropertiesFactory.getProperty("baseURI");
        PROJECT_ID = PropertiesFactory.getProperty("projectId");
        GROUP_ID = PropertiesFactory.getProperty("groupId");
        requestSpecification = given().accept(ContentType.JSON).header("PRIVATE-TOKEN", PropertiesFactory.getProperty("token"));
        requestSpecificationWithInvalidToken = given().accept(ContentType.JSON).header("PRIVATE-TOKEN", "invalid token");
    }
}