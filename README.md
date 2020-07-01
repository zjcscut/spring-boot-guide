# SpringBoot2.x入门：依赖管理

## 前提

这篇文章是《SpringBoot2.x入门》专辑的**第1篇**文章，使用的`SpringBoot`版本为`2.3.1.RELEASE`，`JDK`版本为`1.8`。

主要梳理一下`SpringBoot2.x`的依赖关系和依赖的版本管理，依赖版本管理是开发和管理一个`SpringBoot`项目的前提。

`SpringBoot`其实是通过`starter`的形式，对`spring-framework`进行装箱，消除了（但是兼容和保留）原来的`XML`配置，目的是更加便捷地集成其他框架，打造一个完整高效的开发生态。

<!-- more -->

## SpringBoot依赖关系

因为个人不太喜欢`Gradle`，所以下文都以`Maven`举例。

和`SpringCloud`的版本（`SpringCloud`的正式版是用**伦敦地铁站或者说伦敦某地名的英文名称**作为版本号，例如比较常用的`F`版本`Finchley`就是位于伦敦北部芬奇利）管理不同，`SpringBoot`的依赖组件发布版本格式是：`X.Y.Z.RELEASE`。因为`SpringBoot`组件一般会装箱为`starter`，所以组件的依赖`GAV`一般为：`org.springframework.boot:spring-boot-starter-${组件名}:X.Y.Z.RELEASE`，其中`X`是主版本，不同的主版本意味着可以放弃兼容性，也就是`SpringBoot1.x`和`SpringBoot2.x`并**不保证兼容性**，而**组件名**一般是代表一类中间件或者一类功能，如`data-redis`（`spring-boot-starter-data-redis`，提供`Redis`访问功能）、`jdbc`（`spring-boot-starter-jdbc`，提供基于`JDBC`驱动访问数据库功能）等等。以`SpringBoot`当前最新的发布版本`2.3.1.RELEASE`的`org.springframework.boot:spring-boot-starter:jar:2.3.1.RELEASE`为例，用`mvn dependency:tree`分析它的依赖关系如下：

![](https://throwable-blog-1256189093.cos.ap-guangzhou.myqcloud.com/202006/s-b-g-ch0-1.png)

这个依赖树也印证了`starter`是基于`Spring`项目装箱和扩展的。

## SpringBoot依赖管理

如果使用[Spring Initializr](https://start.spring.io/)创建一个`SpringBoot`项目的话，那么会发现项目的`POM`文件中会加入了一个`parent`元素：

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.3.1.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```

其实`spring-boot-starter-parent`相当于作为了当前项目的父模块，在父模块里面管理了当前指定的`SpringBoot`版本`2.3.1.RELEASE`所有依赖的第三方库的统一版本管理，通过`spring-boot-starter-parent`上溯到最顶层的项目，会找到一个`properties`元素，里面统一管理`Spring`框架和所有依赖到的第三方组件的统一版本号，这样就能确保对于一个确定的`SpringBoot`版本，它引入的其他`starter`不再需要指定版本，同时所有的第三方依赖的版本也是固定的。如项目的`POM`文件如下：

```xml
<!-- 暂时省略其他的配置属性 -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.3.1.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
<groupId>com.example</groupId>
<artifactId>demo</artifactId>
<version>0.0.1-SNAPSHOT</version>
<name>demo</name>
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
        <exclusions>
            <exclusion>
                <groupId>org.junit.vintage</groupId>
                <artifactId>junit-vintage-engine</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
</dependencies>
```

这样只需要修改`parent`元素中的版本号，就能全局更变所有`starter`的版本号。这种做法其实本质上是把当前项目作为`spring-boot-starter-parent`的子项目，其实在一定程度上并不灵活。这里推荐使用另一种方式：通过`dependencyManagement`元素全局管理`SpringBoot`版本，适用于单模块或者多模块的`Maven`项目。项目的（父）`POM`文件如下：

```xml
<!-- spring-boot-guide 父POM -->
<properties>
    <spring.boot.version>2.3.1.RELEASE</spring.boot.version>
</properties>
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>${spring.boot.version}</version>
            <scope>import</scope>
            <type>pom</type>
        </dependency>
    </dependencies>
</dependencyManagement>
```

然后需要用到其他`starter`的时候，只需要在`dependencies`直接引入即可，不再需要指定版本号，版本号由`dependencyManagement`中定义的版本号统一管理。

```xml
<!-- spring-boot-guide/ch0-dependency 子POM -->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
</dependencies>
```

## SpringBoot依赖覆盖

有些特殊的情况，可能项目中大部分的`starter`使用的是相对低的版本，但是由于部分新的功能需要使用到更高版本的个别`starter`，则需要强制引入该高版本的`starter`。这里举一个例子，项目用到的`SpringBoot`组件的版本是`2.1.5.RELEASE`，使用的中间件服务`Elasticsearch`的版本是`7.x`，而`spring-boot-starter-data-elasticsearch`支持的版本如下：

![](https://throwable-blog-1256189093.cos.ap-guangzhou.myqcloud.com/202006/s-b-g-ch0-2.png)

理论上可以一下子升级`SpringBoot`到`2.3.1.RELEASE`，其实也可以直接指定`spring-boot-starter-data-elasticsearch`的版本覆盖掉全局的`SpringBoot`组件版本，这里应用了`Maven`的**依赖调解原则**：

```xml
<!-- 父POM或者全局POM -->
<properties>
    <spring.boot.version>2.1.5.RELEASE</spring.boot.version>
</properties>
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>${spring.boot.version}</version>
            <scope>import</scope>
            <type>pom</type>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
        <version>2.3.1.RELEASE</version>
    </dependency>
</dependencies>
```

这样就能单独提升`spring-boot-starter-data-elasticsearch`的版本为`2.3.1.RELEASE`，其他组件的版本依然保持为`2.1.5.RELEASE`。

## 小结

目前有两种常用的方式管理`SpringBoot`组件的版本（两种方式二选一）：

1. 配置`parent`元素，通过项目继承的方式指定`SpringBoot`组件的版本号，这是`Spring Initializr`生成的项目中默认的配置方式。
2. 配置`dependencyManagement`元素（**推荐此方式**），通过（父）`POM`文件统一指定`SpringBoot`组件的版本号。

另外，`SpringBoot`的`1.x`和`2.x`之间有兼容性问题（最明显的一点是`2.x`中删除了`1.x`中大量的内建类，如果用到了这些`SpringBoot`中的内建类，容易出现`ClassNotFoundException`），降级或者升级都有比较大的风险。一般情况下，建议使用同一个大版本进行项目开发，如果确定需要进行大版本切换，请务必做完毕的功能测试。

（本文完 c-1-d e-a-20200628）

