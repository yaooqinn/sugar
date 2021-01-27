package com.netease.mammut.spark.training.sql

import java.lang.reflect.Field

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object StaticConfigTest extends App {

  println(org.apache.spark.SPARK_VERSION)

  private val wh = "spark.sql.warehouse.dir"

  private val td = "spark.sql.globalTempDatabase"

  System.setProperty(wh, "./data1")

  val conf = new SparkConf()
  println(s"=====> SparkConf: $wh=${conf.get(wh)}")

  conf.set(wh, "./data2")
  private val spark: SparkSession =
    SparkSession.builder()
      .config(conf)
      .enableHiveSupport()
      .config(wh, "./data3")
      .config(td, "bob")
      .getOrCreate()

  val confField: Field = spark.sharedState.getClass.getDeclaredField("conf")
  confField.setAccessible(true)
  private val shared: SparkConf = confField.get(spark.sharedState).asInstanceOf[SparkConf]
  println()
  println(s"=====> SparkSession SharedState: $wh=${shared.get(wh)}")
  println(s"=====> SparkSession SharedState: $td=${shared.get(td)}")

  spark.sql(s"set $wh").show()
  println(s"=====> SparkSession 1 SessionState: $wh=${spark.conf.get(wh)}")
  println(s"=====> SparkSession 1 SessionState: $td=${spark.conf.get(td)}")

  SparkSession.clearActiveSession()
  SparkSession.clearDefaultSession()

  val spark2: SparkSession =
    SparkSession.builder()
      .config(conf)
      .enableHiveSupport()
      .config(wh, "./data4")
      .config(td, "alice")
      .getOrCreate()

  spark2.sql(s"set $wh").show()
  println(s"=====> SparkSession 2 SessionState: $wh=${spark2.conf.get(wh)}")
  println(s"=====> SparkSession 2 SessionState: $td=${spark2.conf.get(td)}")
}


//bin/spark-submit --class com.netease.mammut.spark.training.sql.StaticConfigTest /Users/kentyao/sugar/target/mammut-spark-training-1.0-SNAPSHOT.jar
//3.0.0
//=====> SparkConf: spark.sql.warehouse.dir=./data1
//
//=====> SparkSession SharedState: spark.sql.warehouse.dir=./data3
//=====> SparkSession SharedState: spark.sql.globalTempDatabase=bob
//+--------------------+-------+
//|                 key|  value|
//+--------------------+-------+
//|spark.sql.warehou...|./data3|
//+--------------------+-------+
//
//=====> SparkSession 1 SessionState: spark.sql.warehouse.dir=./data3
//=====> SparkSession 1 SessionState: spark.sql.globalTempDatabase=bob
//+--------------------+-------+
//|                 key|  value|
//+--------------------+-------+
//|spark.sql.warehou...|./data4|
//+--------------------+-------+
//
//=====> SparkSession 2 SessionState: spark.sql.warehouse.dir=./data4
//=====> SparkSession 2 SessionState: spark.sql.globalTempDatabase=alice
