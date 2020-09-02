jvm参数
    
    堆内存
        –Xms:最小堆内存
        -Xmx:最大堆内存
        例： -Xms2G -Xmx5G
    
    新生代内存
        -XX:NewSize和-XX:MaxNewSize指定
        例： -XX:NewSize=256m
            -XX:MaxNewSize=1024m
        
        -Xmn256m 
    
    显示指定永久代/元空间的大小
        
        1.8以前
            -XX:PermSize=N //方法区 (永久代) 初始大小
            -XX:MaxPermSize=N //方法区 (永久代) 最大大小,超过这个值将会抛出 OutOfMemoryError 异常:java.lang.OutOfMemoryError: PermGen
        1.8以后
            -XX:MetaspaceSize=N //设置 Metaspace 的初始（和最小大小）
            -XX:MaxMetaspaceSize=N //设置 Metaspace 的最大大小，如果不指定大小的话，随着更多类的创建，虚拟机会耗尽所有可用的系统内存。
    
    垃圾回收器
    
        -XX:+UseSerialGC    串行垃圾收集器
        -XX:+UseParallelGC  并行垃圾收集器
        -XX:+USeParNewGC    CMS垃圾收集器
        -XX:+UseG1GC        G1垃圾收集器