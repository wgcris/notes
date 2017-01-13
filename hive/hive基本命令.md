# HIVE基本命令

##1. 数据库操作命令

* 显示所有以"test"开头的数据库

```
show databases like 'test*';
```

* 创建test_db数据库

```
create database if not exists test_db;
```

* 指定位置创建数据库

```
create database test_db1 location "/tmp/test_db1.db"
```

* 创建数据库的时候增加注释信息

```
create database test_db2 comment 'This is my test_db2'
```

* 查看数据库的具体信息

```
desc database test_db1;
```

* 创建数据库增加额外属性，并查询额外属性

```
create database teset_db3 with dbproperties('creator'='zcy');
desc database extended test_db3;
```

* 删除数据库,只能删除没有表的数据库。强制删除加入cascade

```
drop database if exists test_db3;
drop database if exists test_db1 cascade;
```