package rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;


/**
 * Created by Dragos on 5/17/2017.
 */
public class Send {
    public static String EXCHANGE_NAME = "topic_logs";
    private static Channel channel;

    public static void init() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            System.out.println("Init called for sent channel: " + EXCHANGE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void notifyChange() {
        if (channel == null)
        {
            init();
        }
        try {
            String routingKey = "anon.info";
            String message = "update";
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println("Notify Change:  " + EXCHANGE_NAME);
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] argv) throws Exception {
        init();
        while (true)
        {
            new Scanner(System.in).nextLine();
            System.out.println("Operation in progress");
            notifyChange();
        }
    }
}
