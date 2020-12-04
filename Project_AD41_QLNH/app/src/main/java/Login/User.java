package Login;

public class User {
    private String user;
    private String pass;
    private String Name;
    private String numberPhone;
    private String avatar;

    public User(String user, String pass, String name, String numberPhone, String avatar) {
        this.user = user;
        this.pass = pass;
        Name = name;
        this.numberPhone = numberPhone;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
