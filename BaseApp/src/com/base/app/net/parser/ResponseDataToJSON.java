package com.base.app.net.parser;

import java.util.Map;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.base.app.net.manager.RequestListenerHolder;
import com.base.app.net.manager.RequestManager.RequestListener;

public class ResponseDataToJSON extends RequestListenerHolder{

	public ResponseDataToJSON(){}
	public ResponseDataToJSON(RequestListener requestListener) {
		super(requestListener);
	}

	@Override
	public void onSuccessToParser(RequestListener requestListener,
			String parsed, Class<?> respClass, Map<String, String> headers,
			String url, int actionId) {
		Log.e("info","json 解析："+"ClassName:"+ respClass.getName()+";parsed:"+parsed);
		Object object = null;
		try {
			   object = JSON.parseObject(parsed,respClass);
		} catch (Exception e) {
		}
		finally
		{
			if(object == null) onError("数据解析异常!", url, actionId);
		}
		requestListener.onSuccess(object, headers, url, actionId);
	}
}
