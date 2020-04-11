package sqlparser;

import org.junit.Test;

import java.io.*;
import java.util.List;

public class TokenizerTest {
    @Test
    public void testProduceTokens() {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.produceTokens("SELECT * FROM table_name1, table_name2 WHERE (a1 = b1 and x = y) or c = d or e = \"literal string 1, literal string 2\"");
        for (Token t : tokens) {
            System.out.println(t);
        }
    }

    @Test
    public void foo() throws IOException {
        FileInputStream is = new FileInputStream("/Users/administrator/Documents/Daily Support/MCC7012561200406.txt");
        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = bf.readLine()) != null) {
            if (line.contains("\"18514\",\"4010070125610")) {

                System.out.println(line);
            }
        }
    }
}
