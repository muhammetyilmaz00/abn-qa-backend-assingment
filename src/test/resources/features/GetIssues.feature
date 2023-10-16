@GitLabIssues @GetIssues

Feature: Retrieving Issues from a GitLab Project

  @GetAllIssues
  Scenario: Retrieve all issues in a project
    When I request the list of all issues
    Then response status code should be 200
    And Response content type should be application/json
    And I should see the issue types are ISSUE and author id's are not null

  @GetProjectIssues
  Scenario: Retrieve project-specific issues
    When I request issues specific to the project
    Then response status code should be 200
    And Response content type should be application/json
    And I should see the opened and closed issues

  @GetInvalidProjectIssues
  Scenario: Retrieve project-specific issues with invalid project id
    When I send a get request to retrieve issues from the project id string
    Then response status code should be 404
    And Response body message should be 404 Project Not Found

  @GetGroupIssues
  Scenario: Retrieve group-specific issues
    When I request issues specific to the group
    Then response status code should be 200
    And Response content type should be application/json
    And I should see the opened and closed issues

  @GetInvalidGroupIssues
  Scenario: Retrieve group-specific issues with invalid group id
    When I send a get request to retrieve issues from the group id invalidId
    Then response status code should be 404
    And Response body message should be 404 Group Not Found

  @GetAllIssuesWithInvalidToken
  Scenario: Retrieve all issues in a project with an invalid token
    When I request the list of all issues without valid token
    Then response status code should be 401
    And Response body message should be 401 Unauthorized