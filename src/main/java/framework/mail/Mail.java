package framework.mail;

public class Mail {
    private String user;
    private String password;
    private String protocol;
    private String host;

    public Mail(String user, String password, String protocol, String host) {
        this.user = user;
        this.password = password;
        this.protocol = protocol;
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }
}
