package repository;

import model.Flight;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import services.FlightList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Dragos on 5/20/2017.
 */
public class FlightClient
{
    private static final String URL = "http://localhost:8080/company/flight";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) {
        try
        {
            return callable.call();
        }
        catch (ResourceAccessException | HttpClientErrorException e)
        { // server down, resource exception
            throw new RuntimeException(e);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public FlightList getAll()
    {
        return execute(() -> restTemplate.getForObject(URL, FlightList.class));
    }

    public Flight getById(int id) {
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), Flight.class));
    }

    public Flight create(Flight fly) {
        return execute(() -> restTemplate.postForObject(URL, fly, Flight.class));
    }

    public void update(Flight fly) {
        execute(() -> {
            restTemplate.put(String.format("%s/%s", URL, fly.getFlightId()), fly);
            return null;
        });
    }

    public void delete(int id) {
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id));
            return null;
        });
    }

}
