package MessagingSystems;

import Model.Result;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.eclipse.paho.client.mqttv3.*;

import java.net.UnknownHostException;

public class MongoService {

    public void onMqttMessage(){

        try {
        String connectionURI = "tcp://localhost:8080";
        MqttClient client = new MqttClient(connectionURI, MqttClient.generateClientId());
        MqttConnectOptions cOptions = new MqttConnectOptions();
        //cOptions.setCleanSession(true);
        client.connect(cOptions);
        client.subscribe("#");
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                System.out.println("you fucked up");
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                System.out.println("Message: " + mqttMessage.toString());
                //MongoService();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
        } catch (MqttException e) {
            e.printStackTrace();
        }


    }

    public void onMqttConnect(){

    }

    public static void MongoService(Result result) {
        MongoClient client;

        {
            try {
                client = new MongoClient();
                MongoDatabase db = (MongoDatabase) client.getDB("results");
                MongoCollection<Document> collection = db.getCollection("resultColl");


                /*
                Document entry = new Document("id", result.getId())
                        .append("yes", result.getYes())
                        .append("no", result.getNo());
                */
                //collection.add(result);



            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }
}
