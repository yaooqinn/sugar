Apache Spark 链接 Oracle 数据库
=============================

本文档介绍如何使用 Apache Spark 连接 Oracle 数据库。

数据类型
-------


Oracle 数据库环境
---------------
在本文档中，我们使用 `Oracle Database 23.3`_ 作为数据库环境。

.. code:: bash
    
    $ docker run -e "ORACLE_PASSWORD=oraclepasswordMy" -p 1521:1521 --name ora -d gvenzl/oracle-free:23.3-slim

Spark Oracle 运行环境
--------------------

- Apache Spark
- `Oracle JDBC 驱动程序`_

.. code:: bash

    $ bin/spark-shell --packages=com.oracle.database.jdbc:ojdbc11:23.3.0.23.09

使用 JDBC 直连 Oracle 数据库
~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code:: scala

    import java.sql.DriverManager
    val conn = DriverManager.getConnection("jdbc:oracle:thin:@hulk.local:1521/freepdb1", "system", "oraclepasswordMy")
    val stmt = conn.createStatement()
    stmt.execute(
        "CREATE TABLE ABC(a TIMESTAMP, b TIMESTAMP WITH TIME ZONE, c TIMESTAMP WITH LOCAL TIME ZONE)")
    stmt.execute("INSERT INTO ABC VALUES (TIMESTAMP '2018-11-17 13:33:33.33', TIMESTAMP '2018-11-17 13:33:33.33 +08:00', TIMESTAMP '2018-11-17 13:33:33.33')")
    val metaData = conn.getMetaData()
    val tables = metaData.getTables(null, null, "AB%", null)
    while (tables.next()) {
      println(tables.getString(3))
    }

使用 Spark Data Source 接口连接 Oracle 数据库
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code:: scala

    // Change JDBC Connection URL, user or password if necessary:
    // jdbc:oracle:thin:@hulk.local:1521/freepdb1
    val reader = spark
      .read
      .format("jdbc")
      .option("url", "jdbc:oracle:thin:@hulk.local:1521/freepdb1")
      .option("driver", "oracle.jdbc.OracleDriver")
      .option("user", "system")
      .option("password","oraclepasswordMy")


.. code:: scala
    
    reader
      .option("query", "SELECT table_name, owner FROM all_tables")
      .load()
      .show()


.. code:: scala
    
    reader
      .option("dbtable", "abc")
      .load()
      .show()


使用 JDBC 直连 Oracle 数据库
~~~~~~~~~~~~~~~~~~~~~~~~~~

.. code:: scala

    import java.sql.DriverManager
    val conn = DriverManager.getConnection("jdbc:oracle:thin:@hulk.local:1521/freepdb1", "system", "oraclepasswordMy")
    val stmt = conn.createStatement()
    stmt.execute(
        "CREATE TABLE ABC(a TIMESTAMP WITH TIME ZONE, b TIMESTAMP (3) WITH LOCAL TIME ZONE, c TIMESTAMP (6) WITH LOCAL TIME ZONE)")
    val metaData = conn.getMetaData()
    val tables = metaData.getTables(null, null, "AB%", null)
    while (tables.next()) {
      println(tables.getString(3))
    }



.. OCI ORACLE FREE: https://github.com/gvenzl/oci-oracle-free
.. Oracle JDBC 驱动程序: https://mvnrepository.com/artifact/com.oracle.database.jdbc/ojdbc11


real to float
Databricks, db2, derby, mssql, postgres

mysql: https://dev.mysql.com/doc/refman/8.0/en/sql-mode.html#sqlmode_real_as_float
teradata: https://docs.teradata.com/r/Enterprise_IntelliFlex_VMware/SQL-Data-Types-and-Literals/Numeric-Data-Types/FLOAT/REAL/DOUBLE-PRECISION-Data-Types
oracle: https://docs.oracle.com/cd/F49540_01/DOC/java.815/a64685/basic3.htm#1010122
h2: http://www.h2database.com/html/datatypes.html#real_type


Snowflake