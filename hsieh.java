package run;
import java.lang.StringBuilder;
class AvlTreeT extends Comparable super T {
   
    AvlNode is a container class that is used to store each element 
    (node) of an AVL tree. 
   
  protected static class AvlNodeT {
    
    
      Node data
     
    protected T  element;
    
    
      Left child
     
    protected AvlNodeT    left;
    
    
      Right child
     
    protected AvlNodeT    right;
    
    
      Height of node
     
    protected int      height;
    
    
      Constructor; creates a node without any children
      
      @param theElement  The element contained in this node
     
    public AvlNode (T theElement){
      this (theElement, null, null);
    }
    
    
      Constructor; creates a node with children
      
      @param theElement  The element contained in this node
      @param lt      Left child    
      @param rt      Right child
     
    public AvlNode (T theElement, AvlNodeT lt, AvlNodeT rt){
      element = theElement;
      left = lt;
      right = rt;
    }
  }

  public AvlNodeT root;
  
   TODO make these optional based on some sort of 'debug' flag
   at the very least, make them read-only properties
  public int countInsertions;
  public int countSingleRotations;
  public int countDoubleRotations;
  
  
    Avl Tree Constructor.
    
    Creates an empty tree
   
  public AvlTree (){
    root = null;
        
    countInsertions = 0;
    countSingleRotations = 0;
    countDoubleRotations = 0;    
  }
  
  
    Determine the height of the given node.
    
    @param t Node
    @return Height of the given node.
   
  public int height (AvlNodeT t){
    return t == null  -1  t.height;
  }
  
  
    Find the maximum value among the given numbers.
    
    @param a First number
    @param b Second number
    @return Maximum value
     
  public int max (int a, int b){
    if (a > b)
      return a;
    return b;
  }
  
  
    Insert an element into the tree.
    
    @param x Element to insert into the tree
    @return True - Success, the Element was added. 
            False - Error, the element was a duplicate.
   
  public boolean insert (T x){
    try {
      root = insert (x, root);
      
      countInsertions++;
      return true;
    } catch(Exception e){  TODO catch a DuplicateValueException instead!
      return false;
    }
  }
  
  
    Internal method to perform an actual insertion.
    
    @param x Element to add
    @param t Root of the tree
    @return New root of the tree
    @throws Exception 
   
  protected AvlNodeT insert (T x, AvlNodeT t) throws Exception{
    if (t == null)
      t = new AvlNodeT (x);
    else if (x.compareTo (t.element)  0){
      t.left = insert (x, t.left);
      
      if (height (t.left) - height (t.right) == 2){
        if (x.compareTo (t.left.element)  0){
          t = rotateWithLeftChild (t);
          countSingleRotations++;
        }
        else {
          t = doubleWithLeftChild (t);
          countDoubleRotations++;
        }
      }
    }
    else if (x.compareTo (t.element)  0){
      t.right = insert (x, t.right);
      
      if (height (t.right) - height (t.left) == 2)
        if (x.compareTo (t.right.element)  0){
          t = rotateWithRightChild (t);
          countSingleRotations++;
        }
        else{
          t = doubleWithRightChild (t);
          countDoubleRotations++;
        }
    }
    else {
      throw new Exception(Attempting to insert duplicate value);
    }
    
    t.height = max (height (t.left), height (t.right)) + 1;
    return t;
  }
  
  
    Rotate binary tree node with left child.
    For AVL trees, this is a single rotation for case 1.
    Update heights, then return new root.
    
    @param k2 Root of tree we are rotating
    @return New root
   
  protected AvlNodeT rotateWithLeftChild (AvlNodeT k2){
    AvlNodeT k1 = k2.left;
    
    k2.left = k1.right;
    k1.right = k2;
    
    k2.height = max (height (k2.left), height (k2.right)) + 1;
    k1.height = max (height (k1.left), k2.height) + 1;
    
    return (k1);
  }
  
  
    Double rotate binary tree node first left child
    with its right child; then node k3 with new left child.
    For AVL trees, this is a double rotation for case 2.
    Update heights, then return new root.
    
    @param k3 Root of tree we are rotating
    @return New root
   
  protected AvlNodeT doubleWithLeftChild (AvlNodeT k3){
    k3.left = rotateWithRightChild (k3.left);
    return rotateWithLeftChild (k3);
  }
  
  
    Rotate binary tree node with right child.
    For AVL trees, this is a single rotation for case 4.
    Update heights, then return new root.
    
