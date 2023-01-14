import org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MostActiveCookieTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private File testFile;

    @Before
    public void setUp() throws IOException {
        // Redirect System.out and System.err
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        // Create a test file
        testFile = new File("cookie_log.csv");
    }

    @After
    public void tearDown() {
        // Reset System.out and System.err
        System.setOut(originalOut);
        System.setErr(originalErr);

        // Delete the test file
        testFile.delete();
    }

    @Test
    public void testOneCookieResult() {
        String[] args = {"cookie_log.csv","-d","2018-12-09"};
        MostActiveCookie.main(args);
        String expectedOutput = "AtY0laUfhglK3lC7";
        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    public void testMultipleCookieResult() {
        String[] args = {"cookie_log.csv","-d","2018-12-08"};
        MostActiveCookie.main(args);
        String actualOutput = outContent.toString().trim();
        String[] cookies = Arrays.sort(actualOutput.split("\n"));
        assertEquals(cookies[0], "4sMM2LxV07bPJzwf");
        assertEquals(cookies[1], "fbcn5UAVanZf6UtG");
        assertEquals(cookies[2], "SAZuXPGUrfbcn5UA");
    }

    @Test
    public void testNoCookieResult() {
        String[] args = {"cookie_log.csv","-d","2018-12-06"};
        MostActiveCookie.main(args);
        String expectedOutput = "";
        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    public void testWrongNumOfArgs() {
        String[] args = {"cookie_log.csv","-d"};
        MostActiveCookie.main(args);
        String expectedOutput = "Please call the program again with the correct number of arguments: ./most_active_cookie log_file_name.csv -d YYYY-MM-DD";
        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    public void testWrongDateFormat() {
        String[] args = {"cookie_log.csv","-d", "12-06-2018"};
        MostActiveCookie.main(args);
        String expectedOutput = "Please call the program again with the correct date format: ./most_active_cookie log_file_name.csv -d YYYY-MM-DD";
        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    public void testNonexistentFile() {
        String[] args = {"fakefile.csv","-d", "2018-12-06"};
        MostActiveCookie.main(args);
        String expectedOutput = "Your log file is currently inaccessible. Make sure that you are inputting the correct file name and path to access the cookie log file.";
        assertEquals(expectedOutput, outContent.toString().trim());
    }
}
