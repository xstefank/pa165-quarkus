package cz.muni.fi.pa165;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class PersonResourceTest {

    @Test
    void testHelloEndpoint() {
        given()
            .when().get("/persons")
            .then()
            .contentType(MediaType.APPLICATION_JSON)
            .statusCode(200);
    }
}