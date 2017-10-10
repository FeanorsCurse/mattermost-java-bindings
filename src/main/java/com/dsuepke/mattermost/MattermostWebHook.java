package com.dsuepke.mattermost;

import java.io.IOException;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author dsuepke
 */
public class MattermostWebHook {

	private static final MediaType JSON = MediaType.parse("application/json; encoding=UTF-8");

	private OkHttpClient client = new OkHttpClient();

	private String webHookURL;


	public MattermostWebHook(String webHookURL) {
		this.webHookURL = webHookURL;
	}


	public void sendMessage(String text, String userName) throws IOException {
		JSONObject json = new JSONObject();
		json.put("text", text);
		json.put("username", userName);

		post(json.toString());
	}


	private String post(String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder()
				.url(webHookURL)
				.post(body)
				.build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}
}
