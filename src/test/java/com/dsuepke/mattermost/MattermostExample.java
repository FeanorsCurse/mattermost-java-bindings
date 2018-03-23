package com.dsuepke.mattermost;

import java.io.IOException;

import javax.annotation.Nullable;

/**
 * @author dsuepke
 */
public class MattermostExample {

	private static final String URL_BASE = "https://myUrl/hooks/";
	private static final String HOOK_TEST_CHANNEL = "myhook";

	private static final String BOT1 = "MyBot";

	public static void main(String[] args) throws IOException {
		MattermostWebHook hook = new MattermostWebHook(URL_BASE + HOOK_TEST_CHANNEL);

		// This should work if URL_BASE and HOOK_TEST_CHANNEL are properly set up
		printOnError(hook.sendMessage("Hello, World!", BOT1));
	}


	/**
	 * MattermostWebHook.sendMessage() will return a String in case of an error, otherwise null. Print the string so we know when something goes wrong.
	 *
	 * @param response Return value from MattermostWebHook.sendMessage()
	 */
	private static void printOnError(@Nullable String response) {
		if (response != null) {
			System.out.println(response);
		}
	}

}
