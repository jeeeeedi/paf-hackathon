import java.lang.annotation.Inherited;

@Document(collection = "errors")
public class Error {

    @Id
    private String errorId;

    @NotNull
    @Field("code")
    private String code;
    
    @NotNull
    @Field("message")
    private String message;

    @NotNull
    @Field("details")
    private String details;

    public Error() {
    }

    // Getters and Setters
    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
