/**
 * AVLTree
 * <p>
 * An implementation of an AVL Tree with
 * distinct integer keys and info.
 */

public class AVLTree {
	IAVLNode root;

	// constructor for an existing tree
	public AVLTree(IAVLNode root){
		this.root = root;
	}

	// constructor for a new tree
	public AVLTree(){
		this.root = null;
	}

	/**
	 * public boolean empty()
	 * <p>
	 * Returns true if and only if the tree is empty.
	 */
	public boolean empty() {
		return this.root == null;
	}

	/**
	 * public String search(int k)
	 * <p>
	 * Returns the info of an item with key k if it exists in the tree.
	 * otherwise, returns null.
	 */
	public String search(int k) {
		IAVLNode foundNode = search(this.root, k);
		if (foundNode == null)
			return null;
		return foundNode.getValue();
	}

	private static IAVLNode search(IAVLNode node, int k) {
		if (node == null || node.getKey() == k)
			return node;

		// Key is greater than root's key
		if (node.getKey() < k)
			return search(node.getRight(), k);

		// Key is smaller than root's key
		return search(node.getLeft(), k);
	}

	// A utility function to right rotate subtree rooted with y
	public IAVLNode rightRotate(IAVLNode y) {
		IAVLNode x = y.getLeft();
		IAVLNode T2 = x.getRight();

		// Perform rotation
		x.setRight(y);
		y.setLeft(T2);

		// Update heights
		y.setHeight(y.getHeight()-1);

		// Update sizes
		y.updateSize();
		x.updateSize();

		// Return new root
		return x;
	}

	// A utility function to left rotate subtree rooted with x
	// See the diagram given above.
	public IAVLNode leftRotate(IAVLNode x) {
		IAVLNode y = x.getRight();
		IAVLNode T2 = y.getLeft();

		// Perform rotation
		y.setLeft(x);
		x.setRight(T2);

		//  Update heights
		x.updateHeight();
		y.updateHeight();

		// Update sizes
		x.updateHeight();
		y.updateHeight();

		// Return new root
		return y;
	}


	/**
	 * public int insert(int k, String i)
	 * <p>
	 * Inserts an item with key k and info i to the AVL tree.
	 * The tree must remain valid, i.e. keep its invariants.
	 * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
	 * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
	 * Returns -1 if an item with key k already exists in the tree.
	 */
	public int insert(int k, String i) {
		IAVLNode newNode = new AVLNode(k, i);
		int rebalanceStepsCnt = 0;
		if (this.root == null) {
			this.root = newNode;
			return rebalanceStepsCnt;
		} else {
			IAVLNode parent = this.treePosition(this.root, k);
			if (k == parent.getKey())
				return -1;
			newNode.setParent(parent);
			if (k < parent.getKey())
				parent.setLeft(newNode);
			else parent.setRight(newNode);

			// check whether parent was a leaf or unary node
			if (parent.getKey() == 1) // it was unary, no rebalance needed
				return rebalanceStepsCnt;
			else {
				parent.setHeight(parent.getHeight() + 1); // Promotes parent's height
				rebalanceStepsCnt += 1; // First rebalance step
				int leftChildRank = parent.getParent().getLeft().getHeight(); // The parent's rank or its bother
				int rightChildRank = parent.getParent().getRight().getHeight(); // The parent's rank or its bother
				while (Math.abs(leftChildRank - rightChildRank) == 1){ // Loops until we are not in case 1
					parent = parent.getParent();
					parent.setHeight(parent.getHeight() + 1); // Promotes parent's height
					leftChildRank = parent.getParent().getLeft().getHeight(); // The parent's rank or its bother
					rightChildRank = parent.getParent().getRight().getHeight(); // The parent's rank or its bother
					rebalanceStepsCnt += 1; // Counts another rebalace step
				}
				if (leftChildRank - rightChildRank == 2 && parent.getLeft().getHeight() - parent.getRight().getHeight() == 1){

				}
			}
		}
	}

	public IAVLNode treePosition(IAVLNode x, int k) {
		IAVLNode node = null;
		while (x.isRealNode()) {
			node = x;
			if (k == x.getKey())
				return x;
			else if (k < x.getKey())
				x = x.getLeft();
			else
				x = x.getRight();
		}
		return node;
	}


	/**
	 * public int delete(int k)
	 * <p>
	 * Deletes an item with key k from the binary tree, if it is there.
	 * The tree must remain valid, i.e. keep its invariants.
	 * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
	 * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
	 * Returns -1 if an item with key k was not found in the tree.
	 */
	public int delete(int k) {
		return 421;    // to be replaced by student code
	}

