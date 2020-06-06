package sqlparser;

import org.junit.Test;

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
    public void testProduceTokens2() {
        String source = "SELECT\n" +
                "   l_orderkey,\n" +
                "   abc,\n" +
                "   o_orderdate,\n" +
                "   o_shippriority\n" +
                "FROM\n" +
                "   customer,\n" +
                "   orders,\n" +
                "   lineitem\n" +
                "WHERE\n" +
                "  c_mktsegment = 'FURNITURE' AND\n" +
                "  c_custkey = o_custkey AND\n" +
                "  l_orderkey = o_orderkey AND\n" +
                "  o_orderdate < 2013-12-21 AND\n" +
                "  l_shipdate > 2014-01-06\n" +
                "GROUP BY\n" +
                "  l_orderkey,\n" +
                "  o_orderdate,\n" +
                "  o_shippriority\n" +
                "ORDER BY\n" +
                "  revenue,\n" +
                "  o_orderdate;";
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.produceTokens(source);
        for (Token t : tokens) {
            System.out.println(t);
        }
    }
}
