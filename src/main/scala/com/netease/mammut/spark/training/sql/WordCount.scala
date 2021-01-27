package com.netease.mammut.spark.training.sql

import org.apache.spark.sql.SparkSession

object WordCount extends App {

  val spark = SparkSession
    .builder()
    .appName("SQLWordCount")
    .getOrCreate()

  import spark.implicits._

  val res = spark.read.textFile(args(0))
    .flatMap(_.split(" "))
    .groupByKey(identity)
    .count
    .collect

  for ((word, count) <- res) {
    println(word + ": " + count)
  }

  spark.stop()
}
