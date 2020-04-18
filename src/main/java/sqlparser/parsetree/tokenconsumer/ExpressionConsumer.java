package sqlparser.parsetree.tokenconsumer;

import sqlparser.Token;
import sqlparser.TokenType;
import sqlparser.parsetree.ExprNode;
import sqlparser.parsetree.NonTerminalExprNode;
import sqlparser.parsetree.TerminalExprNode;

import java.util.*;

public class ExpressionConsumer implements TokenConsumer {

    private static Map<TokenType, String> ACCEPTED_TOKENS = new HashMap<>();

    static {
        ACCEPTED_TOKENS.put(TokenType.IDENTIFIER, "*");
        ACCEPTED_TOKENS.put(TokenType.OPERATOR, "=,>,<");
        ACCEPTED_TOKENS.put(TokenType.LITERAL, "*");
    }

    @Override
    public ExprNode build(LookForwardIterator<Token> tokenIterator) {
        NonTerminalExprNode root = new NonTerminalExprNode();
        TokenType expectNextToken = TokenType.IDENTIFIER;
        while (tokenIterator.hasNext()) {
            Token currToken = tokenIterator.next();
            if (currToken.getTokenType() != expectNextToken) {
                throw new IllegalArgumentException("Must be " + expectNextToken);
            }
            if (!isAcceptedToken(currToken)) {
                throw new IllegalArgumentException("Must be " + ACCEPTED_TOKENS.get(currToken.getTokenType()));
            }

            TerminalExprNode tokenNode = new TerminalExprNode(currToken.getTokenType(), currToken.getLexeme());
            root.add(tokenNode);

            if (currToken.getTokenType() == TokenType.IDENTIFIER) {
                expectNextToken = TokenType.OPERATOR;
            } else if (currToken.getTokenType() == TokenType.OPERATOR) {
                expectNextToken = TokenType.LITERAL;
            } else if (currToken.getTokenType() == TokenType.LITERAL) {
                return root;
            }
        }
        return root;
    }

    private static boolean isAcceptedToken(Token token) {
        if (!ACCEPTED_TOKENS.containsKey(token.getTokenType())) {
            return false;
        }

        String[] arr = ACCEPTED_TOKENS.get(token.getTokenType()).split(",");
        Set<String> acceptedLexemes = new HashSet<>(Arrays.asList(arr));
        return acceptedLexemes.contains("*") || acceptedLexemes.contains(token.getLexeme());
    }


}
