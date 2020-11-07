public class PostTest {
	public static void testConstructor(){
		System.out.println("\tTesting Post constructor");

		try {
			System.out.println("\t\tNull author:");
			Post p = new Post(null, "testText");
			System.err.println("\t\t\tPost was created with null author");
		}catch(InvalidUsernameException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
			System.out.println("\t\t\tUnable to create a post with a null author");
		}catch(Exception e){
			System.err.println("\t\t\tUnknown exception: " + e.getLocalizedMessage());
		}

		try {
			System.out.println("\t\tauthor.length() == 0:");
			Post p = new Post("", "testText");
			System.err.println("\t\t\tPost was created with author.length() == 0");
		}catch(InvalidUsernameException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
			System.out.println("\t\t\tUnable to create a post with author.length() == 0");
		}catch(Exception e){
			System.err.println("\t\t\tUnknown exception: " + e.getLocalizedMessage());
		}

		try {
			System.out.println("\t\tNull text:");
			Post p = new Post("testAuthor", null);
			System.err.println("\t\t\tPost was created with null text");
		}catch(InvalidPostTextException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
			System.out.println("\t\t\tUnable to create a post with null text");
		}catch(Exception e){
			System.err.println("\t\t\tUnknown exception: " + e.getLocalizedMessage());
		}

		try {
			System.out.println("\t\ttext.length() == 0:");
			Post p = new Post("testAuthor", "");
			System.err.println("\t\t\tPost was created with text.length() == 0");
		}catch(InvalidPostTextException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
			System.out.println("\t\t\tUnable to create a post with text.length() == 0");
		}catch(Exception e){
			System.err.println("\t\t\tUnknown exception: " + e.getLocalizedMessage());
		}
	}
}
