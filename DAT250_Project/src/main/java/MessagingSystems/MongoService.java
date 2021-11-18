
package MessagingSystems;

import Model.Result;
import com.google.gson.Gson;
//import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Iterator;

public class MongoService {


    public static void mongoService(Result result) {

        try {
            MongoClient client = new MongoClient("localhost", 27017);

            MongoCredential cred = MongoCredential.createCredential("user", "results", "password".toCharArray());

            MongoDatabase db = client.getDatabase("results");

            if (db.getCollection("resultColl").equals(null)) {
                System.out.println("Did not find the collection... \n Creating new collection named \"resultColl\"");
                db.createCollection("resultColl");
            }

            MongoCollection<Document> collection = db.getCollection("resultColl");


            Document entry = new Document("question", result.getQuestion())
                    .append("yes", result.getYes())
                    .append("no", result.getNo());

            collection.insertOne(entry);


            FindIterable<Document> iterDoc = collection.find();

            Iterator iterator = iterDoc.iterator();


            while (iterator.hasNext()) {
                Document doc = (Document) iterator.next();

                Result r = new Gson().fromJson(doc.toJson(), Result.class);

                System.out.println("Question: " + r.getQuestion() +
                        "\nYes: " + r.getYes() +
                        "\nNo: " + r.getNo() + "\n");
            }


        } catch (Exception e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }


    }
}


