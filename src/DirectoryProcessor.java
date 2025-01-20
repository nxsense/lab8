import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class DirectoryProcessor {
    private String root;
    private char targetLetter;
    private String outputFile;
    private ExecutorService executor = Executors.newCachedThreadPool();
    private List<String> results = Collections.synchronizedList(new ArrayList<>());

    public DirectoryProcessor(String root, char targetLetter, String outputFile) {
        this.root = root;
        this.targetLetter = targetLetter;
        this.outputFile = outputFile;
    }

    public void process() throws ExecutionException, InterruptedException, IOException {
        executor.submit(new ThreadedDirectoryProcessor(root, targetLetter, results, executor)).get();
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
        ResultWriter.write(outputFile, results);
    }

}
