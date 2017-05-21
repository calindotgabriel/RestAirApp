package service;

import model.Flight;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Dragos on 4/24/2017.
 */
public interface IFlightService extends Remote
{
    public List<Flight> getAllFlights() throws RemoteException;

    public int buyFlight(int idf, String client,int noticket,String address) throws RemoteException;

    public void deleteFlight(Flight c) throws RemoteException;

    public List<Flight> findByDestinationAndDate(String dest, String dat) throws RemoteException;


    public List<Flight> findByDestination(String dest) throws RemoteException;


    public List<Flight> findByDate(String dat) throws RemoteException;

}
