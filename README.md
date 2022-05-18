#### 1、项目描述

（1）在线教育系统，分为前台网站系统和后台运营平台，B2C模式。
前台用户系统包括课程、讲师、问答、文章几大大部分，使用了微服务技术架构，前后端分离开发。
后端的主要技术架构是：SpringBoot + SpringCloud + MyBatis-Plus + HttpClient + MySQL +
Maven+EasyExcel+ nginx
前端的架构是：Node.js + Vue.js +element-ui+NUXT+ECharts
其他涉及到的中间件包括Redis、阿里云OSS、阿里云视频点播
业务中使用了ECharts做图表展示，使用EasyExcel完成分类批量添加、注册分布式单点登录使用了JWT

（2）项目前后端分离开发，后端采用SpringCloud微服务架构，持久层用的是MyBatis-Plus，微服务分库设
计，使用Swagger生成接口文档
接入了阿里云视频点播、阿里云OSS。
系统分为前台用户系统和后台管理系统两部分。
前台用户系统包括：首页、课程、名师、问答、文章。
后台管理系统包括：讲师管理、课程分类管理、课程管理、统计分析、Banner管理、订单管理、权限管
理等功能。

在线教育计费案例：
小A是一名杭州的创业者，带领团队研发了一个在线教育平台。他希望把视频托管在阿里云上，存量视频大约1000个，
占用存储空间近1T，每月预计新增视频100个，并新增存储约100G，课程视频的时长集中在20-40分钟，并且按照不同课
程进行分类管理。为了保障各端的观看效果，计划为用户提供“标清480P”和“高清720P”两种清晰度。目前已有用
户400人左右，每日平均视频观看次数1000次，在移动端和PC端观看次数比例大致为3:1。

#### 2、这是一个项目还是一个产品

这是一个产品
1.0版本是单体应用：SSM
2.0版本加入了SpringCloud，将一些关键业务和访问量比较大的部分分离了出去
目前独立出来的服务有教学服务、视频点播服务、用户服务、统计分析服务、网关服务

#### 3、测试要求

首页和视频详情页qps单机qps要求 2000+
经常用每秒查询率来衡量域名系统服务器的机器的性能，其即为QPS
QPS = 并发量 / 平均响应时间

#### 4、企业中的项目（产品）开发流程

一个中大型项目的开发流程
1、需求调研（产品经理）
2、需求评审（产品/设计/前端/后端/测试/运营）
3、立项（项目经理、品管）
4、UI设计
5、开发
架构、数据库设计、API文档、MOCK数据、开发、单元测试
前端
后端
6、前端后端联调
7、项目提测：黑盒白盒、压力测试（qps） loadrunner
8、bug修改
9、回归测试
10、运维和部署上线
11、灰度发布
12、全量发布
13、维护和运营

#### 5、系统中都有那些角色？数据库是怎么设计的？

前台：会员（学员）
后台：系统管理员、运营人员
后台分库，每个微服务一个独立的数据库，使用了分布式id生成器

#### 6、视频点播是怎么实现的（流媒体你们是怎么实现的）

我们直接接入了阿里云的云视频点播。云平台上的功能包括视频上传、转码、加密、智能审核、监控统
计等。
还包括视频播放功能，阿里云还提供了一个视频播放器。

#### 7、前后端联调经常遇到的问题：

1、请求方式post、get
2、json、x-wwww-form-urlencoded混乱的错误
3、后台必要的参数，前端省略了
4、数据类型不匹配
5、空指针异常
6、分布式系统中分布式id生成器生成的id 长度过大（19个字符长度的整数），js无法解析（js智能解
析16个长度：2的53次幂）
id策略改成 ID_WORKER_STR

#### 8、前后端分离项目中的跨域问题是如何解决的

后端服务器配置：我们的项目中是通过Spring注解解决跨域的 @CrossOrigin
也可以使用nginx反向代理、httpClient、网关

#### 9、说说你做了哪个部分、遇到了什么问题、怎么解决的

问题1：
分布式id生成器在前端无法处理，总是在后三位进行四舍五入。
分布式id生成器生成的id是19个字符的长度，前端javascript脚本对整数的处理能力只有2的53次方，也就
是最多只能处理16个字符
解决的方案是把id在程序中设置成了字符串的性质
问题2：
项目迁移到Spring-Cloud的时候，经过网关时，前端传递的cookie后端一只获取不了，看
了cloud中zuul的源码，发现向下游传递数据的时候，zull默认过滤了敏感信息，将cookie过滤掉了
解决的方案是在配置文件中将请求头的过滤清除掉，使cookie可以向下游传递
问题3.......

#### 10、分布式系统的id生成策略

https://www.cnblogs.com/haoxinyue/p/5208136.html

#### 11、项目组有多少人，人员如何组成？

#### 12、分布式系统的CAP原理

CAP定理：
指的是在一个分布式系统中，Consistency（一致性）、 Availability（可用性）、Partition tolerance（分
区容错性），三者不可同时获得。
一致性（C）：在分布式系统中的所有数据备份，在同一时刻是否同样的值。（所有节点在同一时间的
数据完全一致，越多节点，数据同步越耗时）
可用性（A）：负载过大后，集群整体是否还能响应客户端的读写请求。（服务一直可用，而且是正常响
应时间）
分区容错性（P）：分区容错性，就是高可用性，一个节点崩了，并不影响其它的节点（100个节点，挂
了几个，不影响服务，越多机器越好）
CA 满足的情况下，P不能满足的原因：
数据同步(C)需要时间，也要正常的时间内响应(A)，那么机器数量就要少，所以P就不满足
CP 满足的情况下，A不能满足的原因：
数据同步(C)需要时间, 机器数量也多(P)，但是同步数据需要时间，所以不能再正常时间内响应，所以A就
不满足
AP 满足的情况下，C不能满足的原因：
机器数量也多(P)，正常的时间内响应(A)，那么数据就不能及时同步到其他节点，所以C不满足
注册中心选择的原则：
Zookeeper：CP设计，保证了一致性，集群搭建的时候，某个节点失效，则会进行选举行的leader，或
者半数以上节点不可用，则无法提供服务，因此可用性没法满足
Eureka：AP原则，无主从节点，一个节点挂了，自动切换其他节点可以使用，去中心化
结论：
分布式系统中P,肯定要满足，所以我们只能在一致性和可用性之间进行权衡
如果要求一致性，则选择zookeeper，如金融行业
如果要求可用性，则Eureka，如教育、电商系统
没有最好的选择，最好的选择是根据业务场景来进行架构设计

#### 13、前端渲染和后端渲染有什么区别

前端渲染是返回json给前端，通过javascript将数据绑定到页面上
后端渲染是在服务器端将页面生成直接发送给服务器，有利于SEO的优化

#### 14、能画一下系统架构图吗

![image-20220518215121860](C:\Users\安逸i\AppData\Roaming\Typora\typora-user-images\image-20220518215121860.png)