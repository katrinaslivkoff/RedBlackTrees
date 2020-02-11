package cs146F19.Slivkoff.project4;

public class RedBlackTree<Key extends Comparable<Key>> 
{	
	private static RedBlackTree.Node<String> root = null;

	public static class Node<Key extends Comparable<Key>>
	{

		Key key;  		  
		Node<String> parent;
		Node<String> leftChild;
		Node<String> rightChild;
		boolean isRed;
		int color;

		public Node(Key data)
		{
			this.key = data;
			leftChild = null;
			rightChild = null;
		}	

		public int compareTo(Node<Key> n) //this < that  <0
		{ 	
			return key.compareTo(n.key);  //this > that  >0
		}

		public boolean isLeaf()
		{
			if (this.equals(root) && this.leftChild == null && this.rightChild == null) 
			{
				return true;
			}
			if (this.equals(root))
			{
				return false;
			}
			if (this.leftChild == null && this.rightChild == null)
			{
				return true;
			}
			return false;
		}
	}

	public boolean isLeaf(RedBlackTree.Node<String> n)
	{
		if (n.equals(root) && n.leftChild == null && n.rightChild == null)
		{
			return true;
		}
		if (n.equals(root))
		{
			return false;
		}
		if (n.leftChild == null && n.rightChild == null)
		{
			return true;
		}
		return false;
	}

	public interface Visitor<Key extends Comparable<Key>> 
	{
		/**
			This method is called at each node.
			@param n the visited node
		 */
		void visit(Node<Key> n);  
	}

	public void visit(Node<Key> n)
	{
		System.out.println(n.key);
	}

	public void printTree() //preorder: visit, go left, go right
	{  
		RedBlackTree.Node<String> currentNode = root;	
		printTree(currentNode);
	}

	public void printTree(RedBlackTree.Node<String> node)
	{
		System.out.print(node.key);
		if (node.isLeaf())
		{
			return;
		}
		printTree(node.leftChild);
		printTree(node.rightChild);
	}

	// place a new node in the RB tree with data the parameter and color it red. 
	public void addNode(String data) //this < that  <0.  this > that  >0
	{  
		//method filled in by ourselves
		RedBlackTree.Node<String> currentNode = new RedBlackTree.Node<String>(data);
		currentNode.color = 0;
		if (root == null) 
		{
			root = currentNode;
			root.parent = null;
		}
		else 
		{
			RedBlackTree.Node<String> checkNode = root;
			RedBlackTree.Node<String> updateNode = null;

			while (checkNode != null) 
			{
				updateNode = checkNode;
				if (currentNode.key.compareTo(checkNode.key) > 0)
				{
					checkNode = checkNode.rightChild;

				}
				else if(currentNode.key.compareTo(checkNode.key) < 0) 
				{
					checkNode = checkNode.leftChild;
				}	
			}
			currentNode.parent = updateNode;
			if (currentNode.key.compareTo(updateNode.key) < 0)
			{
				updateNode.leftChild = currentNode;
			}
			else 
			{
				updateNode.rightChild = currentNode;
			}

			fixTree(currentNode);
		}
	}	

	public void insert(String data)
	{
		addNode(data);	
	}

	public Node<String> lookup(String k)
	{ 
		//method filled in by ourselves
		RedBlackTree.Node<String> newNode = new RedBlackTree.Node<String>(k);
		RedBlackTree.Node<String> checkNode = root;
		if (newNode.equals(root)) 
		{
			return root;
		}
		while (checkNode != null) 
		{
			if (checkNode.compareTo(newNode) < 0) 
			{
				checkNode = checkNode.rightChild;
			}
			else if (checkNode.compareTo(newNode) > 0) 
			{
				checkNode = checkNode.leftChild;
			}
			else if (checkNode.compareTo(newNode) == 0) 
			{
				return checkNode;
			}
		}
		return null;
	}


	public RedBlackTree.Node<String> getSibling(RedBlackTree.Node<String> n)
	{  
		if (n.parent.leftChild != null && n.compareTo(n.parent) > 0) 
		{
			return n.parent.leftChild;
		}
		else if (n.parent.rightChild != null) 
		{
			return n.parent.rightChild;
		}
		return null;
	}


