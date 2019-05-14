# OKhttp(1): Basic usage and wiki

[TOC]

简单的使用 okhttp 进行网络请求。

## OKhttp

[An HTTP+HTTP/2 client for Android and Java applications. ](https://github.com/square/okhttp)， 进行网络请求。

用 okhttp 进行网络请求需要涉及 request， respose，okhttpclient。

下面就分别看一下这几个部分：

### okhttpclient

### calls

http 客户端就是接口 request，处理并返回 respons，理论上很简单。

#### Requests

每一个 HTTP request 都包含 url， method （set 或者 post, 和 headers；

并且可能会包含 body：某种指定 content type 的数据流。

#### Response 

response 表示对 request 的响应，写到一个 status code; 并带有返回的 headers 和可选的 body.

**Rewriting Requests**

当使用 OKhttp 发送 HTTP 请求时，可以更抽象的描述为：fetch urls with those headers。

追求正确和效率，okhttp 在发请求之前会重写该请求。

Okhttp 会添加原本 headers 中存在的内容，包括 `Content-Length`, `Transfer-Encoding`, `User-Agent`, `Host`, `Connection` and `Content-Type`。 还会添加 `Accept-Encoding` 请求头用于压缩相应，除非之前该请求有已经存在。如果已经有 cookies， 那么也会添加 Cookies 请求头。

一些请求会有对 response 进行缓存。当该缓存过期时，okhttp 会有条件的去 GET 最新的 response。这需要像 `If-modified-since` 和 `If-None-Match`之类的 header。

**Rewriting Response**

如果使用压缩，okhttp 将会丢弃 response headers 中的`Content-Encoding` 和 `Context-Length` , 因为他们无法去解压缩 response。

如果一个 GET 请求响应成功，网络响应和缓存响应将会合并。



**后续请求**

当请求的地址有重定向，web 服务器将会放回 302 状态码表示这是另一个地址，okhttp 将会继续请求重定向后的地址，返回最终的 response。

如果 response 是涉及授权的问题，okhttp 会询问 `Authnicator`（如果有配置）， 去完成授权。如果验证器提供凭据，则使用包含该凭据的请求重试。



**Retry Requests：请求重试**

有时候会因为连接池断开或者不可用，web 服务器不可访问等原因请求失败，okhttp 会使用另一个路由进行请求重试。

#### Calls

经过上面的重写，重定向，重试等，一个简单的请求可能会积攒出好多个请求和响应。okhttp 使用 `Call` 去构建任务，通过许多必要的中间请求和响应来满足请求。

Call 可以被两种方式执行：

* 同步：阻塞线程，知道响应返回
* 异步：在任何线程发出请求，当响应返回获得 回调 Callback 。



Calls 可以在任何线程被取消，没有完成时请求失败，当取消时，如果正在写 body 或者读取 response， 将会有 `IOException` 异常。

#### Dispatch

对于同步请求来说，有必要管理同时请求的个数。同时请求数过多会浪费资源，太少会影响延迟。

对于异步调用，Dispatcher 为最大同时请求实现策略。你可以为每个 web 服务器设置请求数(5) ,或者全部请求数(64)。



### Connections

尽管只提供 URL， 但 okhttp 会使用URL，Address 和Route 连接 web 服务器。

#### URLs

Urls 是 http 和网络最基本的东西。除了作为 web 上所有东西的通用、分散的命名方案之外，还指定了如何访问 web 资源。

URLs 是抽象的：

* 指定了 Call 是明文还是加密的，但没有指定使用那种加密算法。也没有指定如何验证对等方的证书(HostnameVerifier)或哪些证书可以信任(SSLSocketFactory)。

* 没有指定是否应该使用特定的代理服务器或如何使用该代理服务器进行身份验证。

同时每个 URL 有一个特定的 path：/user/info, 和查询 (?q = name)。每个 web 服务器承载许多 url。

#### Address

地址指定 webserver (gitHub.com) 和连接到该服务器所需的所有静态配置：端口号，https 设置，更好的网络协议( HTTP/2 或者 SPDY )。

共享所有连接地址的 url 也可以共享同样的 TCP 套接字连接。共享连接有很好的性能优势：低延迟，更高的吞吐量, 省电等。okhttp 使用连接池去自动重用 HTTP/1.x  连接和多路复用 HTTP/2 和 SPDY 连接。

#### Routes

路由提供实际所连接到 web 服务器所需的动态信息。这是要尝试的特定IP地址(由DNS查询发现)、要使用的确切代理服务器(如果使用 ProxySelector )以及要协商的 TLS 版本(用于 HTTPS 连接)。

一个地址可能有很多路由。例如，驻留在多个数据中心的 web 服务器在 DNS 响应中可能产生多个IP地址。

#### Connections

当使用 okhttp 请求 url 时，做如下工作：

1. 使用该 URL 和配置的 okhttpclient 创建一个地址，指定了将要连接的 web 服务器。
2. 尝试去连接池检索出具有该地址的连接
3. 未找到，选择一个路由进行尝试。此时意味着进行 DNS 请求来获取服务器 IP 地址。有必要会选择一个 TSL 版本和代理服务器。
4. 如果是一个新的路由，通过构建直接套接字连接、TLS隧道(用于 HTTP 代理上的 HTTPS )或直接 TLS 连接进行连接。必要时还会握手。
5. 发送请求和接收响应。

如果连接有问题，会选择另一个路由进行重试。当部分服务器地址不可用时，okhttp 可以进行收回。当池连接过期或不支持尝试的TLS版本时，它也很有用。



### 基本使用

对于  `response.body().string()`， `string()`对于内容少时，高效并且方便。但是当大小超过 1M ，就应该避免使用该方法，因为会将所有内容加载到内容中。这种情况下， 应该将 body 以流形式处理。

**post a string**

当内容超过 1M 时，避免使用下述方法。

```java
public static final MediaType MEDIA_TYPE_MARKDOWN
      = MediaType.parse("text/x-markdown; charset=utf-8");

  private final OkHttpClient client = new OkHttpClient();

  public void run() throws Exception {
    String postBody = ""
        + "Releases\n"
        + "--------\n"
        + "\n"
        + " * _1.0_ May 6, 2013\n"
        + " * _1.1_ June 15, 2013\n"
        + " * _1.2_ August 11, 2013\n";

    Request request = new Request.Builder()
        .url("https://api.github.com/markdown/raw")
        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
        .build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

      System.out.println(response.body().string());
    }
  }
```

其余还有 post 各种数据的使用方法，请参考官网:[https://github.com/square/okhttp/wiki/Recipes](https://github.com/square/okhttp/wiki/Recipes)



**Response Caching**

缓存 response ，需要设置一个可信任的私有目录，并且不能有多个 okhttpclient 去读写该目录的内容。

响应缓存的配置都使用 HTTP 请求头来实现。可以添加 `Cache-Control: max-stale=3600` 来配置其资源的缓存。

```java
private final OkHttpClient client;

  public CacheResponse(File cacheDirectory) throws Exception {
    int cacheSize = 10 * 1024 * 1024; // 10 MiB
    Cache cache = new Cache(cacheDirectory, cacheSize);

    client = new OkHttpClient.Builder()
        .cache(cache)
        .build();
  }

  public void run() throws Exception {
    Request request = new Request.Builder()
        .url("http://publicobject.com/helloworld.txt")
        .build();

    String response1Body;
    try (Response response1 = client.newCall(request).execute()) {
      if (!response1.isSuccessful()) throw new IOException("Unexpected code " + response1);

      response1Body = response1.body().string();
      System.out.println("Response 1 response:          " + response1);
      System.out.println("Response 1 cache response:    " + response1.cacheResponse());
      System.out.println("Response 1 network response:  " + response1.networkResponse());
    }

    String response2Body;
    try (Response response2 = client.newCall(request).execute()) {
      if (!response2.isSuccessful()) throw new IOException("Unexpected code " + response2);

      response2Body = response2.body().string();
      System.out.println("Response 2 response:          " + response2);
      System.out.println("Response 2 cache response:    " + response2.cacheResponse());
      System.out.println("Response 2 network response:  " + response2.networkResponse());
    }

    System.out.println("Response 2 equals Response 1? " + response1Body.equals(response2Body));
  }
```

对于缓存的配置，可以使用 `CacheControl.FORCE_NETWORK`,  `CacheControl.FORCE_CACHE` 来启用不同的缓存策略。

#### Timeouts

当方法调用不可用时，使用超时使请求失败。可能会由于客户端连接问题，服务端可用问题等请求失败。okhttp 支持连接，读，写超时配置。



### Interceptors： 拦截器

拦截器作为 okhttp 最强大的部分，是一种功能强大的机制，可以监视、重写和重试调用。

下述是一个简单的拦截器，记录了发送请求和传入响应。

```java
class LoggingInterceptor implements Interceptor {
  @Override public Response intercept(Interceptor.Chain chain) throws IOException {
    Request request = chain.request();

    long t1 = System.nanoTime();
    logger.info(String.format("Sending request %s on %s%n%s",
        request.url(), chain.connection(), request.headers()));

    Response response = chain.proceed(request);

    long t2 = System.nanoTime();
    logger.info(String.format("Received response for %s in %.1fms%n%s",
        response.request().url(), (t2 - t1) / 1e6d, response.headers()));

    return response;
  }
}
```



`chain.process(request)`是每一个拦截器的关键部分。这个部分是所有的 HTTP 工作发生的地方，返回满足请求的响应。

拦截器能形成链条。当同时有压缩和校验拦截器是，需要确认哪个拦截器先执行。Okhttp 使用 list 来保存拦截器，顺序执行。

![image](https://user-images.githubusercontent.com/10796970/57426352-70afd600-7251-11e9-896d-293cf61ab607.png)



#### Application interceptors

可以将拦截器注册为应用拦截器和网络拦截器。使用上述的 LoggingInterceptor 来展示两者的区别。

注册应用拦截器，调用 `addInterceptor()`方法。

当遇到地址重定向时， okhttp 会自动处理，应用拦截器调用一次并返回响应结果。



#### Network Interceptors

调用 `addNetworkInterceptor` 方法。

当结果返回时，该拦截器调用两次。

对于上图，非常容易理解



那么该如何选择，每个拦截器会有不同的价值：

**Application Interceptors**

* 没必要担心中间响应结果， 比如重定向和重试等。
* 总是调用一次，即使 HTTP 响应结果来自缓存。
* 观察请求的原始意图，不关心 okhttp 注入的头文件。
* 允许短路，不调用 ``Chain.proceed()`
* 允许重试和多次调用 `Chain.proceed()`

**Network Interceptors**

* 能够处理中间响应结果，比如重定向和重试
* 不调用因短路造成的网络缓存情况
* 观察将通过网络传输的数据。
* 访问承载请求的连接。



#### Rewriting Reqeust

拦截器可以添加， 删除，替换请求头，也可以转换请求体。

```java
/** This interceptor compresses the HTTP request body. Many webservers can't handle this! */
final class GzipRequestInterceptor implements Interceptor {
  @Override public Response intercept(Interceptor.Chain chain) throws IOException {
    Request originalRequest = chain.request();
    if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
      return chain.proceed(originalRequest);
    }

    Request compressedRequest = originalRequest.newBuilder()
        .header("Content-Encoding", "gzip")
        .method(originalRequest.method(), gzip(originalRequest.body()))
        .build();
    return chain.proceed(compressedRequest);
  }

  private RequestBody gzip(final RequestBody body) {
    return new RequestBody() {
      @Override public MediaType contentType() {
        return body.contentType();
      }

      @Override public long contentLength() {
        return -1; // We don't know the compressed length in advance!
      }

      @Override public void writeTo(BufferedSink sink) throws IOException {
        BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
        body.writeTo(gzipSink);
        gzipSink.close();
      }
    };
  }
}
```

#### Rewriting Responses

同样，拦截器也可以对响应结果添加， 删除，替换请求头，也可以转换请求体。

这通常比重写请求头更危险，因为它可能违反web服务器的原则。

比如重写缓存请求头：

```java
/** Dangerous interceptor that rewrites the server's cache-control header. */
private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
  @Override public Response intercept(Interceptor.Chain chain) throws IOException {
    Response originalResponse = chain.proceed(chain.request());
    return originalResponse.newBuilder()
        .header("Cache-Control", "max-age=60")
        .build();
  }
};
```



### HTTPS

OKHTTP 试图去平衡两块内容：

* 连接：尽可能多的连接主机。
* 安全性：包括使用证书验证远程服务器， 用强密码交换数据的隐私性。

当进行 HTTP 的连接时，Okhttp 需要知道使用的 TLS 的版本 和密码套件。想要最大限度地连接的客户机将包括过时的TLS版本和设计的密码套件。而一个想要最大限度地提高安全性的严格客户端将仅限于最新的TLS版本和最强的密码套件。

`ConnectionSpec` 来实现指定的连接数和安全性。Okhttp 包含4种配置：

* RESTRICTED_TLS：是一种安全配置，旨在满足更严格的遵从性需求。
* MODERN_TLS：是一个连接到现代HTTPS服务器的安全配置。
* COMPATIBLE_TLS： 是一个安全的配置，它连接到安全但不是当前https服务器。
* CLEARTEXT是一种不安全的配置，用于http://



```java
OkHttpClient client = new OkHttpClient.Builder() 
    .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS))
    .build();
