package home.tong.experiments;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tree {
    private int id = 0;
    private Logger logger = LoggerFactory.getLogger(Tree.class);
    
    public Node root;
    static final String NodeSeparator = ";";
    static final String PairSeparator = ",";
    public Tree() {
        root = null;
    }
    
    public Tree(Node root) {
        this.root = root;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((root == null) ? 0 : root.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tree other = (Tree) obj;
        if (root == null) {
            if (other.root != null)
                return false;
        } else if (!root.equals(other.root))
            return false;
        return true;
    }
    
    /**
     * format:
     * 1. node: {id=0;v=7;l=n1;r=n3}
     * 2. tree: {{id=0;v=7;l=1;r=8},{id=1;v=3;l=2;r=5};...}
     * @return
     */
    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        if (root == null) {
            sb.append('}');
            return sb.toString();
        }
        
        preOrderTraverse(root, sb);
        sb.append('}');
        return sb.toString();
    }
    
    void preOrderTraverse(Node n, StringBuilder sb) {
        logger.info("visiting " + n.toString());
        sb.append(n.toString());
        if (n.left != null) {
            sb.append(NodeSeparator);
            preOrderTraverse(n.left, sb);
        }
        if (n.right != null) {
            sb.append(NodeSeparator);
            preOrderTraverse(n.right, sb);
        }
    }
    
    public static Tree deserialize(String t) {
        validate(t);
        
        if (t == null)
            return null;
        if (t.equals("{}")) {
            return new Tree();
        }
        
        t = t.substring(1, t.length()-1);
        String[] nodes = t.split(NodeSeparator);
        Node r = new Node();
        Stack<Node> stack = new Stack<>();
        stack.push(r);
        for (String s : nodes) {
            Node node = stack.pop();
            Node.populate(node, s);
            if (node.right != null) {
                stack.push(node.right);
            }
            
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        
        return new Tree(r);
    }
    
    static void validate(String t) {
        boolean valid = true;
        if (t == null || t.isEmpty() || t.equals("{}"))
            return;
        
        if (t.charAt(0) == '{' && t.charAt(t.length()-1) == '}') {
            t = t.substring(1, t.length()-1);
            String[] nodes = t.split(NodeSeparator);
            for (String n : nodes) {
                Node.validate(n);
            }
        }
        else {
            valid = false;
        }
        
        if (!valid) {
            throw new IllegalArgumentException(t);
        }
    }
}
