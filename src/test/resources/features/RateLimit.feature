@RateLimit

Feature: Retrieving Issues from a GitLab Project

  Scenario: Verify rate limit for unauthenticated requests
    When I send 400 unauthenticated get requests with 20 concurrent requests to retrieve projects
    When I send 1 unauthenticated get requests with 1 concurrent requests to retrieve projects
    Then I should receive 429 Too Many response