package es.udc.ws.app.model.facebook;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class FacebookParser {
	public static String inputStreamtoString(InputStream input) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String toStringKey(InputStream entity, String key) {
		JSONObject json = (JSONObject) JSONSerializer
				.toJSON(inputStreamtoString(entity));
		return json.getString(key);
	}

	public static Long getLikesLength(InputStream entity) {
		JSONObject json = (JSONObject) JSONSerializer
				.toJSON(inputStreamtoString(entity));
		return Long.valueOf((long) json.getJSONArray("data").size());
	}

	public static String parseFacebookErrorMessage(InputStream entity) {
		String jsonString = inputStreamtoString(entity);
		String error = ((JSONObject) JSONSerializer.toJSON(jsonString))
				.getJSONObject("error").getString("message");
		return error;
	}
}
