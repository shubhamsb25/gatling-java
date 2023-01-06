import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class DemoSimulation extends Simulation {
    FeederBuilder<Object> feeder = jsonFile("data.json").circular();
    ScenarioBuilder jsonFeederScenario = scenario("Json feeder scenario")
            .feed(feeder)
            .exec(
                    http("JsonFeeder")
                            .post("/user")
                            .header("Content-Type", "application/json")
                            .header("Accept", "application/json")
                            // Takes values (i.e. idField and nameField) from feeder json object and creates a json body
                            .body(StringBody("{\"id\":  \"#{idField}\",\"name\":  \"#{nameField}\"}"))
                            .check(status().is(200))
            );

    ScenarioBuilder jsonFeederScenarioWithTemplate = scenario("Json feeder scenario using template file")
            .feed(feeder)
            .exec(
                    http("JsonFeederWithTemplateFile")
                            .post("/user")
                            .header("Content-Type", "application/json")
                            .header("Accept", "application/json")
                            // Similar to above example takes values (i.e. idField and nameField) from feeder json object
                            // and populates template json object which is then sent in the request body
                            .body(ElFileBody("template.json"))
                            .check(status().is(200))
            );
    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080");

    {
        setUp(
                jsonFeederScenario.injectOpen(rampUsers(10).during(10)),
                jsonFeederScenarioWithTemplate.injectOpen(rampUsers(10).during(10))
        ).protocols(httpProtocol)
                .assertions(global().successfulRequests().percent().shouldBe(100.0));
    }
}
