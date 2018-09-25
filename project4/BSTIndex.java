
public class BSTIndex {
	
	private	BST root;
		
	//Constructor
	public BSTIndex(){
		root = new BST(null);
	}
	
	
	//Purpose: to find the corresponding MovieInfo with the exact name as key
	//Input: Name user would like to search
	//Output: MovieInfo cooresponding to the name
	public MovieInfo findExact(String key){
		
		if(root == null){
			System.out.println("Exact Search not found");
		}
		
		BST current = root; 
		
		//Searches through tree until current is not null
		while(current != null){
			if (key.equalsIgnoreCase(current.key)){
				return current.data;
			}
			else if (key.compareToIgnoreCase(current.key) > 0 ){
				current = current.right;
			}
			else if(key.compareToIgnoreCase(current.key) < 0){
				current = current.left;
			}
		}
		
		System.out.println("Exact Serach Not Found");
		return null;
	}
	
	//Purpose: to find the corresponding MovieInfo with a string prefix
	//Input: String prefix that ends with *
	//Output: Corresponding MovieInfo that contains the prefix.
	public MovieInfo findPrefix(String prefix){
		
		if(root == null){
			System.out.println("Prefix Search Not Found");
		}
		
		BST current = root; 
		
		StringBuilder newPrefix = new StringBuilder(prefix);
		newPrefix.deleteCharAt(newPrefix.length()-1);
		
		prefix = String.valueOf(newPrefix);
		
		//Search through the tree while current is not null
		while(current != null){
			
			if(containsIgnoreCase(prefix, current.key)) {
				return current.data;
			}			
			else if (prefix.compareToIgnoreCase(current.key) > 0 ){
				current = current.right;
			}
			else if(prefix.compareToIgnoreCase(current.key) < 0){
				current = current.left;
			}
		}
		
		//Not in BST
		System.out.println("Prefix Search Not Found");
		return null;
	}
	
	//Purpose: to check if string current starts with string prefix
	//Output: true (if current starts with prefix), false otherwise
	private boolean containsIgnoreCase(String prefix, String current){
		
		prefix = prefix.toUpperCase();
		current = current.toUpperCase();
		
		return current.startsWith(prefix);
	}
	
	//Purpose: Call on recInsert and updates root
	public void insert(MovieInfo data){
		root = recInsert(root, data);
	}
	
	//Purpose: To add a new Node in the Binary Search Tree
	//Input: root and data to add
	//Output: the updated Binary Search Tree with new node
	private BST recInsert(BST root, MovieInfo data) {
		
		BST newNode = new BST(data);
		newNode.key = data.shortName;
		
		if(root == null){
			return newNode;
		}
		
		if (root.data == null){
			root = newNode;
			root.key= data.shortName;
			return root;			
		}
			
		if(newNode.key.compareTo(root.key) < 0){
			root.left = recInsert(root.left, data);
		}
		else if (newNode.key.compareTo(root.key)>0){
			root.right = recInsert(root.right, data);
		}
		else{
			root.data = newNode.data;
			root.key = data.shortName;
		}
		
		return root;
	}

	//Inner Class Used to Create a Binary Search Tree
	private class BST{
		BST left;
		BST right; 
		String key; 	//key = data's shortName
		MovieInfo data;
		
		public BST(MovieInfo data){
			this.data=data;
		}
		
	}
}
