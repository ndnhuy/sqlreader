package sqlparser.parsetree;

public interface ExprNode extends Iterable<ExprNode> {
    int height();
    String print(String separator);
}
