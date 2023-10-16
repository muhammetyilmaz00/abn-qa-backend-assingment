@GitLabIssues @CreateIssues

  Feature: Creating Issues in a GitLab Project

    @CreateNewIssueWithParameters @DeleteIssue
    Scenario: Create a new issue with query parameters
      When I create a new issue in the project with the following parameters
        | title       | Issues with authentication  |
        | labels      | bug                         |
        | description | Issue created by automation |
      Then response status code should be 201
      And I verify the response for the expected parameters
        | title       | Issues with authentication  |
        | labels      | bug                         |
        | description | Issue created by automation |

    @CreateIssue @DeleteIssue
    Scenario: Create a new issue
      When I create a new issue in the project with title "404 Not Found instead of 200 OK"
      Then response status code should be 201
      And I verify the response for title "404 Not Found instead of 200 OK" and state opened

    @CreateIssueWithEmptyTitle
    Scenario: Create a new issue with empty title
      When I create a new issue in the project with the following parameters
        | description | Issue created by automation |
      Then response status code should be 400
      Then Response body error should be title is missing