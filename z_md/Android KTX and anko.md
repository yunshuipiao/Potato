# When Android meets KTX and anko 

[TOC]

# Potato(2)：When Android meets KTX and anko

**TAG**: kotlin, android ktx, anko

在 google 官方宣布 Android 可以使用 kotlin 进行开发，并且 Kotlin 在 [Android Studio](https://developer.android.com/studio/?hl=zh-cn) 3.0 及更高版本中都支持后，目前新项目完全可以使用 kotlin 进行开发； 目前已经存在的项目，也可以使用 kotlin 编写测试代码， 逐渐到写一些独立的模块，知道项目全部采用 kotlin 去编写。

对于我来说， 使用 kotlin 极大的提升了开发效率， 主要优点不限于：

1. 空安全：变量分为空和非空，极大避免了空指针异常
2. lambda 和函数式代码的编写，节省 30% 的代码量，越少越不容易出错
3. 扩展函数：在不知道类细节的情况下扩展使用类的能力（以静态方法的形式）
4. …...



## kotlin 的基本使用

本文章对 kotlin 的语法不做过多介绍， 基本语法半个小时即可理解，最好的是边使用边学习。

[使用 Kotlin 开发 Android 应用](使用 Kotlin 开发 Android 应用)

[Kotlin 学习资源](<https://developer.android.com/kotlin/resources?hl=zh-cn>)



## Android KTX 介绍

在上述的链接中，有一个 Android KTX 的学习链接，是 kotlin 相关的扩展程序，同时属于 Android Jetpack（后续介绍）， 官方地址也给出了 一些示例，[Android KTX](<https://developer.android.com/kotlin/ktx?hl=zh-cn>)。

下面去代码中看一下具体有些什么内容：

相关依赖如下：`implementation "androidx.core:core-ktx:$prop_androidx_core_ktx_version"`

<center class="half">
    <img alt="project_structure" src="https://user-images.githubusercontent.com/10796970/56851858-7569b580-6946-11e9-95cf-794fde934dca.png">
    <img  alt="androidx_ktx" src="https://user-images.githubusercontent.com/10796970/56851861-77cc0f80-6946-11e9-8c42-c03cd3af85cd.png">
</center>

展开左图中依赖的 Libraries， 可以看到该库里面的内容，涉及动画，数据库，SP， 网络，操作系统等， 以常用扩展方法为主，极大的方便开发，建议每个文件看一下， 记得有这么一个方法， 省去重复的去定义。

----



**提一句：三方库在不同模式下对apk的大小是不同的，release版开启混淆后会移除不用的方法，权衡是否使用开源三方库**

----

下面找几个 kt.class 文件看下具体内容是什么。

`ViewKt.class --> View.kt`,  简单的几行代码如下，都是扩展属性或者扩展函数。

```kotlin
public var android.view.View.isGone: kotlin.Boolean /* compiled code */

public var android.view.View.isInvisible: kotlin.Boolean /* compiled code */

public var android.view.View.isVisible: kotlin.Boolean /* compiled code */

public val android.view.View.marginBottom: kotlin.Int /* compiled code */

public inline fun android.view.View.setPadding(@androidx.annotation.Px size: kotlin.Int): kotlin.Unit { /* compiled code */ }
```

点击右上角的 `Decompile to Java`, 即可看到此 class 文件， 也即 kotlin 对应的 java 如下：

比如 `View.isGone` 对应的静态方法如下， `View` 作为静态方法的第一个参数，如下：

```kotlin
   public static final boolean isGone(@NotNull View $receiver) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      return $receiver.getVisibility() == 8;
   }
```

```java
     */
    public static final int VISIBLE = 0x00000000;

    /**
     * This view is invisible, but it still takes up space for layout purposes.
     * Use with {@link #setVisibility} and <a href="#attr_android:visibility">{@code
     * android:visibility}.
     */
    public static final int INVISIBLE = 0x00000004;

    /**
     * This view is invisible, and it doesn't take any space for layout
     * purposes. Use with {@link #setVisibility} and <a href="#attr_android:visibility">{@code
     * android:visibility}.
     */
    public static final int GONE = 0x00000008;
```

看源码，首先对参数进行判空，然后判断相应的状态。

在看一个扩展方法， 也是一样的处理方法，后面需要多去了解的是：

**kotlin 的新特性在 java 中怎么实现**

```kotlin
		public static final void setPadding(@NotNull View $receiver, @Px int size) {
      Intrinsics.checkParameterIsNotNull($receiver, "$receiver");
      $receiver.setPadding(size, size, size, size);
   }
```



## Kotlin Anko 介绍

除了上面的 Android_KTX 之外，anko 是另一个旨在让安卓开发变得更快更好的库，其主要分为四部分，包括 Commons， Layouts，SQLite， Coroutines 四个部分，下面进行简单了解。

添加依赖之后， 查看下有哪些包；数量很多，可以根据官网依赖所用到的最小的库；

![image](https://user-images.githubusercontent.com/10796970/56862392-f4a9c880-69dc-11e9-9425-7b7b774ff472.png)


下面也是看几个相关的类来了解一下：

`SupportDialog.kt` 文件，包含 toast  操作的相关知识：

```kotlin
inline fun Fragment.toast(textResource: Int) = requireActivity().toast(textResource)

inline fun Fragment.toast(text: CharSequence) = requireActivity().toast(text)
```

扩展函数，代码很简单，看一下相关的 java 代码：

```java
   @NotNull
   public static final Toast toast(@NotNull Fragment $receiver, int textResource) {
      Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
      FragmentActivity var10000 = $receiver.requireActivity();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "requireActivity()");
      Context $receiver$iv = (Context)var10000;
      Toast var4 = Toast.makeText($receiver$iv, textResource, 0);
      var4.show();
      Intrinsics.checkExpressionValueIsNotNull(var4, "Toast\n        .makeText(…         show()\n        }");
      return var4;
   }
```

不难理解，代码中除了正常的逻辑之外，还有大量的判空处理的代码， 因此无论是写 Java 还是 kotlin代码， 相对应的异常（null）等都要进行处理。

其他函数都可以使用相同的方法进行查看；

在上面的扩展函数中，有一个关键字 inline， 这里做点说明：

官网解释：

> 使用[高阶函数](https://www.kotlincn.net/docs/reference/lambdas.html)会带来一些运行时的效率损失：每一个函数都会创建一个对象，并且会捕获一个闭包，即那些在函数体内会访问到的变量。 内存分配（对于函数对象和类）和虚拟调用会引入运行时间和内存开销。

> `inline` 修饰符影响函数本身和传给它的 lambda 表达式：所有这些都将内联(插到)到调用处。

即内联函数发生调用时，不会创建额外的函数对象，而是将执行代码直接复制到相应的执行处，节省时间和空间开销。



## 参考链接

1. 什么时候使用内联函数和优点：[https://stackoverflow.com/questions/44471284/when-to-use-an-inline-function-in-kotlin](https://stackoverflow.com/questions/44471284/when-to-use-an-inline-function-in-kotlin)



## 总结：

这篇文章介绍了与Android 与 kotlin 开发的情况，以及两个扩展库（扩展函数）带来的开发优势，建议对两个库，大致去看一下源码，了解有什么方法可能会用到即可。

1. kotlin 开发的好处
2. android_ktx 和 anko 的基本使用
3. inline 的介绍
