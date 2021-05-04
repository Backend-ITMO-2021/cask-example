package ru.ifmo.backend_2021

object MinimalApplication extends cask.MainRoutes {
  @cask.get("/")
  def hello(): String = {
    "Hello World"
  }

  @cask.post("/do-thing")
  def doThing(request: cask.Request): String = {
    request.text().reverse
  }

  initialize()
}
