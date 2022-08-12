package persistence;

import model.City;
import model.Position;
import model.buildings.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {
    @Test
    public void testLoadNonExistent() {
        JsonReader reader = new JsonReader("randomFile.json");
        try {
            reader.read();
            fail("read() should have failed with IOException");
        } catch (IOException e) {
            // pass test
        }
    }

    @Test
    public void testLoadEmpty() {
        JsonReader reader = new JsonReader("./data/testCityEmpty.json");
        try {
            City city = reader.read();
            assertEquals("Tokyo 2", city.getName());
            assertEquals(100, city.getPopulation());
            assertEquals(1000, city.getMoney());
            assertEquals(1.0, city.getAttraction());
            assertEquals(0.1, city.getTaxRate());
            assertEquals(0, city.getMap().getNumOfBuildings());

        } catch (IOException e) {
            fail("Failed to read existent file.");
        }
    }

    @Test
    public void testLoadRegularCity() {
        JsonReader reader = new JsonReader("./data/testCityRegular.json");
        try {
            City city = reader.read();
            assertEquals("Tokyo 2", city.getName());
            assertEquals(10, city.getPopulation());
            assertEquals(500, city.getMoney());
            assertEquals(1.0, city.getAttraction());
            assertEquals(0.05, city.getTaxRate());
            assertEquals(8, city.getMap().getNumOfBuildings());
        } catch (IOException e) {
            fail("Failed to read city from existent file.");
        }
    }

    @Test
    public void testLoadRegularBusiness() {
        JsonReader reader = new JsonReader("./data/testCityRegular.json");
        try {
            City city = reader.read();
            Business business = (Business) city.getMap().getBuilding("1");
            checkBuilding("1", 5000, new Position(10, 2), business);
            checkBusiness(8, 20, business);
        } catch (IOException e) {
            fail("Failed to read building from existent file.");
        }
    }

    @Test
    public void testLoadRegularFireHall() {
        JsonReader reader = new JsonReader("./data/testCityRegular.json");
        try {
            City city = reader.read();
            FireHall fireHall = (FireHall) city.getMap().getBuilding("2");
            checkBuilding("2", 2000, new Position(3, 4), fireHall);
            checkFireHall(20, fireHall);
        } catch (IOException e) {
            fail("Failed to read building from existent file.");
        }
    }

    @Test
    public void testLoadRegularHospital() {
        JsonReader reader = new JsonReader("./data/testCityRegular.json");
        try {
            City city = reader.read();
            Hospital hospital = (Hospital) city.getMap().getBuilding("3");
            checkBuilding("3", 10000, new Position(0, 2), hospital);
            checkHospital(10, hospital);
        } catch (IOException e) {
            fail("Failed to read building from existent file.");
        }
    }

    @Test
    public void testLoadRegularHousing() {
        JsonReader reader = new JsonReader("./data/testCityRegular.json");
        try {
            City city = reader.read();
            Housing housing = (Housing) city.getMap().getBuilding("4");
            checkBuilding("4", 1000, new Position(2, 5), housing);
            checkHousing(10, 50, housing);
        } catch (IOException e) {
            fail("Failed to read building from existent file.");
        }
    }

    @Test
    public void testLoadRegularLandfill() {
        JsonReader reader = new JsonReader("./data/testCityRegular.json");
        try {
            City city = reader.read();
            Landfill landfill = (Landfill) city.getMap().getBuilding("5");
            checkBuilding("5", 5000, new Position(3, 9), landfill);
            checkLandfill(10, 10000, landfill);
        } catch (IOException e) {
            fail("Failed to read building from existent file.");
        }
    }

    @Test
    public void testLoadRegularPark() {
        JsonReader reader = new JsonReader("./data/testCityRegular.json");
        try {
            City city = reader.read();
            Park park = (Park) city.getMap().getBuilding("6");
            checkBuilding("6", 2000, new Position(3, 10), park);
            checkPark(1.5, park);
        } catch (IOException e) {
            fail("Failed to read building from existent file.");
        }
    }

    @Test
    public void testLoadRegularPowerPlant() {
        JsonReader reader = new JsonReader("./data/testCityRegular.json");
        try {
            City city = reader.read();
            PowerPlant powerPlant = (PowerPlant) city.getMap().getBuilding("7");
            checkBuilding("7", 10000, new Position(15, 2), powerPlant);
            checkPowerPlant(200, powerPlant);
        } catch (IOException e) {
            fail("Failed to read building from existent file.");
        }
    }

    @Test
    public void testLoadRegularSchool() {
        JsonReader reader = new JsonReader("./data/testCityRegular.json");
        try {
            City city = reader.read();
            School school = (School) city.getMap().getBuilding("8");
            checkBuilding("8", 10000, new Position(5, 7), school);
            checkSchool(500, 1000, school);
        } catch (IOException e) {
            fail("Failed to read building from existent file.");
        }
    }
}
