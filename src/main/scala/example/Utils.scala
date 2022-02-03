package example

import java.net.URI
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions._
import scala.reflect.runtime.universe._

import scala.util.Try

object Utils extends Serializable {
  val getKey: UserDefinedFunction = udf { (url: String) =>
    Try(new URI(url).getHost).toOption.orNull
  }
}
