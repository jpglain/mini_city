package model;

import model.buildings.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SummaryTest {
    private Summary summary;

    @BeforeEach
    public void setup() {
        summary = new Summary("title", "description");
    }

    @Test
    public void testSetName() {
        summary.setName("another title");
        assertEquals("another title", summary.getName());
    }

    @Test
    public void testSetDescription() {
        summary.setDescription("another description");
        assertEquals("another description", summary.getDescription());
    }

    @Test
    public void testPutFields() {
        summary.put("n1", "d1");
        summary.put("n2", "d2");
        summary.put("n3", "d3");
        summary.put("n4", "d4");
        Map<String, String> fields = summary.getFields();
        assertEquals(4, fields.size());
        assertEquals("d1", fields.get("n1"));
        assertEquals("d2", fields.get("n2"));
        assertEquals("d3", fields.get("n3"));
        assertEquals("d4", fields.get("n4"));
    }

    @Test
    public void testCitySummary() {
        City city = new City("Vancouver");
        city.setPopulation(20000);
        city.setMoney(2000);
        city.setAttraction(1.5);
        city.setTaxRate(0.01);
        summary = city.getSummary();

        assertEquals("Vancouver", summary.getName());
        assertEquals("The city of Vancouver", summary.getDescription());

        Map<String, String> fields = summary.getFields();
        assertEquals("20000", fields.get("Population"));
        assertEquals("2000", fields.get("Money"));
        assertEquals("1.50", fields.get("Attraction"));
        assertEquals("1.00%", fields.get("Tax rate"));
        assertEquals("0", fields.get("Buildings"));
        assertEquals("0", fields.get("Worker capacity"));
        assertEquals("0", fields.get("Housing capacity"));
    }

    @Test
    public void testBusinessSummary() {
        Business business = new Business("B1", 2000, 40, Position.ORIGIN);
        business.setWorkers(20);
        summary = business.getSummary();
        checkBuildingSummary(business);
        Map<String, String> fields = summary.getFields();
        assertEquals("20", fields.get("Current workers"));
        assertEquals("40", fields.get("Worker capacity"));
    }

    @Test
    public void testFireHallSummary() {
        FireHall fireHall = new FireHall("F1", 6000, Position.ORIGIN);
        fireHall.setTrucks(10);
        summary = fireHall.getSummary();
        checkBuildingSummary(fireHall);
        Map<String, String> fields = summary.getFields();
        assertEquals("10", fields.get("Current trucks"));
    }

    @Test
    public void testHospitalSummary() {
        Hospital hospital = new Hospital("H1", 10000, Position.ORIGIN);
        hospital.setAmbulances(10);
        summary = hospital.getSummary();
        checkBuildingSummary(hospital);
        Map<String, String> fields = summary.getFields();
        assertEquals("10", fields.get("Current ambulances"));
    }

    @Test
    public void testHousingSummary() {
        Housing housing = new Housing("H1", 6000, 2000, Position.ORIGIN);
        housing.setResidents(200);
        summary = housing.getSummary();
        checkBuildingSummary(housing);
        Map<String, String> fields = summary.getFields();
        assertEquals("200", fields.get("Current residents"));
        assertEquals("2000", fields.get("Resident capacity"));
    }

    @Test
    public void testLandfillSummary() {
        Landfill landfill = new Landfill("L1", 6000, 10000, Position.ORIGIN);
        landfill.setTrucks(40);
        summary = landfill.getSummary();
        checkBuildingSummary(landfill);
        Map<String, String> fields = summary.getFields();
        assertEquals("40", fields.get("Current trucks"));
        assertEquals("10000", fields.get("Garbage capacity"));
    }

    @Test
    public void testParkSummary() {
        Park park = new Park("P1", 3000, 2.0, Position.ORIGIN);
        summary = park.getSummary();
        checkBuildingSummary(park);
        Map<String, String> fields = summary.getFields();
        assertEquals("2.0", fields.get("Value modifier"));
    }

    @Test
    public void testPowerPlantSummary() {
        PowerPlant powerPlant = new PowerPlant("P1", 8000, 1000, Position.ORIGIN);
        summary = powerPlant.getSummary();
        checkBuildingSummary(powerPlant);
        Map<String, String> fields = summary.getFields();
        assertEquals("1000", fields.get("Power production rate"));
    }

    @Test
    public void testSchoolSummary() {
        School school = new School("S1", 6000, 3000, Position.ORIGIN);
        school.setStudents(1000);
        summary = school.getSummary();
        checkBuildingSummary(school);
        Map<String, String> fields = summary.getFields();
        assertEquals("1000", fields.get("Enrolled students"));
        assertEquals("3000", fields.get("Student capacity"));
    }

    // EFFECTS: check that a summary for building has the correct name and a description
    private void checkBuildingSummary(Building b) {
        Map<String, String> fields = summary.getFields();
        assertEquals(b.getName(), summary.getName());
        assertEquals(String.valueOf(b.getValue()), fields.get("Property value"));
        assertNotNull(summary.getDescription());
    }
}
