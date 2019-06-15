package home.tong.experiments;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tree {
	private int id = 0;
	private Logger logger = LoggerFactory.getLogger(Tree.class);
	
	public Node root;
	static final String NodeSeparator = ",";
	static final String PairSeparator = ";";
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
		sb.append(serializeNode(n));
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
		if (t == null)
			return null;
		if (t.equals("{}")) {
			return new Tree();
		}
		
		validate(t);
		
		t.substring(1, t.length()-1);
		String[] nodes = t.split(NodeSeparator);
		Node r = new Node();
		Stack<Node> stack = new Stack<>();
		stack.push(r);
		for (String n : nodes) {
			Node node = stack.pop();
			String[] pairs = n.split(PairSeparator);
			node.setId(Integer.valueOf(pairs[0].substring(pairs[0].indexOf('=')+1)));
			node.value = Integer.valueOf(pairs[1].substring(6));
			int rChildId = Integer.valueOf(pairs[3].substring(6, pairs[3].indexOf('}')));
			if (rChildId == -1)
				node.right = null;
			else {
				node.right = new Node(rChildId);
				stack.push(node.right);
			}
			
			int lChildId = Integer.valueOf(pairs[2].substring(5));
			if (lChildId == -1)
				node.left = null;
			else {
				node.left = new Node(lChildId);
				stack.push(node.left);
			}
		}
		
		return new Tree(r);
	}
	
	static void validate(String t) {
		boolean valid = true;
		if (t.charAt(0) == '{' && t.charAt(t.length()-1) == '}') {
			t = t.substring(1, t.length()-1);
			String[] nodes = t.split(NodeSeparator);
			// check if each node has id, value, left, right elements
			// doesn't validate numeric value. that will be done during parsing
			for (String n : nodes) {
				String[] pairs = n.split(PairSeparator);
				if (!pairs[0].substring(0, 4).equals("{id=")) {
					valid = false;
					break;
				}
				if (!pairs[1].substring(0, 6).equals("value=")) {
					valid = false;
					break;
				}
				if (!pairs[2].substring(0, 5).equals("left=")) {
					valid = false;
					break;
				}
				if (!pairs[3].substring(0, 6).equals("right=")) {
					valid = false;
					break;
				}
			}
		}
		else {
			valid = false;
		}
		
		if (!valid) {
			throw new IllegalArgumentException(t);
		}
	}
	String serializeNode(Node n) {
		String s = '{' + "id=" + n.getId() + ';' + "value=" + n.value + ';'
				+ "left=";
		if (n.left == null) {
			s += "-1;right=";
		}
		else {
			s += n.left.getId() + ";" + "right=";  
		}
		if (n.right == null) {
			s += "-1}";
		}
		else {
			s += n.right.getId() + "}";
		}
		return s;
	}
}
