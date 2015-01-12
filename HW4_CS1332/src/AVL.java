import java.util.Collection;


/**
 * CS 1332 Fall 2013
 * AVL Tree
 * 
 * In this class, you will program an AVL Tree (Adelson Veskii-Landis Tree).
 * This is like a better version of a binary search tree in that it 
 * tries to fill out every level of the tree as much as possible. It
 * accomplishes this by keeping track of each node's height and balance
 * factor. As you recurse back up from operations that modify the tree
 * (like add or remove), you will update the height and balance factor
 * of the current node, and perform a rotation on the current node if 
 * necessary. Keeping this in mind, let's get started!
 * 
 * **************************NOTE*************************************
 * please please please  treat null as positive infinity!!!!!!!!
 * PLEASE TREAT NULL AS POSITIVE INFINITY!!!!
 * *************************NOTE**************************************
 * 
 * I STRONLY RECOMMEND THAT YOU IMPLEMENT THIS DATA STRUCTURE RECURSIVELY!
 * 
 * Please make any new internal classes, instance data, and methods private!!
 * 
 * DO NOT CHANGE ANY OF THE PUBLIC METHOD HEADERS
 */
public class AVL<T extends Comparable<T>> {
	
	private AVLNode<T> root;
	private int size;
	
	/**
	 * I promise you, this is just like the add() method you coded
	 * in the BST part of the homework! You will start off at the
	 * root and find the proper place to add the data. As you 
	 * recurse back up the tree, you will have to update the
	 * heights and balance factors of each node that you visited
	 * while reaching the proper place to add your data. Immediately
	 * before you return out of each recursive step, you should update
	 * the height and balance factor of the current node and then
	 * call rotate on the current node. You will then return the node
	 * that comes from the rotate(). This way, the re-balanced subtrees
	 * will properly be added back to the whole tree. Also, don't forget
	 * to update the size of the tree as a whole.
	 * 
	 * PLEASE TREAT NULL AS POSITIVE INFINITY!!!!
	 * 
	 * @param data The data do be added to the tree.
	 */
	public void add(T data) {            
            if (root == null) {
                root = new AVLNode(data);
                ++size;
                return;
            }
            
            addHelper(root, data, 0);
            ++size;
	}
	
        private void addHelper(AVLNode<T> node, T data, int height) {
            if (data == null ||
                ((Comparable)data).compareTo(node.getData()) > 0) {
                // new > cur
                if (node.getRight() == null) {
                    node.setRight(new AVLNode(data));
                    node.getRight().setHeight(++height);
                    updateHeightAndBF(node.getRight());
                    rotate(node.getRight());
                }
                else {
                    addHelper(node.getRight(), data, ++height);
                }
            }
            else {
                // new < cur
                if (node.getLeft() == null) {
                    node.setLeft(new AVLNode(data));
                    node.getLeft().setHeight(++height);
                    updateHeightAndBF(node.getLeft());
                    rotate(node.getLeft());
                }
                else {
                    addHelper(node.getLeft(), data, ++height);
                }
            }
        }
	
	/**
	 * This is a pretty simple method. All you need to do is to get
	 * every element in the collection that is passed in into the tree.
	 * Try to think about how you can combine a for-each loop and your
	 * add method to accomplish this.
	 * @param c A collection of elements to be added to the tree.
	 */
	public void addAll(Collection<? extends T> c){
            for (T t : c) {
                add(t);
            }
	}
	
	/**
	 * All right, now for the remove method. Just like in the vanilla BST, you
	 * will have to traverse to find the data the user is trying to remove. 
	 * 
	 * You will have three cases:
	 * 
	 * 1. Node to remove has zero children.
	 * 2. Node to remove has one child.
	 * 3. Node to remove has two children.
	 * 
	 * For the first case, you simply return null up the tree. For the second case,
	 * you return the non-null child up the tree. 
	 * 
	 * Just as in add, you'll have to updateHeightAndBF() as well as rotate() just before
	 * you return out of each recursive step.
	 * 
	 * FOR THE THIRD CASE USE THE PREDECESSOR OR YOU WILL LOSE POINTS
	 * 
	 * @param data The data to search in the tree.
	 * @return The data that was removed from the tree.
	 */
	public T remove(T data) {
            
            if (root == null) return null;
            
            if (data == root.getData()) {
                root = null;
                --size;
                return data;
            }
            
            return removeHelper(data, root);
	}
	
