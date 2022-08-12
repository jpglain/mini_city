package persistence;

import org.json.JSONObject;

/**
 * JSON writable interface for objects.
 * Adapted from the <a href="https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo">JsonSerializationDemo</a>.
 */
public interface Writable {
    /**
     * Returns the JSON representation of this object.
     *
     * @return The {@link JSONObject} representing this object.
     */
    JSONObject toJson();
}
