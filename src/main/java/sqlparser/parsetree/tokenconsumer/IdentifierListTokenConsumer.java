package sqlparser.parsetree.tokenconsumer;

import sqlparser.Token;
import sqlparser.TokenType;
import sqlparser.parsetree.ExprNode;
import sqlparser.parsetree.NonTerminalExprNode;
import sqlparser.parsetree.ParsingError;
import sqlparser.parsetree.TerminalExprNode;

public class IdentifierListTokenConsumer implements TokenConsumer {
    @Override
    public ExprNode build(LookForwardIterator<Token> tokenIterator) {
        NonTerminalExprNode identifierList = new NonTerminalExprNode();
        TokenType nextTokenType = TokenType.IDENTIFIER;
        while (tokenIterator.hasNext()) {
            Token nextToken = tokenIterator.lookForward();
            if (nextTokenType == TokenType.SEPARATOR) {
                if (nextToken.getTokenType() != nextTokenType) {
                    return identifierList;
                }
            }
            if (nextToken.getTokenType() != nextTokenType) {
                throw new ParsingError("Must be " + nextTokenType);
            } else if (nextTokenType == TokenType.SEPARATOR) {
                if (!",".equals(nextToken.getLexeme())) {
                    throw new ParsingError("Separator must be ,");
                }
            }
            Token currToken = tokenIterator.next();
            TerminalExprNode tokenNode = new TerminalExprNode(currToken.getTokenType(), currToken.getLexeme());
            identifierList.add(tokenNode);
            if (nextTokenType == TokenType.IDENTIFIER) {
                nextTokenType = TokenType.SEPARATOR;
            } else {
                nextTokenType = TokenType.IDENTIFIER;
            }
        }
        return identifierList;
    }
}
