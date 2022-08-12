package persistence;

import model.City;
import model.Map;
import model.Position;
import model.buildings.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    private City city;

    @BeforeEach
    public void setup() {
        city = new City("Tokyo 2");
        city.setPopulation(5000);
        city.setMoney(1000);
        city.setTaxRate(0.1);
    }

    @Test
    public void testSaveInvalidFile() {
        JsonWriter writer = new JsonWriter("./data/::?*<>");
        try {
            writer.open();
            fail("open() should have failed with IOException.");
        } catch (FileNotFoundException e) {
            // pass test
        }
    }

    @Test
    public void testSaveEmpty() {
        JsonWriter writer = new JsonWriter("./data/testSaveEmpty.json");
        try {
            writer.writeAndClose(city);
            JsonReader reader = new JsonReader("./data/testSaveEmpty.json");
            city = reader.read();

            assertEquals("Tokyo 2", city.getName());
            assertEquals(5000, city.getPopulation());
            assertEquals(1000, city.getMoney());
            assertEquals(1.0, city.getAttraction());
            assertEquals(0.1, city.getTaxRate());
            assertEquals(0, city.getMap().getNumOfBuildings());
        } catch (IOException e) {
            fail("IOException thrown when not expected to");
        }
    }

    @Test
    public void testSaveRegularCity() {
        addBuildings();
        saveAndLoad();

        assertEquals("Tokyo 2", city.getName());
        assertEquals(5000, city.getPopulation());
        assertEquals(1000, city.getMoney());
        assertEquals(1.0, city.getAttraction());
        assertEquals(0.1, city.getTaxRate());
        assertEquals(8, city.getMap().getNumOfBuildings());
    }

    @Test
    public void testSaveRegularBusiness() {
        addBuildings();
        saveAndLoad();

        Business business = (Business) city.getMap().getBuilding("1");
        checkBuilding("1", 3000, new Position(0, 5), business);
        checkBusiness(30, 200, business);
    }

    @Test
    public void testSaveRegularFireHall() {
        addBuildings();
        saveAndLoad();

        FireHall fireHall = (FireHall) city.getMap().getBuilding("2");
        checkBuilding("2", 6000, new Position(16, 4), fireHall);
        checkFireHall(50, fireHall);
    }

    @Test
    public void testSaveRegularHospital() {
        addBuildings();
        saveAndLoad();

        Hospital hospital = (Hospital) city.getMap().getBuilding("3");
        checkBuilding("3", 10000, new Position(10, 5), hospital);
        checkHospital(40, hospital);
    }

    @Test
    public void testSaveRegularHousing() {
        addBuildings();
        saveAndLoad();

        Housing housing = (Housing) city.getMap().getBuilding("4");
        checkBuilding("4", 2000, new Position(7, 2), housing);
        checkHousing(500, 2000, housing);
    }

    @Test
    public void testSaveRegularLandfill() {
        addBuildings();
        saveAndLoad();

        Landfill landFill = (Landfill) city.getMap().getBuilding("5");
        checkBuilding("5", 5000, new Position(2, 10), landFill);
        checkLandfill(30, 1000, landFill);
    }

    @Test
    public void testSaveRegularPark() {
        addBuildings();
        saveAndLoad();

        Park park = (Park) city.getMap().getBuilding("6");
        checkBuilding("6", 2000, new Position(1, 2), park);
        checkPark(1.0, park);
    }

    @Test
    public void testSaveRegularPowerPlant() {
        addBuildings();
        saveAndLoad();

        PowerPlant powerPlant = (PowerPlant) city.getMap().getBuilding("7");
        checkBuilding("7", 8000, new Position(2, 3), powerPlant);
        checkPowerPlant(300, powerPlant);
    }

    @Test
    public void testSaveRegularSchool() {
        addBuildings();
        saveAndLoad();

        School school = (School) city.getMap().getBuilding("8");
        checkBuilding("8", 20000, new Position(4, 5), school);
        checkSchool(400, 2000, school);
    }

    // MODIFIES: this
    // EFFECTS: saves the current city and loads it back in
    private void saveAndLoad() {
        JsonWriter writer = new JsonWriter("./data/testSaveRegular.json");
        try {
            writer.writeAndClose(city);
            JsonReader reader = new JsonReader("./data/testSaveRegular.json");
            city = reader.read();
        } catch (IOException e) {
            fail("IOException thrown when not expected");
        }
    }

    // MODIFIES: this
    // EFFECTS: add a building of every type to map
    private void addBuildings() {
        Map map = city.getMap();

        Business business = new Business("1", 3000, 200, new Position(0, 5));
        business.setWorkers(30);
        map.addBuilding(business);

        FireHall fireHall = new FireHall("2", 6000, new Position(16, 4));
        fireHall.setTrucks(50);
        map.addBuilding(fireHall);

        Hospital hospital = new Hospital("3", 10000, new Position(10, 5));
        hospital.setAmbulances(40);
        map.addBuilding(hospital);

        Housing housing = new Housing("4", 2000, 2000, new Position(7, 2));
        housing.setResidents(500);
        map.addBuilding(housing);

        Landfill landfill = new Landfill("5", 5000, 1000, new Position(2, 10));
        landfill.setTrucks(30);
        map.addBuilding(landfill);

        Park park = new Park("6", 2000, 1.0, new Position(1, 2));
        map.addBuilding(park);

        PowerPlant powerPlant = new PowerPlant("7", 8000, 300, new Position(2, 3));
        map.addBuilding(powerPlant);

        School school = new School("8", 20000, 2000, new Position(4, 5));
        school.setStudents(400);
        map.addBuilding(school);
    }
}
