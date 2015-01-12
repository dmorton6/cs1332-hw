
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * CS 1332 Fall 2013 Binary Search Tree
 *
 * In this assignment, you will be coding methods to make a functional binary
 * search tree. If you do this right, you will save a lot of time in the next
 * two assignments (since they are just augmenting the BST to make it
 * efficient). Let's get started!
 *
 * **************************NOTE************************ YOU WILL HAVE TO
 * HANDLE NULL DATA IN THIS ASSIGNMENT!! PLEASE TREAT NULL AS POSITIVE
 * INFINITY!!!! **************************NOTE************************
 *
 * DO NOT CHANGE ANY OF THE PUBLIC METHOD HEADERS
 *
 * Please make any extra inner classes, instance fields, and methods private
 */
public class BST<T extends Comparable<T>> {

    private Node<T> root;
    private int size;

    /**
     * Add data to the binary search tree. Remember to adhere to the BST
     * Invariant: All data to the left of a node must be smaller and all data to
     * the right of a node must be larger. Don't forget to update the size.
     *
     * For this method, you will need to traverse the tree and find the
     * appropriate location of the data. Depending on the data's value, you will
     * either explore the right subtree or the left subtree. When you reach a
     * dead end (you have reached a null value), simply return a new node with
     * the data that was passed in.
     *
     * PLEASE TREAT NULL DATA AS POSITIVE INFINITY!!!!
     *
     * @param data A comparable object to be added to the tree.
     */
    public void add(T data) {
        Node curNode = root;
        Node newNode = new Node(data);

        if (root == null) {
            root = newNode;
            ++size;
            return;
        }
        
        if (data == null) {
            // move it as far to the right as possible
            while (curNode.getRight() != null) {
                curNode = curNode.getRight();
            }
            curNode.setRight(newNode);
            ++size;
            return;
        }

        // non-empty list with non-null data
        while (curNode != null) {
                        
            if (curNode.getData() == null || 
                ((Comparable) data).compareTo(curNode.getData()) < 0) {
                // new data < current node data
                if (curNode.getLeft() == null) {
                    curNode.setLeft(newNode);
                    break;
                } 
                else {
                    curNode = curNode.getLeft();
                }
            } 
            else {
                // new data > current node data
                if (curNode.getRight() == null) {
                    curNode.setRight(newNode);
                    break;
                } 
                else {
                    curNode = curNode.getRight();
                }
            }
        }

        ++size;
    }

    /**
     * Add the contents of the collection to the BST. To do this method, notice
     * that most every collection in the java collections API implements the
     * iterable interface. This means that you can iterate through every element
     * in these structures with a for-each loop. Don't forget to update the
     * size.
     *
     * @param collection A collection of data to be added to the tree.
     */
    public void addAll(Collection<? extends T> c) {
        for (T t : c) {
            add(t);
        }
    }

    /**
     * Remove the data element from the tree.
     *
     * There are three cases you have to deal with: 1. The node to remove has no
     * children 2. The node to remove has one child 2. The node to remove has
     * two children
     *
     * In the first case, return null. In the second case, return the non-null
     * child. The third case is where things get interesting. Here, you have two
     * you will have to find the successor or predecessor and then copy their
     * data into the node you want to remove. You will also have to fix the
     * successor's or predecessor's children if necessary. Don't forget to
     * update the size.
     *
     * PLEASE TREAT NULL DATA AS POSITIVE INFINITY!
     *
     * @param data The data element to be searched for.
     * @return retData The data that was removed from the tree. Return null if
     * the data doesn't exist.
     */
    public T remove(T data) {
        Node curNode = root;    
        Node left, right;
        
        // return null if tree is empty
        if (curNode == null) return null;
        
        if (data == root.getData()) {
            // removing the root - clearing the list
            T rootData = root.getData();
            clear();
            return rootData;
        }
                    
        if (data == null) {
            if (root.getData() == null) {
                // root will not have a right child
                if (root.getLeft() == null) {
                    // removing the only node in the tree
                    root = null;
                }
                else {
                    // root has left child... set this to root
                    root = root.getLeft();
                }
            }
            else if (root.getRight() == null) {
                // the null data does not exist in the tree
                return null;
            }
            else if (root.getRight().getData() == null) {
               return delete(root, "right");
            }
            else {
                // iterate to what should be the parent of the null-data child
                while (curNode.getRight().getRight() != null) {
                    curNode = curNode.getRight();
                }
                // see if the child actually has null data
                // if so, remove the node... if not, return null regardless
                if (curNode.getRight().getData() == null) {
                    return delete(curNode, "right");
                }
            }
        }
        
        while (curNode != null) {
            left = curNode.getLeft();
            right = curNode.getRight();
            
            if ( ((Comparable)data).compareTo(curNode.getData()) > 0) {
                // new data > current node data
                // compare to right child
                if (right == null) {
                    // the data must not exist in the tree
                    return null;
                }
                else if (right.getData() == null || 
                    ((Comparable)data).compareTo(right.getData()) != 0) {
                    // new data is not right child data
                    curNode = right;
                }
                else {
                    // right child is the node to remove
                    return delete(curNode, "right");
                }
            }
            else if (((Comparable)data).compareTo(curNode.getData()) < 0) {
                // new data < current node data
                // compare to left child
                if (left == null) {
                    // the data must not exist in the tree
                    return null;
                }
                // note that left child data will never be null
                else if ( ((Comparable)data).compareTo(left.getData()) != 0) {
                    curNode = left;
                }
                else {
                    // left child is the node to remove
                    return delete(curNode, "left");
                }
            }
            else {
                // removing the head of the list
            }
        }
            

        // this should never be reached, but is needed to compile
        return null;
    }
    
