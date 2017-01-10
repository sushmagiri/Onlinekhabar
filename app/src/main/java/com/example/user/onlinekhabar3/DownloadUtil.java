package com.example.user.onlinekhabar3;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class DownloadUtil {
	public static String NotOnline = "1";

	private String link;
	private Context context;

	public DownloadUtil(String link, Context context) {
		this.link = link;
		this.context = context;
	}

	public String downloadStringContent() {
		if (isOnline()) {
				String responseString = "";
				// Making HTTP request
				try {
					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(link);

					HttpResponse httpResponse = httpClient.execute(httpGet);
					HttpEntity httpEntity = httpResponse.getEntity();

					responseString = EntityUtils.toString(httpEntity);
				} catch (Exception ignored) {}

				return responseString;
		} else
			return NotOnline;
	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}
}
