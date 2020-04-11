package sqlparser.parsetree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class NonTerminalExprNode implements ExprNode {
    private List<ExprNode> children = new LinkedList<>();

    public void add(ExprNode node) {
        children.add(node);
    }

    @Override
    public Iterator<ExprNode> iterator() {
        return children.iterator();
    }
}
