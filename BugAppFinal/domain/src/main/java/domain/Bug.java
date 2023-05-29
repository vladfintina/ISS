package domain;

public class Bug extends Entity<Integer> {
    private String title;
    private String description;
    private String status;
    private String riskLevel;
    private Integer Id;

    public Bug(String title, String description, String status, String riskLevel) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.riskLevel = riskLevel;
    }

    public Bug() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title  = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", bugStatus='" + status + '\'' +
                ", riskLevel='" + riskLevel + '\'' +
                '}';
    }

}