    @param k1 Root of tree we are rotating.
    @return New root
   
  protected AvlNodeT rotateWithRightChild (AvlNodeT k1){
    AvlNodeT k2 = k1.right;
    
    k1.right = k2.left;
    k2.left = k1;
    
    k1.height = max (height (k1.left), height (k1.right)) + 1;
    k2.height = max (height (k2.right), k1.height) + 1;
    
    return (k2);
  }

  
    Double rotate binary tree node first right child
    with its left child; then node k1 with new right child.
    For AVL trees, this is a double rotation for case 3.
    Update heights, then return new root.
    
    @param k1 Root of tree we are rotating
    @return New root
   
  protected AvlNodeT doubleWithRightChild (AvlNodeT k1){
    k1.right = rotateWithLeftChild (k1.right);
    return rotateWithRightChild (k1);
  }


  
    Serialize the tree to a string using an infix traversal.
    
    In other words, the tree items will be serialized in numeric order. 
    
    @return String representation of the tree
   
  public String serializeInfix(){
    StringBuilder str = new StringBuilder();
    serializeInfix (root, str,  );
    return str.toString();
  }

  
    Internal method to infix-serialize a tree.
    
    @param t    Tree node to traverse
    @param str  Accumulator; string to keep appending items to.
   
  protected void serializeInfix(AvlNodeT t, StringBuilder str, String sep){
    if (t != null){
      serializeInfix (t.left, str, sep);
      str.append(t.element.toString());
      str.append(sep);
      serializeInfix (t.right, str, sep);
    }    
  }
  
  
    Serialize the tree to a string using a prefix traversal.
    
    In other words, the tree items will be serialized in the order that
    they are stored within the tree. 
    
    @return String representation of the tree
     
  public String serializePrefix(){
    StringBuilder str = new StringBuilder();
    serializePrefix (root, str,  );
    return str.toString();
  }
  
  
    Internal method to prefix-serialize a tree.
    
    @param t    Tree node to traverse
    @param str  Accumulator; string to keep appending items to.
     
  private void serializePrefix (AvlNodeT t, StringBuilder str, String sep){
    if (t != null){
      str.append(t.element.toString());
      str.append(sep);
      serializePrefix (t.left, str, sep);
      serializePrefix (t.right, str, sep);
    }
  }
  
  
    Deletes all nodes from the tree.
   
   
  public void makeEmpty(){
    root = null;
  }
  
  
    Determine if the tree is empty.
    
    @return True if the tree is empty 
   
  public boolean isEmpty(){
    return (root == null);
  }



    
      Find the smallest item in the tree.
      @return smallest item or null if empty.
     
    public T findMin( )
    {
        if( isEmpty( ) ) return null;

        return findMin( root ).element;
    }

    
      Find the largest item in the tree.
      @return the largest item of null if empty.
     
    public T findMax( )
    {
        if( isEmpty( ) ) return null;
        return findMax( root ).element;
    }

    
      Internal method to find the smallest item in a subtree.
      @param t the node that roots the tree.
      @return node containing the smallest item.
     
    private AvlNodeT findMin(AvlNodeT t)
    {
        if( t == null )
            return t;

        while( t.left != null )
            t = t.left;
        return t;
    }

    
      Internal method to find the largest item in a subtree.
      @param t the node that roots the tree.
      @return node containing the largest item.
     
    private AvlNodeT findMax( AvlNodeT t )
    {
        if( t == null )
            return t;

        while( t.right != null )
            t = t.right;
        return t;
    }

  
    Remove from the tree. Nothing is done if x is not found.
    @param x the item to remove.
   
  public void remove( T x ) {
      root = remove(x, root);
  }

  public AvlNodeT remove(T x, AvlNodeT t) {
      if (t==null)    {
          System.out.println(Sorry but you're mistaken,  + t +  doesn't exist in this tree )n);
          return null;
      }
      System.out.println(Remove starts...  + t.element +  and  + x);
  
