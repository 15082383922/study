
名词解释
    
    类加载器
    方法区：用于存放类似于元数据信息方面的数据的，比如类信息，常量，静态变量，编译后代码···等，线程不安全
    堆：主要放了一些存储的数据，比如对象实例，数组···等，它和方法区都同属于 线程共享区域。线程不安全
    栈：代码运行空间，线程独享
    程序计数器：完成一个加载工作，线程独享

小结：
    
    Java文件经过编译后变成 .class 字节码文件
    字节码文件通过类加载器被搬运到 JVM 虚拟机中
    虚拟机主要的5大块：方法区，堆都为线程共享区域，有线程安全问题，栈和本地方法栈和计数器都是独享区域，不存在线程安全问题，而 JVM 的调优主要就是围绕堆，栈两大块进行
    
public class App{
    public static void main(String[] args) {
        Student student = newStudent("tellUrDream");
        student.sayName();
    }
}
上述代码的执行过程
    
    1、编译好 App.java 后得到 App.class 后，执行 App.class，系统会启动一个 JVM 进程，从 classpath 路径中找到一个名为 App.class 的二进制文件，将 App 的类信息加载到运行时数据区的方法区内，这个过程叫做 App 类的加载
    2、JVM 找到 App 的主程序入口，执行main方法
    3、这个main中的第一条语句为 Student student = new Student("tellUrDream") ，就是让 JVM 创建一个Student对象，但是这个时候方法区中是没有 Student 类的信息的，所以 JVM 马上加载 Student 类，把 Student 类的信息放到方法区中
    4、加载完 Student 类后，JVM 在堆中为一个新的 Student 实例分配内存，然后调用构造函数初始化 Student 实例，这个 Student 实例持有 指向方法区中的 Student 类的类型信息 的引用
    5、执行student.sayName();时，JVM 根据 student 的引用找到 student 对象，然后根据 student 对象持有的引用定位到方法区中 student 类的类型信息的方法表，获得 sayName() 的字节码地址。
    6、执行sayName()

类加载器的流程

    类被加载到虚拟机内存中开始，到释放内存总共有7个步骤：加载，验证，准备，解析，初始化，使用，卸载
    
    加载：
        将class文件加载到内存
        将静态数据结构转化成方法区中运行时的数据结构
        在堆中生成一个代表这个类的 java.lang.Class对象作为数据访问的入口
    链接
        验证
            确保加载的类符合 JVM 规范和安全，保证被校验类的方法在运行时不会做出危害虚拟机的事件，其实就是一个安全检查
        准备
            为static变量在方法区中分配内存空间，设置变量的初始值，
        解析
            虚拟机将常量池内的符号引用替换为直接引用的过程
    初始化
        初始化其实就是一个赋值的操作，它会执行一个类构造器的<clinit>()方法，由编译器自动收集类中所有变量的赋值动作，此时准备阶段时的那个 static int a = 3 的例子，在这个时候就正式赋值为3
    使用
    卸载
        GC将无用对象从内存中卸载
        
类加载器的加载顺序
    加载一个Class类的顺序也是有优先级的，类加载器从最底层开始往上的顺序是这样的
    
    BootStrap ClassLoader：rt.jar
    Extention ClassLoader: 加载扩展的jar包
    App ClassLoader：指定的classpath下面的jar包
    