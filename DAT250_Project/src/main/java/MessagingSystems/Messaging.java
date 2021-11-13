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
public class Messaging {

    //TODO: Not certain of these variables just yet
    private String topic = "/data/" + "preferredChannelAccordingToATutorial";
    private int qos = 1;

    //Other variables
    private String connectionURI = "URI to the mongoDB i guess?";
    private String username = "username";
    private String password = "password";

    public void MQTTPublishMessage(Result result) throws MqttException {

        MqttClient client = new MqttClient(connectionURI, MqttClient.generateClientId());
        MqttConnectOptions cOptions = new MqttConnectOptions();
        cOptions.setCleanSession(true);
        cOptions.setUserName(username);
        cOptions.setPassword(password.toCharArray());

        //Start Connecting to Broker
        client.connect(cOptions);

        MqttMessage message = new MqttMessage(result.toJson().getBytes());
        message.setQos(qos);
        client.publish(topic, message);

        client.disconnect();
        
    }

}
