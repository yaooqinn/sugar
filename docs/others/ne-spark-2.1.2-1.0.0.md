> create by hzyaoqin @ 2018年03月01日

## 社区基线

    2.1.2

## 版本说明

> **该版本基于社区2.1.2进行修改**    
>> NE-Spark - 版本号 ne-spark-2.1.2-1.0.0    
>> 增加基于ranger权限控制。   
>> 修复相关bug。  
   
> **用途涵盖：**    
>> 猛犸由azkaban调度的数据开发任务         
>> 独立客户端程序     
>> client/cluster
                                                                                                                                                                            
## Bug Fix
<ul>
<li>[<a href='http://jira.netease.com/browse/NESPARK-30'>NESPARK-30</a>] -         [WebUI] HistoryServer WebUI时间戳显示问题
</li>
<li>[<a href='http://jira.netease.com/browse/NESPARK-42'>NESPARK-42</a>] -         [NE][2.1.2] Executor tab show nothing caused by Request replay attack
</li>
<li>[<a href='http://jira.netease.com/browse/NESPARK-92'>NESPARK-92</a>] -         [APACHE][SPARK-20633][SQL] FileFormatWriter should not wrap FetchFailedException
</li>
<li>[<a href='http://jira.netease.com/browse/NESPARK-99'>NESPARK-99</a>] -         [NE][2.1.2]ClsCastException when using jdo to connect hive metastore
</li>
<li>[<a href='http://jira.netease.com/browse/NESPARK-101'>NESPARK-101</a>] -         [NE][2.1.2]jersery-core artifact conflicts between spark and ranger
</li>
<li>[<a href='http://jira.netease.com/browse/NESPARK-102'>NESPARK-102</a>] -         [NE][2.1.2]Add Configuration Templates For Ranger
</li>
<li>[<a href='http://jira.netease.com/browse/NESPARK-103'>NESPARK-103</a>] -         [NE][2.1.2]unable to create policy cache dir in yarn cluster mode 
</li>
<li>[<a href='http://jira.netease.com/browse/NESPARK-104'>NESPARK-104</a>] -         [APACHE][2.1.2][SPARK-22463][YARN][SQL][HIVE] add hadoop/hive/hbase/etc configuration files in SPARK_CONF_DIR to distribute archive
</li>
<li>[<a href='http://jira.netease.com/browse/NESPARK-105'>NESPARK-105</a>] -         [NE][2.1.2][SPARK-22466][SPARK SUBMIT] export SPARK_CONF_DIR while conf is default
</li>
</ul>
                
<h2>特性增加</h2>
<ul>
<li>[<a href='http://jira.netease.com/browse/NESPARK-91'>NESPARK-91</a>] -         [NE][2.1.2]Support Multi SparkContext for Spark
</li>
<li>[<a href='http://jira.netease.com/browse/NESPARK-94'>NESPARK-94</a>] -         [NE][2.1.2]Support of Spark Authorizer Plugin
</li>
<li>[<a href='http://jira.netease.com/browse/NESPARK-97'>NESPARK-97</a>] -         [NE][2.1.2]Implementation of ranger plugins
</li>
<li>[<a href='http://jira.netease.com/browse/NESPARK-98'>NESPARK-98</a>] -         [NE][2.1.2]add mysql-connect-java to spark distribution
</li>
<li>[<a href='http://jira.netease.com/browse/NESPARK-100'>NESPARK-100</a>] -         [NE][2.1.2]Add hadoop-lzo maven artifact
</li>
</ul>
                                            
## 配置增改

|配置项 | 配置文件|  默认值|配置值| 功能简介|   
|---|---|---|---|---|    
|ranger.plugin.hive.policy.rest.url|ranger-hive-security.xml| <none> |** example ：** http://hadoop519.lt.163.org:6080,http://hadoop520.lt.163.org:6080| ranger admin 地址|    
|ranger.plugin.hive.service.name|ranger-hive-security.xml||**example：** hive-cluster3|Name of the Ranger service containing policies for this YARN instance|    
|ranger.plugin.hive.policy.cache.dir|ranger-hive-security.xml||**example：**hive-cluster3/policycache|本地策略的缓存路径，spark会自动创建，因此必须有权限，建议用相对路径|    
|xasecure.audit.is.enabled|ranger-hive-audit.xml|false|false|审计日志总开关，目前请保持关闭状态，因为线上审计日志基于数据库，由于spark cluster模式下，client节点不固定，数据库权限不好管理|
|xasecure.audit.jpa.javax.persistence.jdbc.driver|ranger-hive-audit.xml|com.mysql.jdbc.Driver|com.mysql.jdbc.Driver||
|xasecure.audit.jpa.javax.persistence.jdbc.url|ranger-hive-audit.xml||**example：** jdbc:mysql://10.172.121.106/mengma|审计日志库地址|    
|xasecure.audit.jpa.javax.persistence.jdbc.user|ranger-hive-audit.xml||*username*|审计日志数据库用户名|     
|xasecure.audit.jpa.javax.persistence.jdbc.password|ranger-hive-audit.xml||*Password*|审计日志数据库账号| 

在hive-site.xml中配置ranger相关设置
```xml
 <!-- Ranger -->
    <property>
        <name>hive.security.authorization.enabled</name>
        <value>true</value>
    </property>
    <property>
        <name>hive.security.authorization.manager</name>
        <value>org.apache.ranger.authorization.hive.authorizer.RangerHiveAuthorizerFactory</value>
    </property>
    <property>
        <name>hive.security.authenticator.manager</name>
        <value>org.apache.hadoop.hive.ql.security.SessionStateUserAuthenticator</value>
    </property>
    <property>
        <name>hive.conf.restricted.list</name>
        <value>
            hive.security.authorization.enabled,hive.security.authorization.manager,hive.security.authenticator.manager
        </value>
    </property>
```  

** 提交代码时需将hive-site.xml,ranger-hive-security.xml,ranger-hive-audit.xml 显示的加到classpath中，比较推荐的方式是--files 参数 **

## 限制说明

**ranger审计日志建议不开启，等solr支持后再行实行**


## 打包命令
--name 命名规则 [ne|apache]-[Feature].[Major Issue].[HotFix]    

```shell
/dev/make-distribution.sh \
  --name ne-1.0.0-SNAPSHOT \
  --ranger \
  --pip \
  --tgz \
  -Phadoop-2.7 \
  -Dhadoop.version=2.7.3 \
  -Phive \
  -Phive-thriftserver \
  -Pyarn \
  -Pranger 

```

## 升级指导

#### Azkaban

1.替换当前调度服务的spark distribution：对饮spark-2.1.0(1)-bin-hadoop-2.7    
2.增加hive-site.xml ranger相关配置    
3.增加ranger-hive-security.xml,ranger-hive-audit.xml相关配置，请参考上述配置项    

## 测试指导

#### 1 功能测试
  对应相关JIRA进行功能测试

#### 2 兼容性测试
  TPCDS 兼容性测试
  Hive DDL 兼容性测试

#### 3 权限测试
  
#### 4 连通性测试
   基于猛犸提交SQL、SparkStreaming、Python、Script等任务测试

#### 5 其他
   请QA同学设计测试方案

#### 拟上线版本

   Mammut-4.15 @2018年03月22日

## 附加问题

