package rabbit;

/**
 * Created by Dragos on 5/17/2017.
 */
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static rabbit.Send.EXCHANGE_NAME;


public class Recv
{

    private static Channel channel;

    public static void init(OnMessage onMessage) {
        try
        {
            String bindingKey = "#";
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String queueName = channel.queueDeclare().getQueue();


            channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            System.out.println("Receive init : register to: " + EXCHANGE_NAME);


            Consumer consumer = new DefaultConsumer(channel)
            {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
                {
                    String message = new String(body, "UTF-8");
                    onMessage.onMessage(message);
                }
            };
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public interface OnMessage {
        void onMessage(String message);
    }

    public static void main(String[] argv) throws Exception {
        Recv.init(message -> System.out.println("Am primit " + message));
    }
}
