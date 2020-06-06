package sqlparser.parsetree2.tokenconsumer;

import java.util.StringJoiner;

public class ASTNodePrinter {
    private ASTNode root;

    public ASTNodePrinter(ASTNode root) {
        this.root = root;
    }

    public String print() {
        return print(root, "");
    }

    private String print(ASTNode node, String prefix) {
        String rs = prefix + node.getToken();
        if (node.hasChildren()) {
            StringJoiner stringJoiner = new StringJoiner("\n", "\n", "");
            for (ASTNode cNode : node) {
                stringJoiner.add(print(cNode, prefix + "  "));
            }
            rs = rs + stringJoiner.toString();
        }
        return rs;
    }
}
