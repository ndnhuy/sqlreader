package sqlparser.parsetree2.tokenconsumer;

import org.junit.Test;
import sqlparser.Token;
import sqlparser.TokenType;
import sqlparser.Tokenizer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TokenConsumerTest {

    private TokenConsumer tokenConsumer = new TokenConsumer();
    private Tokenizer tokenizer = new Tokenizer();

    @Test
    public void testConsumeToken() {
        List<Token> tokens = Arrays.asList(
            new Token("SELECT", TokenType.STATEMENT),
            new Token("C1", TokenType.IDENTIFIER),
            new Token(",", TokenType.SEPARATOR),
            new Token("C2", TokenType.IDENTIFIER),
            new Token("FROM", TokenType.CLAUSE),
            new Token("T1", TokenType.IDENTIFIER),
            new Token(",", TokenType.SEPARATOR),
            new Token("T2", TokenType.IDENTIFIER),
            new Token("WHERE", TokenType.CLAUSE),
            new Token("C3", TokenType.IDENTIFIER),
            new Token("=", TokenType.COMPARISON_OPERATOR),
            new Token("C4", TokenType.IDENTIFIER),
            new Token("AND", TokenType.LOGICAL_OPERATOR),
            new Token("C5", TokenType.IDENTIFIER),
            new Token(">", TokenType.COMPARISON_OPERATOR),
            new Token("C6", TokenType.IDENTIFIER)
        );

        ASTNode node = tokenConsumer.build(tokens);
        System.out.println(node.print("|"));
    }

    @Test
    public void testConsumeToken2() {
        String sql = "SELECT\n" +
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
        ASTNode node = tokenConsumer.build(tokenizer.produceTokens(sql));
        System.out.println(new ASTNodePrinter(node).print());
    }
}
