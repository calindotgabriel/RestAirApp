package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Dragos on 4/24/2017.
 */
public interface ILoginService extends Remote
{
    public String initManager(String username,String passwords) throws RemoteException;

    public String login(String username,String passwords) throws RemoteException;
}
