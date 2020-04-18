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
}
