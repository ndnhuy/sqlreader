package sqlparser.parsetree;

import sqlparser.Token;

import java.util.List;

public class TreeParser {
    public ExprNode build(List<Token> tokens) {
        ExprNode root = new CompositeExprNode();
        for (Token token : tokens) {
            root.add(new AtomExprNode(token));
        }
        return root;
    }
}
