import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DirectoryProcessorTest {
    private static final String TEST_DIRECTORY = "testDir";
    private static final String OUTPUT_FILE = "results.txt";

    @BeforeAll
    static void setup() throws IOException {
        Files.createDirectory(Paths.get(TEST_DIRECTORY));
        Files.writeString(Paths.get(TEST_DIRECTORY, "file1.txt"), "apple banana apricot");
        Files.writeString(Paths.get(TEST_DIRECTORY, "file2.txt"), "Apple aardvark ace apple");
        Files.createDirectory(Paths.get(TEST_DIRECTORY, "subDir"));
        Files.writeString(Paths.get(TEST_DIRECTORY, "subDir", "file3.txt"), "banana boat ball");
    }

    @AfterAll
    static void cleanup() throws IOException {
        Files.walk(Paths.get(TEST_DIRECTORY))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        Files.deleteIfExists(Paths.get(OUTPUT_FILE));
    }

    @Test
    void testDirectoryProcessing() throws Exception {
        DirectoryProcessor processor = new DirectoryProcessor(TEST_DIRECTORY, 'a', OUTPUT_FILE);
        processor.process();

        List<String> results = Files.readAllLines(Paths.get(OUTPUT_FILE));
        assertEquals(3, results.size()); // Перевірка кількості унікальних файлів
        assertTrue(results.stream().anyMatch(line -> line.contains("file1.txt : 2")));
        assertTrue(results.stream().anyMatch(line -> line.contains("file2.txt : 4")));
        assertTrue(results.stream().anyMatch(line -> line.contains("file3.txt : 0")));
    }

}

