import model.Flight;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import repository.FlightClient;
import services.FlightList;


/**
 * Created by Dragos on 5/20/2017.
 */
public class Main {
    private final static FlightClient flightclient=new FlightClient();
    public static void main(String[] args) {
        RestTemplate restTemplate=new RestTemplate();
        flightclient.delete(20);
        Flight fly=new Flight(20,"Tokyo","Bacau",50,"2017-07-01");
        try
        {
            show(()-> System.out.println(flightclient.create(fly)));
            show(()->{
                FlightList res=flightclient.getAll();
                for(Flight u:res){
                    System.out.println(u.getFlightId()+" : "+u.getAirport());
                }
            });

            show(()-> System.out.println(flightclient.getById(20)));
        }catch(RestClientException ex){
            System.out.println("Exception ... "+ex.getMessage());
        }
    }



    private static void show(Runnable task) {
        try {
            task.run();
        } catch (RuntimeException e) {
            System.out.println("Service exception: " + e);
        }
    }
}
