package org.bigbluebutton.common.messages;

import java.util.HashMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UserSharedWebcamMessage implements ISubscribedMessage {
  public static final String USER_SHARED_WEBCAM  = "user_shared_webcam_message";
  public static final String VERSION = "0.0.1";

  public final String meetingId;
  public final String userId;
  public final String stream;

  public UserSharedWebcamMessage(String meetingId, String userId, String stream) {
    this.meetingId = meetingId;
    this.userId = userId;
    this.stream = stream;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(Constants.MEETING_ID, meetingId);
    payload.put(Constants.USER_ID, userId);
    payload.put(Constants.STREAM, stream);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(USER_SHARED_WEBCAM, VERSION, null);

    return MessageBuilder.buildJson(header, payload);
  }

  public static UserSharedWebcamMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (USER_SHARED_WEBCAM.equals(messageName)) {
          if (payload.has(Constants.MEETING_ID)
            && payload.has(Constants.USER_ID)
            && payload.has(Constants.STREAM)) {
            String id = payload.get(Constants.MEETING_ID).getAsString();
            String userid = payload.get(Constants.USER_ID).getAsString();
            String stream = payload.get(Constants.STREAM).getAsString();
            return new UserSharedWebcamMessage(id, userid, stream);
          }
        }
      }
    }
    return null;

  }
}