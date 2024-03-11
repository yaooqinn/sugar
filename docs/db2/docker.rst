DB2 Docker 的使用
================

获取 DB2 Docker 镜像
--------------------

.. code::

    docker pull ibmcom/db2:latest

.. code::
    
    docker run \
      -h db2server \
      --name db2server \
      --restart=always \
      --detach \
      --privileged=true \
      -p 50000:50000 \
      -e LICENSE=accept \
      -e DB2INSTANCE=db2inst1 \
      -e DB2INST1_PASSWORD=password \
      -e DBNAME=testdb \
      -e TO_CREATE_SAMPLEDB=false \
      ibmcom/db2:latest

参考
===

https://hub.docker.com/r/ibmcom/db2/tags
https://www.ibm.com/docs/en/db2/11.5?topic=deployments-db2-community-edition-docker