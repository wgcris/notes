# HIVE数据操作

## 1 表结构

```
create table employees(
  name string ,
  salary float ,
  subordinates array<string> ,
  dedutions map<string,float> ,
  address struct<street:string,city:string,state:string,zip:int>
)
partitioned by (country string, state string)
row format delimited fields terminated by '\t'
collection items terminated by '\002'
map keys terminated by '\003'
lines terminated by '\n'

;
```