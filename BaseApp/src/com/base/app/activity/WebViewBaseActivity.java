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
	
	private String code = "<blockquote>\n  <p>转载请标明出处： <br>\n  <a href=\"http://blog.csdn.net/lmj623565791/article/details/49734867\">http://blog.csdn.net/lmj623565791/article/details/49734867</a>； <br>\n  本文出自:<a href=\"http://blog.csdn.net/lmj623565791/\">【张鸿洋的博客】</a></p>\n</blockquote>\n\n\n\n<h2 id=\"一概述\">一、概述</h2>\n\n<p>之前写了篇<a href=\"http://blog.csdn.net/lmj623565791/article/details/47911083\">Android OkHttp完全解析 是时候来了解OkHttp了</a>，其实主要是作为okhttp的普及文章，当然里面也简单封装了工具类，没想到关注和使用的人还挺多的，由于这股热情，该工具类中的方法也是剧增，各种重载方法，以致于使用起来极不方便，实在惭愧。</p>\n\n<p>于是，在这个周末，抽点时间对该工具类，进行了重新的拆解与编写，顺便完善下功能，尽可能的提升其使用起来的方便性和易扩展性。</p>\n\n<p>标题的改善，也是指的是对于我之前的代码进行改善。</p>\n\n<p>如果你对okhttp不了解，可以通过<a href=\"http://blog.csdn.net/lmj623565791/article/details/47911083\">Android OkHttp完全解析 是时候来了解OkHttp了</a>进行了解。</p>\n\n<p>ok，那么目前，该封装库志支持：</p>\n\n<ul>\n<li>一般的get请求</li>\n<li>一般的post请求</li>\n<li>基于Http的文件上传</li>\n<li>文件下载</li>\n<li>上传下载的进度回调</li>\n<li>加载图片</li>\n<li>支持请求回调，直接返回对象、对象集合</li>\n<li>支持session的保持</li>\n<li>支持自签名网站https的访问，提供方法设置下证书就行</li>\n<li>支持取消某个请求</li>\n</ul>\n\n<p>源码地址：<a href=\"https://github.com/hongyangAndroid/okhttp-utils\">https://github.com/hongyangAndroid/okhttp-utils</a></p>\n\n<hr>\n\n<p>引入：</p>\n\n<ul>\n<li><p>Android Studio</p>\n\n<p>使用前，对于Android Studio的用户，可以选择添加:</p>\n\n<pre class=\"prettyprint\"><code class=\" hljs scss\">compile <span class=\"hljs-function\">project(<span class=\"hljs-string\">':okhttputils'</span>)</span></code></pre>\n\n<p>或者</p>\n\n<pre class=\"prettyprint\"><code class=\" hljs bash\">compile <span class=\"hljs-string\">'com.zhy:okhttputils:2.0.0'</span></code></pre></li>\n<li><p>Eclipse</p>\n\n<p>自行copy源码。</p></li>\n</ul>\n\n<h2 id=\"二基本用法\">二、基本用法</h2>\n\n<p>目前基本的用法格式为：</p>\n\n\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \">OkHttpUtils\n    .get()\n    .url(url)\n    .addParams(<span class=\"hljs-string\">\"username\"</span>, <span class=\"hljs-string\">\"hyman\"</span>)\n    .addParams(<span class=\"hljs-string\">\"password\"</span>, <span class=\"hljs-string\">\"123\"</span>)\n    .build()\n    .execute(callback);</code></pre>\n\n<p>通过链式去根据自己的需要添加各种参数，最后调用execute(callback)进行执行，传入callback则代表是异步。如果单纯的execute()则代表同步的方法调用。</p>\n\n<p>可以看到，取消了之前一堆的get重载方法，参数也可以进行灵活的选择了。</p>\n\n<p>下面简单看一下，全部的用法：</p>\n\n<h3 id=\"1get请求\">（1）GET请求</h3>\n\n\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \">String url = <span class=\"hljs-string\">\"http://www.csdn.net/\"</span>;\nOkHttpUtils\n    .get()\n    .url(url)\n    .addParams(<span class=\"hljs-string\">\"username\"</span>, <span class=\"hljs-string\">\"hyman\"</span>)\n    .addParams(<span class=\"hljs-string\">\"password\"</span>, <span class=\"hljs-string\">\"123\"</span>)\n    .build()\n    .execute(<span class=\"hljs-keyword\">new</span> StringCallback()\n            {\n                <span class=\"hljs-annotation\">@Override</span>\n                <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onError</span>(Request request, Exception e)\n                {\n\n                }\n\n                <span class=\"hljs-annotation\">@Override</span>\n                <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onResponse</span>(String response)\n                {\n\n                }\n            });</code></pre>\n\n<h3 id=\"2post请求\">（2）POST请求</h3>\n\n\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \"> OkHttpUtils\n    .post()\n    .url(url)\n    .addParams(<span class=\"hljs-string\">\"username\"</span>, <span class=\"hljs-string\">\"hyman\"</span>)\n    .addParams(<span class=\"hljs-string\">\"password\"</span>, <span class=\"hljs-string\">\"123\"</span>)\n    .build()\n    .execute(callback);\n</code></pre>\n\n<h3 id=\"3post-string\">（3）Post String</h3>\n\n\n\n<pre class=\"prettyprint\"><code class=\" hljs avrasm\">OkHttpUtils\n    <span class=\"hljs-preprocessor\">.postString</span>()\n    <span class=\"hljs-preprocessor\">.url</span>(url)\n    <span class=\"hljs-preprocessor\">.content</span>(new Gson()<span class=\"hljs-preprocessor\">.toJson</span>(new User(<span class=\"hljs-string\">\"zhy\"</span>, <span class=\"hljs-string\">\"123\"</span>)))\n    <span class=\"hljs-preprocessor\">.build</span>()\n    <span class=\"hljs-preprocessor\">.execute</span>(new MyStringCallback())<span class=\"hljs-comment\">;   </span></code></pre>\n\n<p>将string作为请求体传入到服务端，例如json字符串。</p>\n\n\n\n<h3 id=\"4post-file\">（4）Post File</h3>\n\n\n\n<pre class=\"prettyprint\"><code class=\" hljs avrasm\">OkHttpUtils\n    <span class=\"hljs-preprocessor\">.postFile</span>()\n    <span class=\"hljs-preprocessor\">.url</span>(url)\n    <span class=\"hljs-preprocessor\">.file</span>(file)\n    <span class=\"hljs-preprocessor\">.build</span>()\n    <span class=\"hljs-preprocessor\">.execute</span>(new MyStringCallback())<span class=\"hljs-comment\">;</span></code></pre>\n\n<p>将file作为请求体传入到服务端.</p>\n\n<h3 id=\"5基于post的文件上传类似web上的表单\">（5）基于POST的文件上传（类似web上的表单）</h3>\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \">OkHttpUtils.post()<span class=\"hljs-comment\">//</span>\n    .addFile(<span class=\"hljs-string\">\"mFile\"</span>, <span class=\"hljs-string\">\"messenger_01.png\"</span>, file)<span class=\"hljs-comment\">//</span>\n    .addFile(<span class=\"hljs-string\">\"mFile\"</span>, <span class=\"hljs-string\">\"test1.txt\"</span>, file2)<span class=\"hljs-comment\">//</span>\n    .url(url)\n    .params(params)<span class=\"hljs-comment\">//</span>\n    .headers(headers)<span class=\"hljs-comment\">//</span>\n    .build()<span class=\"hljs-comment\">//</span>\n    .execute(<span class=\"hljs-keyword\">new</span> MyStringCallback());</code></pre>\n\n<h3 id=\"6下载文件\">（6）下载文件</h3>\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \">OkHttpUtils<span class=\"hljs-comment\">//</span>\n    .get()<span class=\"hljs-comment\">//</span>\n    .url(url)<span class=\"hljs-comment\">//</span>\n    .build()<span class=\"hljs-comment\">//</span>\n    .execute(<span class=\"hljs-keyword\">new</span> FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), <span class=\"hljs-string\">\"gson-2.2.1.jar\"</span>)<span class=\"hljs-comment\">//</span>\n    {\n        <span class=\"hljs-annotation\">@Override</span>\n        <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">inProgress</span>(<span class=\"hljs-keyword\">float</span> progress)\n        {\n            mProgressBar.setProgress((<span class=\"hljs-keyword\">int</span>) (<span class=\"hljs-number\">100</span> * progress));\n        }\n\n        <span class=\"hljs-annotation\">@Override</span>\n        <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onError</span>(Request request, Exception e)\n        {\n            Log.e(TAG, <span class=\"hljs-string\">\"onError :\"</span> + e.getMessage());\n        }\n\n        <span class=\"hljs-annotation\">@Override</span>\n        <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onResponse</span>(File file)\n        {\n            Log.e(TAG, <span class=\"hljs-string\">\"onResponse :\"</span> + file.getAbsolutePath());\n        }\n    });</code></pre>\n\n<h3 id=\"7显示图片\">（7）显示图片</h3>\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \">OkHttpUtils\n    .get()<span class=\"hljs-comment\">//</span>\n    .url(url)<span class=\"hljs-comment\">//</span>\n    .build()<span class=\"hljs-comment\">//</span>\n    .execute(<span class=\"hljs-keyword\">new</span> BitmapCallback()\n    {\n        <span class=\"hljs-annotation\">@Override</span>\n        <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onError</span>(Request request, Exception e)\n        {\n            mTv.setText(<span class=\"hljs-string\">\"onError:\"</span> + e.getMessage());\n        }\n\n        <span class=\"hljs-annotation\">@Override</span>\n        <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onResponse</span>(Bitmap bitmap)\n        {\n            mImageView.setImageBitmap(bitmap);\n        }\n    });\n</code></pre>\n\n<p>哈，目前来看，清晰多了。</p>\n\n<h2 id=\"三对于上传下载的回调\">三、对于上传下载的回调</h2>\n\n\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \"><span class=\"hljs-keyword\">new</span> Callback&lt;?&gt;()\n{\n    <span class=\"hljs-comment\">//...</span>\n    <span class=\"hljs-annotation\">@Override</span>\n    <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">inProgress</span>(<span class=\"hljs-keyword\">float</span> progress)\n    {\n       <span class=\"hljs-comment\">//use progress: 0 ~ 1</span>\n    }\n}</code></pre>\n\n<p>对于传入的callback有个inProgress方法，需要拿到进度直接复写该方法即可。</p>\n\n<h2 id=\"四对于自动解析为实体类\">四、对于自动解析为实体类</h2>\n\n<p>目前去除了Gson的依赖，提供了自定义Callback的方式，让用户自己去解析返回的数据，目前提供了<code>StringCallback</code>，<code>FileCallback</code>,<code>BitmapCallback</code> 分别用于返回string，文件下载，加载图片。</p>\n\n<p>当然如果你希望解析为对象，你可以：</p>\n\n\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">abstract</span> <span class=\"hljs-class\"><span class=\"hljs-keyword\">class</span> <span class=\"hljs-title\">UserCallback</span> <span class=\"hljs-keyword\">extends</span> <span class=\"hljs-title\">Callback</span>&lt;<span class=\"hljs-title\">User</span>&gt;\n{</span>\n    <span class=\"hljs-comment\">//非UI线程，支持任何耗时操作</span>\n    <span class=\"hljs-annotation\">@Override</span>\n    <span class=\"hljs-keyword\">public</span> User <span class=\"hljs-title\">parseNetworkResponse</span>(Response response) <span class=\"hljs-keyword\">throws</span> IOException\n    {\n        String string = response.body().string();\n        User user = <span class=\"hljs-keyword\">new</span> Gson().fromJson(string, User.class);\n        <span class=\"hljs-keyword\">return</span> user;\n    }\n}</code></pre>\n\n<p>自己使用自己喜欢的Json解析库完成即可。</p>\n\n<p>解析成<code>List&lt;User&gt;</code>，则如下：</p>\n\n\n\n<pre class=\"prettyprint\"><code class=\" hljs php\"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">abstract</span> <span class=\"hljs-class\"><span class=\"hljs-keyword\">class</span> <span class=\"hljs-title\">ListUserCallback</span> <span class=\"hljs-keyword\">extends</span> <span class=\"hljs-title\">Callback</span>&lt;<span class=\"hljs-title\">List</span>&lt;<span class=\"hljs-title\">User</span>&gt;&gt;\n{</span>\n    @Override\n    <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">List</span>&lt;User&gt; parseNetworkResponse(Response response) throws IOException\n    {\n        String string = response.body().string();\n        <span class=\"hljs-keyword\">List</span>&lt;User&gt; user = <span class=\"hljs-keyword\">new</span> Gson().fromJson(string, <span class=\"hljs-keyword\">List</span>.class);\n        <span class=\"hljs-keyword\">return</span> user;\n    }\n\n\n}</code></pre>\n\n<h2 id=\"五对于https单向认证\">五、对于https单向认证</h2>\n\n<p>非常简单，拿到xxx.cert的证书。</p>\n\n<p>然后调用</p>\n\n\n\n<pre class=\"prettyprint\"><code class=\"language-xml hljs \">\nOkHttpUtils.getInstance()\n        .setCertificates(inputstream);</code></pre>\n\n<p>建议使用方式，例如我的证书放在assets目录：</p>\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \">\n<span class=\"hljs-javadoc\">/**\n * Created by zhy on 15/8/25.\n */</span>\n<span class=\"hljs-keyword\">public</span> <span class=\"hljs-class\"><span class=\"hljs-keyword\">class</span> <span class=\"hljs-title\">MyApplication</span> <span class=\"hljs-keyword\">extends</span> <span class=\"hljs-title\">Application</span>\n{</span>\n    <span class=\"hljs-annotation\">@Override</span>\n    <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onCreate</span>()\n    {\n        <span class=\"hljs-keyword\">super</span>.onCreate();\n\n        <span class=\"hljs-keyword\">try</span>\n        {    \n        OkHttpUtils\n         .getInstance()\n         .setCertificates(getAssets().open(<span class=\"hljs-string\">\"aaa.cer\"</span>),\n getAssets().open(<span class=\"hljs-string\">\"server.cer\"</span>));\n        } <span class=\"hljs-keyword\">catch</span> (IOException e)\n        {\n            e.printStackTrace();\n        }\n    }\n}</code></pre>\n\n<p>即可。别忘了注册Application。</p>\n\n<p>注意：如果https网站为权威机构颁发的证书，不需要以上设置。自签名的证书才需要。</p>\n\n<h2 id=\"六配置\">六、配置</h2>\n\n<h3 id=\"1全局配置\">（1）全局配置</h3>\n\n<p>可以在Application中，通过：</p>\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \">OkHttpClient client = \nOkHttpUtils.getInstance().getOkHttpClient();</code></pre>\n\n<p>然后调用client的各种set方法。</p>\n\n<p>例如：</p>\n\n\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \">client.setConnectTimeout(<span class=\"hljs-number\">100000</span>, TimeUnit.MILLISECONDS);</code></pre>\n\n\n\n<h3 id=\"2为单个请求设置超时\">（2）为单个请求设置超时</h3>\n\n<p>比如涉及到文件的需要设置读写等待时间多一点。</p>\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \"> OkHttpUtils\n    .get()<span class=\"hljs-comment\">//</span>\n    .url(url)<span class=\"hljs-comment\">//</span>\n    .tag(<span class=\"hljs-keyword\">this</span>)<span class=\"hljs-comment\">//</span>\n    .build()<span class=\"hljs-comment\">//</span>\n    .connTimeOut(<span class=\"hljs-number\">20000</span>)\n    .readTimeOut(<span class=\"hljs-number\">20000</span>)\n    .writeTimeOut(<span class=\"hljs-number\">20000</span>)\n    .execute()</code></pre>\n\n<p>调用build()之后，可以随即设置各种timeOut.</p>\n\n<h3 id=\"3取消单个请求\">（3）取消单个请求</h3>\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \"> RequestCall call = OkHttpUtils.get().url(url).build();\n call.cancel();\n</code></pre>\n\n\n\n<h3 id=\"4根据tag取消请求\">（4）根据tag取消请求</h3>\n\n<p>目前对于支持的方法都添加了最后一个参数<code>Object tag</code>，取消则通过<code>OkHttpUtils.cancelTag(tag)</code>执行。</p>\n\n<p>例如：在Activity中，当Activity销毁取消请求：</p>\n\n<pre class=\"prettyprint\"><code class=\" hljs java\">OkHttpUtils\n    .get()<span class=\"hljs-comment\">//</span>\n    .url(url)<span class=\"hljs-comment\">//</span>\n    .tag(<span class=\"hljs-keyword\">this</span>)<span class=\"hljs-comment\">//</span>\n    .build()<span class=\"hljs-comment\">//</span>\n\n<span class=\"hljs-annotation\">@Override</span>\n<span class=\"hljs-keyword\">protected</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onDestroy</span>()\n{\n    <span class=\"hljs-keyword\">super</span>.onDestroy();\n    <span class=\"hljs-comment\">//可以取消同一个tag的</span>\n    OkHttpUtils.cancelTag(<span class=\"hljs-keyword\">this</span>);<span class=\"hljs-comment\">//取消以Activity.this作为tag的请求</span>\n}</code></pre>\n\n<p>比如，当前Activity页面所有的请求以Activity对象作为tag，可以在onDestory里面统一取消。</p>\n\n<h2 id=\"七浅谈封装\">七、浅谈封装</h2>\n\n<p>其实整个封装的过程比较简单，这里简单描述下，对于okhttp一个请求的流程大致是这样的：</p>\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \"><span class=\"hljs-comment\">//创建okHttpClient对象</span>\nOkHttpClient mOkHttpClient = <span class=\"hljs-keyword\">new</span> OkHttpClient();\n<span class=\"hljs-comment\">//创建一个Request</span>\n<span class=\"hljs-keyword\">final</span> Request request = <span class=\"hljs-keyword\">new</span> Request.Builder()\n                .url(<span class=\"hljs-string\">\"https://github.com/hongyangAndroid\"</span>)\n                .build();\n<span class=\"hljs-comment\">//new call</span>\nCall call = mOkHttpClient.newCall(request); \n<span class=\"hljs-comment\">//请求加入调度</span>\ncall.enqueue(<span class=\"hljs-keyword\">new</span> Callback()\n{\n    <span class=\"hljs-annotation\">@Override</span>\n    <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onFailure</span>(Request request, IOException e)\n    {\n    }\n\n    <span class=\"hljs-annotation\">@Override</span>\n    <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">onResponse</span>(<span class=\"hljs-keyword\">final</span> Response response) <span class=\"hljs-keyword\">throws</span> IOException\n    {\n            <span class=\"hljs-comment\">//String htmlStr =  response.body().string();</span>\n    }\n});             \n</code></pre>\n\n<p>其中主要的差异，其实就是request的构建过程。</p>\n\n<p>我对Request抽象了一个类：<code>OkHttpRequest</code></p>\n\n\n\n<pre class=\"prettyprint\"><code class=\"language-java hljs \"><span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">abstract</span> <span class=\"hljs-class\"><span class=\"hljs-keyword\">class</span> <span class=\"hljs-title\">OkHttpRequest</span>\n{</span>\n    <span class=\"hljs-keyword\">protected</span> RequestBody requestBody;\n    <span class=\"hljs-keyword\">protected</span> Request request;\n\n    <span class=\"hljs-keyword\">protected</span> String url;\n    <span class=\"hljs-keyword\">protected</span> String tag;\n    <span class=\"hljs-keyword\">protected</span> Map&lt;String, String&gt; params;\n    <span class=\"hljs-keyword\">protected</span> Map&lt;String, String&gt; headers;\n\n    <span class=\"hljs-keyword\">protected</span> <span class=\"hljs-title\">OkHttpRequest</span>(String url, String tag,\n                            Map&lt;String, String&gt; params, Map&lt;String, String&gt; headers)\n    {\n        <span class=\"hljs-keyword\">this</span>.url = url;\n        <span class=\"hljs-keyword\">this</span>.tag = tag;\n        <span class=\"hljs-keyword\">this</span>.params = params;\n        <span class=\"hljs-keyword\">this</span>.headers = headers;\n    }\n\n    <span class=\"hljs-keyword\">protected</span> <span class=\"hljs-keyword\">abstract</span> Request <span class=\"hljs-title\">buildRequest</span>();\n    <span class=\"hljs-keyword\">protected</span> <span class=\"hljs-keyword\">abstract</span> RequestBody <span class=\"hljs-title\">buildRequestBody</span>();\n\n    <span class=\"hljs-keyword\">protected</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">prepareInvoked</span>(ResultCallback callback)\n    {\n        requestBody = buildRequestBody();\n        requestBody = wrapRequestBody(requestBody, callback);\n        request = buildRequest();\n    }\n\n    <span class=\"hljs-keyword\">protected</span> RequestBody <span class=\"hljs-title\">wrapRequestBody</span>(RequestBody requestBody, <span class=\"hljs-keyword\">final</span> ResultCallback callback)\n    {\n        <span class=\"hljs-keyword\">return</span> requestBody;\n    }\n\n\n    <span class=\"hljs-keyword\">public</span> <span class=\"hljs-keyword\">void</span> <span class=\"hljs-title\">invokeAsyn</span>(ResultCallback callback)\n    {\n        prepareInvoked(callback);\n        mOkHttpClientManager.execute(request, callback);\n    }\n\n\n     <span class=\"hljs-comment\">// other common methods</span>\n }   \n</code></pre>\n\n<p>一个request的构建呢，我分三个步骤：<code>buildRequestBody</code> , <code>wrapRequestBody</code> ,<code>buildRequest</code>这样的次序，当以上三个方法没有问题时，我们就拿到了request，然后执行即可。</p>\n\n<p>但是对于不同的请求，requestBody以及request的构建过程是不同的，所以大家可以看到<code>buildRequestBody</code>  ,<code>buildRequest</code>为抽象的方法，也就是不同的请求类，比如<code>OkHttpGetRequest</code>、<code>OkHttpPostRequest</code>等需要自己去构建自己的request。</p>\n\n<p>对于<code>wrapRequestBody</code>方法呢，可以看到它默认基本属于空实现，主要是因为并非所有的请求类都需要复写它，只有上传的时候呢，需要回调进度，需要对requestBody进行包装，所以这个方法类似于一个钩子。</p>\n\n<p>其实这个过程有点类似模板方法模式，有兴趣可以看看一个短篇介绍<a href=\"http://blog.csdn.net/lmj623565791/article/details/26276093\">设计模式 模版方法模式 展现程序员的一天</a> .</p>\n\n<p>对于更加详细的用法，可以查看github上面的readme，以及demo，目前demo包含：</p>\n\n<p><img src=\"http://img.blog.csdn.net/20151109094247142\" width=\"320px\"></p>\n\n<p>对于上传文件的两个按钮，需要自己搭建服务器，其他的按钮可以直接测试。</p>\n\n<p>最后，由于本人水平有限，以及时间比较仓促~~发现问题，欢迎提issue，我会抽时间解决。 have a nice day ~</p>\n\n<p><a href=\"https://github.com/hongyangAndroid/okhttp-utils\">源码点击下载</a></p>\n\n<hr>\n\n<p>欢迎关注我的微博： <br>\n<a href=\"http://weibo.com/u/3165018720\">http://weibo.com/u/3165018720</a></p>\n\n<hr>\n\n<blockquote>\n  <p>群号：<font color=\"red\">514447580</font>，欢迎入群</p>\n  \n  <p>微信公众号：hongyangAndroid <br>\n  （欢迎关注，第一时间推送博文信息） <br>\n  <img src=\"http://img.my.csdn.net/uploads/201501/30/1422600516_2905.jpg\" width=\"200px\"></p>\n</blockquote>";

	// 在WebView加载页面之前，可以选同步Cookie，然后再加载网页
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

	// 清除所有cookie
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
		mPullToRefreshWebView.getLoadingLayoutProxy().setPullLabel("上拉关闭！");
		setWebSetting();
		mPullToRefreshWebView.setRefreshing();
	}
	@SuppressLint("NewApi") 
	public void setWebSetting() {
		if (NetWorkUtils.isNetworkAvailable(this)) {
			mWebView.getSettings().setJavaScriptEnabled(true);
			mWebView.getSettings().setUseWideViewPort(true);// 设置此属性，可任意比例缩放
			//mWebView.getSettings().setLoadWithOverviewMode(true);// 缩放至屏幕的大小
			mWebView.getSettings().setDefaultTextEncodingName("UTF-8") ;
			mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // webview中缓存
			mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
			mWebView.setHorizontalScrollBarEnabled(false);
			mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(
					true);// 支持通过JS打开新窗口
			mWebView.requestFocusFromTouch();
			if (Build.VERSION.SDK_INT >= 11)// 防止对象注入漏洞
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
			  //handler.cancel(); 默认的处理方式，WebView变成空白页
	        	handler.proceed();//接受证书
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