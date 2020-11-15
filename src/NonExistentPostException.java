public class NonExistentPostException extends Exception{
	public NonExistentPostException(){
		super("The specified post doesn't exist");
	}
}
