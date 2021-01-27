package yaooqinn.learning.scala.concurrent

import java.util.concurrent.{Callable, Executors}

/**
 * java -cp ./target/mammut-spark-training-1.0-SNAPSHOT.jar:$SCALA_HOME/lib/scala-library.jar  yaooqinn.learning.scala.concurrent.CallableHelloWorld
 */
class CallableHelloWorld extends Callable[String] {

  override def call(): String = {
    val hello = "Hello world Callable"
    println(hello)
    hello
  }
}

object CallableHelloWorld {
  def main(args: Array[String]): Unit = {
    val c = new CallableHelloWorld
    val service = Executors.newSingleThreadExecutor()
    val result = service.submit(c)
    println("Main Thread Said: " + result)
    service.shutdown()
  }
}
