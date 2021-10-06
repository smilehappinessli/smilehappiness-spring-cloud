@[TOC]

# 1 前言

Spring Cloud 官网，`2021-07-06` 发布了**Hoxton.SR12** 这个版本， **本来想使用 `Hoxton.SR12`这个Spring Cloud版本，查了一些资料，发现基于这个版本，好用的微服务架构体系并且开源的项目不是很多，可能是这个版本刚出来两三个月，就自己折腾了一个基础架构**。在进行依赖管理的过程中，走了不少坑，各种jar冲突或者版本不兼容等等，这里总结记录下，防止以后再次踩坑。

该脚手架项目基于 `Spring Boot`+ `Spring Cloud` + `MyBatis-Plus`，为了提高项目的开发效率，降低项目的维护成本，避免重复造轮子，我基于我上一篇博客中的基础架构，搭建了一套脚手架，可以直接拿来使用。

# 2 脚手架主要提供哪些功能

**该项目主要包括以下功能模块：**

* `统一管理项目依赖，核心依赖的版本控制`
* `缓存管理以及分布式锁的处理`
* `预警通知功能`
* `异常管理`
* `限流Api管理`
* `Mock Server管理`
* `消息中间件MQ管理`
* `操作日志管理`
* `定时任务管理`
* `Swagger-Ui管理`
* `工具类管理`


# 3 如何使用该脚手架
## 3.1  项目统一依赖管理
* **集成基础设施项目:**

  **在自己的maven项目中，在最顶层项目的pom文件中，继承该基础设施项目：**

  ```xml
      <parent>
          <groupId>com.smilehappiness</groupId>
          <artifactId>smilehappiness-architecture</artifactId>
          <version>1.0.0</version>
      </parent>
  ```

## 3.2 集成基础模块功能到自己的项目中
**下面的这些依赖的功能模块，可以根据实际情况，选择集成哪些功能模块，添加如下依赖，即可把基础设施项目的核心功能，集成到自己的项目中：**

```xml
	<dependency>
	   <groupId>com.smilehappiness</groupId>
	    <artifactId>smilehappiness-cache</artifactId>
	    <exclusions>
	        <exclusion>
	            <artifactId>HdrHistogram</artifactId>
	            <groupId>org.hdrhistogram</groupId>
	        </exclusion>
	    </exclusions>
	</dependency>
	<dependency>
	    <groupId>com.smilehappiness</groupId>
	    <artifactId>smilehappiness-common</artifactId>
	</dependency>
	<dependency>
	    <groupId>com.smilehappiness</groupId>
	    <artifactId>smilehappiness-early-warning-notice</artifactId>
	</dependency>
	<dependency>
	    <groupId>com.smilehappiness</groupId>
	    <artifactId>smilehappiness-exception</artifactId>
	</dependency>
	<dependency>
	    <groupId>com.smilehappiness</groupId>
	    <artifactId>smilehappiness-limit-api</artifactId>
	</dependency>
	<dependency>
	    <groupId>com.smilehappiness</groupId>
	    <artifactId>smilehappiness-mq</artifactId>
	</dependency>
	<dependency>
	    <groupId>com.smilehappiness</groupId>
	    <artifactId>smilehappiness-operation-log</artifactId>
	</dependency>
	<dependency>
	    <groupId>com.smilehappiness</groupId>
	    <artifactId>smilehappiness-schedule</artifactId>
	</dependency>
	<dependency>
	    <groupId>com.smilehappiness</groupId>
	    <artifactId>smilehappiness-swagger-ui</artifactId>
	</dependency>
	<dependency>
	    <groupId>com.smilehappiness</groupId>
	    <artifactId>smilehappiness-utils</artifactId>
	</dependency>
```


# 4 基础核心功能模块的使用
## 4.1 集成缓存管理模块

分布式、微服务背景下，对于性能的要求也越来越高，所以缓存越来越受到了重视。现在使用比较流行的缓存是Redis，所以，笔者也基于Redis做缓存处理。

**Redis常见的场景有：** `普通缓存`、`分布式锁`、`分布式限流`、`幂等性校验`、`短信登录限定次数`等等

### 4.1.1 添加cache模块依赖
```xml
     <dependency>
          <groupId>com.smilehappiness</groupId>
          <artifactId>smilehappiness-cache</artifactId>
     </dependency>
```

### 4.1.2 cache模块的功能使用

* Redis工具的基本使用示例

  **注入以下工具类：**


```java
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedissonLockRedisUtil redissonLockRedisUtil;
```
然后使用以下测试用例：

```java
public void testRedisUtil() {
    //赋值
    redisUtil.set("test1", "你好");
    //该工具类，默认过期单位为秒
    redisUtil.set("test2", "测试一下过期时间", 30);

    //取值
    System.out.println(redisUtil.get("test1"));
    System.out.println(redisUtil.get("test2"));

    //删除值
    redisUtil.del("test1");
}
```

* Redis分布式锁工具的基本使用示例

