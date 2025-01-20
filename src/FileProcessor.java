import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class FileProcessor {
    public static int countWordsStartingWith(String fileName, char letter) {
        try(Stream<String> lines = Files.lines(Paths.get(fileName))) {
            return (int) lines.flatMap(line -> Arrays.stream(line.split("\\W+")))
                    .filter(word -> !word.isEmpty() && Character.toLowerCase(word.charAt(0)) == Character.toLowerCase(letter))
                    .count();
        } catch (IOException e) {
            System.out.println("Error reading file " + fileName);
            return 0;
        }
    }
}
