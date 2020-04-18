package sqlparser.parsetree.tokenconsumer;

import sqlparser.Token;
import sqlparser.TokenType;
import sqlparser.parsetree.ExprNode;
import sqlparser.parsetree.NonTerminalExprNode;
import sqlparser.parsetree.TerminalExprNode;

public class SelectTokenConsumer implements TokenConsumer {

    private IdentifierListTokenConsumer identifierListTokenConsumer = new IdentifierListTokenConsumer();

    @Override
    public ExprNode build(LookForwardIterator<Token> tokenIterator) {
        NonTerminalExprNode nonTerminalExprNode = new NonTerminalExprNode();
        nonTerminalExprNode.add(new TerminalExprNode(TokenType.SELECT, TokenType.SELECT.code()));
        nonTerminalExprNode.add(identifierListTokenConsumer.build(tokenIterator));
        return nonTerminalExprNode;
    }
}
