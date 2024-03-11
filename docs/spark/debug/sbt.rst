使用 sbt 调试 Spark 单元测试
=========================

.. code:: 

   ./build/sbt -Pdocker-integration-tests  -Pjdwp-test-debug -Dtest.jdwp.address=5005 "docker-integration-tests/testOnly *jdbc.OracleIntegrationSuite -- -z raw"