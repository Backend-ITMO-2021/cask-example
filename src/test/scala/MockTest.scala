import TestUtils.withServer
import ru.ifmo.backend_2021.MockApplication
import utest.{ArrowAssert, TestSuite, Tests, test}

object MockTest extends TestSuite {
  val tests: Tests = Tests {
    test("success") - withServer(MockApplication) { host =>
      val success = requests.get(host)

      assert(success.text().contains("Reddit: Swain is mad :("))
      assert(success.text().contains("ventus976"))
      assert(success.text().contains("I don't particularly care which interaction they pick so long as it's consistent."))
      assert(success.text().contains("XimbalaHu3"))
      assert(success.text().contains("Exactly, both is fine but do pick one."))
      assert(success.statusCode == 200)
    }
  }
}