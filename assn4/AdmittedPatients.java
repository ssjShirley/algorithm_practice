public class AdmittedPatients{
	protected TreeNode root;
	
	
	public AdmittedPatients(){
		root = new TreeNode();
	
	}
	
	public void admit(HospitalPatient patient){
		if (root.item == null){
			root = new TreeNode(patient);
			return;
		} 
		TreeNode curr = root;
		while (curr.item != null){
			int y=patient.compareTo(curr.item);
			if (y < 0){
				if (curr.left == null){
					TreeNode newNode = new TreeNode(patient);
            		curr.left = newNode;
            		return;
        		} else {
					curr = curr.left;
					continue;
				}
			} else if ( y > 0){
				if (curr.right == null){
					TreeNode newNode = new TreeNode(patient);
					curr.right = newNode;
					return;
				} else {
					curr = curr.right;
					continue;
				}
			} else {
				return;
			}
		}
	}
	
	public HospitalPatient getPatientInfo(String id){
		TreeNode curr = root;
		while (curr.item != null){
			int x=id.compareTo(curr.item.getId());
			if ( x < 0){
				curr = curr.left;
			} else if ( x > 0){
				curr = curr.right;
			} else {
				return curr.item;
			}
		}
		return null;
	}
	
	public void discharge(HospitalPatient patient){
		if(getPatientInfo(patient.getId()) == null){
			return;
		} else {
			root = deleteNode(root,patient);
		}
	}
	
	public TreeNode deleteNode(TreeNode myroot, HospitalPatient data){
        if (myroot == null)
			return null;
		if (data.compareTo(myroot.item)<0) {
			myroot.left = deleteNode(myroot.left, data);
		} else if (data.compareTo(myroot.item)>0 ) {
			myroot.right = deleteNode(myroot.right, data);
 
		} else {
			// if nodeToBeDeleted have both children
			if (myroot.left != null && myroot.right != null) {
				TreeNode temp = myroot;
				// Finding minimum element from right
				TreeNode minNodeForRight = minElement(temp.right);
				// Replacing current node with minimum node from right subtree
				myroot.item = minNodeForRight.item;
				// Deleting minimum node from right now
				deleteNode(myroot.right, minNodeForRight.item);
 
			}
			// if nodeToBeDeleted has only left child
			else if (myroot.left != null) {
				myroot = myroot.left;
			}
			// if nodeToBeDeleted has only right child
			else if (myroot.right != null) {
				myroot = myroot.right;
			}
			// if nodeToBeDeleted do not have child (Leaf node)
			else
				myroot = null;
		}
		return myroot;
    }
	
	public static TreeNode minElement(TreeNode a) {
		if (a.left == null)
			return a;
		else {
			return minElement(a.left);
		}
	}
	
	public void printLocations(){
		inorder(root);
	}
	
	public void inorder(TreeNode r){
		if (r == null){
			return ;
		} else {
			inorder (r.left);
			System.out.println(r.item.getLocation());
			inorder(r.right);
		}
	}
	
	public static void main(String[] args){
	
		AdmittedPatients admitted = new AdmittedPatients();
      	HospitalPatient p1 = new HospitalPatient(
      	new SimpleDate(2018,2,27),"Duck","Donald",'C',123);
      	HospitalPatient p2 = new HospitalPatient(
       	new SimpleDate(2018,1,15),"Mouse","Minnie",'B',307);
      	HospitalPatient p3 = new HospitalPatient(
       	new SimpleDate(2018,3,1),"Dog","Goofie",'A',422);
      	HospitalPatient p4 = new HospitalPatient(
       	new SimpleDate(2017,12,25),"Newman","Alfred",'X',111);
      	admitted.admit(p1);
      	admitted.admit(p4);
      	admitted.admit(p3);
      	admitted.admit(p2);
      	admitted.printLocations();
       	// these two lines cause the tree to be viewed in a little
       	// resizable window.
      	ViewableTree dbt = new ViewableTree(admitted);
      	dbt.showFrame();
	
	}


}