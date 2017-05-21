package service;

import repository.UserManager;

import java.rmi.RemoteException;

public class LoginService implements ILoginService
{
    private UserManager manager;

    public LoginService(UserManager manager)
    {
        this.manager=new UserManager();
    }

    public String initManager(String username,String passwords) throws RemoteException
    {
                String sessionID = login(username,passwords);
                if (sessionID.compareTo("error")!=0)
                {
                    return "valid";
                }
                else
                {
                    return "invalid";
                }
    }

    public String login(String username,String passwords) throws RemoteException
    {
        if (manager.autentificare(username,passwords)==1)
        {
            return "success";
        }
        else
            return "error";
    }

}
