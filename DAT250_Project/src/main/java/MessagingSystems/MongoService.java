
package MessagingSystems;

import Model.Result;
import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


import java.net.UnknownHostException;
import java.util.List;

public class MongoService {


    public static void mongoService(Result result) {


        MongoClient client = MongoClients.create();

        System.out.println("MongoClient init");

        MongoDatabase db = client.getDatabase("results");

        System.out.println("db init");

        MongoCollection<Document> collection = db.getCollection("resultColl");

        System.out.println("mongodb and collection init'ed");

        Document entry = new Document("id", result.getId())
                .append("yes", result.getYes())
                .append("no", result.getNo());

        collection.insertOne(entry);

        System.out.println(collection.find());


    }
}


