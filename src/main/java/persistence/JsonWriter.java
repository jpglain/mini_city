package persistence;

import model.City;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * JSOn writer that writes {@link City} data to a JSON file.
 * partially modelled around JsonWriter class from the
 * <a href="https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo">JsonSerializationDemo</a>
 */
public class JsonWriter {
    private static final int TAB = 4;
    private final String destination;
    private PrintWriter writer;

    /**
     * Creates a new writer.
     *
     * @param destination The destination path to write the file to.
     */
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    /**
     * Opens the writer.
     *
     * @throws FileNotFoundException The destination file cannot be opened.
     */
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    /**
     * Writes city data (converted to json) to the destination file.
     */
    public void write(City city) {
        JSONObject json = city.toJson();
        saveToFile(json.toString(TAB));
    }

    /**
     * Closes the writer.
     */
    public void close() {
        writer.close();
    }

    /**
     * Writes a string to file.
     *
     * @param json The JSON string to write to the writer's destination file.
     */
    private void saveToFile(String json) {
        writer.print(json);
    }

    /**
     * Convenience method to open writer,
     * write city data to destination file, and close writer
     *
     * @param city The {@link City} whose data will be written to the
     *             writer's destination file.
     * @throws FileNotFoundException The destination file cannot be opened.
     */
    public void writeAndClose(City city) throws FileNotFoundException {
        open();
        write(city);
        close();
    }
}
