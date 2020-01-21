##资料
[github-app创建](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)

[Visual-Paradigm](http://www.visual-paradigm.com)

##介绍
在进行登录的时候，点开localhost.8080/就引到了index.html页面  
在index.html中，利用boottrap导航栏示例代码写了导航栏，其中有登录选项，计划使用GitHub第三方账号登录  
其中详细内容见[github-app创建]相关内容  
　　1.参考GitHubAPI中的内容，将index.html中登录的部分定向到GitHub中指定的页面进行登录 GET https://github.com/login/oauth/authorize  
　　　此外index.html中还有一些参数（参考GitHub）随着URL传输，这些参数是用户访问我们自己的网站时携带和使用的参数  
　　2.在成功登录后，GitHub网站会重定位到我们自己的网站，同时传送参数code（10分钟后失效）以及之前传过去的参数state（以供确认登录请求）  
　　　我们之前在GitHub上填写的重定向地址是localhost:8080/callback 因此在代码中需要一个contgitroller来实现这个页面的交互，即AuthorizeController  
　　　在AuthorizeController中，带着AccessTokenDTO去githubprovider中模拟请求  
　　　GithubProvider是一个okhttp的应用，模拟带着AccessTokenDTO解析出来的body去请求https://github.com/login/oauth/access_token  
　　　GitHub确定登录以后，根据body中的URL重定向到登录后的页面，并且返回access_token等内容  
　　3.用户带着这个access_token可以到GitHub上获取用户的身份和相关信息(github->setting->profile中的信息)返回到我们的网站中  
cookie和session：   
　　Cookie的作用通俗地说就是当一个用户通过HTTP访问一个服务器时，这个服务器会将一些Key/Value键值对返回给客户端浏览器，并给这些数据加上一些限制条件，  
　在条件符合时这个用户下次访问这个服务器时，数据又被完整地带回给服务器。　
　　Cookie可以让服务端程序跟踪每个客户端的访问，但是每次客户端的访问都必须传回这些Cookie，如果Cookie很多，则无形地增加了客户端与  
　服务端的数据传输量，而Session的出现正是为了解决这个问题。同一个客户端每次和服务端交互时，不需要每次都传回所有的Cookie值，而是只要传回一个ID，  
　这个ID是客户端第一次访问服务端时生成的，而且每个客户端是唯一的。这样每个客户端就有了一个唯一的ID，客户端只要传回这个ID就行了，  
　这个ID通常是NAME为JSESIONID的一个Cookie。  
　　服务器从cookie中解析出session，关于这个SessionCookieName，如果在web.xml中配置session-config配置项，其cookie-config下的name属性  
　就是这个SessionCookieName的值。如果没有配置sessio-config配置项，默认的SessionCookieName就是大家熟悉的“JSESSIONID”。
　　有了Session ID，服务端就可以创建HttpSession对象了，第一次触发通过request.getSession()方法。  
　如果当前的Session ID还没有对应的HttpSession对象，那么就创建一个新的，并将这个对象加到org.apache.catalina.Manager的session容器中保存。  
　Manager类将管理所有Session生命周期，Session过期将被回收，服务器关闭，Sessoin将被序列化到磁盘等。  
　只要这个HttpSession对象存在，用户就可以根据Session ID来获取这个对象，也就做到了对状态的保持。  
　　在AuthorizeController中，取request中的session并向其中填入githubUser属性，在index.html中取出session中的信息就可以显示到页面上  
mybatis整合：  
　　在pom中引入要使用的数据库依赖，在配置文件application.properties中写入数据库信息以及驱动信息以连接。  
　　idea右侧database可以设置数据库信息，idea可以选择内置数据库或者远程连接等连接方法，其中mysql支持远程，h2支持内置  
HttpServletRequest和HttpServletResponse:  
HttpServletRequest是代表客户端的请求，当客户端通过HTTP协议访问服务器时，HTTP请求头中的所有信息都封装在这个对象中，通过这个对象提供的方法，可以获得客户端请求的所有信息