```



### Events

事件允许您捕获应用程序HTTP调用的程度。使用事件来监视:

* 应用程序发出的HTTP调用的大小和频率。如果太多的 HTTP 调用发生，或者数据量太大，应该被监测到。
* 在底层网络调用的性能：如果网络的性能不够好，则需要改进网络或减少使用网络。



#### EventListener

在一个成功的 HTTP 请求调用（无重定向，重试）， 描述如下：

![image](https://user-images.githubusercontent.com/10796970/57428300-53cbd080-725a-11e9-812d-227d0dc1b419.png)

如下是一个简单的时间监听器，打印每个事件的时间：

```java
class PrintingEventListener extends EventListener {
  private long callStartNanos;

  private void printEvent(String name) {
    long nowNanos = System.nanoTime();
    if (name.equals("callStart")) {
      callStartNanos = nowNanos;
    }
    long elapsedNanos = nowNanos - callStartNanos;
    System.out.printf("%.3f %s%n", elapsedNanos / 1000000000d, name);
  }

  @Override public void callStart(Call call) {
    printEvent("callStart");
  }

  @Override public void callEnd(Call call) {
    printEvent("callEnd");
  }

  @Override public void dnsStart(Call call, String domainName) {
    printEvent("dnsStart");
  }

  @Override public void dnsEnd(Call call, String domainName, List<InetAddress> inetAddressList) {
    printEvent("dnsEnd");
  }

  ...
}
```

```
REQUEST 1 (new connection)
0.000 callStart
0.010 dnsStart
0.017 dnsEnd
0.025 connectStart
0.117 secureConnectStart
0.586 secureConnectEnd
0.586 connectEnd
0.587 connectionAcquired
0.588 requestHeadersStart
0.590 requestHeadersEnd
0.591 responseHeadersStart
0.675 responseHeadersEnd
0.676 responseBodyStart
0.679 responseBodyEnd
0.679 connectionReleased
0.680 callEnd
REQUEST 2 (pooled connection)
0.000 callStart
0.001 connectionAcquired
0.001 requestHeadersStart
0.001 requestHeadersEnd
0.002 responseHeadersStart
0.082 responseHeadersEnd
0.082 responseBodyStart
0.082 responseBodyEnd
0.083 connectionReleased
0.083 callEnd
```

打印的时间如上：第二次调用会重用连接池的资源，达到更好的性能。



## 总结

上述内容来自 Okhttp 官方的 wiki ，算是翻译并简短的整理了一下。其内容有：

* request， response， okhttpclient
* interceptors
* https

详细内容请参考其他网络书籍。