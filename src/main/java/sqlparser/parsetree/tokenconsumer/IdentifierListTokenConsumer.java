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
        TokenType expectTokenType = TokenType.IDENTIFIER;
        while (tokenIterator.hasNext()) {
            Token nextToken = tokenIterator.lookForward();
            if (expectTokenType == TokenType.SEPARATOR) {
                if (nextToken.getTokenType() != expectTokenType) {
                    return identifierList;
                }
            }
            if (nextToken.getTokenType() != expectTokenType) {
                throw new ParsingError("Must be " + expectTokenType);
            } else if (expectTokenType == TokenType.SEPARATOR) {
                if (!",".equals(nextToken.getLexeme())) {
                    throw new ParsingError("Separator must be ,");
                }
            }
            Token currToken = tokenIterator.next();
            TerminalExprNode tokenNode = new TerminalExprNode(currToken.getTokenType(), currToken.getLexeme());
            identifierList.add(tokenNode);
            if (expectTokenType == TokenType.IDENTIFIER) {
                expectTokenType = TokenType.SEPARATOR;
            } else {
                expectTokenType = TokenType.IDENTIFIER;
            }
        }
        if (expectTokenType == TokenType.IDENTIFIER) {
            throw new ParsingError("Expect an " + TokenType.IDENTIFIER.code());
        }
        return identifierList;
    }
}
