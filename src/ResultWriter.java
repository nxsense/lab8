import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ResultWriter {
    public static void write(String file, List<String> results) throws IOException {
        Files.write(Paths.get(file), results);
        results.forEach(System.out::println);
    }
}
