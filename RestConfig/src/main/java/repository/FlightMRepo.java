package repository;

import model.Flight;
import org.springframework.stereotype.Repository;
import services.FlightList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by calin on 24/05/2017.
 */
@Repository
public class FlightMRepo implements IFlightRepo{
    private static FlightList mFlights = new FlightList();
    static {
        mFlights.add(new Flight(1, "Cluj", "Brasov", 23, "ss"));
        mFlights.add(new Flight(2, "Galati", "Timisoara", 23, "ss"));
        mFlights.add(new Flight(2, "Galati", "Craiova", 23, "ss"));
    }

    @Override
    public List<Flight> getAll() {
        return mFlights;
    }

    @Override
    public List<Flight> findByDestinationAndDate(String dest, String data) {
        return mFlights.stream().filter(f -> f.getDestination().equals(dest) && f.getDatehour().equals(data)).collect(Collectors.toList());
    }

    @Override
    public List<Flight> findByDestination(String dest) {
        return mFlights.stream().filter(f -> f.getDestination().equals(dest)).collect(Collectors.toList());
    }

    @Override
    public List<Flight> findByDate(String data) {
        return mFlights.stream().filter(f -> f.getDatehour().equals(data)).collect(Collectors.toList());
    }

    @Override
    public void updateFlight(Flight flight, String client, int noticket, String address) {

    }

    @Override
    public void deleteFlight(int id) {
    }

    @Override
    public Flight findById(int id) {
        return mFlights.stream().filter(f -> f.getFlightId() == id).collect(Collectors.toList()).get(0);
    }

    @Override
    public void save(Flight f) {
        mFlights.add(f);
    }

    @Override
    public void update(int id, Flight f2) {

    }
}
