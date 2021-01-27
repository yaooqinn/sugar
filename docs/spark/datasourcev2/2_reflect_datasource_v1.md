# DataSource V1问题回顾

## 什么是DataSource API？

DataSource API是Spark处理结构化数据（Structured Data）的标准化基础API框架。

用户基于该API可以方便的将自己的数据格式描述成Spark SQL所能识别的逻辑实现。可以帮助用户以插件式的方式，从而依靠SparkSQL处理对应的DataSource实现。

## 谁会用DataSource API？

- spark第三方库开发人员：大部分都可以在[SparkPackages](https://spark-packages.org/?q=tags%3A%22Data%20Sources%22)上找到，不必再造轮子, 比如
    + [spark-mongodb](https://spark-packages.org/package/Stratio/spark-mongodb)
    + [spark-avro](https://spark-packages.org/package/databricks/spark-avro)
- 处理自定义数据源的人员，比如
    + [利用 Spark DataSource API 实现Rest数据源](https://www.jianshu.com/p/6441eaa4d064)
- spark内置的sources：比如parquet/orc/json等，但事实并非如此，这些内置的source，并没有完全的继承DataSource API的接口


sql("create table src7 using parquet as select * from default.src_parquet_1").show
