package com.pfly.util;

import java.io.IOException;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class GcmHelper {

	private GcmHelper() {
		// forbid instantiation
	}

	public static boolean sendNotification(String userGcmId, String type, String id, String title, String message) {
		Message.Builder messageBuilder = new Message.Builder();
		messageBuilder.addData("type", type);
		messageBuilder.addData("taskId", id);
		messageBuilder.addData("title", title);
		messageBuilder.addData("message", message);
		messageBuilder.addData("taskActionType", type);

		System.out.println("user:" + userGcmId);
		
		Message gcmMessage = messageBuilder.build();
		Sender sender = new Sender(GcmConstants.GCM_API_KEY);
		try {
			Result result = sender.send(gcmMessage, userGcmId, 5);
			System.out.println("result - " + result.toString());
			System.out.println(result.getMessageId());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
