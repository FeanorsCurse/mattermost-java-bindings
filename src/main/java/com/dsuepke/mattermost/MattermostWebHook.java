package com.dsuepke.mattermost;

import java.io.IOException;

import javax.annotation.Nullable;

import org.json.JSONObject;

import lombok.NonNull;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author dsuepke
 */
@SuppressWarnings("WeakerAccess") // Only relevant in this small example
public class MattermostWebHook {

	private static final MediaType JSON = MediaType.parse("application/json; encoding=UTF-8");

	private final OkHttpClient client = new OkHttpClient();

	private final String webHookURL;


	public MattermostWebHook(@NonNull String webHookURL) {
		this.webHookURL = webHookURL;
	}


	/**
	 * Sends a markdown message to mattermost.
	 *
	 * @param text     The markdown text to send to the channel
	 * @param userName If null, will use mattermost's default name "webhook"
	 * @return Null if ok, otherwise the error (usual as json string)
	 */
	@Nullable
	public String sendMessage(@NonNull String text, @Nullable String userName) throws IOException {
		JSONObject json = new JSONObject();
		json.put("text", text);
		json.put("username", userName);

		String response = post(json.toString());
		if (response.equals("ok")) {
			return null;
		}
		return response;
	}


	@SuppressWarnings("UnusedReturnValue")
	@NonNull
	private String post(@NonNull String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder()
				.url(webHookURL)
				.post(body)
				.build();
		ResponseBody response = client.newCall(request).execute().body();

		if (response == null) {
			throw new IOException("No response received from " + webHookURL);
		}

		return response.string();
	}
}
