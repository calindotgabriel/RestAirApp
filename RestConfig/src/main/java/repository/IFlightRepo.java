package repository;

import model.Flight;

import java.util.List;

/**
 * Created by calin on 24/05/2017.
 */
public interface IFlightRepo {
    List<Flight> getAll();

    List<Flight> findByDestinationAndDate(String dest, String data);

    List<Flight> findByDestination(String dest);

    List<Flight> findByDate(String data);

    void updateFlight(Flight flight, String client, int noticket, String address);

    void deleteFlight(int id);

    Flight findById(int id);

    void save(Flight f);

    void update(int id, Flight f2);
}
