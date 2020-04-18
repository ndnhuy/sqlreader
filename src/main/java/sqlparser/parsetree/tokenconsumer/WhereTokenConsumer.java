package sqlparser.parsetree.tokenconsumer;

import sqlparser.Token;
import sqlparser.TokenType;
import sqlparser.parsetree.ExprNode;
import sqlparser.parsetree.NonTerminalExprNode;
import sqlparser.parsetree.TerminalExprNode;

import java.util.Iterator;

public class WhereTokenConsumer implements TokenConsumer {

    private PredicateTokenConsumer predicateTokenConsumer = new PredicateTokenConsumer();

    @Override
    public ExprNode build(LookForwardIterator<Token> tokenIterator) {
        NonTerminalExprNode nonTerminalExprNode = new NonTerminalExprNode();
        nonTerminalExprNode.add(new TerminalExprNode(TokenType.WHERE, TokenType.WHERE.code()));
        nonTerminalExprNode.add(predicateTokenConsumer.build(tokenIterator));
        return nonTerminalExprNode;
    }
}
