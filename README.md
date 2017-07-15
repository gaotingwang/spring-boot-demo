# Spring-boot demo

## Web

- JSP支持

  1. 添加依赖

     ```xml
     <dependency>
         <groupId>org.apache.tomcat.embed</groupId>
         <artifactId>tomcat-embed-jasper</artifactId><!--支持jsp格式-->
         <scope>provided</scope>
     </dependency>
     <dependency>
         <groupId>javax.servlet</groupId>
         <artifactId>jstl</artifactId><!--支持jsp一些语法-->
         <version>1.2</version>
         <scope>compile</scope>
     </dependency>
     ```

  2. `application.yml`相关配置

     ```yaml
     spring:
       mvc:
         view:
           prefix: /WEB-INF/jsp/
           suffix: .jsp
     ```

  3. `Config`文件需要继承`SpringBootServletInitializer`重写`configure()`方法

  4. [统一异常处理@ControllerAdvice](https://github.com/gaotingwang/spring-boot-demo/blob/master/web-jsp/src/main/java/com/gtw/jsp/handler/GlobalExceptionHandler.java)

  5. [示例代码](https://github.com/gaotingwang/spring-boot-demo/tree/master/web-jsp)

- Thymeleaf模板引擎（敬请后续...）

## Event

- 事件驱动模型也就是我们常说的观察者，或者发布-订阅模型。

- 事件驱动首先是一种对象间的一对多的关系，当目标发生改变（发布），观察者（订阅者）就可以接收到改变，观察者如何处理（如行人如何走，是快走/慢走/不走，目标红绿灯不会管的），目标无需干涉，所以就松散耦合了它们之间的关系。

1. [定义需要监听的事件](https://github.com/gaotingwang/spring-boot-demo/blob/master/event/src/main/java/com/gtw/event/model/RegisterEvent.java)

2. 定义监听器Listener有三种方式

   - [无序监听器ApplicationListener](https://github.com/gaotingwang/spring-boot-demo/blob/master/event/src/main/java/com/gtw/event/listener/RegisterListener.java)（监听器事件过多，具体执行顺序是不保证的）

   - [有序监听器SmartApplicationListener](https://github.com/gaotingwang/spring-boot-demo/blob/master/event/src/main/java/com/gtw/event/listener/EmailRegisterListener.java)

   - [使用注解@EventListener](https://github.com/gaotingwang/spring-boot-demo/blob/master/event/src/main/java/com/gtw/event/listener/IndexRegisterListener.java)

3. [执行事件](https://github.com/gaotingwang/spring-boot-demo/blob/master/event/src/main/java/com/gtw/event/service/PublishRegister.java)

## Retry

1. 重试一个失败的操作有时是必要的，`Spring Batch` 提供了重试策略，添加相关依赖

   ```xml
   <dependency>
       <groupId>org.springframework.retry</groupId>
       <artifactId>spring-retry</artifactId>
       <version>1.1.4.RELEASE</version>
   </dependency>
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-aop</artifactId>
   </dependency>
   ```

2. 在`Config`类中添加`@EnableRetry`开启重试支持

3. 重试方式有两种：

   - 使用[注解重试](https://github.com/gaotingwang/spring-boot-demo/blob/master/retry/src/main/java/com/gtw/retry/service/IAnnotationRetryService.java)
   - 使用[Template重试](https://github.com/gaotingwang/spring-boot-demo/blob/master/retry/src/main/java/com/gtw/retry/service/TemplateRetryService.java)

## Redis及缓存

1. 添加redis依赖

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-redis</artifactId>
   </dependency>
   ```

2. 如需要开启缓存支持，需要在`Config`类中增加`@EnableCaching`注解开启缓存功能。

3. `application.yml`中添加[redis相关配置](https://github.com/gaotingwang/spring-boot-demo/blob/master/data-redis/src/main/resources/application.yml)

4. `RedisTemplate`及其序列化方式设置，参考[RedisConfig](https://github.com/gaotingwang/spring-boot-demo/blob/master/data-redis/src/main/java/com/gtw/redis/config/RedisConfig.java)

5. `@CacheConfig`相关使用参考[CacheService](https://github.com/gaotingwang/spring-boot-demo/blob/master/data-redis/src/main/java/com/gtw/redis/service/CacheUserServiceImpl.java)

## Json序列化

- [Jackson相关使用介绍](http://www.baeldung.com/jackson-annotations)
- [Jackson相关demo](https://github.com/gaotingwang/spring-boot-demo/blob/master/jackson/src/main/java/com/gtw/jackson/JacksonApplication.java)

## Test

1. 添加相关依赖

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-test</artifactId>
       <scope>test</scope>
   </dependency>
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-test</artifactId>
   </dependency>
   ```

2. 测试用例类前需要添加`@RunWith(SpringRunner.class)`和`@SpringBootTest`，如若有数据库相关测试用例需加`@Transactional`测试完成后进行回滚

3. 若是需要对JPA接口进行单元测试，可以使用[@DataJpaTest](https://github.com/gaotingwang/spring-boot-demo/blob/master/data-jpa/src/test/java/com/gtw/jpa/respository/ProductRepositoryTest.java)

4. 对[Controller接口测试](https://github.com/gaotingwang/spring-boot-demo/blob/master/testing/src/test/java/com/gtw/test/web/SeckillControllerTest.java)

## Mybatis

1. 添加依赖(使用的MySQL)

   ```xml
   <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
       <scope>runtime</scope>
   </dependency>
   <dependency>
       <groupId>org.mybatis.spring.boot</groupId>
       <artifactId>mybatis-spring-boot-starter</artifactId>
       <version>1.1.1</version>
   </dependency>
   ```

2. 关于数据库连接相关设置

   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/msg?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true
       username: root
       password: root
   ```

3. dao层接口需要加`@Mapper`注解，之后spring会自动扫描到

4. 具体sql写入与[dao层接口同名的XML文件](https://github.com/gaotingwang/spring-boot-demo/blob/master/data-mybatis/src/main/java/com/gtw/mybatis/repository/mapper/TransMapper.xml)中

5. 补充：由于IntelliJ IDEA的原因，放在java目录下xml文件最后不会被编译，导致运行时找不到dao的实现接口，解决方法在pom.xml添加：

   ```xml
   <build>
       <resources>
           <resource>
               <directory>src/main/java</directory>
               <includes>
                   <include>**/*.xml</include>
               </includes>
               <filtering>false</filtering>
           </resource>
           <resource>
               <directory>src/main/resources</directory>
           </resource>
       </resources>
   </build>
   ```

6. 多数据源配置

   a.多数据库yml配置

   ```yaml
   demo:
     datasource:
       master:
         url: jdbc:mysql://localhost:3306/test1?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true
         username: root
         password: root
         driver-class-name: com.mysql.jdbc.Driver
       slave:
         url: jdbc:mysql://localhost:3306/test2?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true
         username: root
         password: root
         driver-class-name: com.mysql.jdbc.Driver
   ```

   b.相关config，[具体代码](https://github.com/gaotingwang/spring-boot-demo/tree/master/data-mybatis/src/main/java/com/gtw/mybatis/config)

   ```java
   /**
    * 配置多数据源
    */
   @Configuration
   public class DataSourceConfig {

       /**
        * 主数据源
        */
       @Primary
       @Bean(name = "masterDataSource")
       @Qualifier("masterDataSource")
       @ConfigurationProperties(prefix="demo.datasource.master")
       public DataSource masterDataSource() {
           return DataSourceBuilder.create().build();
       }

       /**
        * 从数据源
        */
       @Bean(name = "slaveDataSource")
       @Qualifier("slaveDataSource")
       @ConfigurationProperties(prefix="demo.datasource.slave")
       public DataSource slaveDataSource() {
           return DataSourceBuilder.create().build();
       }
   }


   /**
    * 主数据源详情配置
    */
   @Configuration
   // 制定分库的mapper文件地址，以及分库到层代码
   @MapperScan(basePackages = "com.gtw.mybatis.repository.mapper.master", sqlSessionTemplateRef  = "masterSqlSessionTemplate")
   public class MasterDataSourceConfig {

       /**
        * 创建SqlSessionFactory
        */
       @Primary
       @Bean(name = "masterSqlSessionFactory")
       public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception {
           SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
           sessionFactoryBean.setDataSource(dataSource);
   //        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
   //                .getResources("classpath*:mapper/master/*.xml"));
           return sessionFactoryBean.getObject();
       }

       /**
        * 创建事务管理
        */
       @Primary
       @Bean(name = "masterTransactionManager")
       public DataSourceTransactionManager transactionManager(@Qualifier("masterDataSource") DataSource dataSource) {
           return new DataSourceTransactionManager(dataSource);
       }

       /**
        * 将SqlSessionFactory包装到SqlSessionTemplate中
        */
       @Primary
       @Bean(name = "masterSqlSessionTemplate")
       public SqlSessionTemplate sqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
           return new SqlSessionTemplate(sqlSessionFactory);
       }

   }
   ```

7. ​

## JPA

1. 引入依赖

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-data-jpa</artifactId>
   </dependency>
   <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
       <scope>runtime</scope>
   </dependency>
   ```

2. [对象实体常用注解使用](https://github.com/gaotingwang/spring-boot-demo/blob/master/data-jpa/src/main/java/com/gtw/jpa/entity/core/Customer.java)

3. JPA提供了`@CreatedDate`等审计功能，自动生成创建时间、修改时间、创建人等，开启审计功能需要在`Config`文件中加`@EnableJpaAuditing`，参考[审计示例](https://github.com/gaotingwang/spring-boot-demo/blob/master/data-jpa/src/main/java/com/gtw/jpa/entity/core/Product.java)

4. 仓储层`Repository`接口不需要进行实现，只要继承`Repository`就好（还有其他`CrudRepository`等等），具体可参考[仓储层接口定义](https://github.com/gaotingwang/spring-boot-demo/blob/master/data-jpa/src/main/java/com/gtw/jpa/respository/CustomerRepository.java)

5. 分页功能与模糊查询功能可参考[分页与模糊查询](https://github.com/gaotingwang/spring-boot-demo/blob/master/data-jpa/src/main/java/com/gtw/jpa/respository/ProductRepository.java)

6. 其他功能，如需请参考[官方文档](http://docs.spring.io/spring-data/jpa/docs/1.11.1.RELEASE/reference/html/)

## Querydsl

1. 引入依赖

   ```xml
   <dependency>
       <groupId>com.querydsl</groupId>
       <artifactId>querydsl-apt</artifactId>
       <version>${querydsl.version}</version>
   </dependency>
   <dependency>
       <groupId>com.querydsl</groupId>
       <artifactId>querydsl-core</artifactId>
       <version>${querydsl.version}</version>
   </dependency>
   <dependency>
       <groupId>com.querydsl</groupId>
       <artifactId>querydsl-collections</artifactId>
       <version>${querydsl.version}</version>
   </dependency>
   ```

2. 工程构建时需要引入插件,这样会在工程模块的target/generated-sources/java对应目录下会生成相应的查询类QClass

   ```xml
   <build>
       <plugins>
           <plugin>
               <groupId>com.mysema.maven</groupId>
               <artifactId>apt-maven-plugin</artifactId>
               <version>1.1.3</version>
               <executions>
                   <execution>
                       <phase>generate-sources</phase>
                       <goals>
                           <goal>process</goal>
                       </goals>
                       <configuration>
                           <!-- If you are not using JPA or JDO -->
                           <outputDirectory>target/generated-sources/java</outputDirectory>
                           <processor>com.querydsl.apt.QuerydslAnnotationProcessor</processor>
                       </configuration>
                   </execution>
               </executions>
           </plugin>
       </plugins>
   </build>
   ```

3. 集合查询的querysql使用，参考[Querydsl使用](https://github.com/gaotingwang/spring-boot-demo/blob/master/data-querydsl/src/main/java/com/gtw/querydsl/core/Product.java)

4. Jpa集成Querysql,引入依赖与插件

   ```xml
   <dependencies>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-data-jpa</artifactId>
       </dependency>
       <dependency>
           <groupId>mysql</groupId>
           <artifactId>mysql-connector-java</artifactId>
           <scope>runtime</scope>
       </dependency>

       <!--querydsl支持-->
       <dependency>
           <groupId>com.querydsl</groupId>
           <artifactId>querydsl-apt</artifactId>
       </dependency>
       <dependency>
           <groupId>com.querydsl</groupId>
           <artifactId>querydsl-jpa</artifactId>
       </dependency>
       <dependency>
           <groupId>com.querydsl</groupId>
           <artifactId>querydsl-jpa</artifactId>
           <version>${querydsl.version}</version>
           <classifier>apt</classifier>
       </dependency>
       <dependency>
           <groupId>javax.inject</groupId>
           <artifactId>javax.inject</artifactId>
           <version>1</version>
       </dependency>
       <!--test-->
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-test</artifactId>
           <scope>test</scope>
       </dependency>
   </dependencies>

   <build>
       <plugins>
           <plugin>
               <groupId>com.mysema.maven</groupId>
               <artifactId>apt-maven-plugin</artifactId>
               <version>1.1.3</version>
               <dependencies>
                   <dependency>
                       <groupId>com.querydsl</groupId>
                       <artifactId>querydsl-apt</artifactId>
                       <version>${querydsl.version}</version>
                   </dependency>
               </dependencies>
               <executions>
                   <execution>
                       <goals>
                           <goal>process</goal>
                       </goals>
                       <configuration>
                           <!--JPAAnnotationProcessor查找使用javax.persistence.Entity注释注释的域类型，并为它们生成查询类型-->
                           <!--<outputDirectory>target/generated-sources/java</outputDirectory>-->
                           <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
                       </configuration>
                   </execution>
               </executions>
           </plugin>
       </plugins>
   </build>
   ```

5. Repository层使用QueryDsl，需要继承QueryDslPredicateExecutor，参考[ProductRepository](https://github.com/gaotingwang/spring-boot-demo/blob/master/data-jpa/src/main/java/com/gtw/jpa/respository/ProductRepository.java)

6. 也可用使用JPAQueryFactory，参考[OrderRepositoryCustomer](https://github.com/gaotingwang/spring-boot-demo/blob/master/data-jpa/src/main/java/com/gtw/jpa/respository/impl/OrderRepositoryCustomerImpl.java)

## Oauth2

- Oauth2相关介绍理解，参考[本人博客理解Oauth2](http://gtwlover.oschina.io/2016/11/01/%E7%90%86%E8%A7%A3Oauth2/)

- Oauth2 Server

  1. 添加依赖

     ```xml
     <!--security-->
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-security</artifactId>
     </dependency>
     <!--oauth2-->
     <dependency>
         <groupId>org.springframework.security.oauth</groupId>
         <artifactId>spring-security-oauth2</artifactId>
     </dependency>
     <!--redis-->
     <dependency>
         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-starter-data-redis</artifactId>
     </dependency>
     ```

  2. 关于`Oauth2ServerConfig`请参考[SecurityConfig](https://github.com/gaotingwang/spring-boot-demo/blob/master/security-oauth2-server/src/main/java/com/gtw/security/config/Oauth2ServerConfig.java)

     1. 用户名和密码的校验，需要指定`UserService`，`UserService`需要继承`UserDetailsService`

     2. 指定`Token`的缓存方式

     3. 指定密码的加密方式

     4. 设置`AuthenticationManager`

     5. **认证服务配置**

     6. 资源服务配置

  3. 用户名或密码修改时，[清除Token](https://github.com/gaotingwang/spring-boot-demo/blob/master/security-oauth2-server/src/main/java/com/gtw/security/service/TokenService.java)

  4. 关于跨域问题，请参考[跨域解决](https://github.com/gaotingwang/spring-boot-demo/blob/master/security-oauth2-server/src/main/java/com/gtw/security/config/CorsConfig.java)

- Oauth2 Client 
  1. `Client`使用只需要从缓存中拿取到`Token`即可，所以客户端主要配置`tokenStore`
  2. `ClientConfig`配置，请参考[ClientConfig](https://github.com/gaotingwang/spring-boot-demo/blob/master/security-oauth2-client/src/main/java/com/gtw/security/config/ClientConfig.java)
  3. 关于`Oauth2`的`Request`请求配置，可参考[Oauth2RequestConfig](https://github.com/gaotingwang/spring-boot-demo/blob/master/security-oauth2-client/src/main/java/com/gtw/security/config/Oauth2RequestConfig.java)




