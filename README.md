# First Spray
This project shows how spray work with spray can or spray routing.

## Reference
Source : https://github.com/spray/spray-template/

### Two option for web container
* spray-can
* Jetty

### Steps
1. Create simple sbt project named "first-spray".
2. Add dependecy to build.sbt
 * **spray-can** for web container
 * **spray-routing** for http request dispatch
 * **akka-actor** for actor system
 * others are for testing
  
  ``` 
libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-routing" % sprayV,
    "io.spray"            %%  "spray-testkit" % sprayV  % "test",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test"
  )
}
```
3. After adding dependencies, project would rebuild soon and we could observ that src folder had been auto created.
4. Create a object which extended **App** as a main class to boot web server.
 * Declare **ActorSystem**
 * Declare a service actor.
 * Declare a **timeout** val for http request, this is required parameter.
 * Config Http server include **interface** and **port** and binding our **service** actor.
5. Create a trait that defines our service behavior and extends **HttpService**.

   Following is the example

  ```
  val myRoute =
    path("") {  // request path
      get {     // request method
        respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
          complete {
            <html>
              <body>
                <h1>Say hello to <i>spray-routing</i> on <i>spray-can</i>!</h1>
              </body>
            </html>
          }
        }
      }
    }
```
6. Create an Actor Class extended **Actor** and your service trait.

   Following functions should be define because of extending **HttpService**
 * **actorRefFactory** for actor context
 * **receive** for handling request
7. Run main object.
8. Go to the web interface(ex:localhost:8080) and checkout the result.
