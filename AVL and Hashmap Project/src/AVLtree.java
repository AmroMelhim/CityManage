import java.io.FileWriter;
import java.io.IOException;

public class AVLtree {

	private class AVLNode {
		City data; // Data in the node
		AVLNode left; // Left child
		AVLNode right; // right child
		int height; // height

		// Constructors

		public AVLNode(City d) {
			this(d, null, null);
		}

		public AVLNode(City d, AVLNode lt, AVLNode rt) {
			data = d;
			left = lt;
			right = rt;
			height = 0;
		}
	}

	private AVLNode root; // The tree root

	public AVLtree() // Construct the tree
	{
		root = null;
	}

	public void makeEmpty() // Make the tree logically empty.
	{
		root = null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	// print sorted
	public void printTree() {
		if (isEmpty())
			System.out.println("Empty tree");
		else
			printTree(root);
	}

	// inorder print
	private void printTree(AVLNode t) {
		if (t != null) {
			printTree(t.left);
			System.out.println(t.data.getCityName() + " ");

			printTree(t.right);
		}
	}

	// inorder String in file format
	public String toStringFile() {
		StringBuilder Filestr = new StringBuilder();
		toStringFile(root, Filestr);
		return Filestr.toString();

	}

	private void toStringFile(AVLNode t, StringBuilder Filestr) {
		if (t != null) {
			toStringFile(t.right, Filestr);
			toStringFile(t.left, Filestr);
			Filestr.append(t.data.getCityName() + "/" + t.data.getCityFile() + "\n");

			
		}
	}

	// inorder String for printing
	public String toString() {
		StringBuilder str = new StringBuilder();
		toString(root, str);
		return str.toString();

	}

	private void toString(AVLNode t, StringBuilder str) {
		if (t != null) {
			toString(t.left, str);
			str.append(t.data.getCityName() + "\n");
			// System.out.println(t.data.getCityName() + " ");

			toString(t.right, str);
		}
	}

	public void insert(City x) {
		root = insert(x, root);
	}

	public void insert(String data) {
		String file = data + ".txt";
		City city = new City(data, file);
		root = insert(city, root);

	}

	private AVLNode insert(City x, AVLNode t) {
		if (t == null)
			return new AVLNode(x, null, null);
		if (x.compareTo(t.data) < 0) {
			t.left = insert(x, t.left);
			if (getBalance(t) == 2)
				if (x.compareTo(t.left.data) <= 0)
					t = rotateLeft(t);
				else
					t = doubleLeft(t);
		} else if (x.compareTo(t.data) >= 0) {
			t.right = insert(x, t.right);
			if (getBalance(t) == 2)
				if (x.compareTo(t.right.data) > 0)
					t = rotateRight(t);
				else
					t = doubleRight(t);
		} 

		t.height = Math.max(height(t.left), height(t.right)) + 1;
		return t;
	}

	// search for element ,returns true if found
	public boolean search(String data) {
		return search(getObject(data, root), root);
	}

	private boolean search(City x, AVLNode t) {
		while (t != null) {
			if (x.compareTo(t.data) < 0)
				t = t.left;
			else if (x.compareTo(t.data) > 0)
				t = t.right;

			else
				return true; // found
		}
		return false; // not found
	}

	public String getFile(String data) {
		return getFile(getObject(data, root), root);

	}

	private String getFile(City x, AVLNode t) {
		while (t != null) {
			if (x.compareTo(t.data) < 0)
				t = t.left;
			else if (x.compareTo(t.data) > 0)
				t = t.right;
			else
				return t.data.getCityFile();
		}
		return null;
	}
	public int height() {
		return height(root);
	}
	// return height of node t, or -1, if tree is empty
	private int height(AVLNode t)

	{
		if (t == null)
			return -1;
		else
			return t.height;
	}

	private AVLNode rotateLeft(AVLNode node2) {
		AVLNode node1 = node2.left;
		node2.left = node1.right;
		node1.right = node2;
		node2.height = Math.max(height(node2.left), height(node2.right)) + 1;
		node1.height = Math.max(height(node1.left), node2.height) + 1;
		return node1;
	}

	private AVLNode rotateRight(AVLNode node1) {
		AVLNode node2 = node1.right;
		node1.right = node2.left;
		node2.left = node1;
		node1.height = Math.max(height(node1.left), height(node1.right)) + 1;
		node2.height = Math.max(height(node2.right), node1.height) + 1;
		return node2;
	}

	private AVLNode doubleLeft(AVLNode node3) {
		
			node3.left = rotateRight(node3.left);
			return rotateLeft(node3);

		 
	}

	private AVLNode doubleRight(AVLNode node1) {
		
			node1.right = rotateLeft(node1.right);
			return rotateRight(node1);
		
	}

	private City getObject(String x, AVLNode t) {
		while (t != null) {
			if (x.compareTo(t.data.getCityName()) < 0)
				t = t.left;
			else if (x.compareTo(t.data.getCityName()) > 0)
				t = t.right;

			else
				return t.data;
		}
		City city = new City("", "");
		return city;
	}

	public AVLNode delete(String data) {

		return delete(root, getObject(data, root));
	}

	private AVLNode delete(AVLNode root, City data) {

		if (root == null) {
			return null;
			
		} 
		else if (root.data.compareTo(data) == 0) {

			if (root.left == null && root.right == null) {
				return null;
			} else if (root.left != null && root.right != null) {

				AVLNode temp = findMin(root.right);
				root.data = temp.data;
				root.right = delete(root.right, temp.data);

			} else if (root.left != null) {

				root.data = root.left.data;
				root.right = root.left.right;
				root.left = root.left.left;

			} else {

				root.data = root.right.data;
				root.left = (root.right.left);
				root.right = root.right.right;
			}

		} else {

			if (root.data.compareTo(data) > 0) {

				root.left = delete(root.left, data);

			} else {

				root.right = delete(root.right, data);
			}
		}
		

		root.height = Math.max(height(root.left), height(root.right)) + 1;

		if (getBalance(root) < -1) {

			// R - L

			if (getBalance(root.right) <= 0) {

				// Single Rotation

				root = rotateLeft(root);

			} else {

				root = doubleLeft(root);

			}

		} else if (getBalance(root) > 1) {

			// L- R

			if (getBalance(root.left) >= 0) {

				// Single Rotation

				root = rotateRight(root);

			} else {

				root = doubleRight(root);

			}

		}

		return root;

	}

	private int getBalance(AVLNode node) {
		if (node == null)
			return 0;

		return (height(node.left) - height(node.right));

	}

	private AVLNode findMin(AVLNode root) {

		if (root.left == null) {
			return root;
		} else {

			return findMin(root.left);
		}

	}

	
}
