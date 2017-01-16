# Spark的编译

# 1 环境

* 操作系统

```
CentOS Linux release 7.1.1503 (Core) 
```

* 版本信息

```
spark-2.1.0.tgz
Scala version 2.10.5
hadoop-2.7.1
```

# 2 编译步骤

* 安装scala。并配置SCALA_HOME，将$SCALA_HOME/bin配置到PATH中。
* 输入如下命令，编译安装。

```
mvn -Pyarn -Dhadoop.version=2.7.1 -Dyarn.version=2.7.1 -DskipTests clean package
```