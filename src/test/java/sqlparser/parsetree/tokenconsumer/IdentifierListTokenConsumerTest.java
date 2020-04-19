package sqlparser.parsetree.tokenconsumer;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import sqlparser.Token;
import sqlparser.TokenType;
import sqlparser.parsetree.ExprNode;
import sqlparser.parsetree.NonTerminalExprNode;
import sqlparser.parsetree.ParsingError;
import sqlparser.parsetree.TerminalExprNode;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class IdentifierListTokenConsumerTest {

    private static String SEPARATOR = " ";

    @Test
    public void testIdentifierList_shouldSuccess() {
        IdentifierListTokenConsumer tokenConsumer = new IdentifierListTokenConsumer();
        List<Token> tokens = Arrays.asList(
                new Token("A", TokenType.IDENTIFIER),
                new Token(",", TokenType.SEPARATOR),
                new Token("B", TokenType.IDENTIFIER)
        );
        ExprNode node = tokenConsumer.build(new LookForwardIterator<>(tokens.iterator()));
        System.out.println(node.print(SEPARATOR));
        Assert.assertEquals("A , B", node.print(SEPARATOR));
    }

    @Test(expected=ParsingError.class)
    public void testIdentifierList_invalidList_shouldThrowException() {
        IdentifierListTokenConsumer tokenConsumer = new IdentifierListTokenConsumer();
        List<Token> tokens = Arrays.asList(
                new Token("A", TokenType.IDENTIFIER),
                new Token(",", TokenType.SEPARATOR),
                new Token("B", TokenType.IDENTIFIER),
                new Token(",", TokenType.SEPARATOR)
        );
        ExprNode node = tokenConsumer.build(new LookForwardIterator<>(tokens.iterator()));
        System.out.println(node.print(" "));
    }

    @Test(expected=ParsingError.class)
    public void testIdentifierList_invalidList2_shouldThrowException() {
        IdentifierListTokenConsumer tokenConsumer = new IdentifierListTokenConsumer();
        List<Token> tokens = Arrays.asList(
                new Token("A", TokenType.IDENTIFIER),
                new Token(",", TokenType.SEPARATOR),
                new Token(",", TokenType.SEPARATOR),
                new Token("B", TokenType.IDENTIFIER)
        );
        ExprNode node = tokenConsumer.build(new LookForwardIterator<>(tokens.iterator()));
        System.out.println(node.print(" "));
    }

    @Test
    public void testIdentifierList_invalidList3_shouldThrowException() {
        IdentifierListTokenConsumer tokenConsumer = new IdentifierListTokenConsumer();
        List<Token> tokens = Arrays.asList(
                new Token("A", TokenType.IDENTIFIER),
                new Token("B", TokenType.IDENTIFIER)
        );
        ExprNode node = tokenConsumer.build(new LookForwardIterator<>(tokens.iterator()));
        System.out.println(node.print(SEPARATOR));
        Assert.assertEquals("(A)", node.print(SEPARATOR));
    }
}
