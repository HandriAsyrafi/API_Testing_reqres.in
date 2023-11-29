package apiauto;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;


public class APITest {

    @Test
    public void getRestAssured() {
        RestAssured.baseURI = "https://reqres.in/";

        given().when().get("api/users?page=1")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("page", Matchers.equalTo(1))
                .assertThat().body("data.id", Matchers.hasSize(6));
    }

    @Test
    public void createNewUserTest() {
        RestAssured.baseURI = "https://reqres.in/";
        String name = "handri";
        String job = "QA";

        JSONObject jsonobject = new JSONObject();
        jsonobject.put("name", name);
        jsonobject.put("job", job);

        given().when()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(jsonobject.toString())
                .post("api/users")
                .then()
                .log().all()
                .assertThat().statusCode(201)
                .assertThat().body("name", Matchers.equalTo(name))
                .assertThat().body("job", Matchers.equalTo(job));

    }

    @Test
    public void testPutUser() {
        RestAssured.baseURI = "https://reqres.in/";
        int userId = 2;
        String newName = "updateUser";

        String firstName = given().when().get("api/users/"+userId).getBody().jsonPath().get("data.first_name");
        System.out.println("Name Before " + firstName);

        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("id", userId);
        bodyMap.put("first_name", newName);
        JSONObject jsonobject = new JSONObject(bodyMap);

        given().when()
                .header("Content-Type", "application/json")
                .body(jsonobject.toString())
                .put("api/users/"+userId)
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("first_name", Matchers.equalTo(newName));

    }

    @Test
    public void testPatchUser() {
        RestAssured.baseURI = "https://reqres.in/";
        int userId = 3;
        String newName = "updateUser";

        String firstName = given().when().get("api/users/"+userId).getBody().jsonPath().get("data.first_name");
        System.out.println("Name Before " + firstName);

        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("first_name", newName);
        JSONObject jsonobject = new JSONObject(bodyMap);

        given().when()
                .header("Content-Type", "application/json")
                .body(jsonobject.toString())
                .patch("api/users/"+userId)
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("first_name", Matchers.equalTo(newName));

    }

    @Test
    public void testDeleteUser() {
        RestAssured.baseURI = "https://reqres.in/";
        int userToDelete = 4;

        given().when()
                .delete("api/users/"+userToDelete)
                .then()
                .log().all()
                .assertThat().statusCode(204);
    }
}
