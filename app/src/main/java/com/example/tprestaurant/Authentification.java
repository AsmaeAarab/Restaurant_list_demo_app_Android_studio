package com.example.tprestaurant;

public class Authentification {
    private String login;
    private String pswd;

    public Authentification(String login, String pswd) {
        this.login = login;
        this.pswd = pswd;
    }

    public String getLogin() {
        return login;
    }

    public String getPswd() {
        return pswd;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

}
