package sqlparser.parsetree2.tokenconsumer;

import sqlparser.TokenType;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringJoiner;

public class ASTNode implements Iterable<ASTNode> {
    private String label;
    private TokenType tokenType;
    private ASTNode next;
    private ASTNode prev;
    private ASTNode headChild;
    private ASTNode tailChild;
    private ASTNode parent;
    private Set<ASTNode> childrenSet = new HashSet<>();
    private int size;

    public ASTNode(String label, TokenType tokenType) {
        this.label = label;
        this.tokenType = tokenType;
    }

    public TokenType getTokenType() {
        return tokenType;
    }


    public ASTNode getParent() {
        return parent;
    }

    public ASTNode getPrev() {
        return prev;
    }

    public ASTNode getNext() {
        return next;
    }

    public ASTNode remove(ASTNode cNode) {
        if (contains(cNode)) {
            if (headChild == cNode) {
                headChild = cNode.next;
                headChild.prev = null;
            } else if (tailChild == cNode) {
                tailChild = cNode.prev;
                tailChild.next = null;
            } else {
                ASTNode prev = cNode.prev;
                ASTNode next = cNode.next;
                prev.next = next;
                next.prev = prev;
            }
            cNode.prev = cNode.next = cNode.parent = null;
            childrenSet.remove(cNode);
            return cNode;
        } else {
            return null;
        }
    }

    public void insert(ASTNode node) {
        if (hasChildren()) {
            tailChild.next = node;
            node.prev = tailChild;
            tailChild = node;
        } else {
            headChild = node;
            tailChild = node;
        }
        node.parent = this;
        size++;
        childrenSet.add(node);
    }

    public boolean hasChildren() {
        return headChild != null;
    }

    public boolean contains(ASTNode node) {
        return childrenSet.contains(node);
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<ASTNode> iterator() {
        return new Iterator<ASTNode>() {
            private ASTNode iter;
            @Override
            public boolean hasNext() {
                return (iter == null && hasChildren())
                            || iter.next != null;
            }

            @Override
            public ASTNode next() {
                if (iter == null) {
                    iter = headChild;
                } else {
                    iter = iter.next;
                }
                return iter;
            }
        };
    }

    public String print(String separator) {
        if (hasChildren()) {
            StringJoiner joiner = new StringJoiner(separator, "(", ")");
            for (ASTNode node : this) {
                joiner.add(node.print(separator));
            }
            return this.label + "#" + joiner.toString();
        } else {
            return label;
        }
    }
}
