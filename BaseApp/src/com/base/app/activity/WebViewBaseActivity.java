package com.base.app.activity;

import java.lang.reflect.InvocationTargetException;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import butterknife.Bind;

import com.base.app.R;
import com.base.app.utils.LogUtil;
import com.base.app.utils.NetWorkUtils;
import com.base.app.widget.pulltorefresh.PullToRefreshBase;
import com.base.app.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.base.app.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener2;
import com.base.app.widget.pulltorefresh.PullToRefreshWebView;

public class WebViewBaseActivity extends BaseActivity implements OnRefreshListener2<WebView>{

	@Bind(R.id.webview)
    PullToRefreshWebView mPullToRefreshWebView;
	WebView mWebView;
	//private String url = "http://www.baidu.com";
	
	private String code = "<blockquote>\n  <p>ת������������� <br>\n  <a href=\"http://blog.csdn.net/lmj623565791/article/details/49734867\">http://blog.csdn.net/lmj623565791/article/details/49734867</a>�� <br>\n  ���ĳ���:<a href=\"http://blog.csdn.net/lmj623565791/\">���ź���Ĳ��͡�</a></p>\n</blockquote>\n\n\n\n<h2 id=\"һ����\">һ������</h2>\n\n<p>֮ǰд��ƪ<a href=\"http://blog.csdn.net/lmj623565791/article/details/47911083\">Android OkHttp��ȫ���� ��ʱ�����˽�OkHttp��</a>����ʵ��Ҫ����Ϊokhttp���ռ����£���Ȼ����Ҳ�򵥷�װ�˹����࣬û�뵽��ע��ʹ�õ��˻�ͦ��ģ�����������飬�ù������еķ���Ҳ�Ǿ������������ط�����������ʹ�������������㣬ʵ�ڲ�����</p>\n\n<p>���ǣ��������ĩ�����ʱ��Ըù����࣬���������µĲ�����д��˳�������¹��ܣ������ܵ�������ʹ�������ķ����Ժ�����չ�ԡ�</p>\n\n<p>����ĸ��ƣ�Ҳ��ָ���Ƕ�����֮ǰ�Ĵ�����и��ơ�</p>\n\n<p>������okhttp���˽⣬����ͨ��<a href=\"http://blog.csdn.net/lmj623565791/article/details/47911083\">Android OkHttp��ȫ���� ��ʱ�����˽�OkHttp��</a>�����˽⡣</p>\n\n<p>ok����ôĿǰ���÷�װ��־֧�֣�</p>\n\n<ul>\n<li>һ���get����</li>\n<li>һ���post����</li>\n<li>����Http���ļ��ϴ�</li>\n<li>�ļ�����</li>\n<li>�ϴ����صĽ��Ȼص�</li>\n<li>����ͼƬ</li>\n<li>֧������ص���ֱ�ӷ��ض��󡢶��󼯺�</li>\n<li>֧��session�ı���</li>\n<li>֧����ǩ����վhttps�ķ��ʣ��ṩ����������֤�����</li>\n<li>֧��ȡ��ĳ������</li>\n</ul>\n\n<p>Դ���ַ��<a href=\"https://github.com/hongyangAndroid/okhttp-utils\">https://github.com/hongyangAndroid/okhttp-utils</a></p>\n\n<hr>\n\n<p>���룺</p>\n\n<ul>\n<li><p>Android Studio</p>\n\n<p>ʹ��ǰ������Android Studio���û�������ѡ�����:</p>\n\n<pre class=\"prettyprint\"><code class=\" hljs scss\">compile <span class=\"hljs-function\">project(<span class=\"hljs-string\">':okhttputils'</span>)</span></code></pre>\n\n<p>����</p>\n\n<pre class=\"prettyprint\"><code class=\" hljs bash\">compile <span class=\"hljs-string\">'com.zhy:okhttputils:2.0.0'</span></code></pre></li>\n<li><p>Eclipse</p>\n\n<p>����copyԴ�롣</p></li>\n</ul>\n\n<h2 id=\"�������÷�\">���������÷�</h2>\n\n<p>Ŀǰ�������÷���ʽΪ��</p>\n\n\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \">OkHttpUtils\n    .get()\n    .url(url)\n    .addParams(<span class=\"hljs-string\">\"username\"</span>, <span class=\"hljs-string\">\"hyman\"</span>)\n    .addParams(<span class=\"hljs-string\">\"password\"</span>, <span class=\"hljs-string\">\"123\"</span>)\n    .build()\n    .execute(callback);</code></pre>\n\n<p>ͨ����ʽȥ�����Լ�����Ҫ��Ӹ��ֲ�����������execute(callback)����ִ�У�����callback��������첽�����������execute()�����ͬ���ķ������á�</p>\n\n<p>���Կ�����ȡ����֮ǰһ�ѵ�get���ط���������Ҳ���Խ�������ѡ���ˡ�</p>\n\n<p>����򵥿�һ�£�ȫ�����÷���</p>\n\n<h3 id=\"1get����\">��1��GET����</h3>\n\n\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \">String url = <span class=\"hljs-string\">\"http://www.csdn.net/\"</span>;\nOkHttpUtils\n    .get()\n    .url(url)\n    .addParams(<span class=\"hljs-string\">\"username\"</span>, <span class=\"hljs-string\">\"hyman\"</span>)\n    .addParams(<span class=\"hljs-string\">\"password\"</span>, <span class=\"hljs-string\">\"123\"</span>)\n    .build()\n    .execute(<span class=\"hljs-keyword\">new</span> StringCallback()\n            {\n                <span class=\"hljs-annotation\">@Override</span>\n                <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onError</span>(Request request, Exception e)\n                {\n\n                }\n\n                <span class=\"hljs-annotation\">@Override</span>\n                <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onResponse</span>(String response)\n                {\n\n                }\n            });</code></pre>\n\n<h3 id=\"2post����\">��2��POST����</h3>\n\n\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \"> OkHttpUtils\n    .post()\n    .url(url)\n    .addParams(<span class=\"hljs-string\">\"username\"</span>, <span class=\"hljs-string\">\"hyman\"</span>)\n    .addParams(<span class=\"hljs-string\">\"password\"</span>, <span class=\"hljs-string\">\"123\"</span>)\n    .build()\n    .execute(callback);\n</code></pre>\n\n<h3 id=\"3post-string\">��3��Post String</h3>\n\n\n\n<pre class=\"prettyprint\"><code class=\" hljs avrasm\">OkHttpUtils\n    <span class=\"hljs-preprocessor\">.postString</span>()\n    <span class=\"hljs-preprocessor\">.url</span>(url)\n    <span class=\"hljs-preprocessor\">.content</span>(new Gson()<span class=\"hljs-preprocessor\">.toJson</span>(new User(<span class=\"hljs-string\">\"zhy\"</span>, <span class=\"hljs-string\">\"123\"</span>)))\n    <span class=\"hljs-preprocessor\">.build</span>()\n    <span class=\"hljs-preprocessor\">.execute</span>(new MyStringCallback())<span class=\"hljs-comment\">;   </span></code></pre>\n\n<p>��string��Ϊ�����崫�뵽����ˣ�����json�ַ�����</p>\n\n\n\n<h3 id=\"4post-file\">��4��Post File</h3>\n\n\n\n<pre class=\"prettyprint\"><code class=\" hljs avrasm\">OkHttpUtils\n    <span class=\"hljs-preprocessor\">.postFile</span>()\n    <span class=\"hljs-preprocessor\">.url</span>(url)\n    <span class=\"hljs-preprocessor\">.file</span>(file)\n    <span class=\"hljs-preprocessor\">.build</span>()\n    <span class=\"hljs-preprocessor\">.execute</span>(new MyStringCallback())<span class=\"hljs-comment\">;</span></code></pre>\n\n<p>��file��Ϊ�����崫�뵽�����.</p>\n\n<h3 id=\"5����post���ļ��ϴ�����web�ϵı�\">��5������POST���ļ��ϴ�������web�ϵı���</h3>\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \">OkHttpUtils.post()<span class=\"hljs-comment\">//</span>\n    .addFile(<span class=\"hljs-string\">\"mFile\"</span>, <span class=\"hljs-string\">\"messenger_01.png\"</span>, file)<span class=\"hljs-comment\">//</span>\n    .addFile(<span class=\"hljs-string\">\"mFile\"</span>, <span class=\"hljs-string\">\"test1.txt\"</span>, file2)<span class=\"hljs-comment\">//</span>\n    .url(url)\n    .params(params)<span class=\"hljs-comment\">//</span>\n    .headers(headers)<span class=\"hljs-comment\">//</span>\n    .build()<span class=\"hljs-comment\">//</span>\n    .execute(<span class=\"hljs-keyword\">new</span> MyStringCallback());</code></pre>\n\n<h3 id=\"6�����ļ�\">��6�������ļ�</h3>\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \">OkHttpUtils<span class=\"hljs-comment\">//</span>\n    .get()<span class=\"hljs-comment\">//</span>\n    .url(url)<span class=\"hljs-comment\">//</span>\n    .build()<span class=\"hljs-comment\">//</span>\n    .execute(<span class=\"hljs-keyword\">new</span> FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), <span class=\"hljs-string\">\"gson-2.2.1.jar\"</span>)<span class=\"hljs-comment\">//</span>\n    {\n        <span class=\"hljs-annotation\">@Override</span>\n        <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">inProgress</span>(<span class=\"hljs-keyword\">float</span> progress)\n        {\n            mProgressBar.setProgress((<span class=\"hljs-keyword\">int</span>) (<span class=\"hljs-number\">100</span> * progress));\n        }\n\n        <span class=\"hljs-annotation\">@Override</span>\n        <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onError</span>(Request request, Exception e)\n        {\n            Log.e(TAG, <span class=\"hljs-string\">\"onError :\"</span> + e.getMessage());\n        }\n\n        <span class=\"hljs-annotation\">@Override</span>\n        <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onResponse</span>(File file)\n        {\n            Log.e(TAG, <span class=\"hljs-string\">\"onResponse :\"</span> + file.getAbsolutePath());\n        }\n    });</code></pre>\n\n<h3 id=\"7��ʾͼƬ\">��7����ʾͼƬ</h3>\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \">OkHttpUtils\n    .get()<span class=\"hljs-comment\">//</span>\n    .url(url)<span class=\"hljs-comment\">//</span>\n    .build()<span class=\"hljs-comment\">//</span>\n    .execute(<span class=\"hljs-keyword\">new</span> BitmapCallback()\n    {\n        <span class=\"hljs-annotation\">@Override</span>\n        <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onError</span>(Request request, Exception e)\n        {\n            mTv.setText(<span class=\"hljs-string\">\"onError:\"</span> + e.getMessage());\n        }\n\n        <span class=\"hljs-annotation\">@Override</span>\n        <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onResponse</span>(Bitmap bitmap)\n        {\n            mImageView.setImageBitmap(bitmap);\n        }\n    });\n</code></pre>\n\n<p>����Ŀǰ�������������ˡ�</p>\n\n<h2 id=\"�������ϴ����صĻص�\">���������ϴ����صĻص�</h2>\n\n\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \"><span class=\"hljs-keyword\">new</span> Callback&lt;?&gt;()\n{\n    <span class=\"hljs-comment\">//...</span>\n    <span class=\"hljs-annotation\">@Override</span>\n    <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">inProgress</span>(<span class=\"hljs-keyword\">float</span> progress)\n    {\n       <span class=\"hljs-comment\">//use progress: 0 ~ 1</span>\n    }\n}</code></pre>\n\n<p>���ڴ����callback�и�inProgress��������Ҫ�õ�����ֱ�Ӹ�д�÷������ɡ�</p>\n\n<h2 id=\"�Ķ����Զ�����Ϊʵ����\">�ġ������Զ�����Ϊʵ����</h2>\n\n<p>Ŀǰȥ����Gson���������ṩ���Զ���Callback�ķ�ʽ�����û��Լ�ȥ�������ص����ݣ�Ŀǰ�ṩ��<code>StringCallback</code>��<code>FileCallback</code>,<code>BitmapCallback</code> �ֱ����ڷ���string���ļ����أ�����ͼƬ��</p>\n\n<p>��Ȼ�����ϣ������Ϊ��������ԣ�</p>\n\n\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">abstract</span> <span class=\"hljs-class\"><span class=\"hljs-keyword\">class</span> <span class=\"hljs-title\">UserCallback</span> <span class=\"hljs-keyword\">extends</span> <span class=\"hljs-title\">Callback</span>&lt;<span class=\"hljs-title\">User</span>&gt;\n{</span>\n    <span class=\"hljs-comment\">//��UI�̣߳�֧���κκ�ʱ����</span>\n    <span class=\"hljs-annotation\">@Override</span>\n    <span class=\"hljs-keyword\">public</span> User <span class=\"hljs-title\">parseNetworkResponse</span>(Response response) <span class=\"hljs-keyword\">throws</span> IOException\n    {\n        String string = response.body().string();\n        User user = <span class=\"hljs-keyword\">new</span> Gson().fromJson(string, User.class);\n        <span class=\"hljs-keyword\">return</span> user;\n    }\n}</code></pre>\n\n<p>�Լ�ʹ���Լ�ϲ����Json��������ɼ��ɡ�</p>\n\n<p>������<code>List&lt;User&gt;</code>�������£�</p>\n\n\n\n<pre class=\"prettyprint\"><code class=\" hljs php\"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">abstract</span> <span class=\"hljs-class\"><span class=\"hljs-keyword\">class</span> <span class=\"hljs-title\">ListUserCallback</span> <span class=\"hljs-keyword\">extends</span> <span class=\"hljs-title\">Callback</span>&lt;<span class=\"hljs-title\">List</span>&lt;<span class=\"hljs-title\">User</span>&gt;&gt;\n{</span>\n    @Override\n    <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">List</span>&lt;User&gt; parseNetworkResponse(Response response) throws IOException\n    {\n        String string = response.body().string();\n        <span class=\"hljs-keyword\">List</span>&lt;User&gt; user = <span class=\"hljs-keyword\">new</span> Gson().fromJson(string, <span class=\"hljs-keyword\">List</span>.class);\n        <span class=\"hljs-keyword\">return</span> user;\n    }\n\n\n}</code></pre>\n\n<h2 id=\"�����https������֤\">�塢����https������֤</h2>\n\n<p>�ǳ��򵥣��õ�xxx.cert��֤�顣</p>\n\n<p>Ȼ�����</p>\n\n\n\n<pre class=\"prettyprint\"><code class=\"language-xml hljs \">\nOkHttpUtils.getInstance()\n        .setCertificates(inputstream);</code></pre>\n\n<p>����ʹ�÷�ʽ�������ҵ�֤�����assetsĿ¼��</p>\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \">\n<span class=\"hljs-javadoc\">/**\n * Created by zhy on 15/8/25.\n */</span>\n<span class=\"hljs-keyword\">public</span> <span class=\"hljs-class\"><span class=\"hljs-keyword\">class</span> <span class=\"hljs-title\">MyApplication</span> <span class=\"hljs-keyword\">extends</span> <span class=\"hljs-title\">Application</span>\n{</span>\n    <span class=\"hljs-annotation\">@Override</span>\n    <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onCreate</span>()\n    {\n        <span class=\"hljs-keyword\">super</span>.onCreate();\n\n        <span class=\"hljs-keyword\">try</span>\n        {    \n        OkHttpUtils\n         .getInstance()\n         .setCertificates(getAssets().open(<span class=\"hljs-string\">\"aaa.cer\"</span>),\n getAssets().open(<span class=\"hljs-string\">\"server.cer\"</span>));\n        } <span class=\"hljs-keyword\">catch</span> (IOException e)\n        {\n            e.printStackTrace();\n        }\n    }\n}</code></pre>\n\n<p>���ɡ�������ע��Application��</p>\n\n<p>ע�⣺���https��վΪȨ�������䷢��֤�飬����Ҫ�������á���ǩ����֤�����Ҫ��</p>\n\n<h2 id=\"������\">��������</h2>\n\n<h3 id=\"1ȫ������\">��1��ȫ������</h3>\n\n<p>������Application�У�ͨ����</p>\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \">OkHttpClient client = \nOkHttpUtils.getInstance().getOkHttpClient();</code></pre>\n\n<p>Ȼ�����client�ĸ���set������</p>\n\n<p>���磺</p>\n\n\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \">client.setConnectTimeout(<span class=\"hljs-number\">100000</span>, TimeUnit.MILLISECONDS);</code></pre>\n\n\n\n<h3 id=\"2Ϊ�����������ó�ʱ\">��2��Ϊ�����������ó�ʱ</h3>\n\n<p>�����漰���ļ�����Ҫ���ö�д�ȴ�ʱ���һ�㡣</p>\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \"> OkHttpUtils\n    .get()<span class=\"hljs-comment\">//</span>\n    .url(url)<span class=\"hljs-comment\">//</span>\n    .tag(<span class=\"hljs-keyword\">this</span>)<span class=\"hljs-comment\">//</span>\n    .build()<span class=\"hljs-comment\">//</span>\n    .connTimeOut(<span class=\"hljs-number\">20000</span>)\n    .readTimeOut(<span class=\"hljs-number\">20000</span>)\n    .writeTimeOut(<span class=\"hljs-number\">20000</span>)\n    .execute()</code></pre>\n\n<p>����build()֮�󣬿����漴���ø���timeOut.</p>\n\n<h3 id=\"3ȡ����������\">��3��ȡ����������</h3>\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \"> RequestCall call = OkHttpUtils.get().url(url).build();\n call.cancel();\n</code></pre>\n\n\n\n<h3 id=\"4����tagȡ������\">��4������tagȡ������</h3>\n\n<p>Ŀǰ����֧�ֵķ�������������һ������<code>Object tag</code>��ȡ����ͨ��<code>OkHttpUtils.cancelTag(tag)</code>ִ�С�</p>\n\n<p>���磺��Activity�У���Activity����ȡ������</p>\n\n<pre class=\"prettyprint\"><code class=\" hljs java\">OkHttpUtils\n    .get()<span class=\"hljs-comment\">//</span>\n    .url(url)<span class=\"hljs-comment\">//</span>\n    .tag(<span class=\"hljs-keyword\">this</span>)<span class=\"hljs-comment\">//</span>\n    .build()<span class=\"hljs-comment\">//</span>\n\n<span class=\"hljs-annotation\">@Override</span>\n<span class=\"hljs-keyword\">protected</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onDestroy</span>()\n{\n    <span class=\"hljs-keyword\">super</span>.onDestroy();\n    <span class=\"hljs-comment\">//����ȡ��ͬһ��tag��</span>\n    OkHttpUtils.cancelTag(<span class=\"hljs-keyword\">this</span>);<span class=\"hljs-comment\">//ȡ����Activity.this��Ϊtag������</span>\n}</code></pre>\n\n<p>���磬��ǰActivityҳ�����е�������Activity������Ϊtag��������onDestory����ͳһȡ����</p>\n\n<h2 id=\"��ǳ̸��װ\">�ߡ�ǳ̸��װ</h2>\n\n<p>��ʵ������װ�Ĺ��̱Ƚϼ򵥣�����������£�����okhttpһ����������̴����������ģ�</p>\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \"><span class=\"hljs-comment\">//����okHttpClient����</span>\nOkHttpClient mOkHttpClient = <span class=\"hljs-keyword\">new</span> OkHttpClient();\n<span class=\"hljs-comment\">//����һ��Request</span>\n<span class=\"hljs-keyword\">final</span> Request request = <span class=\"hljs-keyword\">new</span> Request.Builder()\n                .url(<span class=\"hljs-string\">\"https://github.com/hongyangAndroid\"</span>)\n                .build();\n<span class=\"hljs-comment\">//new call</span>\nCall call = mOkHttpClient.newCall(request); \n<span class=\"hljs-comment\">//����������</span>\ncall.enqueue(<span class=\"hljs-keyword\">new</span> Callback()\n{\n    <span class=\"hljs-annotation\">@Override</span>\n    <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onFailure</span>(Request request, IOException e)\n    {\n    }\n\n    <span class=\"hljs-annotation\">@Override</span>\n    <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onResponse</span>(<span class=\"hljs-keyword\">final</span> Response response) <span class=\"hljs-keyword\">throws</span> IOException\n    {\n            <span class=\"hljs-comment\">//String htmlStr =  response.body().string();</span>\n    }\n});             \n</code></pre>\n\n<p>������Ҫ�Ĳ��죬��ʵ����request�Ĺ������̡�</p>\n\n<p>�Ҷ�Request������һ���ࣺ<code>OkHttpRequest</code></p>\n\n\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">abstract</span> <span class=\"hljs-class\"><span class=\"hljs-keyword\">class</span> <span class=\"hljs-title\">OkHttpRequest</span>\n{</span>\n    <span class=\"hljs-keyword\">protected</span> RequestBody requestBody;\n    <span class=\"hljs-keyword\">protected</span> Request request;\n\n    <span class=\"hljs-keyword\">protected</span> String url;\n    <span class=\"hljs-keyword\">protected</span> String tag;\n    <span class=\"hljs-keyword\">protected</span> Map&lt;String, String&gt; params;\n    <span class=\"hljs-keyword\">protected</span> Map&lt;String, String&gt; headers;\n\n    <span class=\"hljs-keyword\">protected</span> <span class=\"hljs-title\">OkHttpRequest</span>(String url, String tag,\n                            Map&lt;String, String&gt; params, Map&lt;String, String&gt; headers)\n    {\n        <span class=\"hljs-keyword\">this</span>.url = url;\n        <span class=\"hljs-keyword\">this</span>.tag = tag;\n        <span class=\"hljs-keyword\">this</span>.params = params;\n        <span class=\"hljs-keyword\">this</span>.headers = headers;\n    }\n\n    <span class=\"hljs-keyword\">protected</span> <span class=\"hljs-keyword\">abstract</span> Request <span class=\"hljs-title\">buildRequest</span>();\n    <span class=\"hljs-keyword\">protected</span> <span class=\"hljs-keyword\">abstract</span> RequestBody <span class=\"hljs-title\">buildRequestBody</span>();\n\n    <span class=\"hljs-keyword\">protected</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">prepareInvoked</span>(ResultCallback callback)\n    {\n        requestBody = buildRequestBody();\n        requestBody = wrapRequestBody(requestBody, callback);\n        request = buildRequest();\n    }\n\n    <span class=\"hljs-keyword\">protected</span> RequestBody <span class=\"hljs-title\">wrapRequestBody</span>(RequestBody requestBody, <span class=\"hljs-keyword\">final</span> ResultCallback callback)\n    {\n        <span class=\"hljs-keyword\">return</span> requestBody;\n    }\n\n\n    <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">invokeAsyn</span>(ResultCallback callback)\n    {\n        prepareInvoked(callback);\n        mOkHttpClientManager.execute(request, callback);\n    }\n\n\n     <span class=\"hljs-comment\">// other common methods</span>\n }   \n</code></pre>\n\n<p>һ��request�Ĺ����أ��ҷ��������裺<code>buildRequestBody</code> , <code>wrapRequestBody</code> ,<code>buildRequest</code>�����Ĵ��򣬵�������������û������ʱ�����Ǿ��õ���request��Ȼ��ִ�м��ɡ�</p>\n\n<p>���Ƕ��ڲ�ͬ������requestBody�Լ�request�Ĺ��������ǲ�ͬ�ģ����Դ�ҿ��Կ���<code>buildRequestBody</code>  ,<code>buildRequest</code>Ϊ����ķ�����Ҳ���ǲ�ͬ�������࣬����<code>OkHttpGetRequest</code>��<code>OkHttpPostRequest</code>����Ҫ�Լ�ȥ�����Լ���request��</p>\n\n<p>����<code>wrapRequestBody</code>�����أ����Կ�����Ĭ�ϻ������ڿ�ʵ�֣���Ҫ����Ϊ�������е������඼��Ҫ��д����ֻ���ϴ���ʱ���أ���Ҫ�ص����ȣ���Ҫ��requestBody���а�װ�������������������һ�����ӡ�</p>\n\n<p>��ʵ��������е�����ģ�巽��ģʽ������Ȥ���Կ���һ����ƪ����<a href=\"http://blog.csdn.net/lmj623565791/article/details/26276093\">���ģʽ ģ�淽��ģʽ չ�ֳ���Ա��һ��</a> .</p>\n\n<p>���ڸ�����ϸ���÷������Բ鿴github�����readme���Լ�demo��Ŀǰdemo������</p>\n\n<p><img src=\"http://img.blog.csdn.net/20151109094247142\" width=\"320px\"></p>\n\n<p>�����ϴ��ļ���������ť����Ҫ�Լ���������������İ�ť����ֱ�Ӳ��ԡ�</p>\n\n<p>������ڱ���ˮƽ���ޣ��Լ�ʱ��Ƚϲִ�~~�������⣬��ӭ��issue���һ��ʱ������ have a nice day ~</p>\n\n<p><a href=\"https://github.com/hongyangAndroid/okhttp-utils\">Դ��������</a></p>\n\n<hr>\n\n<p>��ӭ��ע�ҵ�΢���� <br>\n<a href=\"http://weibo.com/u/3165018720\">http://weibo.com/u/3165018720</a></p>\n\n<hr>\n\n<blockquote>\n  <p>Ⱥ�ţ�<font color=\"red\">514447580</font>����ӭ��Ⱥ</p>\n  \n  <p>΢�Ź��ںţ�hongyangAndroid <br>\n  ����ӭ��ע����һʱ�����Ͳ�����Ϣ�� <br>\n  <img src=\"http://img.my.csdn.net/uploads/201501/30/1422600516_2905.jpg\" width=\"200px\"></p>\n</blockquote>";

	// ��WebView����ҳ��֮ǰ������ѡͬ��Cookie��Ȼ���ټ�����ҳ
	private void setWebCookies() {
		// print cookie
		// System.out.println(cookieManager.getCookie("http://xxx.xxx.xxx"));
		// ad.a("WebViewBaseActivity", "set cookie..." + str);
		CookieSyncManager.createInstance(this);
		CookieManager mCookieManager = CookieManager.getInstance();
		mCookieManager.setAcceptCookie(true);
		mCookieManager.setCookie("app.zz.com", "uin=");
		mCookieManager.setCookie("app.zz.com", "skey=");
		CookieSyncManager.getInstance().sync();
	}

	// �������cookie
	// private void removeAllCookie() {
	// CookieSyncManager cookieSyncManager = CookieSyncManager
	// .createInstance(this);
	// CookieManager cookieManager = CookieManager.getInstance();
	// cookieManager.setAcceptCookie(true);
	// cookieManager.removeSessionCookie();
	// String testcookie1 = cookieManager.getCookie("");
	// cookieManager.removeAllCookie();
	// cookieSyncManager.sync();
	// String testcookie2 = cookieManager.getCookie("");
	// }

	@JavascriptInterface
	@SuppressLint({ "SetJavaScriptEnabled" })
	@Override
	public void init() {
		setContentView(R.layout.activity_webview);
		mPullToRefreshWebView.setMode(Mode.BOTH);
		mWebView = mPullToRefreshWebView.getRefreshableView();
		mPullToRefreshWebView.setOnRefreshListener(this);
		mPullToRefreshWebView.getLoadingLayoutProxy().setPullLabel("�����رգ�");
		setWebSetting();
		mPullToRefreshWebView.setRefreshing();
	}
	@SuppressLint("NewApi") 
	public void setWebSetting() {
		if (NetWorkUtils.isNetworkAvailable(this)) {
			mWebView.getSettings().setJavaScriptEnabled(true);
			mWebView.getSettings().setUseWideViewPort(true);// ���ô����ԣ��������������
			//mWebView.getSettings().setLoadWithOverviewMode(true);// ��������Ļ�Ĵ�С
			mWebView.getSettings().setDefaultTextEncodingName("UTF-8") ;
			mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // webview�л���
			mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
			mWebView.setHorizontalScrollBarEnabled(false);
			mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(
					true);// ֧��ͨ��JS���´���
			mWebView.requestFocusFromTouch();
			if (Build.VERSION.SDK_INT >= 11)// ��ֹ����ע��©��
				mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
			 mWebView.setWebChromeClient(new CustomChromeClient());
			 mWebView.setWebViewClient(new CustomWebClient());
		} else {
			mWebView.getSettings().setCacheMode(
					WebSettings.LOAD_CACHE_ELSE_NETWORK);
		}
	}

	protected void onDestroy() {
		LogUtil.e("WebViewBaseActivity", "onDestroy");
		mWebView.clearAnimation();
		mWebView.freeMemory();
		mWebView.destroyDrawingCache();
		System.gc();
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		LogUtil.e("WebViewBaseActivity", "onKeyDown");
		if ((keyCode == KeyEvent.KEYCODE_BACK) && (mWebView.canGoBack()))
		{
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	protected void onPause() {
		super.onPause();
		LogUtil.e("WebViewBaseActivity", "onPause");
		mWebView.pauseTimers();
		if (isFinishing()) {
			mWebView.loadUrl("about:blank");
			setContentView(new FrameLayout(this));
		}
		doMethod("onPause");
	}

	protected void onResume() {
		super.onResume();
		mWebView.resumeTimers();
		doMethod("onResume");
	}

	private void doMethod(String method) {
		if (mWebView != null) {
			try {
				WebView.class.getMethod(method).invoke(mWebView, new Class[0]);
			} catch (NoSuchMethodException localNoSuchMethodException) {
				Log.i("No such method: " + method,
						localNoSuchMethodException.toString());
			} catch (IllegalAccessException localIllegalAccessException) {
				Log.i("Illegal Access: " + method,
						localIllegalAccessException.toString());
			} catch (InvocationTargetException localInvocationTargetException) {
				Log.d("Invocation Target Exception: " + method,
						localInvocationTargetException.toString());
			}
		}
	}
	
	class CustomWebClient extends WebViewClient
	{
		
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			LogUtil.w("WebViewBaseActivity", "onPageStarted-->" + url);
			super.onPageStarted(view, url, favicon);
		}
		
		@Override
		public void onPageFinished(WebView view, String url) {
			//WebViewBaseActivity.this.url = url;
		    LogUtil.w("WebViewBaseActivity", "onPageFinished-->" + url);
			super.onPageFinished(view, url);
		}
		
		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler,
				SslError error) {
			  //handler.cancel(); Ĭ�ϵĴ���ʽ��WebView��ɿհ�ҳ
	        	handler.proceed();//����֤��
	        	//view.loadUrl("file:///android_asset/error_page.htm");
			super.onReceivedSslError(view, handler, error);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			view.loadUrl("file:///android_asset/error_page.htm");
//			super.onReceivedError(view, errorCode, description, failingUrl);
		}
		
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return super.shouldOverrideUrlLoading(view, url);
		}
	}

	class CustomChromeClient extends WebChromeClient
	{
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				if(mPullToRefreshWebView!=null)
				{
					mPullToRefreshWebView.onRefreshComplete();
				}
			}
			super.onProgressChanged(view, newProgress);
		}
	}

//	@Override
//	public void onRefresh(PullToRefreshBase<WebView> refreshView) {
//		if(url!=null&&!"".equals(url))
//		{
//			mWebView.loadUrl(url);
//		}
//	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<WebView> refreshView) {
//		if(url!=null&&!"".equals(url))
//		{
//			mWebView.loadUrl(url);
//		}
		mWebView.loadData(code, "text/html; charset=UTF-8", null);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<WebView> refreshView) {
		this.finish();
	}
}