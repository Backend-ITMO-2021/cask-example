import TestUtils.withServer
import ru.ifmo.backend_2021.RedditApplication
import utest.{TestSuite, Tests, test}

object RedditTest extends TestSuite {
  val tests: Tests = Tests {
    test("success") - withServer(RedditApplication) { host =>
      val success = requests.get(host)

      assert(success.text().contains("Reddit: Swain is mad :("))
      assert(success.text().contains("ventus976"))
      assert(success.text().contains("I don't particularly care which interaction they pick so long as it's consistent."))
      assert(success.text().contains("XimbalaHu3"))
      assert(success.text().contains("Exactly, both is fine but do pick one."))
      assert(success.statusCode == 200)

      val response = requests.post(host, data = Map("name" -> "ilya", "msg" -> "Test Message!"))

      assert(success.text().contains("Reddit: Swain is mad :("))
      assert(success.text().contains("ventus976"))
      assert(success.text().contains("I don't particularly care which interaction they pick so long as it's consistent."))
      assert(success.text().contains("XimbalaHu3"))
      assert(success.text().contains("Exactly, both is fine but do pick one."))
      assert(response.text().contains("ilya"))
      assert(response.text().contains("Test Message!"))
      assert(response.statusCode == 200)
    }
    /*test("failure") - withServer(RedditApplication) { host =>
      val response1 = requests.post(host, data = Map("name" -> "ilya"), check = false)
      assert(response1.statusCode == 400)
      val response2 = requests.post(host, data = Map("name" -> "ilya", "msg" -> ""))
      assert(response2.text().contains("Message cannot be empty"))
      val response3 = requests.post(host, data = Map("name" -> "", "msg" -> "Test Message!"))
      assert(response3.text().contains("Name cannot be empty"))
    }*/
  }
}