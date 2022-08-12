package persistence;

import model.Position;
import model.buildings.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Procedures for comparing buildings in json tests
public class JsonTest {
    // EFFECTS: check if two buildings are equal
    protected void checkBuilding(String name, int value, Position position, Building actual) {
        assertEquals(name, actual.getName());
        assertEquals(value, actual.getValue());
        assertEquals(position, actual.getPosition());
    }

    // EFFECTS: check if two businesses are equal
    protected void checkBusiness(int workers, int workerCapacity, Business actual) {
        assertEquals(workers, actual.getWorkers());
        assertEquals(workerCapacity, actual.getWorkerCapacity());
    }

    // EFFECTS: check if two fire halls are equal
    protected void checkFireHall(int trucks, FireHall actual) {
        assertEquals(trucks, actual.getTrucks());
    }

    // EFFECTS: check if two hospitals are equal
    protected void checkHospital(int ambulances, Hospital actual) {
        assertEquals(ambulances, actual.getAmbulances());
    }

    // EFFECTS: check if two housing objects are equal
    protected void checkHousing(int residents, int residentCapacity, Housing actual) {
        assertEquals(residents, actual.getResidents());
        assertEquals(residentCapacity, actual.getResidentCapacity());
    }

    // EFFECTS: check if two landfills are equal
    protected void checkLandfill(int trucks, int capacity, Landfill actual) {
        assertEquals(trucks, actual.getTrucks());
        assertEquals(capacity, actual.getCapacity());
    }

    // EFFECTS: check if two parks are equal
    protected void checkPark(double valueModifier, Park actual) {
        assertEquals(valueModifier, actual.getValueModifier());
    }

    // EFFECTS: check if two power plants are equal
    protected void checkPowerPlant(int productionRate, PowerPlant actual) {
        assertEquals(productionRate, actual.getProductionRate());
    }

    // EFFECTS: check if two schools are equal
    protected void checkSchool(int students, int studentCapacity, School actual) {
        assertEquals(students, actual.getStudents());
        assertEquals(studentCapacity, actual.getStudentCapacity());
    }
}
