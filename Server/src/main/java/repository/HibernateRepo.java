package repository;


import model.Flight;
import org.hibernate.SessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Dragos on 5/8/2017.
 */
public class HibernateRepo implements IFlightRepo
{
    private static SessionFactory factory;
    private List<Flight> flights = new ArrayList<Flight>();

    public HibernateRepo()
    {
        try
        {
            this.factory = new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable ex)
        {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            flights = session.createQuery("FROM Flight").list();
            for (Iterator iterator = flights.iterator(); iterator.hasNext();)
            {
                Flight f = (Flight) iterator.next();
                System.out.print("Id: " + f.getFlightId());
                System.out.print("Airport: " + f.getAirport());
                System.out.println("Destination: " + f.getDestination());
            }
            tx.commit();
        }
        catch (HibernateException e)
        {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }
        finally
        {
            session.close();
        }
    }

    public List<Flight> getAll()
    {
        return flights;
    }

    public List<Flight> findByDestinationAndDate(String dest, String data)
    {
        List<Flight> rez = new ArrayList<Flight>();
        for(Flight flight : flights)
            if(flight.getDestination().equals(dest) && flight.getDatehour().compareTo(data) == 0)
                rez.add(flight);
        return rez;
    }

    public List<Flight> findByDestination(String dest)
    {
        List<Flight> rez = new ArrayList<Flight>();
        for(Flight flight : flights)
            if(flight.getDestination().equals(dest))
                rez.add(flight);
        return rez;
    }

    public List<Flight> findByDate(String data)
    {
        List<Flight> rez = new ArrayList<Flight>();
        for(Flight flight : flights)
            if(flight.getDatehour().compareTo(data) == 0)
                rez.add(flight);
        return rez;
    }

    public void updateFlight(Flight flight,String client,int noticket,String address)
    {
        for (Flight f: flights) {
            if (f.getFlightId() == flight.getFlightId())
            {
                Session session = factory.openSession();
                Transaction tx = null;
                try
                {
                    tx = session.beginTransaction();
                    Flight fg = (Flight) session.get(Flight.class, flight.getFlightId());
                    int free = fg.getFreeseats();
                    int nou = free - noticket;
                    fg.setFreeseats(nou);
                    session.update(fg);
                    tx.commit();

                    int idul = flights.indexOf(f);
                    int bilete =  flights.get(idul).getFreeseats();
                    flights.get(idul).setFreeseats(bilete-noticket);
                    String s = flight.toString();
                    if (flights.get(idul).getFreeseats() == 0)
                    {
                        deleteFlight(flight.getFlightId(),idul);
                    }

                } catch (HibernateException e) {
                    if (tx != null) tx.rollback();
                    e.printStackTrace();
                } finally {
                    session.close();
                }
            }
        }
    }

    @Override
    public void deleteFlight(int id) {
        deleteFlight(id, id);
    }

    public void deleteFlight(int id,int idul)
    {
        for (Flight f: flights)
        {
            if (f.getFlightId() == id)
            {
                Session session = factory.openSession();
                Transaction tx = null;
                try
                {
                    tx = session.beginTransaction();
                    Flight fg = (Flight) session.get(Flight.class, id);
                    session.delete(fg);
                    tx.commit();
                    flights.remove(idul);
                }
                catch (HibernateException e)
                {
                    if (tx != null)
                        tx.rollback();
                    e.printStackTrace();
                }
                finally
                {
                    session.close();
                }
            }
        }
    }

    public Flight findById(int id)
    {
        Flight fg = new Flight();
        Session session = factory.openSession();
        Transaction tx = null;
        try
        {
            tx = session.beginTransaction();
            fg = (Flight) session.get(Flight.class, id);
        }
        catch (HibernateException e)
        {
            if (tx!=null)
                tx.rollback();
            e.printStackTrace();
        }
        finally
        {
            session.close();
        }
        return fg;
    }
}
