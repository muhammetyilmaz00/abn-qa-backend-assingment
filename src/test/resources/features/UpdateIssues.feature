@GitLabIssues @UpdateIssues

Feature: Updating Issues in a GitLab Project

  @UpdateIssue @DeleteIssue
  Scenario: Update an issue
    When I create a new issue in the project with title "New Issue"
    Then response status code should be 201
    When I update the created issue with description "The issue is updated with this new description"
    Then response status code should be 200
    And I verify the issue description has been changed to "The issue is updated with this new description"

  @UpdateIssueWithNegativeId
  Scenario: Update an issue with negative issue id
    When I update the issue id -1
      | description | new description |
    Then response status code should be 404
    And Response body message should be 404 Not found

  @UpdateIssueWithInvalidId
  Scenario: Update an issue with invalid issue id
    When I send an update request with invalidId
    Then response status code should be 400
    Then Response body error should be issue_iid is invalid

  @UpdateIssueWithInvalidParameter
  Scenario: Update an issue with invalid parameter
    When I update the issue id 1
      | invalidKey | invalidValue |
    Then response status code should be 400
    And Response body error should be assignee_id, assignee_ids, confidential, created_at, description, discussion_locked, due_date, labels, add_labels, remove_labels, milestone_id, state_event, title, issue_type, weight, epic_id, epic_iid are missing, at least one parameter must be provided

  @CloseIssue @DeleteIssue
  Scenario: Close an issue
    When I create a new issue in the project with title "New Issue"
    Then response status code should be 201
    When I change the state of the issue to close
    Then response status code should be 200
    And I verify the response for the state closed
    When I request the list of all issues
    Then The issue should be in a closed state

  @ReopenIssue @DeleteIssue
  Scenario: Reopen an issue
    When I create a new issue in the project with title "New Issue"
    Then response status code should be 201
    When I change the state of the issue to close
    Then response status code should be 200
    And I verify the response for the state closed
    When I change the state of the issue to reopen
    Then response status code should be 200
    And I verify the response for the state opened
    When I request the list of all issues
    And The issue should be in a opened state