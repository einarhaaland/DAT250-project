package MessagingSystems;

import Model.Result;
import org.eclipse.paho.client.mqttv3.*;

import java.nio.charset.StandardCharsets;


/**
 * For this class i followed my own ingenious intuition, but mostly(99.999%) this tutorial:
 * https://docs.cloudplugs.com/kb/Developer-Guides/MQTT-API/Java-Examples
 *
 * TODO: Change all of the variables to the needed ones for mqtt and mongodb to work
 */
// TODO: rename file to something better
// Sender
public class Messaging {

    //TODO: Not certain of these variables just yet
    private static String topic = "/data/" + "preferredChannelAccordingToATutorial";
    private static int qos = 1;

    //Other variables
    private static String connectionURI = "tcp://localhost:8080";
    private static String username = "username";
    private static String password = "password";

    public static void MQTTPublishMessage(Result result) throws MqttException {

        MqttClient client = new MqttClient(connectionURI, MqttClient.generateClientId());
        MqttConnectOptions cOptions = new MqttConnectOptions();

        /*
        cOptions.setCleanSession(true);
        cOptions.setUserName(username);
        cOptions.setPassword(password.toCharArray());

         */

        //Start Connecting to Broker
        client.connect(cOptions);

        MqttMessage message = new MqttMessage(result.toJson().getBytes());
        message.setQos(qos);
        client.publish(topic, message);

        client.disconnect();

    }

}
