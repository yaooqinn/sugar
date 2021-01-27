package org.apache.yao.spark.sql

import org.apache.spark.sql.SparkSession

/**
 * bin/spark-submit --master local --class org.apache.yao.spark.sql.ShowHiveTables ~/sugar/target/sugar-1.0-SNAPSHOT.jar
 */
object ShowHiveTables {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Show Hive Tables")
      .enableHiveSupport()
      .getOrCreate()
    spark.sql("show databases").show()
    spark.sql("show tables").show()
    spark.stop()
  }
}