```java
 /**
   * <p>
   * 测试分布式锁的使用，基于Redisson客户端实现（该方法的实现推荐使用）
   * <p/>
   *
   * @param
   * @return void
   * @Date 2021/10/4 16:51
   */
  @Test
  public void testDistributeLock() {
      String bizLockKey = "smilehappiness:trialOrder:orderNumberxxxxxxx";

      //支持过期解锁功能，10秒钟以后自动解锁, 无需调用unlock方法手动解锁，当然，为了不占用资源，使用锁处理完业务，一般还是建议手动释放锁
      RLock lock = redissonLockRedisUtil.lock(bizLockKey, 60L);
      if (lock.tryLock()) {
          try {
              //处理业务方法。。。
          } catch (Exception e) {
              log.error("获取分布式锁失败，失败原因：{}", e);
              throw new SystemInternalException("获取分布式锁失败，失败原因：" + e.getMessage());
          } finally {
              lock.unlock();
          }
      } else {
          log.error("系统繁忙，请稍后再试！");
          throw new BusinessException("系统繁忙，请稍后再试！");
      }
  }

  /**
   * <p>
   * 测试分布式锁的使用，基于Redisson客户端实现（该方法的实现推荐使用）
   * <p/>
   *
   * @param
   * @return void
   * @Date 2021/10/4 16:51
   */
  @Test
  public void testDistributeLockTwo() {
      String bizLockKey = "smilehappiness:trialOrder:orderNumberxxxxxxx";

      //尝试加锁，最多等待30秒，上锁以后120秒自动解锁
      boolean lockFlag = redissonLockRedisUtil.tryLock(bizLockKey, 30L, 2 * 60L);
      if (lockFlag) {
          try {
              //处理业务方法。。。
          } catch (Exception e) {
              log.error("获取分布式锁失败，失败原因：{}", e);
              throw new SystemInternalException("获取分布式锁失败，失败原因：" + e.getMessage());
          } finally {
              redissonLockRedisUtil.unlock(bizLockKey);
          }
      } else {
          log.error("系统繁忙，请稍后再试！");
          throw new BusinessException("系统繁忙，请稍后再试！");
      }
  }

  /**
   * <p>
   * 测试分布式锁的使用，基于Redisson客户端实现方式
   * <p/>
   *
   * @param
   * @return void
   * @Date 2021/10/4 16:51
   */
  @Test
  public void testDistributeLockOriginal() {
      String bizLockKey = "smilehappiness:trialOrder:orderNumberxxxxxxx";
      RLock lock = redissonClient.getLock(bizLockKey);

      if (lock.tryLock()) {
          try {
              //处理业务方法。。。
          } catch (Exception e) {
              log.error("获取分布式锁失败，失败原因：{}", e);
              throw new SystemInternalException("获取分布式锁失败，失败原因：" + e.getMessage());
          } finally {
              lock.unlock();
          }
      } else {
          log.error("系统繁忙，请稍后再试！");
          throw new BusinessException("系统繁忙，请稍后再试！");
      }
  }
  ```

## 4.2 集成通知预警管理模块

目前，只设计了`钉钉预警通知`，后续可以集成邮件通知等等

### 4.2.1 添加通知预警模块依赖
```xml
     <dependency>
          <groupId>com.smilehappiness</groupId>
          <artifactId>smilehappiness-early-warning-notice</artifactId>
     </dependency>
```

### 4.2.2 添加yml配置
**在yml文件或者properties配置文件中添加如下内容：**

```yml
# 钉钉预警通知
earlyWarning:
  notice:
    # 一般预警通知
    generalDingNoticeUrl: xxx
    # 高频异常通知预警
    errorDingNoticeUrl: xxx
```

记录好机器人的Webhook 地址，可以在自己项目中调用此地址向群聊发送相关消息通知，做到`项目异常的预警通知`或者一些`其它业务通知`，至此已经设置完成，剩下的只需要自己在项目中需要预警的地方调用接口通知即可。

如果你设定的机器人类型是关键字，内容需要包含关键字，才可以发送通知成功

