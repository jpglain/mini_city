package model.buildings;

import model.Position;
import model.Summary;
import org.json.JSONObject;

/**
 * A class representing a business building that has workers.
 */
public class Business extends Building {
    private final int workerCapacity;
    private int workers;

    /**
     * Create a new business.
     *
     * @param name           The name of the business.
     * @param value          The initial value of the business.
     * @param workerCapacity The initial worker capacity of the business.
     * @param position       The position of the business.
     */
    public Business(String name, int value, int workerCapacity, Position position) {
        super(name, value, position);
        type = BuildingType.BUSINESS;
        this.workerCapacity = workerCapacity;
        this.workers = 0;
    }

    /**
     * Create a new business object from building json data.
     *
     * @param json The json object to parse from.
     */
    public static Business fromJson(JSONObject json) {
        String name = json.getString("name");
        int value = json.getInt("value");
        int capacity = json.getInt("capacity");
        Business business = new Business(name, value, capacity, positionFromJson(json));
        business.setWorkers(json.getInt("workers"));
        return business;
    }

    public int getWorkers() {
        return workers;
    }

    public void setWorkers(int workers) {
        this.workers = workers;
    }

    public int getWorkerCapacity() {
        return workerCapacity;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = super.toJson();
        jsonObject.put("workers", workers);
        jsonObject.put("capacity", getWorkerCapacity());
        return jsonObject;
    }

    @Override
    public Summary getSummary() {
        Summary summary = super.getSummary();
        summary.setDescription("A business that has workers.");
        summary.put("Current workers", String.valueOf(getWorkers()));
        summary.put("Worker capacity", String.valueOf(getWorkerCapacity()));
        return summary;
    }
}
