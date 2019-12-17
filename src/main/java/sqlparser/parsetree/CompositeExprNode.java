package sqlparser.parsetree;

import java.util.Iterator;
import java.util.List;

public class CompositeExprNode<String> implements ExprNode<String> {
    private List<ExprNode> nodes;

    @Override
    public Iterator<ExprNode> iterator() {
        return nodes.iterator();
    }

    @Override
    public void add(ExprNode node) {
        nodes.add(node);
    }

    @Override
    public boolean isAtom() {
        return false;
    }
}
