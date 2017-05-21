package service;

import model.Flight;
import repository.HibernateRepo;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class FlightService implements IFlightService
{
    private HibernateRepo repo;

    public FlightService(HibernateRepo frep)
    {
        this.repo = frep;
    }

    public List<Flight> getAllFlights() throws RemoteException
    {
        List<Flight> s = new ArrayList<Flight>();
        List<Flight> list=repo.getAll();
        for (Flight f:list)
            s.add(f);
        return s;

    }

    public int buyFlight(int idf, String client,int noticket,String address) throws RemoteException
    {
        Flight cop = repo.findById(idf);
        int nrorig = cop.getFreeseats();
        if (nrorig < noticket)
        {
            return 0;
        }

        repo.updateFlight(cop,client,noticket,address);
        cop = repo.findById(idf);

        if (cop.getFreeseats() == nrorig - noticket)
        {
            return 1;
        }
        else
            return 0;

    }

    public void deleteFlight(Flight c) throws RemoteException
    {

    }
    public List<Flight> findByDestinationAndDate(String dest, String dat) throws RemoteException
    {
        List<Flight> rez;
        rez = repo.findByDestinationAndDate(dest,dat);
        return rez;
    }

    public List<Flight> findByDestination(String dest) throws RemoteException
    {
        List<Flight> rez;
        rez = repo.findByDestination(dest);
        return rez;
    }

    public List<Flight> findByDate(String dat) throws RemoteException
    {
        List<Flight> rez;
        rez = repo.findByDate(dat);
        return rez;
    }

}
