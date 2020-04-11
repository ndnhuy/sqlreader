package sqlparser.parsetree;

import sqlparser.Token;
import sqlparser.TokenType;

import java.util.Iterator;
import java.util.regex.Pattern;

public class ExpressionBuilder {
    public void build(Iterator<Token> tokenIterator) {
        NonTerminalExprNode currNode = new NonTerminalExprNode();
        if (!tokenIterator.hasNext()) {
            throw new IllegalArgumentException("No tokens found");
        }
        TokenType tokenTypeIdentifier = TokenType.SELECT;
        while (tokenIterator.hasNext()) {
            Token currToken = tokenIterator.next();
            if (TokenType.SELECT == tokenTypeIdentifier) {
                if (currToken.getTokenType() != tokenTypeIdentifier) {
                    throw new RuntimeException("Query must start with " + tokenTypeIdentifier.code());
                }
                TerminalExprNode node = new TerminalExprNode(tokenTypeIdentifier, currToken.getLexeme());
                currNode.add(node);
                tokenTypeIdentifier = TokenType.COLUMN_IDENTIFIER_LIST;
            } else if (TokenType.COLUMN_IDENTIFIER_LIST == tokenTypeIdentifier) {
                if (!"*".equals(currToken.getLexeme()) && !Pattern.matches("[a-zA-Z0-9]", currToken.getLexeme())) {
                    throw new ParsingError("After SELECT should be: *, a-z, A-Z, 0-9");
                }
                TerminalExprNode node = new TerminalExprNode(tokenTypeIdentifier, currToken.getLexeme());
                currNode.add(node);
                tokenTypeIdentifier = TokenType.FROM;
            } else if (TokenType.FROM == tokenTypeIdentifier) {
                if (currToken.getTokenType() != tokenTypeIdentifier) {
                    throw new RuntimeException("must be " + tokenTypeIdentifier.code());
                }
                TerminalExprNode node = new TerminalExprNode(tokenTypeIdentifier, currToken.getLexeme());
                currNode.add(node);
                tokenTypeIdentifier = TokenType.TABLE_IDENTIFIER_LIST;
            } else if (TokenType.TABLE_IDENTIFIER_LIST == tokenTypeIdentifier) {
                if (!Pattern.matches("[a-zA-Z0-9]", currToken.getLexeme())) {
                    throw new ParsingError("After FROM should be: a-z, A-Z, 0-9");
                }
                TerminalExprNode node = new TerminalExprNode(tokenTypeIdentifier, currToken.getLexeme());
                currNode.add(node);
                tokenTypeIdentifier = TokenType.WHERE;
            } else if (TokenType.WHERE == tokenTypeIdentifier) {
                if (currToken.getTokenType() != tokenTypeIdentifier) {
                    throw new RuntimeException("must be " + tokenTypeIdentifier.code());
                }
                TerminalExprNode node = new TerminalExprNode(tokenTypeIdentifier, currToken.getLexeme());
                currNode.add(node);
                tokenTypeIdentifier = TokenType.PREDICATE;
            } else if (TokenType.PREDICATE == tokenTypeIdentifier) {
                if (TokenType.OPERATOR == currToken.getTokenType()
                     || TokenType.SEPARATOR == currToken.getTokenType()) {
                    throw new RuntimeException("Must not start with " + currToken.getLexeme());
                }
                if (TokenType.IDENTIFIER == currToken.getTokenType()) {
                    TerminalExprNode node = new TerminalExprNode(tokenTypeIdentifier, currToken.getLexeme());
                }
            }
        }
    }
}
