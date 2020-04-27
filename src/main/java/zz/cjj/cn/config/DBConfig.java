package zz.cjj.cn.config;

public class DBConfig {

    /**
     * com.mysql.jdbc.Driver
     */
    private String driver;

    /**
     * jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8</property>
     */
    private String url;

    /**
     * root
     */
    private String username;

    /**
     * 123456
     */
    private String password;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}