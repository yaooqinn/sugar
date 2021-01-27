package com.netease.mammut.spark.test

import org.apache.spark.sql.SparkSession

import org.elasticsearch.spark.sql._

/**
  * bin/spark-submit \
  * --master yarn \
  * --executor-memory 20g \
  * --num-executors 40 \
  * --driver-memory 20g \
  * --executor-cores 4 \
  * --class com.netease.mammut.spark.test.HDFS2ES \
  * --jars ../elasticsearch-hadoop-5.6.3/dist/elasticsearch-spark-20_2.10-5.6.3.jar \
  * /tmp/mammut-spark-elasticsearch-1.0-SNAPSHOT.jar /user/hzyaoqin/data/ hzyaoqin/data1
  */
object HDFS2ES {

  def main(args: Array[String]): Unit = {
    if (args.length != 2){
      println("Usage: CMD <HDFS PATH> <ES PATH>")
    }

    val spark = SparkSession
      .builder()
      .config("es.nodes", "10.120.181.34")
      .config("es.port", 9200)
      .config("es.auto.create.index", true)
      .getOrCreate()

    val src = spark.read.json(args(0))

    EsSparkSQL.saveToEs(src, args(1))
  }

}
