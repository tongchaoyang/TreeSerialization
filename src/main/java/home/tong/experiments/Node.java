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

	@Override
	public String toString() {
		String s = "Node [id=" + id + ", value=" + value + ", left ="; 
		s += left == null ? "-1, right=" : left.id + ", right=";
		s += right == null ? "-1]" : right.id + "]";
		return s;
	}
}
