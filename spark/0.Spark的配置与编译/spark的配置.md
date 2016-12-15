# 1. Spark的安装
*  解压spark-2.0.2-bin-hadoop2.7.tgz, 并设置SPARK_HOME
*  拷贝hadoop的core-site.xml和hdfs-site.xml到spark的conf目录下
*  拷贝配置文件

```
	mv spark-env.sh.template spark-env.sh
	mv spark-defaults.conf.template spark-defaults.conf
	mv metrics.properties.template metrics.properties
	mv log4j.properties.template log4j.properties
```
* 配置spark-env.sh, 加入如下环境变量

```
	export HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop
	export SPARK_LOCAL_DIRS=~/data/spark
	export SPARK_MASTER_IP=localhost
	export SPARK_MASTER_PORT=8070
	export SPARK_MASTER_WEBUI_PORT=8090
	export SPARK_WORKER_CORES=1
	export SPARK_WORKER_MEMORY=1g
	export SPARK_WORKER_PORT=8092
	export SPARK_WORKER_INSTANCES=1
	export SPARK_LOG_DIR=$SPARK_HOME/logs/spark
	export SPARK_PID_DIR=$SPARK_HOME/tmp/spark
```
* 配置slaves文件,写入一行localhost 
* spark集群模式的启动与验证

```
  (1)sbin/start-all.sh 启动spark集群
  (2)http://localhost:8090/ 访问spark的web界面
  (3)输入spark-shell可以运行spark-shell环境
```
* yarn集群模式的启动

```
	在spark-default.conf里面配置spark.mastr为yarn，然后使用spark-submit提交任务到yarn
```
* spark jh的启动

```
在spark-default.conf配置spark.history.ui.port为18080，spark.eventLog.dir和spark.history.fs.logDirectory配置为/userlogs/spark
执行./start-history-server.sh启动程序
```


# 2. SparkPi实例
* spark集群模式

```
./bin/spark-submit --class org.apache.spark.examples.SparkPi --master spark://localhost:7077 --executor-memory 1G --total-executor-cores 1 examples/jars/spark-examples_2.11-2.0.2.jar 1000
```

* yarn集群模式
如果提交到yarn，不需要开启spark的master和worker，但是需要启动hdfs和yarn的进程。提交命令如下:

```
./bin/spark-submit --class org.apache.spark.examples.SparkPi --master yarn-cluster --executor-memory 1G --total-executor-cores 1 examples/jars/spark-examples_2.11-2.0.2.jar 1000
```

如果在spark-default.conf里面配置spark.mastr为yarn，则在提交的时候不用指定--master

```
./bin/spark-submit --class org.apache.spark.examples.SparkPi --executor-memory 1G --total-executor-cores 1 examples/jars/spark-examples_2.11-2.0.2.jar 1000
```

# 附录 spark yarn模式的配置
* spark-evn.sh的配置

```
export HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop
export SPARK_LOCAL_DIRS=~/data/spark
export SPARK_MASTER_IP=localhost
export SPARK_MASTER_PORT=8070
export SPARK_MASTER_WEBUI_PORT=8090
export SPARK_WORKER_CORES=1
export SPARK_WORKER_MEMORY=1g
export SPARK_WORKER_PORT=8092
export SPARK_WORKER_INSTANCES=1
export SPARK_LOG_DIR=$SPARK_HOME/logs/spark
export SPARK_PID_DIR=$SPARK_HOME/tmp/spark
```

* spark-default.conf的配置

```
spark.master yarn
spark.history.ui.port 18080
spark.eventLog.dir hdfs://172.16.189.122:9000/userlogs/spark
spark.history.fs.logDirectory hdfs://172.16.189.122:9000/userlogs/spark
```
