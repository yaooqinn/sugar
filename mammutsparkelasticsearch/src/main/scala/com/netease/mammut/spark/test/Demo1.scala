package com.netease.mammut.spark.test

import org.apache.spark.sql.SparkSession
import org.elasticsearch.spark._

object Demo1 extends App {
  if (args.length != 2) {
    println(s"Usage: --class ${getClass.getCanonicalName} <jar> src dest")
    System.exit(1)
  }
  val spark = SparkSession
    .builder()
    .appName("Demo1")
    .config("es.index.auto.create", "true")
    .config("es.nodes", "")
    .config("es.port", 9200)
    .getOrCreate()

//  spark.read.json(args(0)).rdd.saveToEs(args(1))
}
