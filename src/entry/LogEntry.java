package entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import converters.DispositionConverter;
import converters.FilenameConverter;
import converters.UuidConverter;
import validation.Disposition;

import java.util.Objects;

public class LogEntry {

    private long timestamp;

    private long processingTime;

    private String sessionId;

    private String userUuid;

    private String businessUuid;

    private String sha;

    private String filename;

    private String filePath;

    private Disposition disposition;

    @JsonCreator
    public LogEntry(@JsonProperty(value = "ts", required = true) @JsonFormat(shape = JsonFormat.Shape.NUMBER) long timestamp,
                    @JsonProperty(value = "pt", required = true) @JsonFormat(shape = JsonFormat.Shape.NUMBER) long processingTime,
                    @JsonProperty(value = "si", required = true) @JsonFormat(shape = JsonFormat.Shape.STRING) String sessionId,
                    @JsonProperty(value = "ph", required = true) @JsonFormat(shape = JsonFormat.Shape.STRING) String filePath,
                    @JsonProperty(value = "sha", required = true) @JsonFormat(shape = JsonFormat.Shape.STRING) String sha,
                    @JsonProperty(value = "uu", required = true) @JsonDeserialize(using = UuidConverter.class) @JsonFormat(shape = JsonFormat.Shape.STRING) String userUuid,
                    @JsonProperty(value = "bg", required = true) @JsonDeserialize(using = UuidConverter.class) @JsonFormat(shape = JsonFormat.Shape.STRING) String businessUuid,
                    @JsonProperty(value = "nm", required = true) @JsonDeserialize(using = FilenameConverter.class) @JsonFormat(shape = JsonFormat.Shape.STRING) String filename,
                    @JsonProperty(value = "dp", required = true) @JsonDeserialize(using = DispositionConverter.class) Disposition disposition) {
        this.timestamp = timestamp;
        this.processingTime = processingTime;
        this.sessionId = sessionId;
        this.userUuid = userUuid;
        this.businessUuid = businessUuid;
        this.sha = sha;
        this.filename = filename;
        this.filePath = filePath;
        this.disposition = disposition;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(long processingTime) {
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

    /**
     * We will use both the SHA and filename as a unique identifier, therefore two files are only equal if and only if
     * the SHA and the filename is the same.
     *
     * @param o other object to compare.
     * @return true if files match, false if they don't match.
     */
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
