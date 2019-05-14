# Logger:Powerful logging library in Android

# Potato(3):Powerful logging library in Android



[TOC]



这篇文章介绍 Android 中常用的日志功能。

日志上在开发中常用的一个工具，可以打印任何需要的信息来辅助开发，系统提供的日志模块在这里不做过多叙述；下面会分别从基本使用和源码去介绍一个强大的日志工具。

## 基本用法

这个日志的地址和基本使用方法在其开源库主页都有基本介绍：[logger](https://github.com/orhanobut/logger).

总结一下对于使用者来说，优点在哪：

1. 支持打印当前线程和当前方法
2. 支持 json，xml， list， map， set 等不同的格式输出
3. 自定义输出到文件，持久化保存日志文件。



### 在 Potato 中使用

在上一篇文章的基础上，初始化日志模块。

这里还有一个常见的**开发技巧**：对于第三方库来说，尽量自己再包装一层，方便以后替换。

1. 初始化: `ThirdModule.kt`

   ```kotlin
       fun init(context: Context) {
           this.context = context
           initLog()
       }
   
       private fun initLog() {
           Logger.addLogAdapter(AndroidLogAdapter())
           Logger.d("logger init")
       }
   ```

2. 新建文件 `LogUtils` 进行常用方法封装

   ```kotlin
   object LogUtils {
       const val TAG = "Potato"
       fun d(any: Any) {
           Logger.d(any)
       }
   
       fun i(message: String) {
           Logger.i(message)
       }
       fun w(message: String) {
           Logger.w(message)
       }
       fun e(message: String) {
           Logger.e(message)
       }
       fun wtf(message: String) {
           Logger.wtf(message)
       }
   }
   ```

上述的基本的用法介绍，对于 debug 级别的日志来说，可以打印很多数据类型，其余级别只支持打印 String 类型的数据。

基本使用和日志输出见官网



## 进阶使用

在进阶使用中，可以配置相关的日志输出选项，比如线程信息，方法信息等， 见下面代码：

```kotlin

    private fun initLog() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false) // 是否显示线程信息
            .methodCount(0)        // 是否显示方法信息
            .methodOffset(7)       //一个方法会有很多层级调用，偏移的方法数
//            .logStrategy()          //日志输出策略，logcat 还是 disk
            .tag("Potato")        // 自定义 tag
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }
```

相应代码的输出如下：

```kotlin
        am_btn_log.setOnClickListener {
            LogUtils.d("LogUtils debug")
            LogUtils.i("LogUtils info")
            LogUtils.w("LogUtils warning")
            LogUtils.e("LogUtils error")
            LogUtils.wtf("LogUtils wtf")

            LogUtils.d(arrayListOf(1, 2, 3))
            LogUtils.d(mapOf(1 to 1, 2 to 2))
            LogUtils.d(setOf(1, 2, 3, 2))

            val json = "{ \"key\": \"content\"}"
            LogUtils.d(json)
        }
```

![image](https://user-images.githubusercontent.com/10796970/57189045-dc393100-6f3b-11e9-82ff-29641523e0df.png)



此外， 还可以控制日志是否打印,  以及自定义 logtag 输出到文件。

```kotlin
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return false
            }
        })
			
			Logger.addLogAdapter(DiskLogAdapter())
```



### 优化

在 Android 常见的性能优化中，对于三方库来说，能延迟初始化的一定与延迟初始化，所以这里没有必要在 Provider 中做初始化，可以在具体使用的地方初始化，改动如下：

```kotlin
object LogUtils {
    const val TAG = "Potato"

    init {
        initLog()
        i("LogUtils init done")
    }

    private fun initLog() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false) // 是否显示线程信息
            .methodCount(0)        // 是否显示方法信息
            .methodOffset(7)       //一个方法会有很多层级调用，偏移的方法数
//            .logStrategy()          //日志输出策略，logcat 还是 disk
            .tag("Potato")        // 自定义 tag
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }
```







## 源码分析

![image](https://user-images.githubusercontent.com/10796970/57189342-405df400-6f40-11e9-91da-02b6b5442ba2.png)

首先看一下官网的流程图。



### 初始化

`Logger.addLogAdapter(AndroidLogAdapter())`: 首先来看初始化工作做了什么：

初始化 AndroidAdapter，如上所示，是接口 LogAdapter的具体实现类，另一个是 DiskLogAdater；分别用来输出到 LogCat 和文件，其内部的接口变量 FormatStrategy 来实现具体的内容。

首先来看 AndroidLogAdapter:

```kotlin
public class AndroidLogAdapter implements LogAdapter {

  @NonNull private final FormatStrategy formatStrategy;

  public AndroidLogAdapter() {
    this.formatStrategy = PrettyFormatStrategy.newBuilder().build();
  }

  public AndroidLogAdapter(@NonNull FormatStrategy formatStrategy) {
    this.formatStrategy = checkNotNull(formatStrategy);
  }

  @Override public boolean isLoggable(int priority, @Nullable String tag) {
    return true;
  }

  @Override public void log(int priority, @Nullable String tag, @NonNull String message) {
    formatStrategy.log(priority, tag, message);
  }
}
```

两个构造方法，提供默认实现和接收自定义的输出策略(对参数的必要性检查)。

默认的输出策略如下：PrettyFormatStrategy,  与 CsvFormatStrategy 一样，是 FormatStrategy 的具体实现类，控制不同的输出。  

1. 采用 Build Pattern 创建进阶使用的相关参数

   ```kotlin
     public static class Builder {
       int methodCount = 2;
       int methodOffset = 0;
       boolean showThreadInfo = true;
       @Nullable LogStrategy logStrategy;
       @Nullable String tag = "PRETTY_LOGGER";
   ```

2. 下面是最重要的 log 方法的实现：

   ```kotlin
     @Override public void log(int priority, @Nullable String onceOnlyTag, @NonNull String message) {
       // 必要的参数合法性校验，每个人都需要注意
       checkNotNull(message);
   		
       // 一次性日志的使用
       String tag = formatTag(onceOnlyTag);
   		
       // 上层边框
       logTopBorder(priority, tag);
       // 打印线程信息和方法信息
       logHeaderContent(priority, tag, methodCount);
   
       //get bytes of message with system's default charset (which is UTF-8 for Android)
       // 默认编码 UTF—8， 获取 message 长度
       byte[] bytes = message.getBytes();
       int length = bytes.length;
       // 支持最大长度 4000 个字节Byte， 可使用 adb logcat -d 查看大小
       if (length <= CHUNK_SIZE) {
         if (methodCount > 0) {
           // 分割线
           logDivider(priority, tag);
         }
         logContent(priority, tag, message);
         logBottomBorder(priority, tag);
         return;
       }
       if (methodCount > 0) {
         logDivider(priority, tag);
       }
       for (int i = 0; i < length; i += CHUNK_SIZE) {
         // 超出长度则分段输出
         int count = Math.min(length - i, CHUNK_SIZE);
         //create a new String with system's default charset (which is UTF-8 for Android)
         logContent(priority, tag, new String(bytes, i, count));
       }
       logBottomBorder(priority, tag);
     }
   ```

   其中打印方法的函数如下：

   ```kotlin
     private void logHeaderContent(int logType, @Nullable String tag, int methodCount) {
       // 获取当前执行的所有堆栈帧
       StackTraceElement[] trace = Thread.currentThread().getStackTrace();
       if (showThreadInfo) {
         logChunk(logType, tag, HORIZONTAL_LINE + " Thread: " + Thread.currentThread().getName());
         logDivider(logType, tag);
       }
       // 控制打印方法的缩进
       String level = "";
   		
       // 方法的偏移量
       int stackOffset = getStackOffset(trace) + methodOffset;
   
       //corresponding method count with the current stack may exceeds the stack trace. Trims the count
       if (methodCount + stackOffset > trace.length) {
         methodCount = trace.length - stackOffset - 1;
       }
   
       for (int i = methodCount; i > 0; i--) {
         int stackIndex = i + stackOffset;
         if (stackIndex >= trace.length) {
           continue;
         }
         // 具体的方法打印
         StringBuilder builder = new StringBuilder();
         builder.append(HORIZONTAL_LINE)
             .append(' ')
             .append(level)
             .append(getSimpleClassName(trace[stackIndex].getClassName()))  //类名
             .append(".")
             .append(trace[stackIndex].getMethodName())  //方法名
             .append(" ")
             .append(" (")
             .append(trace[stackIndex].getFileName())  //文件名
             .append(":")
             .append(trace[stackIndex].getLineNumber())  // 执行行数
             .append(")");
         level += "   ";
         logChunk(logType, tag, builder.toString());
       }
     }
   ```

   其中一个堆栈帧如下：

   ![image](https://user-images.githubusercontent.com/10796970/57190040-861eba80-6f48-11e9-87ff-55aaebd0c8fa.png)

   打印的方法数由偏移量决定，上述得到的偏移量为8（默认5 + 非 LoggerPrinter 和 非 Logger）， 打印方法数为2， 所以在控制台看到打印的。

   

   ### 打印流程

   至此，分析了初始化的相关源码， 下面来看一下一个具体的 `Logger.d`  是如何工作的。

   具体的实现在 Logger 类里面：

   ```java
     public static void d(@NonNull String message, @Nullable Object... args) {
       printer.d(message, args);
     }
   
     public static void d(@Nullable Object object) {
       printer.d(object);
     }
   
   private static Printer printer = new LoggerPrinter();  // Printer的实现类
   ```

   当 debug 级别输出一个对象时， 

   ```java
   // LoggerPrinter
     @Override public void d(@Nullable Object object) {
       log(DEBUG, null, Utils.toString(object));
     }
   
     @Override 
   public synchronized void log(int priority,
                                            @Nullable String tag,
                                            @Nullable String message,
                                            @Nullable Throwable throwable) {
     	// 获取包含 Throwable 的相关信息
       if (throwable != null && message != null) {
         message += " : " + Utils.getStackTraceString(throwable);
       }
       if (throwable != null && message == null) {
         message = Utils.getStackTraceString(throwable);
       }
       if (Utils.isEmpty(message)) {
         message = "Empty/NULL log message";
       }
   		
     // 按照添加的 LogAdater 进行相应的打印输出（LogCat， Disk）
       for (LogAdapter adapter : logAdapters) {
         if (adapter.isLoggable(priority, tag)) {
           adapter.log(priority, tag, message);
         }
       }
     }
   
   // Utils: debug 下对不同的对象处理成字符串
     public static String toString(Object object) {
       if (object == null) {
         return "null";
       }
       if (!object.getClass().isArray()) {
         return object.toString();
       }
       if (object instanceof boolean[]) {
         return Arrays.toString((boolean[]) object);
       }
       if (object instanceof byte[]) {
         return Arrays.toString((byte[]) object);
       }
       if (object instanceof char[]) {
         return Arrays.toString((char[]) object);
       }
       if (object instanceof short[]) {
         return Arrays.toString((short[]) object);
       }
       if (object instanceof int[]) {
         return Arrays.toString((int[]) object);
       }
       if (object instanceof long[]) {
         return Arrays.toString((long[]) object);
       }
       if (object instanceof float[]) {
         return Arrays.toString((float[]) object);
       }
       if (object instanceof double[]) {
         return Arrays.toString((double[]) object);
       }
       if (object instanceof Object[]) {
         return Arrays.deepToString((Object[]) object);
       }
       return "Couldn't find a correct type for the object";
     }
   ```

   需要注意的一点：Logger 提供 一次打印 tag， `private final ThreadLocal<String> localTag = new ThreadLocal<>();` 是用 ThreadLocal 来保存，隔离线程。

   那么，到现在，Logger.d()  方法输出到控制台的整个流程和源码都完成了分析。

   其余的其他方法， 包括保存在 Disk 的方法这里就不做过多叙述，有疑问可以提 issue 共同探讨。



## 总结

1. Logger 中面向接口编程实践的很好，运用了不同的设计模式。
2. 主流程（如上图）设计简单，易于理解。

