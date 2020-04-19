package sqlparser.parsetree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class NonTerminalExprNode implements ExprNode {
    private List<ExprNode> children = new LinkedList<>();

    public void add(ExprNode node) {
        children.add(node);
    }

    @Override
    public Iterator<ExprNode> iterator() {
        return children.iterator();
    }

    @Override
    public String toString() {
        return print(",");
    }

    @Override
    public int height() {
        return children.stream().map(ExprNode::height).mapToInt(Integer::intValue).max().orElse(0) + 1;
    }

    @Override
    public String print(String separator) {
        String s = children.stream().map(ExprNode::toString).collect(Collectors.joining(separator));
        return "(" + s + ")";
    }
}
