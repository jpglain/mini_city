package model;

import java.util.Map;
import java.util.TreeMap;

/**
 * A summary is a structured data object that contains
 * descriptions that describe an entity, such as a building.
 */
public class Summary {
    private final Map<String, String> fields;
    private String name;
    private String description;

    /**
     * Create a new summary.
     *
     * @param name        The name of the summary.
     * @param description The description of the summary.
     */
    public Summary(String name, String description) {
        this.name = name;
        this.description = description;
        this.fields = new TreeMap<>();
    }

    /**
     * Add a field's name and description to the summary.
     *
     * @param name        The field's name.
     * @param description The field's description.
     */
    public void put(String name, String description) {
        fields.put(name, description);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String summary) {
        this.description = summary;
    }

    public Map<String, String> getFields() {
        return fields;
    }
}
