/*
package MessagingSystems;

import Model.Result;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MongoService implements  MqttCallback {

    public static void test2() {

//We're using eclipse paho library  so we've to go with MqttCallback
        MqttClient client = null;
        try {
            client = new MqttClient("tcp://localhost:8883", "clientid");
            //client.setCallback(this);
            MqttConnectOptions mqOptions = new MqttConnectOptions();
            mqOptions.setCleanSession(true);
            client.connect(mqOptions);      //connecting to broker
            client.subscribe("test/topic"); //subscribing to the topic name  test/topic

        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
//Override methods from MqttCallback interface
@Override
public void messageArrived(String topic, MqttMessage message) throws Exception {
    System.out.println("message is : " + message);
}

    /*
    try {
        String connectionURI = "tcp://localhost:1883";


        MqttClient subscriber = new MqttClient(connectionURI, MqttClient.generateClientId());

        System.out.println("Mqtt subscribe initialized");

        MqttConnectOptions cOptions = new MqttConnectOptions();
        cOptions.setAutomaticReconnect(true);
        cOptions.setCleanSession(true);
        cOptions.setConnectionTimeout(10);

        subscriber.connect(cOptions);



        CountDownLatch reiceve = new CountDownLatch(10);
        subscriber.subscribe(Messaging.TOPIC, (topic, msg) -> {
            byte[] payload = msg.getPayload();
            System.out.println(payload.toString());

        });


        reiceve.await(1, TimeUnit.MINUTES);
    /*
    subscriber.setCallback(new MqttCallback() {
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
    } catch (InterruptedException e) {
        e.printStackTrace();
    }


}
*/
/*
    public void onMqttConnect() {

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
//collection.insertOne(result);

/*
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
*/


