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
    3.这个access_token可以使人带着GitHub上用户的身份去访问我们自己的网站