    /**
     * Helper method for the remove() method
     * @param parent the parent of the node to be deleted
     * @param child either "left" or "right" (which child to delete)
     * @return The data of that node
     */
    private T delete(Node parent, String child) {
        Node toDelete;
        Node leftGC, rightGC; // grandchildren
        T childData;
        
        if (child.equals("left")) {
            // deleting the left child
            toDelete = parent.getLeft();
            childData = (T) toDelete.getData();
            
            leftGC = parent.getLeft().getLeft();
            rightGC = parent.getLeft().getRight();
            
            if (leftGC == null && rightGC == null) {
                // node to delete is a leaf - just set it to null
                parent.setLeft(null);
            }
            else if (leftGC == null && rightGC != null) {
                // node to delete has null left child and non-null right child
                parent.setLeft(rightGC);
            }
            else if (leftGC != null && rightGC == null) {
                // node to delete has non-null left child and null right child
                parent.setLeft(leftGC);
            }
            else {
                // node to delete has two non-null children
                parent.setLeft(leftGC);
                if (leftGC.getRight() == null) {
                    leftGC.setRight(rightGC);
                }
                else {
                    leftGC.getRight().setRight(rightGC);
                }
            }
            toDelete = null;
            --size;
            return childData;
        }
        else {
            // deleting the right child
            toDelete = parent.getRight();
            childData = (T) toDelete.getData();
            
            leftGC = parent.getRight().getLeft();
            rightGC = parent.getRight().getRight();
            
            if (leftGC == null && rightGC == null) {
                // left child is a leaf - just set it to null
                childData = (T) parent.getRight().getData();
                parent.setRight(null);
            }
            else if (leftGC == null && rightGC != null) {
                // node to delete has null left child and non-null right child
                parent.setRight(rightGC);
            }
            else if (leftGC != null && rightGC == null) {
                // node to delete has non-null left child and null right child
                parent.setRight(leftGC);
            }
            else {
                // node to delete has two non-null children
                parent.setRight(leftGC);
                if (leftGC.getRight() == null) {
                    leftGC.setRight(rightGC);
                }
                else {
                    leftGC.getRight().setRight(rightGC);
                }
            }
            toDelete = null;
            --size;
            return childData;
        }
        
    }

    /**
     * Get the data from the tree.
     *
     * This method simply returns the data that was stored in the tree.
     *
     * TREAT NULL DATA AS POSITIVE INFINITY!
     *
     * @param data The datum to search for in the tree.
     * @return The data that was found in the tree. Return null if the data
     * doesn't exist.
     */
    public T get(T data) {
        if (data == null) return null;
        
        if (contains(data)) return data;
        return null;
    }

    /**
     * See if the tree contains the data.
     * TREAT NULL DATA AS POSITIVE INFINITY!
     * @param data The data to search for in the tree.
     * @return Return true if the data is in the tree, false otherwise.
     */
    public boolean contains(T data) {
        if (data != null) {
            return (get(data) != null);
        }
        else {
            //TODO alter this
            return false;
        }
    }

    /**
     * Linearize the tree using the pre-order traversal.
     * @return A list that contains every element in pre-order.
     */
    public List<T> preOrder() {
        ArrayList<T> preList = new ArrayList<>();
        
        preOrderHelper(root, preList);
        
        return preList; 
    }

    private void preOrderHelper(Node n, ArrayList<T> preList) {
        if (n == null) return;
        
        preList.add((T) n.getData());
        preOrderHelper(n.getLeft(), preList);
        preOrderHelper(n.getRight(), preList);
    }
            
    /**
     * Linearize the tree using the in-order traversal.
     * @return A list that contains every element in-order.
     */
    public List<T> inOrder() {
        ArrayList<T> inList = new ArrayList<>();
        
        inOrderHelper(root, inList);
        
        return inList; 
    }
    
    private void inOrderHelper(Node n, ArrayList<T> inList) {
        if (n == null) return;
        
        inOrderHelper(n.getLeft(), inList);
        inList.add((T) n.getData());
        inOrderHelper(n.getRight(), inList);
    }

    /**
     * Linearize the tree using the post-order traversal.
     * @return A list that contains every element in post-order.
     */
   public List<T> postOrder() {
       ArrayList<T> postList = new ArrayList<>();
       
       postOrderHelper(root, postList);
       
       return postList; 
   }
    
   private void postOrderHelper(Node n, ArrayList<T> postList) {
       if (n == null) return;
       
       postOrderHelper(n.getLeft(), postList);
       postOrderHelper(n.getRight(), postList);
       postList.add((T) n.getData());
   }

    /**
     * Test to see if the tree is empty.
     * @return Return true if the tree is empty, false otherwise.
     */
    public boolean isEmpty() {
        return (root == null);
    }

    /**
     * @return Return the number of elements in the tree.
     */
    public int size() {
        return size;
    }

    /**
     * Clear the tree. (ie. set root to null and size to 0)
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Clear the existing tree, and rebuilds a unique binary search tree with
     * the pre-order and post-order traversals that are passed in. Draw a tree
     * out on paper and generate the appropriate traversals. See if you can
     * manipulate these lists to generate the same tree.
     *
     * TL;DR - at the end of this method, the tree better have the same
     * pre-order and post-order as what was passed in.
     *
     * @param preOrder A list containing the data in a pre-order linearization.
     * @param postOrder A list containing the data in a post-order
     * linearization.
     */
    public void reconstruct(List<? extends T> preOrder, List<? extends T> postOrder) {
        Node theRoot = root; // this seems silly but comes in handy soon
        
        clear();
        reconstructHelper(preOrder, postOrder, theRoot);
    }

    private void reconstructHelper(List<? extends T> preOrder, List<? extends T> postOrder,
                                   Node theRoot) {
        
    }
}
