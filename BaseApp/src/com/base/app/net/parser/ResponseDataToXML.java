package com.base.app.net.parser;

import java.util.Map;

import com.base.app.net.manager.RequestListenerHolder;
import com.base.app.net.manager.RequestManager.RequestListener;
import com.base.app.utils.LogUtil;

public class ResponseDataToXML extends RequestListenerHolder{

	public ResponseDataToXML(){}
	public ResponseDataToXML(RequestListener requestListener) {
		super(requestListener);
	}
	
	@Override
	public void onSuccessToParser(RequestListener requestListener,
			String parsed, Class<?> respClass, Map<String, String> headers,
			String url, int actionId) {
		LogUtil.e("info","xml ½âÎö£º"+"ClassName:"+ respClass.getName()+";parsed:"+parsed);
		super.onSuccessToParser(requestListener, parsed, respClass, headers, url, actionId);
	}

}
