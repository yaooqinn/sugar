package com.netease.mammut.spark.test

import org.apache.spark.sql.SparkSession

object DataFrameTest {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(getClass.getSimpleName)
      .config("es.nodes", "10.120.181.34,10.120.181.33,10.120.181.32,10.120.181.31")
      .config("es.port", 9200)
      .getOrCreate()
    val demo = spark.read.json("/user/hzyaoqin/demo/10.json")

    demo.printSchema()

    val df = spark.read.format("org.elasticsearch.spark.sql").load("hzyaoqin/data1")

    df.printSchema()

    val filter = df.filter(df(""))

  }


}
