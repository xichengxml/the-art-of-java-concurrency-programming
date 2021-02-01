#### 使用
DCL + volatile

#### 相关概念
* 指令重排
* 内存屏障
* happens-before

##### 学习方向
1. java代码层面
2. java字节码层面
3. openjdk源码 c++
4. 汇编层面
5. CPU层面

#### 分析角度·
* 读volatile修饰的变量 getstatic
1. 每次读的时候都会从主内存读取，然后在工作内存中生成一个副本
* 写volatile修饰的变量 putstatic
* volatile保证了并发编程的三大特性
1. 可见性
2. 有序性
3. 原子性（部分）

执行引擎代码一般结构
```
while() {
    case getstatic:
    break;
    case putstatic:
    break;
}
```

openjdk三层引擎:
* 字节码解释器
* 模板解释器
* JIT优化技术

* 主内存：堆区 + 方法区
* 工作内存：虚拟机栈

* 编译期指令重排
1. java字节码层面，没有指令重排
2. openjdk源码层面，做了gcc -O2
3. 如果不希望编译器优化，可以使用编译屏障
* 运行期指令重排：CPU乱序执行

* as-if-serial语义：跟在单线程环境下运行的结果是一致的
* happens-before原则：
1. JVM在设计的时候，有些逻辑的先后顺序是固定的，比如，new要先与finalize
2. 有些逻辑是无法提前知晓的，引入了内存屏障

#### 面试题
1. volatile如何保证可见性

加volatile，会回写内存

2. JVM有多少个虚拟机栈

一个线程一个，随线程的创建而创建，随线程的结束而销毁

3. 有多少栈帧

方法的调用次数，调用一次方法会生成一个栈帧

4. volatile是如何保证有序性的

禁止指令重排

5. 加不加volatile，字节码是否一样

一样（查看com.xicheng.concurrent.tuling.C01_Volatile.change字节码），在JVM层面判断是否加volatile
```
// 加volatile
0 iconst_1
1 putstatic #7 <com/xicheng/concurrent/tuling/C01_Volatile.found>
4 return
```

```
// 不加volatile
0 iconst_1
1 putstatic #7 <com/xicheng/concurrent/tuling/C01_Volatile.found>
4 return
```

6. DCL为什么要加volatile


7. 为什么说new不是原子操作

查看com.xicheng.concurrent.tuling.C02_DCL.newInstance字节码

JVM new正常流程（没有指令重排）
```
// 在堆区（TLAB）分配内存，生成一个不完全对象
// 将不完全对象的引用压入栈顶
 0 new #3 <com/xicheng/concurrent/tuling/C02_DCL>
// 复制栈顶元素
// 将复制的数值压入栈顶
 3 dup
// this指针
// 堆区（TLAB）中的对象就是一个完整的对象（执行了默认的构造方法）
 4 invokespecial #5 <com/xicheng/concurrent/tuling/C02_DCL.<init>>
// 将完整对象的引用赋值给方法区的共享变量INSTANCE
 7 putstatic #2 <com/xicheng/concurrent/tuling/C02_DCL.INSTANCE>
10 return
```

4、7乱序执行，有的对象可能拿到的对象引用就是不完全对象的引用，这就是DCL加volatile的原因




