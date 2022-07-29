package org.apache.yao.spark.sql

import org.apache.commons.logging.{Log, LogFactory}
import org.apache.spark.sql.SparkSession

object RunSparkSql {

  private val log: Log = LogFactory.getLog(this.getClass.getCanonicalName)
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Run Spark SQL with Hive")
      .enableHiveSupport()
      .getOrCreate()
    val sqltext = args(0)
    log.info(sqltext)
    val plan = spark.sessionState.sqlParser.parsePlan(sqltext)
    log.info(plan.treeString)
    spark.sql(args(0)).show()
    spark.stop()
  }
}
