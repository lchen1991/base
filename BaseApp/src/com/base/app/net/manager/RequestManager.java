package com.base.app.net.manager;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;

/**
 * RequestManager
 * 
 */
public class RequestManager {

	private static final int TIMEOUT_COUNT = 10 * 1000;

	private static final int RETRY_TIMES = 1;

	private volatile static RequestManager INSTANCE = null;

	private RequestQueue mRequestQueue = null;
	
	private RequestListenerHolder requestListenerHolder = null;

	/**
	 * Request Result Callback
	 */
	public interface RequestListener {

		void onRequest();

		void onSuccess(Object response, Map<String, String> headers,
					   String url, int actionId);

		void onError(String errorMsg, String url, int actionId);
	}

	private RequestManager() {

	}

	public void init(Context context) {
		this.mRequestQueue = Volley.newRequestQueue(context);
	}

	/**
	 * SingleTon
	 * 
	 * @return  single Instance
	 */
	public static RequestManager getInstance() {
		if (null == INSTANCE) {
			synchronized (RequestManager.class) {
				if (null == INSTANCE) {
					INSTANCE = new RequestManager();
				}
			}
		}
		return INSTANCE;
	}

	public RequestQueue getRequestQueue() {
		return this.mRequestQueue;
	}

	/**
	 * default get method
	 * 
	 * @param url
	 * @param requestListener
	 * @param actionId
	 * @return LoadControler object
	 */
	public LoadControler get(String url, RequestListener requestListener,Class<?> respClass,
			int actionId) {
		
		return this.get(url, requestListener,respClass, true, actionId);
	}

	public LoadControler get(String url, RequestListener requestListener,Class<?> respClass,
			boolean shouldCache, int actionId) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("If-Modified-Since", "Tue, 15 Dec 2015 15:23:52 GMT+00:00");
		headers.put("version", "1.7");
		headers.put("User-Agent", "Dalvik/1.6.0 (Linux; U; Android 4.4.4; OPPO R7 Build/KTU84P)");
		headers.put("Connection", "Keep-Alive");
		headers.put("Accept-Encoding", "gzip");
		return this.request(Method.GET, url, null, headers, requestListener,respClass,
				shouldCache, TIMEOUT_COUNT, RETRY_TIMES, actionId);
	}

	/**
	 * default post method
	 *
	 * @param url
	 * @param data
	 *            String, Map<String, String> or RequestMap(with file)
	 * @param requestListener
	 * @param actionId
	 * @return LoadControler object
	 */
	public LoadControler post(final String url, Object data,
			final RequestListener requestListener,Class<?> respClass, int actionId) {
		return this.post(url, data, requestListener,respClass, false, TIMEOUT_COUNT,
				RETRY_TIMES, actionId);
	}

	/**
	 *
	 * @param url
	 * @param data
	 *            String, Map<String, String> or RequestMap(with file)
	 * @param requestListener
	 * @param shouldCache
	 * @param timeoutCount
	 * @param retryTimes
	 * @param actionId
	 * @return LoadControler object
	 */
	public LoadControler post(final String url, Object data,
			final RequestListener requestListener,Class<?> respClass, boolean shouldCache,
			int timeoutCount, int retryTimes, int actionId) {
		return request(Method.POST, url, data, null, requestListener,respClass,
				shouldCache, timeoutCount, retryTimes, actionId);
	}

	/**
	 * request
	 *
	 * @param method
	 *            mainly Method.POST and Method.GET
	 * @param url
	 *            target url
	 * @param data
	 *            request params
	 * @param headers
	 *            request headers
	 * @param requestListener
	 *            request callback
	 * @param shouldCache
	 *            useCache
	 * @param timeoutCount
	 *            reqeust timeout count
	 * @param retryTimes
	 *            reqeust retry times
	 * @param actionId 
	 *            request id
	 * @return LoadControler object
	 */
	public LoadControler request(int method, final String url, Object data,
			final Map<String, String> headers,
			final RequestListener requestListener, Class<?> respClass,boolean shouldCache,
			int timeoutCount, int retryTimes, int actionId) {
		return this.sendRequest(method, url, data, headers,
				getParser(requestListener) ,respClass, shouldCache,
				timeoutCount, retryTimes, actionId);
	}
	
	/**
	 * @param method
	 * @param url
	 * @param data
	 * @param headers
	 * @param requestListener
	 * @param shouldCache
	 * @param timeoutCount
	 * @param retryTimes
	 * @param actionId
	 * @return LoadControler object
	 */
	public LoadControler sendRequest(int method, final String url, Object data,
			final Map<String, String> headers,
			final LoadListener requestListener,final Class<?> respClass, boolean shouldCache,
			int timeoutCount, int retryTimes, int actionId) {
		if (requestListener == null)
			throw new NullPointerException();

		final ByteArrayLoadControler loadControler = new ByteArrayLoadControler(
				requestListener,respClass, actionId);
		
		Request<?> request = null;
		if (data != null && data instanceof RequestMap) {// force POST and No
															// Cache
			request = new ByteArrayRequest(Method.POST, url, data,
					loadControler, loadControler);
			request.setShouldCache(false);
		} else {
			request = new ByteArrayRequest(method, url, data, loadControler,
					loadControler);
			request.setShouldCache(shouldCache);
		}

		if (headers != null && !headers.isEmpty()) {// add headers if not empty
			try {
				request.getHeaders().putAll(headers);
			} catch (AuthFailureError e) {
				e.printStackTrace();
			}
		}

		RetryPolicy retryPolicy = new DefaultRetryPolicy(timeoutCount,
				retryTimes, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		request.setRetryPolicy(retryPolicy);
		loadControler.bindRequest(request);

		if (this.mRequestQueue == null)
			throw new NullPointerException();
		requestListener.onStart();
		this.mRequestQueue.add(request);
		
		return loadControler;
	}

	private RequestListenerHolder getParser(RequestListener requestListener)
	{
		if(requestListenerHolder == null)
		{
			requestListenerHolder = new RequestListenerHolder(requestListener);
		}
		requestListenerHolder.mRequestListener = requestListener;
		return requestListenerHolder;
	}
	
	public void setParser(RequestListenerHolder requestListenerHolder)
	{
		this.requestListenerHolder = requestListenerHolder;
	}
	
}
