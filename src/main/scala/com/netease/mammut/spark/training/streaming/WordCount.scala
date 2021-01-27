package com.netease.mammut.spark.training.streaming

import org.apache.spark.sql.SparkSession

/**
  * bin/spark-submit --class com.netease.mammut.spark.training.streaming.WordCount /Users/Kent/Documents/mammutsparktraining/target/mammut-spark-training-1.0-SNAPSHOT.jar
  */
object WordCount extends App {
  val spark = SparkSession
    .builder()
    .appName("StructuredNetworkWordCount")
    .getOrCreate()
  import spark.implicits._

  val lines = spark.readStream
    .format("socket")
    .option("host", "localhost")
    .option("port", 9999)
    .load()

  val words = lines.as[String].flatMap(_.split(" "))

  val wordCounts = words.groupBy("value").count()

  val query = wordCounts.writeStream
    .outputMode("complete")
    .format("console")
    .start()

  query.awaitTermination()
}