        private T removeHelper(T data, AVLNode parent) {
            T parentData = (T) parent.getData();
            AVLNode leftChild = parent.getLeft();
            AVLNode rightChild = parent.getRight();
            
            if (rightChild == null) {
                if (data == null ||
                    ((Comparable)data).compareTo(parentData) > 0) {
                    // data does not exist
                    return null;
                }   
            }
            else {
               if (data == null ||
                   ((Comparable)data).compareTo(parentData) > 0) {
                   // new data > parent data
                   if ( (data == null && rightChild.getData() != null) ||
                        ((Comparable)data).compareTo(rightChild.getData()) != 0) {
                       // new data is greater than parent, but not equal to 
                       // that of the right node
                       return removeHelper(data, rightChild);
                   }
                   else {
                       // removing the right child
                       if (rightChild.getLeft() == null) {
                           if (rightChild.getRight() != null) {
                               // right child has only right child
                               parent.setRight(rightChild.getRight());
                           }
                           rightChild = null;
                           --size;
                           return data;
                       }
                       else {
                           if (rightChild.getRight() == null) {
                               // right child has only left child
                               parent.setRight(rightChild.getLeft());
                           }
                           else {
                               // right child has two children
                               // TODO replace with predecessor data and rotate
                               return null;
                           }
                       }
                   }
               }
            }
            
            if (leftChild == null) {
                if (((Comparable)data).compareTo(parentData) < 0) {
                    // data does not exist
                    return null;
                }
                else {
                    if (((Comparable)data).compareTo(parentData) < 0) {
                        // new data < parent data
                        if (((Comparable)data).compareTo(leftChild.getData()) != 0) {
                            return removeHelper(data, leftChild);
                        }
                        else {
                            // removing the left child
                            if (leftChild.getLeft() == null) {
                               if (leftChild.getRight() != null) {
                                    // right child has only right child
                                    parent.setRight(leftChild.getRight());
                                }
                                leftChild = null;
                                --size;
                                return data;
                            }
                            else {
                                if (leftChild.getRight() == null) {
                                    // right child has only left child
                                    parent.setRight(leftChild.getLeft());
                                }
                                else {
                                    // left child has two children
                                    // replace with predecessor data and rotate
                                    return null;
                                }
                            }
                        }
                    }
                }
            }
            
            // this line of code should never be reached
            return null;
        }
        
	
	/**
	 * This method should be pretty simple, all you have to do is recurse
	 * to the left or to the right and see if the tree contains the data.
	 * 
	 * @param data The data to search for in the tree.
	 * @return The boolean flag that indicates if the data was found in the tree or not.
	 */
	public boolean contains(T data) {
           return containsHelper(data, root);
	}
        
        private boolean containsHelper(T data, AVLNode node) {
            if (data == null ||
                ((Comparable)data).compareTo(node.getData()) > 0) {
                // new data > current node data
                if (node.getRight() == null) return false;
                else return containsHelper(data, node.getRight());
            }
            else if (((Comparable)data).compareTo(node.getData()) < 0) {
                // new data < current node data
                if (node.getLeft() == null) return false;
                else return containsHelper(data, node.getLeft());
            }
            else {
                // new data == current node data
                return true;
            }
        }
	
	
	/**
	 * Again, simply recurse through the tree and find the data that is passed in.
	 * 
	 * @param data The data to fetch from the tree.
	 * @return The data that the user wants from the tree. Return null if not found.
	 */
	public T get(T data) {
            if (data == null) return null;
        
            if (contains(data)) return data;
            return null;
	}
	
	
	/**
	 * Test to see if the tree is empty.
	 * @return A boolean flag that is true if the tree is empty.
	 */
	public boolean isEmpty(){
            return (size == 0);
	}
	
	/**
	 * Return the number of data in the tree.
	 * @return The number of data in the tree.
	 */
	public int size() {
            return size;
	}
	
	/**
	 * Reset the tree to its original state. Get rid of every element in the tree.
	 */
	public void clear() {
            clearHelper(root);
            size = 0;
	}
        