注：这里分为了两个地址，可以根据实际情况，来决定用一个还是用两个，使用的时候非常简单，在钉钉上创建一个机器人，把webhook地址复制过来即可，限于篇幅，就不再详细说明，玩不转的小伙伴，可以参考资料：[https://blog.csdn.net/nbskycity/article/details/106068455](https://blog.csdn.net/nbskycity/article/details/106068455)


### 4.2.3 钉钉预警使用示例
* 首先注入工具类

  ```java
      @Autowired
      private DingTalkWarningNoticeServer dingTalkWarningNoticeServer;
  ```

* 参考代码示例

  ```java
  /**
   * 钉钉预警通知测试，注意，如果你设定的机器人类型是关键字，内容需要包含关键字，才可以发送通知成功
   */
  @Test
  public void testDingTalkNotice() {
      dingTalkWarningNoticeServer.sendWarningMessage("smile:这是一个警告的通知");
      //第一个参数title，可以理解为关键信息标识，后续跟踪日志可以使用该关键信息标识快速找到
      dingTalkWarningNoticeServer.sendWarningMessage("hello，", "smile:这是一个警告的通知");

      try {
          log.info("结果：{}", 1 / 0);
      } catch (Exception e) {
          dingTalkWarningNoticeServer.sendErrorMessage(StringUtils.join("smile:异常通知，原因：", e.getMessage()));
          dingTalkWarningNoticeServer.sendErrorMessage("world", StringUtils.join("smile:异常通知，原因：", e.getMessage()));
      }
  }
  ```

## 4.3 集成异常管理模块

项目中，经常会遇到各种各样的异常，有时候异常信息可以给用户看，比如说：**银行卡号填写错误、邮箱格式不合法等**、而有的信息不能给用户看，比如说：**系统走神了...**

还有一种场景，针对异常信息，有时候需要进行文字翻译，比如：系统返回的失败信息是 AAA，可能需要在适配层翻译为BBB返回给用户，可以做对照表等等进行处理

### 4.3.1 添加异常模块依赖

```xml
    <dependency>
        <groupId>com.smilehappiness</groupId>
        <artifactId>smilehappiness-exception</artifactId>
    </dependency>
```

### 4.3.2 使用异常

#### 4.3.2.1 两种异常说明

**目前定义了两种异常：** `一种是业务异常，另外一种是系统级异常`

* 使用业务异常（`BusinessException`）时，异常信息会直接返回给用户端，业务异常支持添加了普通code，业务code，以及异常信息。在设计的时候，**业务异常类这里额外添加一个业务`bizCode`参数**，`为了后续扩展性更强(可以基于业务bizCode做不同的信息对照展示，对于前端而言，基于普通的code，200或者非200即可判断是否请求接口成功)`

* 使用系统级异常（`SystemInternalException`）时，针对这种系统异常，会统一降级处理，比如可以友好的返回：`系统升级中，请您稍后再试...`，而不是返回系统走神了或者一大串英文异常给客户

#### 4.3.2.2 异常使用示例

**具体使用可参考如下示例：**

```java
    @GetMapping("/getApiLoggerInfoByRequestUrlAndMethodName")
    public CommonResult<List<ApiLogger>> getApiLoggerInfoByRequestUrlAndMethodName(@RequestParam("requestUrl") String requestUrl, @RequestParam("methodName") String methodName) {
        LocalDateTime bizTimeStart = LocalDateTime.now();
        CommonResult<List<ApiLogger>> commonResult = new CommonResult<>();
        try {
            List<ApiLogger> apiLoggerList = apiLoggerService.getApiLoggerInfoByRequestUrlAndMethodName(requestUrl, methodName);
            if (CollectionUtils.isEmpty(apiLoggerList)) {
                throw new BusinessException(FrameworkBusinessExceptionEnum.API_LOGGER_INFO_NULL);
            }

            log.info("通过请求url和方法名称，获取日志信息接口返回结果:{}", JSON.toJSONString(commonResult));
            log.info("通过请求url和方法名称，获取日志信息方法执行耗时（毫秒）：{}", DateUtil.getTakeTime(bizTimeStart, LocalDateTime.now(), TimeUnit.MILLISECONDS));

            return commonResult;
        } catch (BusinessException e) {
            log.error("【业务异常】通过请求url和方法名称，获取日志信息异常，异常原因：{}", e.getMessage());
            log.info("通过请求url和方法名称，获取日志信息方法执行耗时（毫秒）：{}", DateUtil.getTakeTime(bizTimeStart, LocalDateTime.now(), TimeUnit.MILLISECONDS));

            throw new BusinessException(e.getCode(), e.getBizCode(), "通过请求url和方法名称，获取日志信息异常，异常原因：" + e.getMessage());
        } catch (Exception e) {
            log.error("【系统异常】通过请求url和方法名称，获取日志信息异常，异常原因：{}", e.getMessage());
            log.info("通过请求url和方法名称，获取日志信息方法执行耗时（毫秒）：{}", DateUtil.getTakeTime(bizTimeStart, LocalDateTime.now(), TimeUnit.MILLISECONDS));

            throw new SystemInternalException("通过请求url和方法名称，获取日志信息异常，异常原因：" + e.getMessage());
        }
    }
```




限于篇幅，这里就不详细介绍了，**详细的使用教程，可以参考我另外的博客，写的非常详细：**

* [Spring Cloud 微服务基础功能架构来啦](https://blog.csdn.net/smilehappiness/article/details/120623974)
* [基于Spring Cloud 的微服务架构脚手架，满满的干货来啦](https://blog.csdn.net/smilehappiness/article/details/120624307)
***
* **GitHub下载地址：** [smilehappiness-spring-cloud](https://github.com/smilehappinessli/smilehappiness-spring-cloud.git)
* **Gitee下载地址：** [smilehappiness-spring-cloud](https://gitee.com/smilehappiness/smilehappiness-spring-cloud)
