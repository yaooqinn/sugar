package com.netease.mammut.spark.test

import org.apache.spark.sql.SparkSession

object SQLTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName(getClass.getSimpleName)
      .config("es.nodes", "")
      .config("es.port", 9200)
      .getOrCreate()

    spark.sql(
      s"""
         | r myIndex
         | USING org.elasticsearch.spark.sql
         | OPTIONS ( resource 'spark/index', nodes 'spark/index')
       """.stripMargin)
  }

}
