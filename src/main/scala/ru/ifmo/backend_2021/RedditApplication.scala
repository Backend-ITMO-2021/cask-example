package ru.ifmo.backend_2021

import ru.ifmo.backend_2021.ApplicationUtils.Document
import ru.ifmo.backend_2021.pseudodb.{MessageDB, PseudoDB}
import scalatags.Text
import scalatags.Text.all._

object RedditApplication extends cask.MainRoutes {
  val serverUrl = s"http://$host:$port"
  val db: MessageDB = PseudoDB(s"db.txt", clean = true)

  @cask.get("/")
  def hello(): Document = doctype("html")(
    html(
      head(link(rel := "stylesheet", href := ApplicationUtils.styles)),
      body(
        div(cls := "container")(
          h1("Reddit: Swain is mad :("),
          div(for (Message(name, msg) <- db.getState) yield p(b(name), " ", msg)),
          form(action := "/", method := "post")(
            input(
              `type` := "text",
              name := "name",
              placeholder := "User name",
            ),
            input(
              `type` := "text",
              name := "msg",
              placeholder := "Write a message!",
            ),
            input(`type` := "submit", value := "Send"),
          )
        )
      )
    )
  )

  @cask.postForm("/")
  def postChatMsg(name: String, msg: String): Text.all.doctype = {
    log.debug(name, msg)
    db.addMessage(Message(name, msg))
    hello()
  }

  log.debug(s"Starting at $serverUrl")
  initialize()
}
