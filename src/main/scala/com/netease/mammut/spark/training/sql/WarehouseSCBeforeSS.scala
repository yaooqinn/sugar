package com.netease.mammut.spark.training.sql

import java.lang.reflect.Field

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object WarehouseSCBeforeSS extends App {

  val wh = "spark.sql.warehouse.dir"
  val td = "spark.sql.globalTempDatabase"
  val custom = "spark.sql.custom"

  val conf = new SparkConf()
    .setMaster("local")
    .setAppName("SPARK-32991")
    .set(wh, "./data1")
    .set(td, "bob")

  val sc = new SparkContext(conf)

  val spark = SparkSession.builder()
    .config(wh, "./data2")
    .config(td, "alice")
    .config(custom, "kyao")
    .getOrCreate()

  spark.sql("desc database default").show()

  val confField: Field = spark.sharedState.getClass.getDeclaredField("conf")
  confField.setAccessible(true)
  private val shared: SparkConf = confField.get(spark.sharedState).asInstanceOf[SparkConf]
  println()
  println(s"=====> SharedState: $wh=${shared.get(wh)}")
  println(s"=====> SharedState: $td=${shared.get(td)}")
  println(s"=====> SharedState: $custom=${shared.get(custom, "")}")

  println(s"=====> SessionState: $wh=${spark.conf.get(wh)}")
  println(s"=====> SessionState: $td=${spark.conf.get(td)}")
  println(s"=====> SessionState: $custom=${spark.conf.get(custom, "")}")

  val spark2 = SparkSession.builder().config(td, "fred").getOrCreate()

  println(s"=====> SessionState 2: $wh=${spark2.conf.get(wh)}")
  println(s"=====> SessionState 2: $td=${spark2.conf.get(td)}")
  println(s"=====> SessionState 2: $custom=${spark2.conf.get(custom, "")}")

  SparkSession.setActiveSession(spark)
  spark.sql("RESET")

  println(s"=====> SessionState RESET: $wh=${spark.conf.get(wh)}")
  println(s"=====> SessionState RESET: $td=${spark.conf.get(td)}")
  println(s"=====> SessionState RESET: $custom=${spark.conf.get(custom, "")}")

  val spark3 = SparkSession.builder().getOrCreate()

  println(s"=====> SessionState 3: $wh=${spark2.conf.get(wh)}")
  println(s"=====> SessionState 3: $td=${spark2.conf.get(td)}")
  println(s"=====> SessionState 3: $custom=${spark2.conf.get(custom, "")}")
}

//+-------------------------+--------------------------+
//|database_description_item|database_description_value|
//+-------------------------+--------------------------+
//|            Database Name|                   default|
//|                  Comment|          default database|
//|                 Location|                     data1|
//|                    Owner|                          |
//+-------------------------+--------------------------+
//
//
//=====> SharedState: spark.sql.warehouse.dir=./data1
//=====> SharedState: spark.sql.globalTempDatabase=alice
//=====> SharedState: spark.sql.custom=kyao
//=====> SessionState: spark.sql.warehouse.dir=./data2
//=====> SessionState: spark.sql.globalTempDatabase=alice
//=====> SessionState: spark.sql.custom=kyao
//=====> SessionState 2: spark.sql.warehouse.dir=./data2
//=====> SessionState 2: spark.sql.globalTempDatabase=alice
//=====> SessionState 2: spark.sql.custom=kyao
//=====> SessionState RESET: spark.sql.warehouse.dir=./data1
//=====> SessionState RESET: spark.sql.globalTempDatabase=bob
//=====> SessionState RESET: spark.sql.custom=
//=====> SessionState 3: spark.sql.warehouse.dir=./data1
//=====> SessionState 3: spark.sql.globalTempDatabase=bob
//=====> SessionState 3: spark.sql.custom=