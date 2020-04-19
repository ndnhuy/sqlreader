package sqlparser.parsetree.tokenconsumer;

import sqlparser.Token;
import sqlparser.TokenType;
import sqlparser.parsetree.ExprNode;
import sqlparser.parsetree.NonTerminalExprNode;
import sqlparser.parsetree.ParsingError;
import sqlparser.parsetree.TerminalExprNode;

import java.util.Iterator;

public class PredicateTokenConsumer implements TokenConsumer {

    private TokenConsumer expressionConsumer = new ExpressionConsumer();

    @Override
    public ExprNode build(LookForwardIterator<Token> tokenIterator) {
        Token nextToken = tokenIterator.lookForward();
        ExprNode exprNode;
        if (nextToken.getTokenType() == TokenType.LEFT_BRACKET) {
            tokenIterator.next();
            exprNode = this.build(tokenIterator);
            if (!tokenIterator.hasNext() || tokenIterator.next().getTokenType() != TokenType.RIGHT_BRACKET) {
                throw new IllegalArgumentException("Missing " + TokenType.RIGHT_BRACKET.code());
            }
        } else if (nextToken.getTokenType() == TokenType.IDENTIFIER) {
            exprNode = expressionConsumer.build(tokenIterator);
        } else {
            throw new IllegalArgumentException("Predicate cannot start with " + nextToken.getLexeme());
        }
        if (tokenIterator.hasNext()) {
            Token nextToken2 = tokenIterator.lookForward();
            if (TokenType.CONDITIONAL_OPERATOR == nextToken2.getTokenType()) {
                tokenIterator.next();
                NonTerminalExprNode condExprNode = new NonTerminalExprNode();
                condExprNode.add(exprNode);
                condExprNode.add(new TerminalExprNode(TokenType.CONDITIONAL_OPERATOR, nextToken2.getLexeme()));
                condExprNode.add(this.build(tokenIterator));
                return condExprNode;
            }
        }
        return exprNode;
    }
}
