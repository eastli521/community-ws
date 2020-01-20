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
　　　我们之前在GitHub上填写的重定向地址是localhost:8080/callback 因此在代码中需要一个controller来实现这个页面的交互，即AuthorizeController  
　　　在AuthorizeController中，带着AccessTokenDTO去githubprovider中模拟请求  
　　　GithubProvider是一个okhttp的应用，模拟带着AccessTokenDTO解析出来的body去请求https://github.com/login/oauth/access_token  
　　　GitHub确定登录以后，根据body中的URL重定向到登录后的页面，并且返回access_token等内容  
　　3.用户带着这个access_token可以到GitHub上获取用户的身份和相关信息(github->setting->profile中的信息)返回到我们的网站中  
cookie和session：
　　在AuthorizeController中，取request中的session并向其中填入githubUser属性，在index.html中取出session中的信息就可以显示到页面上
mybatis整合：
　　在pom中引入要使用的数据库依赖，在配置文件application.properties中写入数据库信息以及驱动信息以连接。idea右侧database可以设置数据库信息
　　idea可以选择内置数据库或者远程连接等连接方法，其中mysql支持远程，h2支持内置
