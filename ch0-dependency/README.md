# SpringBoot2.x入门：快速创建一个SpringBoot应用

## 前提

这篇文章是《SpringBoot2.x入门》专辑的**第2篇**文章，使用的`SpringBoot`版本为`2.3.1.RELEASE`，`JDK`版本为`1.8`。

常规的套路会建议使用`Spring`官方提供的工具[Spring Initializr](https://start.spring.io)通过指定配置创建一个`SpringBoot`项目，但是考虑到**Spring Initializr必须联网使用**，对于项目配置和依赖的控制粒度不够精细，本文会从更一般的情况考虑，详细分析怎么通过`Maven`和`IntelliJ IDEA`（下称`IDEA`）快速创建一个`SpringBoot`应用，包括单模块的`Maven`和多模块的`Maven`应用创建。

<!-- more -->

## 依赖分析

必要的插件：

- `Maven`编译插件：`maven-compiler-plugin`。
- `SpringBoot`封装的`Maven`插件（一般必选，项目最终打包依赖到这个插件，**它的版本建议跟随选用的SpringBoot的版本**）：`spring-boot-maven-plugin`。

`Maven`编译插件尽可能选用高版本，以适配更高版本的`JDK`。一般会选用的基本依赖如下：

- `lombok`（可选，个人认为能提高开发效率，不过需要安装对应的插件）。
- `junit`（`spring-boot-starter-test`）：单元测试。
- `spring-boot-starter`：`Bean`管理、配置读取等，简单理解就是`IOC`容器核心组件和一些扩展。
- `spring-boot-starter-web`：基于`spring-boot-starter`扩展，主要集成了`SpringMVC`的功能。

> 多数情况下，选用spring-boot-starter-web即可，版本选取REALEASE版本即可，注意尽可能整套项目使用同一个大版本的SpringBoot。

下面例子用到的各个组件的版本如下：

|序号|组件|版本号|描述|
|:-:|:-:|:-:|:-:|
|1|`maven-compiler-plugin`|`3.8.1`|`Maven`编译插件|
|2|`spring-boot-starter`|`2.3.1.RELEASE`|`IOC`容器核心组件|
|3|`spring-boot-maven-plugin`|`2.3.1.RELEASE`|`SpringBoot`封装的`Maven`插件|
|4|`lombok`|`1.18.12`|-|

## 创建一个单模块的SpringBoot应用

点击`IDEA`主菜单`File -> Project`进入创建新项目的界面：

![](https://throwable-blog-1256189093.cos.ap-guangzhou.myqcloud.com/202006/s-b-g-ch1-1.png)

选择左侧的`Maven`选项，上方下拉选择好`JDK`版本，勾选`Create from archetype`，然后选中`maven-archetype-webapp`这个骨架的`RELEASE`版本，然后点击**下一步**按钮：

![](https://throwable-blog-1256189093.cos.ap-guangzhou.myqcloud.com/202006/s-b-g-ch1-2.png)

输入项目的`GAV`，选定项目的磁盘目录，然后点击**下一步**按钮：

![](https://throwable-blog-1256189093.cos.ap-guangzhou.myqcloud.com/202006/s-b-g-ch1-3.png)

选定`Maven`的安装路径、配置文件和本地仓库，配置好相应的属性，最后点击完成即可：

![](https://throwable-blog-1256189093.cos.ap-guangzhou.myqcloud.com/202006/s-b-g-ch1-4.png)

创建出来的是一个标准的`Maven`项目，它的结构如下：

```java
spring-boot-guide
   - src
     - main
       - webapp
         - web.xml
   - pom.xml
```

一般可以直接删除`src/main/webapp`目录，在`pom.xml`中增加对应的依赖，最终的`pom.xml`如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>club.throwable</groupId>
    <artifactId>spring-boot-guide</artifactId>
    <version>1.0-SNAPSHOT</version>
    <!-- 指定打包方式为Jar -->
    <packaging>jar</packaging>
    <name>spring-boot-guide</name>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <maven.compiler.plugin.version>3.8.1</maven.compiler.plugin.version>
        <lombok.version>1.18.12</lombok.version>
        <spring.boot.version>2.3.1.RELEASE</spring.boot.version>
    </properties>
    <!-- BOM全局管理starter版本 -->
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
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <build>
        <!-- 指定最终打出来的Jar包的名称 -->
        <finalName>spring-boot-guide</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${maven.compiler.plugin.version}</version>
                <configuration>
                    <source>${maven.compiler.source}</source>
                    <target>${maven.compiler.target</target>
                    <encoding>${project.build.sourceEncoding}</encoding>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring.boot.version}</version>
<!--                <configuration>-->
<!--                    <mainClass>可选配置，这里填写启动类的全类名</mainClass>-->
<!--                </configuration>-->
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

有依赖版本变动，只需要直接修改`properties`元素中对应的版本号即可。在`src/main`下新建启动类`java/club/throwable/App.java`：

![](https://throwable-blog-1256189093.cos.ap-guangzhou.myqcloud.com/202006/s-b-g-ch1-5.png)

```java
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Hello SpringBoot!");
    }
}
```

启动`App`中的`main`函数后输出如下：

![](https://throwable-blog-1256189093.cos.ap-guangzhou.myqcloud.com/202006/s-b-g-ch1-6.png)

`spring-boot-starter`模块引入的只是核心容器组件，并没有集成像`Tomcat`这样的`Servlet`容器，启动后不会挂起主线程，所以执行完`CommandLineRunner`中的逻辑就会自行退出主线程。

## 创建一个多模块的SpringBoot应用

多模块应用的创建基于单模块应用，准确来说就是在一个创建完的单模块应用中添加新的模块（`New Module`）。在原来的根项目`spring-boot-guide`右键弹出菜单中选择新建模块：

![](https://throwable-blog-1256189093.cos.ap-guangzhou.myqcloud.com/202006/s-b-g-ch1-7.png)

后续的步骤与上一小节的过程完全相同，不过定义的模块名称必须和根项目的名称不相同，这里定义为`ch0-dependency`，然后调整父`pom.xml`和子`pom.xml`：

- `spring-boot-guide -> pom.xml`

```xml 
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>club.throwable</groupId>
    <artifactId>spring-boot-guide</artifactId>
    <version>1.0-SNAPSHOT</version>
    <modules>
        <module>ch0-dependency</module>
    </modules>
    <packaging>pom</packaging>
    <name>spring-boot-guide</name>
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <maven.compiler.plugin.version>3.8.1</maven.compiler.plugin.version>
        <lombok.version>1.18.12</lombok.version>
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
    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <build>
        <finalName>spring-boot-guide</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${maven.compiler.plugin.version}</version>
                <configuration>
                    <source>${maven.compiler.source}</source>
                    <target>${maven.compiler.target</target>
                    <encoding>${project.build.sourceEncoding}</encoding>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

- `spring-boot-guide/ch0-dependency -> pom.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>club.throwable</groupId>
        <artifactId>spring-boot-guide</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <artifactId>ch0-dependency</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>
    <name>ch0-dependency</name>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
    </dependencies>
    <build>
        <finalName>ch0-dependency</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring.boot.version}</version>
<!--                <configuration>-->
<!--                    <mainClass>club.throwable.App</mainClass>-->
<!--                </configuration>-->                           
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

注意：

- `spring-boot-maven-plugin`一般情况下只需配置在需要打包的模块中，一般父模块是全局管理的模块，不需要全局定义此插件。
- `maven-compiler-plugin`可以配置在父模块中，让所有子模块都应用此插件。
- `spring-boot-starter-test`和`lombok`可以在父模块的`dependencies`元素中添加，相当于所有子模块都引入了这两个依赖。

父模块中的`spring-boot-guide`的`src`模块需要丢弃，可以直接剪切到`ch0-dependency`子模块中，如下：

![](https://throwable-blog-1256189093.cos.ap-guangzhou.myqcloud.com/202006/s-b-g-ch1-8.png)

后面再添加其他新的模块，直接重复上述的步骤即可。

## 代码仓库

这里给出本文搭建的一个多模块的`SpringBoot`应用的仓库地址（持续更新）：

- Github：https://github.com/zjcscut/spring-boot-guide

（本文完 c-2-d e-a-20200701 8:39 AM）

技术公众号（《Throwable文摘》，id：throwable-doge），不定期推送笔者原创技术文章（绝不抄袭或者转载）：

![](https://public-1256189093.cos.ap-guangzhou.myqcloud.com/static/wechat-account-logo.png)

