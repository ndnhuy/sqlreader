package sqlparser.parsetree2.tokenconsumer;

import org.springframework.util.Assert;
import sqlparser.Token;
import sqlparser.TokenType;

import java.util.List;

public class TokenConsumer {

    public ASTNode build(List<Token> tokens) {
        Token firstToken = tokens.get(0);
        if (firstToken.getTokenType() != TokenType.STATEMENT) {
            throw new RuntimeException("Must start with a statement");
        }

        ASTNode root = new ASTNode(firstToken.getLexeme(), TokenType.STATEMENT);
        ASTNode nodeRef = root;

        if ("select".equalsIgnoreCase(firstToken.getLexeme())) {
            ASTNode selectClause = new ASTNode(firstToken.getLexeme(), TokenType.CLAUSE);
            root.insert(selectClause);
            nodeRef = selectClause;
        }
        for (int i = 1; i < tokens.size(); i++) {
            Token t = tokens.get(i);
            if (TokenType.CLAUSE == t.getTokenType()) {
                ASTNode node = new ASTNode(t);
                nodeRef.getParent().insert(node);
                nodeRef = node;
            } else if (TokenType.LEFT_BRACKET == t.getTokenType()) {
                ASTNode node = new ASTNode(null, null);
                nodeRef.insert(node);
                nodeRef = node;

                ASTNode lNode = new ASTNode(t);
                nodeRef.insert(lNode);
            } else if (TokenType.RIGHT_BRACKET == t.getTokenType()) {
                ASTNode node = new ASTNode(t);
                nodeRef.insert(node);
                nodeRef = nodeRef.getParent();
            } else {
                ASTNode node = new ASTNode(t);
                nodeRef.insert(node);
            }
        }

        composeOperator(root);
        return root;
    }

    private void composeOperator(ASTNode node) {
        composeOperator(node, TokenType.COMPARISON_OPERATOR);
        composeOperator(node, TokenType.LOGICAL_OPERATOR);
    }

    private void composeOperator(ASTNode node, TokenType tokenType) {
        if (node.hasChildren()) {
            for (ASTNode cNode : node) {
                composeOperator(cNode, tokenType);
            }
        } else if (node.getTokenType() == tokenType) {
            Assert.notNull(node.getPrev(), "Prev is null");
            Assert.notNull(node.getNext(), "Next is null");
            ASTNode l = node.getParent().remove(node.getPrev());
            ASTNode r = node.getParent().remove(node.getNext());
            node.insert(l);
            node.insert(r);
        }
    }
}
