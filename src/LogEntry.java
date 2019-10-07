import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import converters.DispositionConverter;
import converters.FilenameConverter;
import validation.Disposition;

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
    @JsonDeserialize(converter = FilenameConverter.class)
    private String filename;

    @JsonProperty("ph")
    private String filePath;

    @JsonProperty("dp")
    @JsonDeserialize(converter = DispositionConverter.class)
    private Disposition disposition;

    public boolean isValid() {
        return disposition != null && filename != null;
    }

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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Disposition getDisposition() {
        return disposition;
    }

    public void setDisposition(Disposition disposition) {
        this.disposition = disposition;
    }
}
