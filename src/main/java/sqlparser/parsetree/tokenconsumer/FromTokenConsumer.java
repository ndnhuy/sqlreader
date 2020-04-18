package sqlparser.parsetree.tokenconsumer;

import sqlparser.Token;
import sqlparser.TokenType;
import sqlparser.parsetree.ExprNode;
import sqlparser.parsetree.NonTerminalExprNode;
import sqlparser.parsetree.TerminalExprNode;

import java.util.Iterator;

public class FromTokenConsumer implements TokenConsumer {

    private IdentifierListTokenConsumer identifierListTokenConsumer = new IdentifierListTokenConsumer();

    @Override
    public ExprNode build(LookForwardIterator<Token> tokenIterator) {
        NonTerminalExprNode nonTerminalExprNode = new NonTerminalExprNode();
        nonTerminalExprNode.add(new TerminalExprNode(TokenType.FROM, TokenType.FROM.code()));
        nonTerminalExprNode.add(identifierListTokenConsumer.build(tokenIterator));
        return nonTerminalExprNode;
    }
}
