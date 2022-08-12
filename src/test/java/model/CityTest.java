package model;

import model.buildings.Business;
import model.buildings.Hospital;
import model.buildings.Housing;
import model.buildings.PowerPlant;
import model.exceptions.InsufficientMoneyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CityTest {
    private City city;

    @BeforeEach
    public void setup() {
        city = new City("Tokyo 2");
    }

    @Test
    public void testUpdatePopulation() {
        city.getMap().addBuilding(new Housing("H1", 2000, 200, Position.ORIGIN));
        city.updateHousingCapacity();
        int expectedPopulation = city.getPopulation() + (int) (City.BASE_POPULATION_INCREASE * city.getAttraction());
        city.updatePopulation();
        assertEquals(expectedPopulation, city.getPopulation());
    }

    @Test
    public void testUpdatePopulationZero() {
        city.setAttraction(-10);
        city.updatePopulation();
        assertEquals(0, city.getPopulation());
    }

    @Test
    public void testUpdatePopulationOverCapacity() {
        city.getMap().addBuilding(new Housing("H1", 2000, 200, Position.ORIGIN));
        city.updateHousingCapacity();
        city.setPopulation(200);
        city.updatePopulation();
        assertEquals(200, city.getPopulation());
    }

    @Test
    public void testGetName() {
        assertEquals("Tokyo 2", city.getName());
    }

    @Test
    public void testSetName() {
        city.setName("Vancouver");
        assertEquals("Vancouver", city.getName());
    }

    @Test
    public void testGetHousingCapacity() {
        city.getMap().addBuilding(new Housing("1", 100, 5, Position.ORIGIN));
        city.getMap().addBuilding(new Housing("2", 100, 2, Position.ORIGIN));
        city.getMap().addBuilding(new Housing("3", 100, 8, Position.ORIGIN));
        assertEquals(15, city.updateHousingCapacity());
    }

    @Test
    public void testGetWorkerCapacity() {
        city.getMap().addBuilding(new Business("1", 100, 5, Position.ORIGIN));
        city.getMap().addBuilding(new Business("2", 100, 3, Position.ORIGIN));
        city.getMap().addBuilding(new Business("3", 100, 9, Position.ORIGIN));
        assertEquals(17, city.updateWorkerCapacity());
    }

    @Test
    public void testUpdateMoneyNone() {
        city.updateMoney();
        assertEquals(0, city.getMoney());
    }

    @Test
    public void testUpdateMoneyRegular() {
        city.getMap().addBuilding(new Business("1", 200, 5, Position.ORIGIN));
        city.getMap().addBuilding(new Housing("2", 100, 50, Position.ORIGIN));
        int expected = (int) (city.getMoney() + (200 * city.getTaxRate()) + (100 * city.getTaxRate()));
        city.updateMoney();
        assertEquals(expected, city.getMoney());
    }

    @Test
    public void testBuyBuildingSuccess() {
        city.setMoney(2000);
        try {
            city.buyBuilding(new Hospital("H1", 2000, Position.ORIGIN));
            assertEquals(0, city.getMoney());
            assertEquals(1, city.getMap().getNumOfBuildings());
        } catch (InsufficientMoneyException e) {
            fail("Did not expect exception");
        }
    }

    @Test
    public void testBuyBuildingFail() {
        city.setMoney(2000);
        try {
            city.buyBuilding(new PowerPlant("P1", 2001, 200, Position.ORIGIN));
            fail("Method should have thrown an exception");
        } catch (InsufficientMoneyException e) {
            // pass test
        }
    }

    @Test
    public void testSetTaxRate() {
        city.setTaxRate(0.10);
        assertEquals(0.10, city.getTaxRate());
    }
}
