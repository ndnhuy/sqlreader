package sqlparser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ScannerTest {
    @Test
    public void testScanner() {
        Scanner scanner = new Scanner();
        List<String> lexemes = scanner.produceLexemes("SELECT * FROM table_name1, table_name2 WHERE (a=b and x = y) or c = d or e = \"literal string 1, literal string 2\"");
        Assert.assertEquals(Arrays.asList(
                "SELECT",
                "*",
                "FROM",
                "table_name1",
                ",",
                "table_name2",
                "WHERE",
                "(",
                "a",
                "=",
                "b",
                "and",
                "x",
                "=",
                "y",
                ")",
                "or",
                "c",
                "=",
                "d",
                "or",
                "e",
                "=",
                "\"literal string 1, literal string 2\""),
                lexemes);
    }
}
