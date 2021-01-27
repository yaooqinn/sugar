package org.apache.yao.hive.udf

import java.time.LocalDateTime

import org.apache.hadoop.hive.ql.exec.UDF

class Age extends UDF {

  def evaluate(bornYear: Int): Int = {
    LocalDateTime.now().getYear - bornYear
  }

}
