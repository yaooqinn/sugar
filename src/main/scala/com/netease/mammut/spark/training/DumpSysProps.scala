package com.netease.mammut.spark.training

import scala.collection.JavaConverters._

import org.apache.spark.sql.SparkSession

object DumpSysProps {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Dump Sys Props")
      .getOrCreate()

    println("==== SparkConf ====")
    spark.conf.getAll.foreach(println(_))
    println("==== System Environments ====")
    System.getenv().asScala.foreach(println(_))
    println("==== System Properties ====")
    System.getProperties.asScala.foreach(println(_))

    spark.stop()
  }

}
