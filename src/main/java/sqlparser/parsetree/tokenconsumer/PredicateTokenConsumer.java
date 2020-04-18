package sqlparser.parsetree.tokenconsumer;

import sqlparser.Token;
import sqlparser.TokenType;
import sqlparser.parsetree.ExprNode;
import sqlparser.parsetree.NonTerminalExprNode;
import sqlparser.parsetree.ParsingError;
import sqlparser.parsetree.TerminalExprNode;

import java.util.Iterator;

public class PredicateTokenConsumer implements TokenConsumer {

    TokenConsumer expressionConsumer = new ExpressionConsumer();

    @Override
    public ExprNode build(LookForwardIterator<Token> tokenIterator) {
        NonTerminalExprNode nonTerminalExprNode = new NonTerminalExprNode();
        nonTerminalExprNode.add(expressionConsumer.build(tokenIterator));

    }
}
