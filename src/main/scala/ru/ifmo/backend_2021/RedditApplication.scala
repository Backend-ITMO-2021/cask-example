package ru.ifmo.backend_2021

import ru.ifmo.backend_2021.ApplicationUtils.Document
import ru.ifmo.backend_2021.pseudodb.{MessageDB, PseudoDB}
import scalatags.Text.all._
import scalatags.generic
import scalatags.text.Builder

object RedditApplication extends cask.MainRoutes {
  val serverUrl = s"http://$host:$port"
  val db: MessageDB = PseudoDB(s"db.txt", clean = true)

  @cask.staticResources("/static")
  def staticResourceRoutes() = "static"

  @cask.get("/")
  def hello(): Document = doctype("html")(
    html(
      head(
        link(rel := "stylesheet", href := ApplicationUtils.styles),
        script(src := "/static/app.js")
      ),
      body(
        div(cls := "container")(
          h1("Reddit: Swain is mad :("),
          div(id := "messageList")(messageList()),
          div(id := "errorDiv", color.red),
          form(onsubmit := "return submitForm()")(
            input(`type` := "text", id := "nameInput", placeholder := "Username"),
            input(`type` := "text", id := "msgInput", placeholder := "Write a message!"),
            input(`type` := "submit", value := "Send"),
          )
        )
      )
    )
  )

  def messageList(): generic.Frag[Builder, String] = frag(for (Message(name, msg) <- db.getMessages) yield p(b(name), " ", msg))

  @cask.postJson("/")
  def postChatMsg(name: String, msg: String): ujson.Obj = {
    log.debug(name, msg)
    if (name == "") ujson.Obj("success" -> false, "err" -> "Name cannot be empty")
    else if (msg == "") ujson.Obj("success" -> false, "err" -> "Message cannot be empty")
    else if (name.contains("#")) ujson.Obj("success" -> false, "err" -> "Username cannot contain '#'")
    else {
      db.addMessage(Message(name, msg))
      ujson.Obj("success" -> true, "err" -> "", "txt" -> messageList().render)
    }
  }

  log.debug(s"Starting at $serverUrl")
  initialize()
}
