# 各类进程的debug模式

## Spark

#### Driver
```
--driver-java-options -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=50014
```

#### ApplicationMaster

```
spark.yarn.am.extraJavaOptions -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=hzadg-hadoop-dev3.server.163.org:50014
```

IP是期望起ApplicationMaster的节点，端口任意, 想要期望与实际相符，可以暴力的关闭NodeManager为一个节点。

#### Executor

```
spark.executor.extraJavaOptions -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=hzadg-hadoop-dev3.server.163.org:50014
```

IP同上，NodeManager也是一个节点，并同时制定executor的instance个数为1


#### What's More
启动时和加上 `--conf spark.executor.heartbeatInterval=3600s` 防止调试时间过长，executorLost导致app失败

## Hive MetaStore

```
export HADOOP_CLIENT_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=50014"
```

## Scala REPL
```
scala -Dscala.repl.debug=true
```

## Spark Shell(Spark REPL)

```
bin/spark-shell -Dscala.repl.debug=true
```
