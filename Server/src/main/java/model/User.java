package model;

import java.io.Serializable;

/**
 * Created by Dragos on 3/23/2017.
 */

public class User implements Serializable{

    private static final long serialVersionUID = 7230149089310163575L;
    private String user;
    private String parola;

    public User(String usr,String psd)
    {
        this.user=usr;
        this.parola=psd;
    }

    public User()
    {

    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String val)
    {
        user=val;
    }

    public String getParola()
    {
        return parola;
    }

    public void setParola(String val)
    {
        parola=val;
    }

    public String toString()
    {
        return user+" "+parola;
    }



}
