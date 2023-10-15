package gitLabIssues.utils;

import gitLabIssues.pojo.Issue;

public class IssueUtils {

    /**
     * retrieves from the Issue object based on the provided key and returns the corresponding value from the Issue object
     */
    public static Object retrieveValueFromIssue(Issue issue, String key) {
        return switch (key) {
            case "id" -> issue.getId();
            case "iid" -> issue.getIid();
            case "projectId" -> issue.getProjectId();
            case "title" -> issue.getTitle();
            case "description" -> issue.getDescription();
            case "state" -> issue.getState();
            case "labels" -> issue.getLabels().get(0);
            // Add more cases for other fields as needed
            default -> throw new IllegalArgumentException("Invalid key: " + key);
        };
    }
}

