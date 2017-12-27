package io.udash.testing

import org.scalajs.dom
import org.scalatest._
import org.scalatest.concurrent.PatienceConfiguration
import org.scalatest.time.{Millis, Span}

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.scalajs.concurrent.JSExecutionContext
import scala.scalajs.js.Date
import scala.util.{Failure, Success}

trait AsyncUdashSharedTest extends AsyncUdashSharedTestBase {
  override implicit def executionContext: ExecutionContext = JSExecutionContext.queue

  override def retrying(code: => Any)(implicit patienceConfig: PatienceConfig): Future[Assertion] = {
    val start = Date.now()
    val p = Promise[Assertion]
    var lastEx: Option[Throwable] = None
    def startTest(): Unit = {
      dom.window.setTimeout(() => {
        if (patienceConfig.timeout.toMillis > Date.now() - start) {
          try {
            code
            p.complete(Success(Succeeded))
          } catch {
            case ex: Throwable =>
              lastEx = Some(ex)
              startTest()
          }
        } else {
          p.complete(Failure(lastEx.getOrElse(RetryingTimeout())))
        }
      }, patienceConfig.interval.toMillis)
    }
    startTest()
    p.future
  }
}
