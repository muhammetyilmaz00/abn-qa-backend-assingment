@GitLabIssues @DeleteIssues

Feature: Deleting Issues from a GitLab Project

  @DeleteIssue
  Scenario: Delete an issue
    When I create a new issue in the project with title "New Issue"
    Then Response status code should be 201
    When I delete the created issue
    Then Response status code should be 204
    Then I check if the issue is deleted from the list of all issues

  @DeleteIssueWithoutIssueId
  Scenario: Delete an issue without issue id
    When I send a delete request with invalidId
    Then Response status code should be 400
    Then Response body error should be issue_iid is invalid