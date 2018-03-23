package com.dsuepke.mattermost;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author dsuepke
 */
@Getter
@Setter
@SuppressWarnings("WeakerAccess") // Only relevant in this small example with just one package
public class MattermostMessageAttachment {

	@NonNull
	private String fallback;
	@NonNull
	private String text;

	@Nullable
	private String title;
	@Nullable
	private URL thumbUrl;
	@Nullable
	private String color;

	@NonNull
	private List<AttachmentField> fields;


	public MattermostMessageAttachment(@NonNull String fallback, @NonNull String text) {
		this.fallback = fallback;
		this.text = text;

		fields = new ArrayList<>();
	}


	public void addField(boolean shortField, @Nullable String title, @Nullable String value) {
		fields.add(new AttachmentField(shortField, title, value));
	}


	@AllArgsConstructor
	@Getter
	@Setter
	public static class AttachmentField {
		private boolean shortField;

		private String title;

		private String value;
	}
}
