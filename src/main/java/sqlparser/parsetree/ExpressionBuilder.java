package sqlparser.parsetree;

import sqlparser.Token;
import sqlparser.TokenType;
import sqlparser.parsetree.tokenconsumer.FromTokenConsumer;
import sqlparser.parsetree.tokenconsumer.SelectTokenConsumer;
import sqlparser.parsetree.tokenconsumer.WhereTokenConsumer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

    static private Map<TokenType, TokenType> rules = new HashMap<>();
    static {
        rules.put(TokenType.SELECT, TokenType.FROM);
        rules.put(TokenType.FROM, TokenType.WHERE);
    }

    public void build2(Iterator<Token> tokenIterator) {
        if (!tokenIterator.hasNext()) {
            throw new IllegalArgumentException("No tokens found");
        }
        NonTerminalExprNode root = new NonTerminalExprNode();
        Token prev = null;
        while (tokenIterator.hasNext()) {
            Token currToken = tokenIterator.next();

            // validate
            if (prev != null
                    && rules.containsKey(prev.getTokenType())
                    && rules.get(prev.getTokenType()) != currToken.getTokenType()) {
                throw new IllegalArgumentException(
                        String.format("Should be %s, not %s", rules.get(prev.getTokenType()), currToken.getTokenType()));
            }

            if (currToken.getTokenType() == TokenType.SELECT) {
                ExprNode exprNode = new SelectTokenConsumer().build(tokenIterator);
                root.add(exprNode);
            } else if (currToken.getTokenType() == TokenType.FROM) {
                ExprNode exprNode = new FromTokenConsumer().build(tokenIterator);
                root.add(exprNode);
            } else if (currToken.getTokenType() == TokenType.WHERE) {
                ExprNode exprNode = new WhereTokenConsumer().build(tokenIterator);
                root.add(exprNode);
            }
            prev = currToken;
        }
    }
}
