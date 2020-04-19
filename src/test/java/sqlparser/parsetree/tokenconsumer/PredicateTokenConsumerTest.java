package sqlparser.parsetree.tokenconsumer;

import org.junit.Assert;
import org.junit.Test;
import sqlparser.Token;
import sqlparser.TokenType;
import sqlparser.parsetree.ExprNode;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PredicateTokenConsumerTest {

    @Test
    public void testSimpleExpr() {
        PredicateTokenConsumer tokenConsumer = new PredicateTokenConsumer();
        // A = B AND C = D
        List<Token> tokens = Stream.of(
                newExpression("A", "=", "B"),
                newConditionalOp("AND"),
                newExpression("C", "=", "D")
        ).flatMap(Function.identity()).collect(Collectors.toList());

        ExprNode node = tokenConsumer.build(new LookForwardIterator<>(tokens.iterator()));
        System.out.println(node);
        Assert.assertEquals("((A,=,B),AND,(C,=,D))", node.toString());
        Assert.assertEquals(2, node.height());
    }

    @Test
    public void testSimpleExpr2() {
        PredicateTokenConsumer tokenConsumer = new PredicateTokenConsumer();
        // A = B AND C = D OR E = F
        List<Token> tokens = Stream.of(
                newExpression("A", "=", "B"),
                newConditionalOp("AND"),
                newExpression("C", "=", "D"),
                newConditionalOp("OR"),
                newExpression("E", "=", "F")
        ).flatMap(Function.identity()).collect(Collectors.toList());

        ExprNode node = tokenConsumer.build(new LookForwardIterator<>(tokens.iterator()));
        System.out.println(node);
        Assert.assertEquals("((A,=,B),AND,((C,=,D),OR,(E,=,F)))", node.toString());
        Assert.assertEquals(3, node.height());
    }

    @Test
    public void testSimpleExpr3() {
        PredicateTokenConsumer tokenConsumer = new PredicateTokenConsumer();
        // X = Y AND (A = B AND C = D OR E = F)
        List<Token> tokens = Stream.of(
                newExpression("X", "=", "Y"),
                newConditionalOp("AND"),
                newLeftBracket(),
                newExpression("A", "=", "B"),
                newConditionalOp("AND"),
                newExpression("C", "=", "D"),
                newConditionalOp("OR"),
                newExpression("E", "=", "F"),
                newRightBracket()
                ).flatMap(Function.identity()).collect(Collectors.toList());

        ExprNode node = tokenConsumer.build(new LookForwardIterator<>(tokens.iterator()));
        System.out.println(node);
        Assert.assertEquals("((X,=,Y),AND,((A,=,B),AND,((C,=,D),OR,(E,=,F))))", node.toString());
        Assert.assertEquals(4, node.height());
    }

    @Test
    public void testSimpleExpr4() {
        PredicateTokenConsumer tokenConsumer = new PredicateTokenConsumer();
        // (X = Y AND A = B) AND (C = D OR E = F)
        List<Token> tokens = Stream.of(
                newLeftBracket(),
                newExpression("X", "=", "Y"),
                newConditionalOp("AND"),
                newExpression("A", "=", "B"),
                newRightBracket(),
                newConditionalOp("AND"),
                newExpression("C", ">", "D"),
                newConditionalOp("OR"),
                newExpression("E", "=", "F")
        ).flatMap(Function.identity()).collect(Collectors.toList());

        ExprNode node = tokenConsumer.build(new LookForwardIterator<>(tokens.iterator()));
        System.out.println(node);
        Assert.assertEquals("(((X,=,Y),AND,(A,=,B)),AND,((C,>,D),OR,(E,=,F)))", node.toString());
        Assert.assertEquals(3, node.height());
    }

    private Stream<Token> newConditionalOp(String a) {
        return Stream.of(new Token(a, TokenType.CONDITIONAL_OPERATOR));
    }

    private Stream<Token> newLeftBracket() {
        return Stream.of(new Token("(", TokenType.LEFT_BRACKET));
    }

    private Stream<Token> newRightBracket() {
        return Stream.of(new Token(")", TokenType.RIGHT_BRACKET));
    }

    private Stream<Token> newExpression(String a, String op, String b) {
        return Stream.of(new Token(a, TokenType.IDENTIFIER),
                new Token(op, TokenType.EXPRESSION_OPERATOR),
                new Token(b, TokenType.LITERAL));
    }
}
