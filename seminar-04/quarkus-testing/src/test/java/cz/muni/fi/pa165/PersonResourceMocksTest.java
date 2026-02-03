package cz.muni.fi.pa165;

import cz.muni.fi.pa165.socialnetwork.api.PersonDetailedViewDto;
import cz.muni.fi.pa165.socialnetwork.facade.PersonFacade;
import cz.muni.fi.pa165.socialnetwork.util.TestDataFactory;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class PersonResourceMocksTest {

    @InjectMock
    private PersonFacade personFacade;

    @Test
    void findById_personFound_returnsPerson() throws Exception {
        // Arrange
        long id = 1;
        Mockito.when(personFacade.findById(id)).thenReturn(TestDataFactory.personDetailedViewDto);

        // Act
        PersonDetailedViewDto response = given()
            .accept(MediaType.APPLICATION_JSON)
            .when().get("/persons/" + id)
            .then()
            .statusCode(200)
            .contentType(MediaType.APPLICATION_JSON)
            .extract().as(PersonDetailedViewDto.class);

        // Assert
        assertThat(response).isEqualTo(TestDataFactory.personDetailedViewDto);
    }

}
