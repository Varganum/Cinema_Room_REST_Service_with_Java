package cinema;

public class UserReturnRequest {

    private String token;

    public UserReturnRequest() {}

    public UserReturnRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}