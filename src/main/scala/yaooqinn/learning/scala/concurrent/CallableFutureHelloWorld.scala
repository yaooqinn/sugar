package yaooqinn.learning.scala.concurrent

import java.util.concurrent.{Callable, Executors}

class CallableFutureHelloWorld extends Callable[String] {
  override def call(): String = {
    println("sub thread calculating ")
    Thread.sleep(3000)
    "hello world callable future".map(_.toUpper)
  }
}

object CallableFutureHelloWorld {
  def main(args: Array[String]): Unit = {
    val service = Executors.newCachedThreadPool()
    val cf = new CallableFutureHelloWorld
    service.submit(cf)
    service.shutdown()
    try {
      Thread.sleep(500)
    } catch {
      case e: InterruptedException => e.printStackTrace()
    }
    println("main thread working ")

    try {

    }
  }
}