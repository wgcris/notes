# HIVE查询
## 1 简单查询

* 分别查询array，map，struct的子字段。查询不到的会返回NULL

```
select name,subordinates[1] from employees;
select name,dedutions["dk1"] from employees;
select name,address.street from employees;
```

* 使用正则表达式查询
```
select name,sub*[0] from employees;
```

* 算术运算查询

```
select upper(name),round(salary+dedutions["dk1"]) from employees;
```

* 聚合运算查询。得到employees表中的记录条数和平均工资。加distinct比消除重复项。

```
select count(*),avg(salary) from employees;
select count(distinct salary) from employees;
```

* 使用limit限制

```
select * from employees limit 2;
```

* 多层select,使用as来设置别名

```
from (select name,salary as sal from employees where salary>200000) e select e.name,e.sal where e.sal<500000;
```

## 2 where的使用

* 在where语句中，不能写入别名。如下是一个错误的例子。

```
select name,salary*dedutions["dk1"] as res from employee where res<300000;
```

* 若想达到上面的效果，可以嵌套使用where

```
select e.* from (select name,salary,salary*dedutions["dk1"] as res from employees) as e where e.res<300000;
```

* 显示指定精度。可以防止不同精度比较的误差。下面300000强制转化为float类型。

```
select e.* from (select name,salary,salary*dedutions["dk1"] as res from employees) as e where e.res< cast(300000 as float);
```

## 3 group by 

* group by通常与聚合函数一起使用。譬如如下sql语句计算了中国不同省份的平均工资。

```
select state,avg(salary) from employees where country='CN' group by state;
```

* 可以使用having过滤group by的结果。下面过滤平均工资大于280000的省份。

```
select state,avg(salary) from employees where country='CN' group by state having avg(salary)>280000;
```

## 4 连接

* inner join，内连接。
