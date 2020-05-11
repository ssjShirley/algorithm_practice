/**
 * EmergencyTriage.java
 * Provided the necessary information here,
 * making sure to delete these lines.
 */

// This is the beginning of the source code to help you get started.
// Do not change the following:

public class EmergencyTriage {
	private Heap<ERPatient> waiting;

	/**
	 * The empty EmergencyTriage is initialized.
	 */
	public EmergencyTriage() {
		waiting = new Heap<>();
	}

	public void register(String lastName, String firstName, String triageCategory){
		ERPatient x = new ERPatient(lastName, firstName, triageCategory);
		waiting.insert(x);
	
	}
	
	public ERPatient admitToER(){
		return waiting.getRootItem();
	}
	
	public ERPatient whoIsNext(){
		return waiting.peek();
	}
	
	public int numberOfPatientsWaiting(){
		return waiting.size();
	}
	
	public static void main(String[] args){
		EmergencyTriage a = new EmergencyTriage();
		a.register("Shu", "Elaine", "major fracture");
		System.out.println(a.whoIsNext());
		System.out.println(a.numberOfPatientsWaiting());
		a.register("Zhang", "Mark", "Acute");
		System.out.println(a.whoIsNext());
		System.out.println(a.numberOfPatientsWaiting());
		System.out.println(a.admitToER());
		System.out.println(a.whoIsNext());
		System.out.println(a.numberOfPatientsWaiting());
		System.out.println(a.admitToER());
	}
}
	