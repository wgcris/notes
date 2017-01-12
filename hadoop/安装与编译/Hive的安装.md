# HIVE的安装

## 1 安装包与资源


## 2 mysql配置
mysql用户存储hive的元数据
### 2.1 安装

```
yum install -y mysql-server mysql mysql-devel
yum install -y mariadb-server mariadb
```

### 2.2 启动mysql服务

```
systemctl start mariadb
```

###2.3 配置mysql

```
mysql -u root -p			# 连接mysql服务
create user 'hive' identified by '123456';   #创建hive用户，密码123456
grant all privileges on *.* to 'hive'@'%' with grant option;  #配置权限

create user 'hive'@'localhost' identified by '123456';
grant all privileges on *.* to 'hive'@'localhost' with grant option;  

create user 'hive'@'BJYF-Docker-189122.hadoop.jd.local' identified by '123456';
grant all privileges on *.* to 'hive'@'BJYF-Docker-189122.hadoop.jd.local' with grant option;  

户端赋权限。这是在指定ip可以通过mysql -u hive -p登录了

```
> 这类有一个问题。如果只配置了'hive'@'%'用户，可以在远程主机上通过mysql -u hive -p通过密码访问mysql服务，但是在本机上不可以。发现是本机使用'hive'@'localhost'访问，所以将'hive'@'localhost'加入权限中。
> 
> 同理启动metastore的时候也会同样错误，只需要将为'hive'@'BJYF-Docker-189122.hadoop.jd.local'授权即可。


### 2.4 mysql其他常见操作：

* 查询所有用户

```
SELECT DISTINCT CONCAT('User: ''',user,'''@''',host,''';') AS query FROM mysql.user;
```

* 显示用户权限

```
show grants for hive@**.**.**.**
```

* 删除用户

````
delete from mysql.user where user='hive'
````

* 执行操作后执行如下命令确认生效

```
flush privileges
```


### 2.5 在mysql上创建hive数据库

```
create database hive
```

## 3 安装HIVE

### 3.1 解压安装包

```
tar zxvf apache-hive-2.1.1-bin.tar.gz
```

### 3.2 配置环境变量
* 配置HIVE_HOME
* 配置HIVE_CONF_DIR为$HIVE_HOME/conf
* 把$HIVE_HOME/bin加入到$PATH中

### 3.3 在hdfs中创建目录

```
hdfs dfs -mkdir /tmp
hdfs dfs -mkdir /user/hive/warehouse
hdfs dfs -chmod g+w /tmp
hdfs dfs -chmod g+w /user/hive/warehouse
```

### 3.4 修改hive配置
拷贝默认配置文件

```
cp hive-default.xml.template hive-site.xml
```
在hive-site.xml增加如下配置

```
  <property>
    <name>hive.metastore.warehouse.dir</name>
    <value>/user/hive/warehouse</value>
    <description>location of default database for the warehouse</description>
  </property>
  <property>
    <name>javax.jdo.option.ConnectionURL</name>
    <value>jdbc:mysql://**.**.**.**:3306/hive_db?createDatabaseIfNotExist=true</value>
    <description>JDBC connect string for a JDBC metastore</description>
  </property>
  <property>
    <name>javax.jdo.option.ConnectionDriverName</name>
    <value>com.mysql.jdbc.Driver</value>
    <description>Driver class name for a JDBC metastore</description>
  </property>
  <property>
    <name>javax.jdo.option.ConnectionUserName</name>
    <value>hive</value>
    <description>username to use against metastore database</description>
  </property>
  <property>
    <name>javax.jdo.option.ConnectionPassword</name>
    <value>123456</value>
    <description>password to use against metastore database</description>
  </property>
  <property>
    <name>hive.metastore.local</name>
    <value>false</value>
  </property>
  <property>
    <name>hive.metastore.uris</name>
    <value>thrift://**.**.**.**:9083</value>
  </property>  
```

### 3.5 安装jdbc驱动

```
yum install mysql-connector-java -y
ln -s /usr/share/java/mysql-connector-java.jar  $HIVE_HOME/lib/mysql-connector-java.jar
```
### 3.6 其他配置

添加或修改如下配置，解决报错。

```
  <!-- 以下用于解决metastore无法启动 -->
  <property>
    <name>hive.metastore.schema.verification</name>
    <value>false</value>
  </property>
  <property>  
    <name>datanucleus.fixedDatastore</name>  
    <value>false</value>   
  </property>  
  <property>  
    <name>datanucleus.autoCreateSchema</name>  
    <value>true</value>  
  </property>      
  <property>  
    <name>datanucleus.autoCreateTables</name>  
    <value>true</value>  
  </property>      
  <property>  
    <name>datanucleus.autoCreateColumns</name>  
    <value>true</value>  
  </property> 
  
```


## 4 启动服务
### 4.1 启动metastore服务

```
hive --service metastore
```
### 4.2 启动hive
执行如下命令，修改临时目录，防止启动报错。修改用户名字，防止启动hive后，无法查询。

```
sed -i 's:\${system\:java\.io\.tmpdir}:\/home\/zcy\/software\/servers\/apache-hive-2.1.1-bin\/tmp:g' ./conf/hive-site.xml
sed -i 's:\${system\:user\.name}:hive:g' ./conf/hive-site.xml
```

启动hive shell,

```
hive
```

hive shell中创建hive_db

```
create database hive_db;
```
## 5 HIVE SHELL的简单操作

* 列出数据库,表

```
show databases;
show tables;
```

* 创建表

```
create table t1(id int, name string) row format delimited fields terminated by '\t'
```

* 从本地导入表

```
load data local inpath '/home/zcy/sql.t1.data' into table t1;
```
其中sql.t1.data格式如下，字段以\t分割，一行一条记录

```
10	name1
20	name2
```