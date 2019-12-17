package sqlparser.parsetree;

import java.util.Iterator;

public interface ExprNode<T> {
    Iterator<ExprNode> iterator();

    void add(ExprNode node);

    boolean isAtom();
}
