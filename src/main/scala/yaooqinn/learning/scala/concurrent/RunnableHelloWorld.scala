package yaooqinn.learning.scala.concurrent

/**
 * java -cp ./target/mammut-spark-training-1.0-SNAPSHOT.jar:$SCALA_HOME/lib/scala-library.jar  yaooqinn.learning.scala.concurrent.RunnableHelloWorld
 */
class RunnableHelloWorld extends Runnable {
  override def run(): Unit = {
    println("Hello World Runnable")
  }
}

object RunnableHelloWorld {
  def main(args: Array[String]): Unit = {
    val r = new RunnableHelloWorld
    val t = new Thread(r)
    t.start()
    t.join()
  }
}
