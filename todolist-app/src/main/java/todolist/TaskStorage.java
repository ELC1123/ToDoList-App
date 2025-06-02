package todolist;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class TaskStorage {
    private static final Path FILE_PATH = Paths.get("tasks.json");
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, context) ->
                LocalDate.parse(json.getAsString()))
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
                new JsonPrimitive(src.toString()))
            .setPrettyPrinting()
            .create();

    public static void saveTasks(List<Task> tasks) {
        try(Writer writer = Files.newBufferedWriter(FILE_PATH)) {
            gson.toJson(tasks, writer);
            System.out.println("Tasks saved to: " + FILE_PATH.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Task> loadTasks() {
        if(!Files.exists(FILE_PATH)) {
            return new ArrayList<>();
        }
        try(Reader reader = Files.newBufferedReader(FILE_PATH)) {
            Type taskListType = new TypeToken<List<Task>>(){}.getType();
            return gson.fromJson(reader, taskListType);
        } catch(IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
