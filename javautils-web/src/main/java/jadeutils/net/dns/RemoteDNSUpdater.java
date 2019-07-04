package jadeutils.net.dns;

import jadeutils.encryption.ByteArrayQueue;
import jadeutils.net.http.HttpPool;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RemoteDNSUpdater implements DNSUpdater {
	private HttpPool addrApi = null;

	public RemoteDNSUpdater(String url, int timeout) throws MalformedURLException {
		addrApi = new HttpPool(new URL(url), timeout);
	}

	@Override
	public List<String[]> updateDynamicRecords() {
		List<String[]> recs = new ArrayList<String[]>();
		ByteArrayQueue body = new ByteArrayQueue();
		try {
			int status = addrApi.get("?r=" + System.currentTimeMillis(), null,
					body, null);
			if (status >= 400) {
				System.out.println(body.toString());
			}
			try {
				JSONArray datas = new JSONArray(body.toString());
				for (int i = 0; i < datas.length(); i++) {
					JSONObject o = datas.getJSONObject(i);
					String key = o.getString("host");
					String value = o.getString("ip");
					String[] rec = { key, value };
					recs.add(rec);
				}
			} catch (JSONException e) {
				System.out.println(body.toString());
				e.printStackTrace();
			}
			try {
				Thread.sleep(5_000);
			} catch (InterruptedException e) {
				/* */
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return recs;
	}

}
