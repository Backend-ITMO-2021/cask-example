import TestUtils.withServer
import ru.ifmo.backend_2021.MinimalApplication
import utest.{ArrowAssert, TestSuite, Tests, test}

object MinimalTest extends TestSuite {
  val tests: Tests = Tests {
    test("hello world") - withServer(MinimalApplication) { host =>
      val success = requests.get(host)
      success.statusCode ==> 200
      success.text() ==> "Hello World"
    }
    test("do-thing") - withServer(MinimalApplication) { host =>
      val success = requests.post(s"$host/do-thing", data = "Hello World")
      success.statusCode ==> 200
      success.text() ==> "dlroW olleH"
    }
  }
}