package ui;

import model.City;
import model.Event;
import model.EventLog;
import persistence.JsonReader;

import java.io.IOException;

/**
 * Main application class. Initializes a new city and editor.
 */
public class CitySimulator {
    public static final String DATA_PATH = "./data/city.json";
    public static final int STARTING_MONEY = 50000;

    /**
     * Start the program by creating and running a {@link Launcher}.
     */
    public CitySimulator() {
        Launcher launcher = new Launcher(this);
        launcher.run();
    }

    /**
     * Print the event log to the console.
     */
    public static void printLog() {
        for (Event e : EventLog.getInstance()) {
            System.out.println(e);
            System.out.println();
        }
    }

    // EFFECTS: load the city saved to a file and start the city editor

    /**
     * Create a new {@link City} and run a new {@link CityEditor}.
     *
     * @param name Name of the new city.
     */
    public void createNewCity(String name) {
        City city = new City(name);
        city.setMoney(STARTING_MONEY);
        CityEditor editor = new CityEditor(city);
        editor.run();
    }

    /**
     * Load the city saved to disk and run a new {@link CityEditor}
     *
     * @throws IOException If the JSON file at {@code DATA_PATH} cannot be parsed.
     */
    public void loadCity() throws IOException {
        JsonReader reader = new JsonReader(DATA_PATH);
        City city = reader.read();
        CityEditor editor = new CityEditor(city);
        editor.run();
    }
}
