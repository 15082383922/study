Spring 的 6 个特征:

    核心技术 ：依赖注入(DI)，AOP，事件(events)，资源，i18n，验证，数据绑定，类型转换，SpEL。
    测试 ：模拟对象，TestContext框架，Spring MVC 测试，WebTestClient。
    数据访问 ：事务，DAO支持，JDBC，ORM，编组XML。
    Web支持 : Spring MVC和Spring WebFlux Web框架。
    集成 ：远程处理，JMS，JCA，JMX，电子邮件，任务，调度，缓存。
    语言 ：Kotlin，Groovy，动态语言。

注释解释：
    
    @Controller：返回一个页面，单独使用 @Controller 不加 @ResponseBody的话一般使用在要返
                回一个视图的情况，这种情况属于比较传统的Spring MVC 的应用，对应于前后端不分离的情况。
                
    @RestController：返回JSON 或 XML 形式数据，@RestController只返回对象，对象数据直接以 JSON 或 XML 形式写入 
                    HTTP 响应(Response)中，这种情况属于 RESTful Web服务，这也是目前日常开发所接触的最常用的情况（前后端分离）。
    
    @Controller +@ResponseBody：返回JSON 或 XML 形式数据。如果你需要在Spring4之前开发 RESTful Web服务的话，你需要使用@Controller 
                                并结合@ResponseBody注解，也就是说@Controller +@ResponseBody= @RestController（Spring 4 之后新加的注解）
                                
    @ResponseBody:将 Controller 的方法返回的对象通过适当的转换器转换为指定的格式之后，写入到HTTP 响应(Response)对象的 body 中，
                    通常用来返回 JSON 或者 XML 数据，返回 JSON 数据的情况比较多。
    
    @Component： 注解作用于类，通常是通过类路径扫描来自动侦测以及自动装配到Spring容器中
           可标注任意类为 Spring 组件。如果一个Bean不知道属于哪个层，可以使用@Component 注解标注。
    
    @Bean：注解作用于方法，注解通常是我们在标有该注解的方法中定义产生这个 bean,@Bean告诉了Spring这是某个类的示例，当我需要用它的时候还给我
    
    @Repository : 对应持久层即 Dao 层，主要用于数据库相关操作
    
    @Service : 对应服务层，主要涉及一些复杂的逻辑，需要用到 Dao层。
    
    @Transactional(rollbackFor = Exception.class):@Transactional注解作用于类上时，该类的所有 public 方法将都具有该类型的事务属性，
        同时，我们也可以在方法级别使用该标注来覆盖类级别的定义。如果类或者方法加了这个注解，那么这个类里面的方法抛出异常，就会回滚，数据库里面的数据也会回滚
        在@Transactional注解中如果不配置rollbackFor属性,那么事物只会在遇到RuntimeException的时候才会回滚,加上rollbackFor=Exception.class,可以让事物在遇到非运行时异常时也回滚。
        
Spring IoC
    
    Inverse of Control:控制反转
         将原本在程序中手动创建对象的控制权，交由Spring框架来管理
         IoC 容器是 Spring 用来实现 IoC 的载体， IoC 容器实际上就是个Map（key，value）,Map 中存放的是各种对象
         IoC 容器就像是一个工厂一样，当我们需要创建一个对象的时候，只需要配置好配置文件/注解即可，完全不用考虑对象是如何被创建出来的
    
    Spring IoC的初始化过程：
            读取           解析                 注册
        XML----->Resource------>BeanDefinition----->Beanfactory

Spring IOC 
    
    Aspect-Oriented Programming:面向切面编程
        能够将那些与业务无关，却为业务模块所共同调用的逻辑或责任（例如事务处理、日志管理、权限控制等）封装起来，
        便于减少系统的重复代码，降低模块间的耦合度，并有利于未来的可拓展性和可维护性
        
        Spring AOP就是基于动态代理的，如果要代理的对象，实现了某个接口，那么Spring AOP会使用JDK Proxy，去创建代理对象，而对于没有实现接口
        的对象，就无法使用 JDK Proxy 去进行代理了，这时候Spring AOP会使用Cglib ，这时候Spring AOP会使用 Cglib 生成一个被代理对象的子类来作为代理
        
Spring AOP 和 AspectJ AOP 有什么区别
    
    Spring AOP 
        属于运行时增强
        基于代理(Proxying)
    AspectJ 
        编译时增强。
        基于字节码操作(Bytecode Manipulation)。
        Java 生态系统中最完整的 AOP 框架了
        
    切面比较少，那么两者性能差异不大。但是，当切面太多的话，最好选择 AspectJ ，它比Spring AOP 快很多
    
Spring 中的 bean 的作用域有哪些?
    
    singleton : 唯一bean实例，Spring中的bean默认都是单例的。
    prototype : 每次请求都会创建一个新的bean实例。
    request : 每一次HTTP请求都会产生一个新的bean，该bean仅在当前HTTP request内有效。
    session : 每一次HTTP请求都会产生一个新的bean，该bean仅在当前HTTP session内有效。
    global-session： 全局session作用域，仅仅在基于portlet的web应用中才有意义，Spring5已经没有了。Portlet是能够生成语义代码(例如：HTML)片段的小型Java Web插件。它们基于portlet容器，
                    可以像servlet一样处理HTTP请求。但是，与 servlet 不同，每个 portlet 都有不同的会话

Spring 中的单例 bean 的线程安全问题了解吗？

    在Bean对象中尽量避免定义可变的成员变量（不太现实）
    在类中定义一个ThreadLocal成员变量，将需要的可变成员变量保存在 ThreadLocal 中（推荐的一种方式）。
    
