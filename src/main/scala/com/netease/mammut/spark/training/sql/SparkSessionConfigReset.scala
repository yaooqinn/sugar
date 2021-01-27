package com.netease.mammut.spark.training.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkSessionConfigReset extends App {
  val wh = "spark.sql.warehouse.dir"
  val td = "spark.sql.globalTempDatabase"
  val custom = "spark.sql.custom"

  private val conf = new SparkConf()
    .setMaster("local")
    .setAppName("xx")
    .set(wh, "sparkconf")
    .set(td, "sparkconf")
    .set(custom, "sparkconf")

  private val ss: SparkSession =
    SparkSession.builder()
      .config(conf)
      .config(wh, "initconfig")
      .config(td, "initconfig")
      .config(custom, "initconfig").getOrCreate()
  ss.conf.getAll.foreach(c => println(c._1 + "=" + c._2))

  ss.sql("reset")
  println("---------")
  ss.conf.getAll.foreach(c => println(c._1 + "=" + c._2))

}
