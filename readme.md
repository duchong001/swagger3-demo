# Swagger3使用说明

`swagger`是干嘛的不是本说明介绍的内容，请自行百度。

本说明旨在快速上手使用`swagger`生成接口文档，`swagger3`真香！！！

# 一、依赖

添加依赖和`spring-boot-starter-parent`的版本有关，自动引入的`spring-plugin-core`包版本不一致会导致项目跑不起来，这里是个大坑。

## 1.1、2.1.x版本

```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
    <exclusions>
        <exclusion>
            <artifactId>spring-plugin-core</artifactId>
            <groupId>org.springframework.plugin</groupId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.plugin</groupId>
    <artifactId>spring-plugin-core</artifactId>
    <version>2.0.0.RELEASE</version>
</dependency>
```

## 1.2、2.3.x版本

```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency> 
```

# 二、配置

## 2.1、启动类

启动类添加`@EnableOpenApi`注解，然并卵，经过测试不加也可以(黑人问号脸.jpg)，到底加还是不加，看你心情吧。

```java
@EnableOpenApi
@SpringBootApplication
public class Swagger3DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Swagger3DemoApplication.class, args);
    }
}
```

## 2.2、swagger配置

可以根据`Environment`和`Profiles`对象来控制不同环境文档地址是否对外暴漏

```java
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket initDocket(Environment env) {

        //设置要暴漏接口文档的配置环境
        Profiles profile = Profiles.of("dev", "test");
        boolean flag = env.acceptsProfiles(profile);
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger3-Demo接口文档")
                .description("技术支持-云嘉健康技术团队")
                .contact(new Contact("云嘉健康技术团队", "http://www.yunjiacloud.com", "duchong@yunjiacloud.com "))
                .version("1.0")
                .build();
    }
}
```

# 三、常用注解

## 3.1、类

`@Api()`：表示这个类是 swagger 资源

- tags：表示说明内容，只写 tags 就可以省略属性名
- value：同样是说明，不过会被 tags 替代，没卵用

## 3.2、方法上

`@ApiOperation()` ：对方法的说明，注解位于方法上

- value：方法的说明
- notes：额外注释说明
- response：返回的对象
- tags：这个方法会被单独摘出来，重新分组，若没有，所有的方法会在一个Controller分组下

## 3.3、方法入参

`@ApiParam() `：对方法参数的具体说明，`用在方法入参括号里`，该注解在post请求参数时，参数名不显示

- name：参数名
- value：参数说明
- required：是否必填



`@ApiImplicitParam`对方法参数的具体说明，`用在方法上@ApiImplicitParams之内`，该注解在get,post请求参数时，参数名均正常显示

- name 参数名称
- value 参数的简短描述
- required 是否为必传参数
- dataType 参数类型，可以为类名，也可以为基本类型（String，int、boolean等）指定也不起作用，没卵用
- paramType 参数的传入（请求）类型，可选的值有path, query, body, header or form。



## 3.4、实体

`@ApiModel`描述一个Model的信息（一般用在请求参数无法使用`@ApiImplicitParam`注解进行描述的时候）

- value model的别名，默认为类名
- description model的详细描述

`@ApiModelProperty`描述一个model的属性

- value 属性简短描述
- example 属性的示例值
- required 是否为必须值

## 3.5、header参数

```java
@ApiImplicitParams({      @ApiImplicitParam(paramType="header",name="USERTOKEN",dataType="String",required=true,value="用户token")
    })
```
## 3.6、file入参

需要使用`@RequestPart` 注解

```java
@ApiOperation(value = "上传文件接口",notes = "上传文件接口")
@ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "上传人")
})
@PostMapping(value = "/uploadFile")
public String uploadFile(@RequestParam("name") String name,@RequestPart("file") MultipartFile file){
    
}
```


# 
# 四、拦截器放行

若项目中有使用拦截器，放行以下路径

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    TokenInterceptor tokenInterceptor;

    /**
     * 拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册token拦截器
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/swagger-ui/**")
                .excludePathPatterns("/**/swagger-resources/**")
                .excludePathPatterns("/**/v3/**")
        ;
    }
}
```

# 五、文档访问地址

http://ip:port/context-path/swagger-ui/

http://ip:port/context-path/swagger-ui/index.html

# 六、统一返回值问题

若项目中使用了统一返回值的包装类例如`BaseResponse`，方法返回时加上泛型，

例如返回 `BaseResponse<User>`