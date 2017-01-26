# Spark的编译

# 1 环境

* 操作系统

```
CentOS Linux release 7.1.1503 (Core) 
```

* 版本信息

```
spark-2.1.0
Scala-2.11.8
hadoop-2.7.1
```

# 2 编译步骤

* 安装scala。并配置SCALA_HOME，将$SCALA_HOME/bin配置到PATH中。
* 加入如下环境变量。java7需要配置XX:MaxPermSize=512M

```
export MAVEN_OPTS="-Xmx2g -XX:ReservedCodeCacheSize=512m -XX:MaxPermSize=512M"
```
* 清除SCALA_HOME环境变量
* 增加java环境变量，不要使用openjdk
* 输入如下命令，编译安装。

```
mvn -Pyarn -Phadoop-2.7 -Dhadoop.version=2.7.1 -Phive -Phive-thriftserver -Pnative -DskipTests clean package
```

> 如果出现如下类型错误，请更换maven中央仓库地址后重试。
> ```
> Failed to execute goal net.alchim31.maven:scala-maven-plugin:3.2.2:compile (scala-compile-first) on project spark-tags_2.11: wrap: org.apache.maven.artifact.resolver.ArtifactResolutionException: Could not transfer artifact org.scala-lang:scala-compiler:jar:2.11.8 from/to central (https://repo1.maven.org/maven2): GET request of: org/scala-lang/scala-compiler/2.11.8/scala-compiler-2.11.8.jar from central failed
> ```
