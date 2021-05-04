package ru.ifmo.backend_2021

import scalatags.Text
import scalatags.Text.all._

object MockApplication extends cask.MainRoutes {
  val serverUrl = s"http://$host:$port"

  @cask.get("/")
  def hello(): Text.all.doctype = doctype("html")(
    html(
      head(link(rel := "stylesheet", href := ApplicationUtils.styles)),
      body(
        div(cls := "container")(
          h1("Reddit: Swain is mad :("),
          div(
            p(b("ventus976"), " ", "I don't particularly care which interaction they pick so long as it's consistent."),
            p(b("XimbalaHu3"), " ", "Exactly, both is fine but do pick one."),
          ),
          div(
            input(`type` := "text", placeholder := "User name"),
            input(`type` := "text", placeholder := "Write a message")
          )
        )
      )
    )
  )

  @cask.post("/do-thing")
  def doThing(request: cask.Request): String = {
    request.text().reverse
  }

  log.debug(s"Starting at $serverUrl")
  initialize()
}
