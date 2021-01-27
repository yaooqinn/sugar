package com.netease.mammut.spark.training.streaming

import java.sql.Timestamp

import com.netease.mammut.spark.training.common.TimeBasedWord
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object TimeBasedWordCount {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("TimeBasedWordCount")
      .getOrCreate()
    import spark.implicits._
    val lines = spark.readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", 9999)
      .load()
    val timeWords = lines.as[String]
      .flatMap(_.split(" "))
      .map(TimeBasedWord(new Timestamp(System.currentTimeMillis()), _))
    val windowedCounts = timeWords
      .groupBy(window($"timestamp", "10 minutes", "5 minutes"),
        $"word"
      ).count()
    val query = windowedCounts.writeStream
      .outputMode("complete")
      .format("console")
      .start()
    query.awaitTermination()
  }
}
