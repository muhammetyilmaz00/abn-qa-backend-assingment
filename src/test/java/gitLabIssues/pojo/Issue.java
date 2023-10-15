package gitLabIssues.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Issue {
    Integer id;
    Integer iid;
    Integer projectId;
    String title;
    String description;
    String state;
    List<String> labels;
    String type;
    @SerializedName("issue_type")
    String issueType;
    Author author;

    @Data
    public static class Author {
        Integer id;
        String username;
    }

}
