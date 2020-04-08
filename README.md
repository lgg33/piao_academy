# piao_academy
在线学习系统后端Spring Cloud

* 根据业务逻辑将系统划分为后台管理服务、阿里云OSS服务、课程服务、用户服务、搜索服务、订单服务服务。
* 使用Spring Cloud Gateway作为微服务网关，便于统一api接口并实现鉴权。
* 使用nacos作为服务注册中心，便于服务的发现与部署。前端采用Vue框架开发，部署在Nginx服务器上。
* 搜索服务使用ElasticSearch实现站内搜索引擎。引入RabbitMQ实现服务间解耦，异步通信提升处理效率。
* 大文件存储在阿里云OSS上，如图片及视频资源。
* 订单支付通过支付宝沙箱版模拟完成。
* 前台网站考虑seo的需要，使用nuxt.js进行ssr
