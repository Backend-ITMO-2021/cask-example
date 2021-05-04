package ru.ifmo.backend_2021

import ru.ifmo.backend_2021.ApplicationUtils.Document
import scalatags.Text.all._

object MinimalApplication extends cask.MainRoutes {
  @cask.get("/")
  def hello(): Document = doctype("html")(
    html(
      head(link(rel := "stylesheet", href := ApplicationUtils.styles)),
      body(
        div(cls := "container")(
          h1("Hello"),
          p("World")
        )
      )
    )
  )

  @cask.post("/do-thing")
  def doThing(request: cask.Request): String = {
    request.text().reverse
  }

  initialize()
}
