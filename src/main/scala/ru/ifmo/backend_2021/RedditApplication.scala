package ru.ifmo.backend_2021

import ru.ifmo.backend_2021.ApplicationUtils.Document
import ru.ifmo.backend_2021.pseudodb.{MessageDB, PseudoDB}
import scalatags.Text
import scalatags.Text.all._

object RedditApplication extends cask.MainRoutes {
  val serverUrl = s"http://$host:$port"
  val db: MessageDB = PseudoDB(s"db.txt", clean = true)

  @cask.get("/")
  def hello(
    errorOpt: Option[String] = None,
    userName: Option[String] = None,
    msg: Option[String] = None
  ): Document = doctype("html")(
    html(
      head(link(rel := "stylesheet", href := ApplicationUtils.styles)),
      body(
        div(cls := "container")(
          h1("Reddit: Swain is mad :("),
          div(for (Message(name, msg) <- db.getMessages) yield p(b(name), " ", msg)),
          for (error <- errorOpt) yield i(color.red)(error),
          form(action := "/", method := "post")(
            input(
              `type` := "text",
              name := "name",
              placeholder := "Username",
              userName.map(value := _)
            ),
            input(
              `type` := "text",
              name := "msg",
              placeholder := "Write a message!",
              msg.map(value := _)
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
    if (name == "") hello(Some("Name cannot be empty"), Some(name), Some(msg))
    else if (msg == "") hello(Some("Message cannot be empty"), Some(name), Some(msg))
    else if (name.contains("#")) hello(Some("Username cannot contain '#'"), Some(name), Some(msg))
    else {
      db.addMessage(Message(name, msg))
      hello(None, Some(name), None)
    }
  }

  log.debug(s"Starting at $serverUrl")
  initialize()
}
