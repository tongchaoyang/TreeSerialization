package home.tong.experiments;

public class Node {
    // non-negative id, unique within a tree
    private int id;

    public int value;
    public Node left;
    public Node right;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((left == null) ? 0 : left.hashCode());
        result = prime * result + ((right == null) ? 0 : right.hashCode());
        result = prime * result + value;
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
        Node other = (Node) obj;
        if (left == null) {
            if (other.left != null)
                return false;
        } else if (!left.equals(other.left))
            return false;
        if (right == null) {
            if (other.right != null)
                return false;
        } else if (!right.equals(other.right))
            return false;
        if (value != other.value)
            return false;
        return true;
    }

    public Node() {
        value = 0;
        id = 0;
        left = right = null;
    }

    public Node(int id) {
        value = 0;
        if (id < 0)
            throw new IllegalArgumentException("Tree node id must be non-negative integer");

        this.id = id;
        left = right = null;
    }

    public Node(int id, int v) {
        if (id < 0)
            throw new IllegalArgumentException("Tree node id must be non-negative integer");
        value = v;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0)
            throw new IllegalArgumentException("Tree node id must be non-negative integer");
        this.id = id;
    }

    public static void populate(Node n, String s) {
        s = s.substring(1, s.length()-1);
        
        String[] pairs = s.split(",");
        n.setId(Integer.parseInt(pairs[0].substring(3)));
        n.value = Integer.parseInt(pairs[1].substring(6));
        String left = pairs[2].substring(5);
        if (left.equals("-1")) {
            n.left = null;
        }
        else {
            n.left = new Node(Integer.parseInt(left));
        }
        String right = pairs[3].substring(6);
        if (right.equals("-1")) {
            n.right = null;
        }
        else {
            n.right = new Node(Integer.parseInt(right));
        }
    }

    // validation: check if each node has id, value, left, right elements
    // doesn't validate numeric value. that will be done during parsing
    public static void validate(String s) {
        boolean valid = true;
        if (s.charAt(0) == '{' && s.charAt(s.length() - 1) == '}') {
            String[] pairs = s.split(",");
            if (!pairs[0].substring(0, 4).equals("{id=")) {
                valid = false;
            }
            if (!pairs[1].substring(0, 6).equals("value=")) {
                valid = false;
            }
            if (!pairs[2].substring(0, 5).equals("left=")) {
                valid = false;
            }
            if (!pairs[3].substring(0, 6).equals("right=")) {
                valid = false;
            }
        } else {
            valid = false;
        }

        if (!valid) {
            throw new IllegalArgumentException(s);
        }
    }

    @Override
    public String toString() {
        String s = "{id=" + id + ",value=" + value + ",left=";
        s += left == null ? "-1,right=" : left.id + ",right=";
        s += right == null ? "-1}" : right.id + "}";
        return s;
    }
}
