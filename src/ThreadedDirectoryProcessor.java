import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

public class ThreadedDirectoryProcessor implements Runnable{

    private String directory;
    private char targetLetter;
    private List<String> results;
    private ExecutorService executor;
    private Set<String> processed = Collections.synchronizedSet(new HashSet<>());

    public ThreadedDirectoryProcessor(String directory, char targetLetter, List<String> results, ExecutorService executor) {
        this.directory = directory;
        this.targetLetter = targetLetter;
        this.results = results;
        this.executor = executor;
    }

    @Override
    public void run() {
        try (Stream<Path> paths = Files.list(Paths.get(directory))) {
            paths.forEach(path -> {
                if (Files.isDirectory(path)) {
                    executor.submit(new ThreadedDirectoryProcessor(path.toString(), targetLetter, results, executor));
                } else if (path.toString().endsWith(".txt")) {
                    if (processed.add(path.toString())) { // Перевірка на унікальність файлу
                        int wordCount = FileProcessor.countWordsStartingWith(path.toString(), targetLetter);
                        results.add(path + " : " + wordCount);
                    }
                }
            });
        } catch (IOException e) {
            System.out.println("Error processing : " + directory);
        }
    }

}
