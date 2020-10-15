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


//bin/spark-submit --class com.netease.mammut.spark.training.sql.WarehouseSCBeforeSS /Users/kentyao/sugar/target/mammut-spark-training-1.0-SNAPSHOT.jar
//
// 1. Make the cloned spark conf in shared state respect the warehouse dir from the 1st SparkSession
//=====> SharedState: spark.sql.warehouse.dir=./data1
// 2. ⏬
//=====> SharedState: spark.sql.globalTempDatabase=alice
//=====> SharedState: spark.sql.custom=kyao
//=====> SessionState: spark.sql.warehouse.dir=./data2
//=====> SessionState: spark.sql.globalTempDatabase=alice
//=====> SessionState: spark.sql.custom=kyao
//=====> SessionState 2: spark.sql.warehouse.dir=./data2
//=====> SessionState 2: spark.sql.globalTempDatabase=alice
//=====> SessionState 2: spark.sql.custom=kyao
// 2'.🔼 OK until here
// 3. Make the below 3 ones respect the cloned spark conf in shared state with issue 1 fixed
//=====> SessionState RESET: spark.sql.warehouse.dir=./data1
//=====> SessionState RESET: spark.sql.globalTempDatabase=bob
//=====> SessionState RESET: spark.sql.custom=
// 4. Then the SparkSessions created after RESET will be corrected.
//=====> SessionState 3: spark.sql.warehouse.dir=./data1
//=====> SessionState 3: spark.sql.globalTempDatabase=bob
//=====> SessionState 3: spark.sql.custom=
// 5. SparkSession.clearActiveSession/clearDefaultSession will make the shared state invisible and unsharable
// They will be internal only soon (confirmed with Wenchen), so cases with them called will not be a problem