package model.buildings;

import model.Position;
import model.Summary;
import org.json.JSONObject;

/**
 * Represents a school with students.
 */
public class School extends Building {
    private final int studentCapacity;
    private int students;

    /**
     * Create a new school.
     *
     * @param name            The name of the new building.
     * @param value           The initial value of new the building.
     * @param studentCapacity The initial student capacity.
     * @param position        The position of the new building.
     */
    public School(String name, int value, int studentCapacity, Position position) {
        super(name, value, position);
        type = BuildingType.SCHOOL;

        this.students = 0;
        this.studentCapacity = studentCapacity;
    }

    // EFFECTS: creates a new school object from building json data
    public static School fromJson(JSONObject json) {
        String name = json.getString("name");
        int value = json.getInt("value");
        int capacity = json.getInt("capacity");
        School school = new School(name, value, capacity, positionFromJson(json));
        school.setStudents(json.getInt("students"));
        return school;
    }

    public int getStudents() {
        return students;
    }

    public void setStudents(int students) {
        this.students = students;
    }

    public int getStudentCapacity() {
        return studentCapacity;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson();
        jsonObject.put("students", getStudents());
        jsonObject.put("capacity", getStudentCapacity());
        return jsonObject;
    }

    @Override
    public Summary getSummary() {
        Summary summary = super.getSummary();
        summary.setDescription("A school educates your citizens and increases happiness.");
        summary.put("Enrolled students", String.valueOf(getStudents()));
        summary.put("Student capacity", String.valueOf(getStudentCapacity()));
        return summary;
    }
}
