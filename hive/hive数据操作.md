# HIVE数据操作

## 1 表结构

* 建立employees表
* 以country和state做为分区。
* 每个字段的分隔符为空格
* 每个字段内item的分割为"|"。包括array，map，struct类型各个item的分割。
* 字段内map类型的key与value的分割符号为":"。
* 一行为一条记录

```
create table employees(
  name string ,
  salary float ,
  subordinates array<string> ,
  dedutions map<string,float> ,
  address struct<street:string,city:string,state:string,zip:int>
)
partitioned by (country string, state string)
row format delimited fields terminated by ' '
collection items terminated by '|'
map keys terminated by ':'
lines terminated by '\n'
stored as textfile
;
```
* 下面是一条记录的文本格式

```
name1 300000.0 s1|s2 dk1:1.0|dk2:2.0 street1|city1|state1|100000 CN beijing
```

## 2 向表中装载数据
* 使用如下命令装载表。会在hdfs上增加/user/hive/warehouse/hive_db.db/employees/country=CN/state=beijing/test1.data文件。

```
load data local inpath '/home/zcy/test1.data' overwrite into table employees partition(country='CN', state='beijing')
```

> 如果增加overwrite之后，会覆盖之前的数据。
> 
> 如果没有增加overwrite的话，但hdfs上传的文件有重名，新的文件会重新命名为，这里test1.data会重命名为test1_copy_1.data

## 3 通过查询语句插入数据
* 先复制一个表

```
create table if not exists employees2 like employees
```
* 查询语句插入表。

```
insert overwrite table employees2 partition(country='CN', state='beijing') select name,salary,subordinates,dedutions,address from employees where country='CN'and state='beijing'
```
> 由于存在分区，这里使用select * 会报错显示查询结果是7列，实际需要5列。所以，这里按照列进行输入
> 
> 本地模式有文件数量限制。可以通过设置set hive.exec.mode.local.auto.input.files.max=10或更大来保证程序通过
>
> 插入文件为/user/hive/warehouse/hive_db.db/employees2/country=CN/state=beijing/000000_0，而不是之前的文件名。

## 4 创建表并插入数据

```
create table employees3 as select * from employees;
```

## 5 从表中导出数据

```
insert overwrite local directory '/tmp/employees.data' select * from employees;
```