      if (x.compareTo(t.element)  0 ) {
          t.left = remove(x,t.left);
          int l = t.left != null  t.left.height  0;
  
          if((t.right != null) && (t.right.height - l = 2)) {
              int rightHeight = t.right.right != null  t.right.right.height  0;
              int leftHeight = t.right.left != null  t.right.left.height  0;
  
              if(rightHeight = leftHeight)
                  t = rotateWithLeftChild(t);            
              else
                  t = doubleWithRightChild(t);
          }
      }
      else if (x.compareTo(t.element)  0) {
          t.right = remove(x,t.right);
          int r = t.right != null  t.right.height  0;
          if((t.left != null) && (t.left.height - r = 2)) {
              int leftHeight = t.left.left != null  t.left.left.height  0;
              int rightHeight = t.left.right != null  t.left.right.height  0;

              if(leftHeight = rightHeight)
                  t = rotateWithRightChild(t);               
              else
                  t = doubleWithLeftChild(t);
          }
      }
      
         Here, we have ended up when we are node which shall be removed. 
         Check if there is a left-hand node, if so pick out the largest element out, and move down to the root.
       
      else if(t.left != null) {
          t.element = findMax(t.left).element;
          remove(t.element, t.left);
       
          if((t.right != null) && (t.right.height - t.left.height = 2)) {
              int rightHeight = t.right.right != null  t.right.right.height  0;
              int leftHeight = t.right.left != null  t.right.left.height  0;

              if(rightHeight = leftHeight)
                  t = rotateWithLeftChild(t);            
              else
                  t = doubleWithRightChild(t);
          }
      }
       
      else
          t = (t.left != null)  t.left  t.right;
       
      if(t != null) {
          int leftHeight = t.left != null  t.left.height  0;
          int rightHeight = t.right!= null  t.right.height  0;
          t.height = Math.max(leftHeight,rightHeight) + 1;
      }
      return t;
  } End of remove...

  
    Search for an element within the tree. 
   
    @param x Element to find
    @return True if the element is found, false otherwise
   
  public boolean contains(T x){
    return contains(x, root);
  }

  
    Internal find method; search for an element starting at the given node.
   
    @param x Element to find
    @return True if the element is found, false otherwise
   
  protected boolean contains(T x, AvlNodeT t) {
    if (t == null){
      return false;  The node was not found

    } else if (x.compareTo(t.element)  0){
      return contains(x, t.left);
    } else if (x.compareTo(t.element)  0){
      return contains(x, t.right); 
    }

    return true;  Can only reach here if node was found
  }
  
  
   Diagnostic functions for the tree
  public boolean checkBalanceOfTree(AvlTree.AvlNodeInteger current) {
    
    boolean balancedRight = true, balancedLeft = true;
    int leftHeight = 0, rightHeight = 0;
    
    if (current.right != null) {
      balancedRight = checkBalanceOfTree(current.right);
      rightHeight = getDepth(current.right);
    }
    
    if (current.left != null) {
      balancedLeft = checkBalanceOfTree(current.left);
      leftHeight = getDepth(current.left);
    }
    
    return balancedLeft && balancedRight && Math.abs(leftHeight - rightHeight)  2;
  }
  
  public int getDepth(AvlTree.AvlNodeInteger n) {
    int leftHeight = 0, rightHeight = 0;
    
    if (n.right != null)
      rightHeight = getDepth(n.right);
    if (n.left != null)
      leftHeight = getDepth(n.left);
    
    return Math.max(rightHeight, leftHeight)+1;
  }
  
  public boolean checkOrderingOfTree(AvlTree.AvlNodeInteger current) {
    if(current.left != null) {
      if(current.left.element.compareTo(current.element)  0)
        return false;
      else
        return checkOrderingOfTree(current.left);
    } else  if(current.right != null) {
      if(current.right.element.compareTo(current.element)  0)
        return false;
      else
        return checkOrderingOfTree(current.right);
    } else if(current.left == null && current.right == null)
      return true;
    
    return true;
  }

  
    Main entry point; contains test code for the tree.
   
  public static void main (String []args){
    AvlTreeInteger t = new AvlTree();
    
    t.insert (2);
    t.insert (1);
    t.insert (4);
    t.insert (5);
    t.insert (9);
    t.insert (3);
    t.insert (6);
    t.insert (7);
    
    System.out.println (Infix Traversal);
    System.out.println(t.serializeInfix());
     1 2 3 4 5 6 7 9

    System.out.println (Prefix Traversal);
    System.out.println(t.serializePrefix());
     4 2 1 3 6 5 9 7
  }
}
