package MessagingSystems;

import Model.Result;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.UUID;
import java.util.concurrent.Callable;


/**
 * For this class i followed my own ingenious intuition, but mostly(99.999%) this tutorial:
 * https://docs.cloudplugs.com/kb/Developer-Guides/MQTT-API/Java-Examples
 * <p>
 * TODO: Change all of the variables to the needed ones for mqtt and mongodb to work
 */
// TODO: rename file to something better
// Sender
public class Messaging implements Runnable, MqttCallback {

    //TODO: Not certain of these variables just yet
    public static final String TOPIC = "#";
    private static int qos = 1;

    //Other variables
    private static String connectionURI = "tcp://localhost:1883";
    private static String username = "username";
    private static String password = "password";
    MemoryPersistence persistence = new MemoryPersistence();

    public void tester() throws MqttException {

        String publisherId = UUID.randomUUID().toString();
        MqttClient publisher = new MqttClient(connectionURI,publisherId, persistence);

        String subscriberId = UUID.randomUUID().toString();
        MqttClient subscriber = new MqttClient(connectionURI,subscriberId, persistence);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);


        subscriber.connect(options);
        System.out.println("Subscriber connected");

        publisher.connect(options);
        System.out.println("Subscriber connected");

        MqttMessage message = new MqttMessage("Ed Sheeran".getBytes());


        publisher.publish("/test/topic", message);
        subscriber.subscribe("/test/topic", (topic, mess) -> {
            byte[] payload = mess.getPayload();
            System.out.println(payload.toString());
        });



    }



    public void testPaho() {
        String broker = "tcp://localhost:1883";
        String topicName = "test/topic";

        int qos = 1;

        try {
            IMqttClient mqttClient = new MqttClient(broker, String.valueOf(System.nanoTime()), persistence);
            //Mqtt ConnectOptions is used to set the additional features to mqtt message

            MqttConnectOptions connOpts = new MqttConnectOptions();

            connOpts.setCleanSession(true); //no persistent session
            connOpts.setKeepAliveInterval(1000);


            MqttMessage message = new MqttMessage("Ed Sheeran".getBytes());

            message.setQos(qos);     //sets qos level 1
            message.setRetained(true); //sets retained message

            MqttTopic topic2 = mqttClient.getTopic(topicName);

            System.out.println("Starting connection...");
            mqttClient.connect(connOpts); //connects the broker with connect options
            System.out.println("Connected[x]");

            topic2.publish(message);    // publishes the message to the topic(test/topic)
            System.out.println("publishing testmessage");

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void run(){

//We're using eclipse paho library  so we've to go with MqttCallback
        MqttClient client = null;
        try {
            client = new MqttClient("tcp://localhost:1883", "clientid");
            client.setCallback(this);
            MqttConnectOptions mqOptions = new MqttConnectOptions();
            mqOptions.setCleanSession(true);
            client.connect(mqOptions);      //connecting to broker
            client.subscribe("test/topic"); //subscribing to the topic name  test/topic

        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    public static void MQTTPublishMessage(Result result) throws MqttException {

        String pId = UUID.randomUUID().toString();
        IMqttClient publisher = new MqttClient(connectionURI, pId);
        MqttConnectOptions cOptions = new MqttConnectOptions();

        cOptions.setAutomaticReconnect(true);
        cOptions.setCleanSession(true);
        cOptions.setConnectionTimeout(10);

        //Start Connecting to Broker
        publisher.connect(cOptions);


        // Turning the result into an MqttMessage
        MqttMessage message = new MqttMessage(result.toJson().getBytes());
        message.setQos(qos);


        publisher.publish(TOPIC, message);

        publisher.disconnect();

    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println(mqttMessage);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
