package featureTests;

import com.MindMySelf.SharedVariables;
import io.restassured.RestAssured;

public class BookingGetFeatureTest {
    private void setBaseURL() {
        RestAssured.baseURI = SharedVariables.basicURL;
    }
}
