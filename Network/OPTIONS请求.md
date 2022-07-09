# OPTIONS请求

在说`OPTIONS`请求出的原因之前，要先说下浏览器的[同源策略](https://link.juejin.cn/?target=https%3A%2F%2Fdeveloper.mozilla.org%2Fzh-CN%2Fdocs%2FWeb%2FSecurity%2FSame-origin_policy)和[跨域资源共享 CORS](https://link.juejin.cn/?target=https%3A%2F%2Fdeveloper.mozilla.org%2Fzh-CN%2Fdocs%2FWeb%2FHTTP%2FAccess_control_CORS%23Requests_with_credentials)

### 同源策略

如果两个`URL`的协议(protocol)、端口（port）、主机（host）,都相同，则称这个URL为同源。 以`http://music.javaswing.cn/home/index.html`为例子：

| URL                                         | 结果   | 原因         |
| ------------------------------------------- | ------ | ------------ |
| http://music.javaswing.cn/static/other.html | 同源   |              |
| http://music.javaswng.cn/inner/start.html   | 同源   |              |
| http://music.javaswing.cn:8000/other.html   | 不同源 | **端口不同** |
| https://music.javaswing.cn/inner/start.html | 不同源 | **协议不同** |
| http://api.javaswing.cn/start.html          | 不同源 | **主机不同** |

作用：同源策略的存在，主要是为用于限制文档与它加载的脚本如何能与另一个资源进行交互，为重要的安全策略。

比如：你本地http://localhost:3000的项目访问http://localhost:8000的项目，就会出现：**has been blocked by CORS policy**

```
Access to XMLHttpRequest at 'http://localhost:3000/' from origin 'http://localhost:8080' has been blocked by CORS policy: Response to preflight request doesn't pass access control check: No 'Access-Control-Allow-Origin' header is present on the requested resource.
```

### CORS

> 跨域资源共享(CORS) 是一种机制，它使用额外的 HTTP 头来告诉浏览器  让运行在一个 origin (domain) 上的Web应用被准许访问来自不同源服务器上的指定的资源。当一个资源从与该资源本身所在的服务器不同的域、协议或端口请求一个资源时，资源会发起一个跨域 HTTP 请求。

简单的来说：CORS就是两种在不同的域、协议或端口（即不在同源中），服务之间能相互访问。

### OPTIONS请求

在CORS机制一个域名A要访问域名B的服务，在一些特殊的复杂请求下（简单请求并不会进行预请求），浏览器必须先使用OPTIONS请求进行一个预检请求（preflight request）来获取B服务是否允许跨域请求，服务进行确认之后，才会发起真正的HTTP请求。在预检请求的返回中，服务器端也可以通知客户端，是否需要携带身份凭证（包括 Cookies 和 HTTP 认证相关数据）。

**request header 的关键字段**

| 关键字段                       | 作用                                           |
| :----------------------------- | :--------------------------------------------- |
| Access-Control-Request-Method  | 告知服务器，实际请求将使用 POST 方法           |
| Access-Control-Request-Headers | 告知服务器，实际请求将携带的自定义请求首部字段 |

```
Access-Control-Request-Method: POST  
Access-Control-Request-Headers: X-PINGOTHER, Content-Type 
```

**HTTP 响应首部字段**

> CORS请求相关的字段，都以Access-Control-开头

| 字段名                           | 语法                                   | 作用                                                         |
| -------------------------------- | -------------------------------------- | ------------------------------------------------------------ |
| Access-Control-Allow-Origin      | Access-Control-Allow-Origin:  或 *     | orgin指定允许访问该资源的URL,设置为*则为任意                 |
| Access-Control-Allow-Methods     | Access-Control-Allow-Methods: [, ]*    | 用于预检测请求响应，告诉浏览器实际请求支持的方法             |
| Access-Control-Allow-Headers     | Access-Control-Allow-Headers: [, ]*    | 用于预检测请求响应，告诉浏览器实际请求中允许携带的字段       |
| Access-Control-Max-Age           | Access-Control-Max-Age:                | 指定浏览器preflight请求能被缓存多长时间，单位（秒）          |
| Access-Control-Allow-Credentials | Access-Control-Allow-Credentials: true | 当浏览器的credentials设置为true时是否允许浏览器读取response的内容。在`XMLHttpRequest`中设置withCredentials为true,且设置了该属性，则会带到身份Cookies。如果Access-Control-Allow-Origin为*的，这里的一切设置都会失效。 |

## 如何优化

我们发起跨域请求时，如果是简单请求，那么我们只会发出一次请求，但是如果是复杂请求则先发出 options 请求，用于确认目标资源是否支持跨域，然后浏览器会根据服务端响应的 header 自动处理剩余的请求，如果响应支持跨域，则继续发出正常请求，如果不支持，则在控制台显示错误。

由此可见，当触发预检时，跨域请求便会发送 2 次请求，既增加了请求数，也延迟了请求真正发起的时间，严重影响性能。

所以，我们可以优化 Options 请求，主要有 2 种方法。

1.  转为简单请求，如用 JSONP 做跨域请求
2.  对 options 请求进行缓存，服务器端设置 Access-Control-Max-Age 字段，那么当第一次请求该 URL 时会发出 OPTIONS 请求，浏览器会根据返回的 Access-Control-Max-Age 字段缓存该请求的 OPTIONS 预检请求的响应结果（具体缓存时间还取决于浏览器的支持的默认最大值，取两者最小值，一般为 10 分钟）。在缓存有效期内，该资源的请求（URL 和 header 字段都相同的情况下）不会再触发预检。（chrome 打开控制台可以看到，当服务器响应 Access-Control-Max-Age 时只有第一次请求会有预检，后面不会了。注意要开启缓存，去掉 disable cache 勾选。）

### 使用CORS的方式非常简单，但是需要同时对前端和服务器端做相应处理。

1、 前端

客户端使用XmlHttpRequest发起Ajax请求，当前绝大部分浏览器已经支持CORS方式，且主流浏览器均提供了对跨域资源共享的支持。

2、 服务器端

如果服务器端未做任何配置，则前端发起Ajax请求后，会得到CORS Access Deny，即跨域访问被拒绝。

**OPTIONS请求方法的主要用途有两个：**

1、获取[服务器](https://cloud.tencent.com/product/cvm?from=10680)支持的HTTP请求方法；

2、用来检查服务器的性能（安全）。例如：AJAX进行跨域请求时的预检，需要向另外一个[域名](https://dnspod.cloud.tencent.com/)的资源发送一个HTTP OPTIONS请求头，用以判断实际发送的请求是否安全。