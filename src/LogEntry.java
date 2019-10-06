import com.fasterxml.jackson.annotation.JsonProperty;

public class LogEntry {

    @JsonProperty("ts")
    private Long timestamp;

    @JsonProperty("pt")
    private Long processingTime;

    @JsonProperty("si")
    private String sessionId;

    @JsonProperty("uu")
    private String userUuid;

    @JsonProperty("bg")
    private String businessUuid;

    @JsonProperty("sha")
    private String sha;

    @JsonProperty("nm")
    private String fileName;

    @JsonProperty("ph")
    private String filePath;

    @JsonProperty("dp")
    private String disposition;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(Long processingTime) {
        this.processingTime = processingTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getBusinessUuid() {
        return businessUuid;
    }

    public void setBusinessUuid(String businessUuid) {
        this.businessUuid = businessUuid;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }
}
