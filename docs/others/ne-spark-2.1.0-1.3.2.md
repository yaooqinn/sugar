> create by hzyaoqin @ 2018年03月03日

## 社区基线

    2.1.0
    NE-Spark - 版本 ne-spark-2.1.0-1.3.2
                                                                                                                                                                            
<h2> 缺陷
</h2>
<ul>
<li>[<a href='http://jira.netease.com/browse/NESPARK-87'>NESPARK-87</a>] -         Deprecate Static Spark Thrift Server
</li>
<li>[<a href='http://jira.netease.com/browse/NESPARK-88'>NESPARK-88</a>] -         Generate Hive table properties for hive table
</li>
<li>[<a href='http://jira.netease.com/browse/NESPARK-93'>NESPARK-93</a>] -         Implementation of spark-authorizer
</li>
<li>[<a href='http://jira.netease.com/browse/NESPARK-95'>NESPARK-95</a>] -         Statement ERR Cause Mammut can&#39;t get log 
</li>
<li>[<a href='http://jira.netease.com/browse/NESPARK-107'>NESPARK-107</a>] -         CTAS hive/datasource table claim for select privilege for target table
</li>
</ul>

**备注**：rc1 @hznizhifeng 已有大鲵完成测试，regression 为 [NESPARK-107]    
问题点一共两个：    
一、CTAS语句，其实可以分解为 1，対源表的的扫描，这很简单不做解释；2对目标表的创建（需要db、table 的create的权限），对目标表执行Insert权限，当Overwrite为true时，应该是需要UPDATE的权限，当时spark-authorizer做了对应的处理，但hive貌似没有这么严格的权限控制，所以这边进行妥协。
二、CTAS对于猛犸而言、及对于普通用户而言是不同的逻辑，这块在底层的平台侧做这样hard cord的判断实为不妥，所以移除ctas中对hive元数据的更新操作，防止权限的紊乱。并将其移动到analyse语句中。
                                                                        

## 生命周期

## 配置增改

|配置项 | 配置文件|  默认值|配置值| 功能简介|   
|---|---|---|---|---|    

## 限制说明

**本次release无附加限制**

## 打包命令
--name 命名规则 [ne|apache]-[Feature].[Major Issue].[HotFix]     

./dev/make-distribution.sh **--name ne-1.3.2** --ranger --tgz -Phadoop-2.7 -Dhadoop.version=2.7.3 -Phive -Phive-thriftserver -Pyarn -Pranger

## 升级指导

[[上线文档] - Spark Thrift Server 上线文档 @ Mammut v4.8 Hotfix](https://g.hz.netease.com/hadoop/Spark-Skills/issues/32)

[[上线文档] - Spark Thrift Server 上线文档 @ Mammut v4.6 Hotfix](https://g.hz.netease.com/hadoop/Spark-Skills/issues/28)

## 测试指导

**本例只列出开发侧测试用例**


## QA 测试

请QA同学补充测试用例

## 附加问题
