# Spark Common Error List



### 错误类型
```scala
org.apache.spark.shuffle.MetadataFetchFailedException: Missing an output location for shuffle 0
```
### 错误详情
```scala
17/11/01 10:29:50 WARN TaskSetManager: Lost task 4.0 in stage 3.1 (TID 4584, bigdata21.hz.163.org, executor 32): FetchFailed(null, shuffleId=0, mapId=-1, reduceId=4, message=
org.apache.spark.shuffle.MetadataFetchFailedException: Missing an output location for shuffle 0
	at org.apache.spark.MapOutputTracker$$anonfun$org$apache$spark$MapOutputTracker$$convertMapStatuses$2.apply(MapOutputTracker.scala:697)
	at org.apache.spark.MapOutputTracker$$anonfun$org$apache$spark$MapOutputTracker$$convertMapStatuses$2.apply(MapOutputTracker.scala:693)
	at scala.collection.TraversableLike$WithFilter$$anonfun$foreach$1.apply(TraversableLike.scala:733)
	at scala.collection.IndexedSeqOptimized$class.foreach(IndexedSeqOptimized.scala:33)
	at scala.collection.mutable.ArrayOps$ofRef.foreach(ArrayOps.scala:186)
	at scala.collection.TraversableLike$WithFilter.foreach(TraversableLike.scala:732)
	at org.apache.spark.MapOutputTracker$.org$apache$spark$MapOutputTracker$$convertMapStatuses(MapOutputTracker.scala:693)
	at org.apache.spark.MapOutputTracker.getMapSizesByExecutorId(MapOutputTracker.scala:147)
	at org.apache.spark.shuffle.BlockStoreShuffleReader.read(BlockStoreShuffleReader.scala:49)
	at org.apache.spark.sql.execution.ShuffledRowRDD.compute(ShuffledRowRDD.scala:169)
	at org.apache.spark.rdd.RDD.computeOrReadCheckpoint(RDD.scala:323)
	at org.apache.spark.rdd.RDD.iterator(RDD.scala:287)
	at org.apache.spark.scheduler.ResultTask.runTask(ResultTask.scala:87)
	at org.apache.spark.scheduler.Task.run(Task.scala:99)
	at org.apache.spark.executor.Executor$TaskRunner.run(Executor.scala:325)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)
```

### 解决方案
>


### 错误类型
```scala
org.apache.spark.shuffle.FetchFailedException: Direct buffer memory
```

### 错误详情
```scala
Caused by: org.apache.spark.shuffle.FetchFailedException: Direct buffer memory
	at org.apache.spark.storage.ShuffleBlockFetcherIterator.throwFetchFailedException(ShuffleBlockFetcherIterator.scala:361)
	at org.apache.spark.storage.ShuffleBlockFetcherIterator.next(ShuffleBlockFetcherIterator.scala:336)
	at org.apache.spark.storage.ShuffleBlockFetcherIterator.next(ShuffleBlockFetcherIterator.scala:54)
	at scala.collection.Iterator$$anon$11.next(Iterator.scala:409)
	at scala.collection.Iterator$$anon$12.nextCur(Iterator.scala:434)
	at scala.collection.Iterator$$anon$12.hasNext(Iterator.scala:440)
	at scala.collection.Iterator$$anon$11.hasNext(Iterator.scala:408)
	at org.apache.spark.util.CompletionIterator.hasNext(CompletionIterator.scala:32)
	at org.apache.spark.InterruptibleIterator.hasNext(InterruptibleIterator.scala:39)
	at scala.collection.Iterator$$anon$11.hasNext(Iterator.scala:408)
	at org.apache.spark.sql.execution.datasources.FileFormatWriter$DynamicPartitionWriteTask.execute(FileFormatWriter.scala:362)
	at org.apache.spark.sql.execution.datasources.FileFormatWriter$$anonfun$org$apache$spark$sql$execution$datasources$FileFormatWriter$$executeTask$3.apply(FileFormatWriter.scala:190)
	at org.apache.spark.sql.execution.datasources.FileFormatWriter$$anonfun$org$apache$spark$sql$execution$datasources$FileFormatWriter$$executeTask$3.apply(FileFormatWriter.scala:188)
	at org.apache.spark.util.Utils$.tryWithSafeFinallyAndFailureCallbacks(Utils.scala:1353)
	at org.apache.spark.sql.execution.datasources.FileFormatWriter$.org$apache$spark$sql$execution$datasources$FileFormatWriter$$executeTask(FileFormatWriter.scala:193)
	... 8 more
Caused by: java.lang.OutOfMemoryError: Direct buffer memory
	at java.nio.Bits.reserveMemory(Bits.java:693)
	at java.nio.DirectByteBuffer.<init>(DirectByteBuffer.java:123)
	at java.nio.ByteBuffer.allocateDirect(ByteBuffer.java:311)
	at io.netty.buffer.PoolArena$DirectArena.allocateDirect(PoolArena.java:711)
	at io.netty.buffer.PoolArena$DirectArena.newChunk(PoolArena.java:700)
	at io.netty.buffer.PoolArena.allocateNormal(PoolArena.java:237)
	at io.netty.buffer.PoolArena.allocate(PoolArena.java:221)
	at io.netty.buffer.PoolArena.allocate(PoolArena.java:141)
	at io.netty.buffer.PooledByteBufAllocator.newDirectBuffer(PooledByteBufAllocator.java:296)
	at io.netty.buffer.AbstractByteBufAllocator.directBuffer(AbstractByteBufAllocator.java:177)
	at io.netty.buffer.AbstractByteBufAllocator.directBuffer(AbstractByteBufAllocator.java:168)
	at io.netty.buffer.AbstractByteBufAllocator.ioBuffer(AbstractByteBufAllocator.java:129)
	at io.netty.channel.AdaptiveRecvByteBufAllocator$HandleImpl.allocate(AdaptiveRecvByteBufAllocator.java:104)
	at io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:117)
	at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:643)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:566)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:480)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:442)
	at io.netty.util.concurrent.SingleThreadEventExecutor$2.run(SingleThreadEventExecutor.java:131)
	at io.netty.util.concurrent.DefaultThreadFactory$DefaultRunnableDecorator.run(DefaultThreadFactory.java:144)
	... 1 more
```

### 解决方案
> 1. 该问题一般见于Shuffle Read阶段，Spark 基于Netty做RPC通信拉取远端数据，Netty默认用的是DirectByteBuffer内存，当特定条件下使得Client端拉取的Block块过大时，即会出现该错误。
> 2. 避免数据倾斜
> 3. 增加partition数量
>
>
>
>
>
> 参考：https://issues.apache.org/jira/browse/SPARK-13510


## test
