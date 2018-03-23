package com.dsuepke.mattermost;

import java.io.IOException;

import javax.annotation.Nullable;

import org.json.JSONArray;
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
@SuppressWarnings("WeakerAccess") // Only relevant in this small example with just one package
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
		return response + " \nJSon string sent: " + json.toString();
	}


	@Nullable
	public String sendMessageAttachment(@NonNull MattermostMessageAttachment attachment, @Nullable String userName) throws IOException {
		JSONObject json = new JSONObject();
		JSONArray jsonAttachments = new JSONArray();

		JSONObject jsonAttachment = new JSONObject();
		jsonAttachment.put("text", attachment.getText());
		jsonAttachment.put("username", userName);
		jsonAttachment.put("fallback", attachment.getFallback());
		jsonAttachment.putOpt("title", attachment.getTitle());
		jsonAttachment.putOpt("thumb_url", attachment.getThumbUrl());
		jsonAttachment.putOpt("color", attachment.getColor());

		JSONArray fields = new JSONArray();
		for (MattermostMessageAttachment.AttachmentField field : attachment.getFields()) {
			JSONObject jsonField = new JSONObject();
			jsonField.put("short", field.isShortField());
			jsonField.put("title", field.getTitle());
			jsonField.put("value", field.getValue());
			fields.put(jsonField);
		}
		jsonAttachment.put("fields", fields);
		jsonAttachments.put(jsonAttachment);
		json.put("attachments", jsonAttachments);

		String response = post(json.toString());
		if (response.equals("ok")) {
			return null;
		}
		return response + " \nJSon string sent: " + json.toString();
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
