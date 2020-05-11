import java.util.*;

public class VigenereCipher implements Cipher{
	private String key;
	
	public VigenereCipher(String key){
		this.key = key;
	}
	
	public void setKey(String key){
		this.key = key;
	}
	
	public void dumpArray(int[] array, String text){
		System.out.print(text);
		System.out.print(Arrays.toString(array) + "\n");
	}
	
	public String decrypt(String ciphertext){
		int[] carray1 = stringToIntArray(ciphertext);
		int[] carray2 = new int[ciphertext.length()];
		int[] k = stringToIntArray(key);
		for (int i = 0, j = 0; i < ciphertext.length(); i++, j = i % k.length)
		{
			carray2[i] = (26 + carray1[i] - k[j]) % 26; 
		}
		ciphertext = intArrayToString(carray2);
		return ciphertext;
	}
	
	public String encrypt(String plaintext){
		int[] parray1 = stringToIntArray(plaintext);
		int[] parray2 = new int[plaintext.length()];
		int[] k = stringToIntArray(key);
		for (int i = 0, j = 0; i < plaintext.length(); i++, j = i % k.length)
		{
			parray2[i] = (parray1[i] + k[j]) % 26; 
		}
		plaintext = intArrayToString(parray2);
		return plaintext;
	}
	
	public String intArrayToString(int[] encodedText){
		char[] arr = new char[encodedText.length];
		for(int i = 0; i < encodedText.length; i++)
		{
			int letter = encodedText[i] + 97;
			arr[i] = (char)letter;
		}
		String s = String.copyValueOf(arr);
		return s;
	}
	
	private int[] stringToIntArray(String text){
		int [] a = new int[text.length()];
		for(int i = 0; i < text.length(); i++)
		{
			char letter = text.charAt(i);
			int number = letter - 97;
			a[i] = number;
		}
		return a;
	}	

	public static void main(String[] args){
		// the following statement creates an object
		// this object provides the access to all the methods
		// (even the private ones)
		
		VigenereCipher vc = new VigenereCipher("dd"); 
		System.out.println("converting 'blog' to an array of ints"); 
		int[] toNums = vc.stringToIntArray("blog"); 
		vc.dumpArray(toNums, "result:");
		String toStr = vc.intArrayToString(toNums);
		System.out.println(toStr);
		vc.setKey("bob");
		System.out.println(vc.decrypt("uvfnsttohf"));
		System.out.println(vc.encrypt("themessage"));
	}

}