        private void clearHelper(AVLNode node) {
            if (node.getLeft() == null) {
                if (node.getRight() == null) {
                    // remove the leaf
                    node = null;
                }
                else {
                    clearHelper(node.getRight());
                }
            }
            else clearHelper(node.getLeft());
        }
	
	// The below methods are all private, so we will not be directly grading them,
	// however we strongly recommend you not change them, and make use of them.
	
	
	/**
	 * Use this method to update the height and balance factor for a node.
	 * 
	 * @param node The node whose height and balance factor need to be updated.
	 */
	private void updateHeightAndBF(AVLNode<T> node) {
		//TODO Implement this method!
	}
	
	/**
	 * In this method, you will check the balance factor of the node that is passed in and
	 * decide whether or not to perform a rotation. If you need to perform a rotation, simply
	 * call the rotation and return the new root of the balanced subtree. If there is no need
	 * for a rotation, simply return the node that was passed in.
	 * 
	 * @param node - a potentially unbalanced node
	 * @return The new root of the balanced subtree.
	 */
	private AVLNode<T> rotate(AVLNode<T> node) {
		//TODO Implement this method!
		return null;
	}
	
	/**
	 * In this method, you will perform a left rotation. Remember, you perform a 
	 * LEFT rotation when the sub-tree is RIGHT heavy. This moves more nodes over to
	 * the LEFT side of the node that is passed in so that the height differences
	 * between the LEFT and RIGHT subtrees differ by at most one.
	 * 
	 * HINT: DO NOT FORGET TO RE-CALCULATE THE HEIGHT OF THE NODES
	 * WHOSE CHILDREN HAVE CHANGED! YES, THIS DOES MAKE A DIFFERENCE!
	 * 
	 * @param node - the current root of the subtree to rotate.
	 * @return The new root of the subtree
	 */
	private AVLNode<T> leftRotate(AVLNode<T> node) {
		//TODO Implement this method!
		return null;
	}
	
	/**
	 * In this method, you will perform a right rotation. Remember, you perform a
	 * RIGHT rotation when the sub-tree is LEFT heavy. THis moves more nodes over to
	 * the RIGHT side of the node that is passed in so that the height differences
	 * between the LEFT and RIGHT subtrees differ by at most one.
	 * 
	 * HINT: DO NOT FORGET TO RE-CALCULATE THE HEIGHT OF THE NODES
	 * WHOSE CHILDREN HAVE CHANGED! YES, THIS DOES MAKE A DIFFERENCE!
	 * 
	 * @param node - The current root of the subtree to rotate.
	 * @return The new root of the rotated subtree.
	 */
	private AVLNode<T> rightRotate(AVLNode<T> node) {
		//TODO Implement this method!
		return null;
	}
	
	/**
	 * In this method, you will perform a left-right rotation. You can simply use
	 * the left and right rotation methods on the node and the node's child. Remember
	 * that you must perform the rotation on the node's child first, otherwise you will
	 * end up with a mangled tree (sad face). After rotating the child, remember to link up
	 * the new root of the that first rotation with the node that was passed in.
	 * 
	 * The whole point of heterogeneous rotations is to transform the node's 
	 * subtree into one of the cases handled by the left and right rotations.
	 * 
	 * @param node
	 * @return The new root of the subtree.
	 */
	private AVLNode<T> leftRightRotate(AVLNode<T> node) {
		//TODO Implement this method!
		return null;
	}
	
	/**
	 * In this method, you will perform a right-left rotation. You can simply use your
	 * right and left rotation methods on the node and the node's child. Remember
	 * that you must perform the rotation on the node's child first, otherwise
	 * you will end up with a mangled tree (super sad face). After rotating the node's child,
	 * remember to link up the new root of that first rotation with the node that was
	 * passed in.
	 * 
	 * Again, the whole point of the heterogeneous rotations is to first transform the
	 * node's subtree into one of the cases handled by the left and right rotations.
	 * 
	 * @param node
	 * @return The new root of the subtree.
	 */
	private AVLNode<T> rightLeftRotate(AVLNode<T> node) {
		//TODO Implement this method!
		return null;
	}

}
