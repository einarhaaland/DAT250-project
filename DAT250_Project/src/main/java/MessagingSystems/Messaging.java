package MessagingSystems;

import Model.Result;
import Model.Vote;
import com.google.gson.Gson;
import com.mongodb.Mongo;
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
public class Messaging implements MqttCallback {

    //TODO: Not certain of these variables just yet
    public static final String TOPIC = "#";
    private static int qos = 1;

    //Other variables
    private static String connectionURI = "tcp://localhost:1883";
    private static String username = "username";
    private static String password = "password";
    MemoryPersistence persistence = new MemoryPersistence();


    public void sendResult(Result result) {
        String broker = "tcp://localhost:1883";
        String topicName = "test/topic";

        int qos = 1;

        try {
            IMqttClient mqttClient = new MqttClient(broker, String.valueOf(System.nanoTime()), persistence);
            //Mqtt ConnectOptions is used to set the additional features to mqtt message

            MqttConnectOptions connOpts = new MqttConnectOptions();

            connOpts.setCleanSession(true); //no persistent session
            connOpts.setKeepAliveInterval(1000);


            MqttMessage message = new MqttMessage(result.toJson().getBytes());

            message.setQos(qos);     //sets qos level 1
            message.setRetained(true); //sets retained message

            MqttTopic topic2 = mqttClient.getTopic(topicName);


            mqttClient.connect(connOpts); //connects the broker with connect options

            topic2.publish(message);    // publishes the message to the topic(test/topic)


        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void run(){


        MqttClient client = null;
        try {
            client = new MqttClient("tcp://localhost:1883", "clientid", persistence);
            client.setCallback(this);
            MqttConnectOptions mqOptions = new MqttConnectOptions();
            mqOptions.setCleanSession(true);
            client.connect(mqOptions);      //connecting to broker
            client.subscribe("test/topic"); //subscribing to the topic name  test/topic

        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

        System.out.println("message arrived");

        String message = new String(mqttMessage.getPayload());

        Result result = new Gson().fromJson(message, Result.class);



        MongoService mongoservice = new MongoService();
        mongoservice.mongoService(result);

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
