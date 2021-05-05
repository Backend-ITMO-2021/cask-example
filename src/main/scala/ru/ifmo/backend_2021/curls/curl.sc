val host = "http://localhost:8080"

//Minimal
val hello = requests.get(host)
hello.text()
val doThing = requests.post(
  s"$host/do-thing",
  data = "Hello World"
)
doThing.text()

