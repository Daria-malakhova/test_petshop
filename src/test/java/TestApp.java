import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import static io.restassured.RestAssured.given;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestApp {


    private String test_base_uri = "https://petstore.swagger.io/v2/pet";
    private String format = "application/json";
    private int petId = 123;
    private String updatedName = "Green Dragon";
    private String updateStatus = "sold";


    @Before
    public void init(){RestAssured.baseURI = test_base_uri;}

    @Test
    public void test1AddANewPetToTheStore(){
        given()
                .accept(format)
                .contentType(format)
                .body(new File("src/test/java/resources/pet.json"))
                .when()
                .post()
                .then()
                .statusCode(200)
                .log().all().extract().response();

    }

    @Test
    public void test2UpdateAnExistingPet(){
        given()
                .accept(format)
                .contentType(format)
                .body(new File("src/test/java/resources/updatedPet.json"))
                .when()
                .put()
                .then()
                .statusCode(200)
                .log().all();

    }

    @Test
    public void test3FindPetByStatus(){
        given()
                .accept(format)
                .queryParam("status", "available")
                .when()
                .get("/findByStatus")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void test4FindPetById(){
        given()
                .accept(format)
                .pathParam("petId", petId)
                .when()
                .get("/{petId}")
                .then()
                .statusCode(200)
                .log().all();

    }

    @Test
    public void test5UpdatesAPetInTheStoreFromData(){
        given()
                .accept(format)
                .pathParam("petId", petId)
                .queryParam("name",updatedName)
                .queryParam("status",updateStatus)
                .when()
                .post("/{petId}")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void test6DeleteAPet(){
        given()
                .accept(format)
                .pathParam("petId", petId)
                .when()
                .delete("/{petId}")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test
    public void test7UploadImage(){
        given()
                .multiPart("file", new File("src/test/java/resources/dragon.jpg"))
                .accept(format)
                .pathParam("petId", "123")
                .when()
                .log().all()
                .post("/pet/{petId}/uploadImage")
                .then()
                .statusCode(404)
                .log().all();
    }

}
