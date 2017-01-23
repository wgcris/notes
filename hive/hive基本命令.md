# HIVE基本命令

## 1 数据库操作命令

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

* 创建数据库增加额外属性，并查询额外属性。修改数据库额外属性

```
create database teset_db3 with dbproperties('creator'='zcy');
desc database extended test_db3;
alter database test_db set dbproperties("author"="zcy");
```

* 删除数据库,只能删除没有表的数据库。强制删除加入cascade

```
drop database if exists test_db3;
drop database if exists test_db1 cascade;
```

## 2 表的操作

* 创建表，设置位置，属性等信息

```
create table if not exists test_db.employees(
  name string comment "employee name",
  salary float comment "employee salary",
  subordinates array<string> comment 'names of subordinates',
  dedutions map<string,float> comment 'dedutions',
  address struct<street:string,city:string,state:string,zip:int> comment "address") 
comment 'dicriptions of talbe' 
location '/user/hive/warehouse/test_db.db/employee' 
tblproperties ('creator'='me','created_at'='20170123');
```
* 复制一张表的结构，不复制数据

```
create table if not exists employees2 like employees;
```
* 查询表，查询其他数据的表,查询匹配模式的表

```
show tables;
show tables in hive_db;
show tables 'employees*';
```
* 查询表的详细信息。使用formatted会显示更详细的信息，因此推荐使用。

```
desc extended test_db.employees;
desc formatted test_db.employees;
```
* 创建外部表

```
create external table ext_tb(name string);
```

* 创建分区表。这里country和state也是字段，不得在定义普通字段处重复定义。

```
create table employees3(
  name string ,
  salary float ,
  subordinates array<string> ,
  dedutions map<string,float> ,
  address struct<street:string,city:string,state:string,zip:int>
)
partitioned by (country string, state string);

```

* 查看分区。查看子分区。

```
show partitions employees3;
show partitions employees3 partition(country='US');
```

* 删除表

```
drop table if exists employees;
```

* 修改表的名称

```
alter table employees2 rename to employees;
```

* 增加改表的分区，修改表的分区，删除分区

```
alter table employees3 add if not exists partition (country='US',state='washington') location "/user/hive/warehouse/employees3/country=US/state=washington";
alter table employees3 partition(country='US',state='washington') set location "/user/hive/warehouse/test_db/employees3/country=US/state=washington";
alter table employees3 drop if exists partition (country='US',state='washington');
```

* 修改列信息,并查询表结构

```
alter table employees3 change column salary wages float comment "change wages" 
desc employees3
```
* 增加一列

```
alter table employees add columns(id int comment "this is id")
```

* 修改表属性

```
alter table employees set tblproperties ('notes'="This is the noete!");
```



