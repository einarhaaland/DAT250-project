package RestAPI;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.eclipse.persistence.internal.oxm.MediaType;

public class PutRequest {

	public static final MediaType JSON
    = MediaType.parse("application/json; charset=utf-8");
	
	public static void main(String[] args) {

		//Counters counters = new Counters(2,4);
		
		OkHttpClient client = new OkHttpClient();

		RequestBody body = RequestBody.create(JSON, counters.toJson());
		
		Request request = new Request.Builder().url("http://localhost:8080/todo").put(body).build();

		System.out.println(request.toString());

		try (Response response = client.newCall(request).execute()) {
			System.out.println(response.body().string());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