	/**
	 * public String min()
	 *
	 * Returns the info of the item with the smallest key in the tree,
	 * or null if the tree is empty.
	 */
	public String min() {
		if (empty()) return null;
		else {
			IAVLNode x = getRoot();
			while (x != null && x.getLeft().isRealNode()) {
				x = x.getLeft();
			} return x.getValue();
		}
	}

	/**
	 * public String max()
	 *
	 * Returns the info of the item with the largest key in the tree,
	 * or null if the tree is empty.
	 */
	public String max() {
		if (empty()) return null;
		else {
			IAVLNode x = getRoot();
			while (x != null && x.getRight().isRealNode()) {
				x = x.getRight();
			} return x.getValue();
		}
	}

	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree,
	 * or an empty array if the tree is empty.
	 */
	public int[] keysToArray() {
		int [] keysArray = new int[size()];
		if (!empty()){
			keysToArrayHelper(keysArray, 0,getRoot());
		}
		return keysArray;
	}

	/**
	 * private int keysToArrayHelper
	 *
	 * Returns the key interger of the AVLTree nodes in-order.
	 */
	private int keysToArrayHelper(int[] intArray, int integer, IAVLNode node){
		if (!node.isRealNode()) return integer;
		integer = keysToArrayHelper(intArray,integer,node.getLeft());
		intArray[integer] = node.getKey();
		return keysToArrayHelper(intArray,integer + 1, node.getRight());

	}
	public String[] infoToArray() {
		return new String[55]; // to be replaced by student code
	}

	/**
	 * public int size()
	 * <p>
	 * Returns the number of nodes in the tree.
	 */
	public int size() {
		return this.size; // to be replaced by student code
	}

	/**
	 * public int getRoot()
	 * <p>
	 * Returns the root AVL node, or null if the tree is empty
	 */
	public IAVLNode getRoot() {
		return this.root;
	}

	/**
	 * public AVLTree[] split(int x)
	 * <p>
	 * splits the tree into 2 trees according to the key x.
	 * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
	 * <p>
	 * precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
	 * postcondition: none
	 */
	public AVLTree[] split(int x) {
		return null;
	}

	/**
	 * public int join(IAVLNode x, AVLTree t)
	 * <p>
	 * joins t and x with the tree.
	 * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	 * <p>
	 * precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be empty (rank = -1).
	 * postcondition: none
	 */
	public int join(IAVLNode x, AVLTree t) {
		return -1;
	}

	/**
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
	 */
	public interface IAVLNode {
		public int getKey(); // Returns node's key (for virtual node return -1).

		public String getValue(); // Returns node's value [info], for virtual node returns null.

		public void setLeft(IAVLNode node); // Sets left child.

		public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.

		public void setRight(IAVLNode node); // Sets right child.

		public IAVLNode getRight(); // Returns right child, if there is no right child return null.

		public void setParent(IAVLNode node); // Sets parent.

		public IAVLNode getParent(); // Returns the parent, if there is no parent return null.

		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.

		public void setHeight(int height); // Sets the height of the node.

		public int getHeight(); // Returns the height of the node (-1 for virtual nodes).

		public int setSize();

		public int getSize();

	}

	/**
	 * public class AVLNode
	 * <p>
	 * If you wish to implement classes other than AVLTree
	 * (for example AVLNode), do it in this file, not in another file.
	 * <p>
	 * This class can and MUST be modified (It must implement IAVLNode).
	 */
	public class AVLNode implements IAVLNode {
		int key;
		String value;
		IAVLNode left;
		IAVLNode right;
		IAVLNode parent;
		int height;
		int size;

		/**
		 * Constructor for virtual nodes.
		 */
		public AVLNode(IAVLNode parent) {
			this.parent = parent;
			key = -1;
			height = -1;
		}

		/**
		 * Constructor for real nodes.
		 */
		public AVLNode(int key, String value) {
			this.key = key;
			this.value = value;
			this.left = new AVLNode(this);
			this.right = new AVLNode(this);
			this.size = 1;
		}

		public int getKey() {
			return this.key;
		}

		public String getValue() {
			return this.value;
		}

		public void setLeft(IAVLNode node) {
			this.left = node;
		}

		public IAVLNode getLeft() {
			return this.left;
		}

		public void setRight(IAVLNode node) {
			this.right = node;
		}

		public IAVLNode getRight() {
			return this.right;
		}

		public void setParent(IAVLNode node) {
			this.parent = node;
		}

		public IAVLNode getParent() {
			return this.parent;
		}

		public boolean isRealNode() {
			return height != -1;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getHeight() {
			return this.height;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public int getSize() {
			return size;
		}

		public void updateHeight() {
			this.setHeight(Math.max(this.getLeft().getHeight(), this.getRight().getHeight()) + 1);
		}

		public void updateSize() {
			this.setSize(getLeft().getSize() + this.getRight().getSize() + 1);
		}

	}

}
