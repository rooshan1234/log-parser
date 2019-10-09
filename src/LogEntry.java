import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import converters.DispositionConverter;
import converters.FilenameConverter;
import validation.Disposition;

import java.util.Objects;

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
    @JsonDeserialize(using = FilenameConverter.class)
    private String filename;

    @JsonProperty("ph")
    private String filePath;

    @JsonProperty("dp")
    @JsonDeserialize(using = DispositionConverter.class)
    private Disposition disposition;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogEntry logEntry = (LogEntry) o;
        return Objects.equals(getSha(), logEntry.getSha()) &&
                Objects.equals(getFilename(), logEntry.getFilename());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSha(), getFilename());
    }
}