	public RedBlackTree.Node<String> getAunt(RedBlackTree.Node<String> n)
	{
		if (n.parent.compareTo(n.parent.parent) > 0 && n.parent.parent.leftChild != null ) 
		{
			return n.parent.parent.leftChild;
		}
		if (n.parent.compareTo(n.parent.parent) < 0 && n.parent.parent.rightChild != null) 
		{
			return n.parent.parent.rightChild;
		}
		return null;
	}

	public RedBlackTree.Node<String> getGrandparent(RedBlackTree.Node<String> n)
	{
		return n.parent.parent;
	}

	public void rotateLeft(RedBlackTree.Node<String> n)
	{
		RedBlackTree.Node<String> nodeY = n.rightChild;
		n.rightChild = nodeY.leftChild;
		if (nodeY.leftChild != null) 
		{
			nodeY.leftChild.parent = n;
		}
		nodeY.parent = n.parent;
		if (n.parent == null)
		{
			root = nodeY;
		}
		else if (n == n.parent.leftChild) 
		{
			n.parent.leftChild = nodeY;
		}
		else 
		{
			n.parent.rightChild = nodeY;
		}
		nodeY.leftChild = n;
		n.parent = nodeY;	
	}

	public void rotateRight(RedBlackTree.Node<String> n)
	{
		RedBlackTree.Node<String> nodeX = n.leftChild;
		n.leftChild = nodeX.rightChild;
		if (nodeX.rightChild != null) {
			nodeX.rightChild.parent = n;
		}
		nodeX.parent = n.parent;
		if (n.parent == null) 
		{
			root = nodeX;
			root.color = 1;
		}
		else if (n == n.parent.rightChild) 
		{
			n.parent.leftChild = nodeX;
		}
		else 
		{
			n.parent.rightChild = nodeX;
		}
		nodeX.rightChild = n;
		n.parent = nodeX;
	}

	public void fixTree(RedBlackTree.Node<String> current) 
	{
		if (current.equals(root)) 
		{
			current.color = 1; 
			return;
		}

		if(current.parent == null || current.parent.color == 1 || current.parent == root) 
		{
			return;
		}

		if (current.color == 0 && current.parent.color == 0) 
		{
			if (getAunt(current) == null || getAunt(current).color == 1) 
			{
				if (isLeftChild(current.parent, current) && current == current.parent.rightChild) 
				{
					rotateLeft(current.parent);
					current = current.parent;
					fixTree(current);
				}
				else if (isRightChild(current.parent, current) && current == current.parent.leftChild) 
				{
					rotateRight(current.parent);
					current = current.parent;
					fixTree(current);
				}
				else if (isLeftChild(current.parent, current) && current == current.parent.leftChild) 
				{
					current.parent.color = 1;
					getGrandparent(current).color = 0;
					rotateRight(getGrandparent(current));
					return;
				}
				else if (isRightChild(current.parent, current) && current == current.parent.rightChild) 
				{
					current.parent.color = 1;
					getGrandparent(current).color = 0;
					rotateLeft(getGrandparent(current));
					return;
				}
			}
			else if (getAunt(current).color == 0) 
			{
				current.parent.color = 1;
				getAunt(current).color = 1;
				getGrandparent(current).color = 0;
				current = getGrandparent(current);
				fixTree(current);
			}

		}

	}

	public boolean isEmpty(RedBlackTree.Node<String> n)
	{
		if (n.key == null)
		{
			return true;
		}
		return false;
	}
	
	public boolean isLeftChild(RedBlackTree.Node<String> parent, RedBlackTree.Node<String> child)
	{
		if (child.compareTo(parent) < 0 ) 
		{	//child is less than parent
			return true;
		}
		return false;
	}

	public boolean isRightChild(RedBlackTree.Node<String> parent, RedBlackTree.Node<String> child)
	{
		if (child.compareTo(parent) > 0 ) 
		{	//child is less than parent
			return true;
		}
		return false;
	}

	public void preOrderVisit(Visitor<String> v) 
	{
		preOrderVisit(root, v);
	}


	private static void preOrderVisit(RedBlackTree.Node<String> n, Visitor<String> v) 
	{
		if (n == null) 
		{
			return;
		}
		v.visit(n);
		preOrderVisit(n.leftChild, v);
		preOrderVisit(n.rightChild, v);
	}


}