Spring 中的 bean 生命周期?

    Bean 容器找到配置文件中 Spring Bean 的定义。
    Bean 容器利用 Java Reflection API 创建一个Bean的实例。
    如果涉及到一些属性值 利用 set()方法设置一些属性值。
    如果 Bean 实现了 BeanNameAware 接口，调用 setBeanName()方法，传入Bean的名字。
    如果 Bean 实现了 BeanClassLoaderAware 接口，调用 setBeanClassLoader()方法，传入 ClassLoader对象的实例。
    与上面的类似，如果实现了其他 *.Aware接口，就调用相应的方法。
    如果有和加载这个 Bean 的 Spring 容器相关的 BeanPostProcessor 对象，执行postProcessBeforeInitialization() 方法
    如果Bean实现了InitializingBean接口，执行afterPropertiesSet()方法。
    如果 Bean 在配置文件中的定义包含 init-method 属性，执行指定的方法。
    如果有和加载这个 Bean的 Spring 容器相关的 BeanPostProcessor 对象，执行postProcessAfterInitialization() 方法
    当要销毁 Bean 的时候，如果 Bean 实现了 DisposableBean 接口，执行 destroy() 方法。
    当要销毁 Bean 的时候，如果 Bean 在配置文件中的定义包含 destroy-method 属性，执行指定的方法。
    
SpringMVC 工作原理了解吗?

    客户端（浏览器）发送请求，直接请求到 DispatcherServlet。
    DispatcherServlet 根据请求信息调用 HandlerMapping，解析请求对应的 Handler。
    解析到对应的 Handler（也就是我们平常说的 Controller 控制器）后，开始由 HandlerAdapter 适配器处理。
    HandlerAdapter 会根据 Handler 来调用真正的处理器来处理请求，并处理相应的业务逻辑。
    处理器处理完业务后，会返回一个 ModelAndView 对象，Model 是返回的数据对象，View 是个逻辑上的 View。
    ViewResolver 会根据逻辑 View 查找实际的 View。
    DispaterServlet 把返回的 Model 传给 View（视图渲染）。
    把 View 返回给请求者（浏览器）
    
Spring 框架中用到了哪些设计模式
    
    工厂设计模式 : Spring使用工厂模式通过 BeanFactory、ApplicationContext 创建 bean 对象。
    代理设计模式 : Spring AOP 功能的实现。
    单例设计模式 : Spring 中的 Bean 默认都是单例的。
    模板方法模式 : Spring 中 jdbcTemplate、hibernateTemplate 等以 Template 结尾的对数据库操作的类，它们就使用到了模板模式。
    包装器设计模式 : 我们的项目需要连接多个数据库，而且不同的客户在每次访问中根据需要会去访问不同的数据库。这种模式让我们可以根据客户的需求能够动态切换不同的数据源。
    观察者模式: Spring 事件驱动模型就是观察者模式很经典的一个应用。
    适配器模式 :Spring AOP 的增强或通知(Advice)使用到了适配器模式、spring MVC 中也是用到了适配器模式适配Controller。
    
Spring 管理事务的方式有几种
    
    编程式事务，在代码中硬编码。(不推荐使用)
    声明式事务，在配置文件中配置（推荐使用）
    
    声明式事务又分为两种：
        基于XML的声明式事务
        基于注解的声明式事务
    
Spring 事务中的隔离级别有哪几种
    ransactionDefinition 接口中定义了五个表示隔离级别的常量：
    
    TransactionDefinition.ISOLATION_DEFAULT: 使用后端数据库默认的隔离级别，Mysql 默认采用的 REPEATABLE_READ隔离级别 Oracle 默认采用的 READ_COMMITTED隔离级别.
    TransactionDefinition.ISOLATION_READ_UNCOMMITTED: 最低的隔离级别，允许读取尚未提交的数据变更，可能会导致脏读、幻读或不可重复读
    TransactionDefinition.ISOLATION_READ_COMMITTED: 允许读取并发事务已经提交的数据，可以阻止脏读，但是幻读或不可重复读仍有可能发生
    TransactionDefinition.ISOLATION_REPEATABLE_READ: 对同一字段的多次读取结果都是一致的，除非数据是被本身事务自己所修改，可以阻止脏读和不可重复读，但幻读仍有可能发生。
    TransactionDefinition.ISOLATION_SERIALIZABLE: 最高的隔离级别，完全服从ACID的隔离级别。所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。但是这将严重影响程序的性能。通常情况下也不会用到该级别。

Spring 事务中哪几种事务传播行为

    支持当前事务的情况：
    TransactionDefinition.PROPAGATION_REQUIRED： 如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
    TransactionDefinition.PROPAGATION_SUPPORTS： 如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
    TransactionDefinition.PROPAGATION_MANDATORY： 如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。（mandatory：强制性）
    
    不支持当前事务的情况：
    TransactionDefinition.PROPAGATION_REQUIRES_NEW： 创建一个新的事务，如果当前存在事务，则把当前事务挂起。
    TransactionDefinition.PROPAGATION_NOT_SUPPORTED： 以非事务方式运行，如果当前存在事务，则把当前事务挂起。
    TransactionDefinition.PROPAGATION_NEVER： 以非事务方式运行，如果当前存在事务，则抛出异常。
    
    其他情况：
    
    TransactionDefinition.PROPAGATION_NESTED： 如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于TransactionDefinition.PROPAGATION_REQUIRED。