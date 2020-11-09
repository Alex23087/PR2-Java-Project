import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

		try {
			System.out.println("\t\tValid post:");
			Post p = new Post("testAuthor", "testText");
			System.out.println("\t\t\tPost was created correctly");
		}catch(InvalidPostTextException | InvalidUsernameException iue){
			System.err.println("\t\t\t" + iue.getLocalizedMessage());
			System.err.println("\t\t\tUnable to create post");
		} catch(Exception e){
			System.err.println("\t\t\tUnknown exception: " + e.getLocalizedMessage());
		}
	}

	public static void testContainsAny(){
		System.out.println("\tTesting Post.containsAny");
		Post p;
		try {
			p = new Post("author", "test text that will be tested by this test function");
		}catch(Exception e){
			System.err.println("\t\tSomething went wrong while creating the test post: " + e.getLocalizedMessage());
			return;
		}
		List<String> listContained = new ArrayList<>();
		List<String> listNotContained = new ArrayList<>();

		listContained.add("text");
		listContained.add("test");

		listNotContained.add("dijkstra");
		listNotContained.add("lambda");


		System.out.println("\t\tNull list:");
		if(p.containsAny(null)){
			System.out.println("\t\t\tReturned true correctly");
		}else{
			System.err.println("\t\t\tReturned false");
		}

		System.out.println("\t\tEmpty list:");
		if(p.containsAny(new ArrayList<String>(0))){
			System.out.println("\t\t\tReturned true correctly");
		}else{
			System.err.println("\t\t\tReturned false");
		}


		System.out.println("\t\tList that contains a word that is in the post text:");
		if(p.containsAny(listContained)){
			System.out.println("\t\t\tReturned true correctly");
		}else{
			System.err.println("\t\t\tReturned false");
		}

		System.out.println("\t\tList that does not contain words that are in the post text:");
		if(p.containsAny(listNotContained)){
			System.err.println("\t\t\tReturned true");
		}else{
			System.out.println("\t\t\tReturned false correctly");
		}
	}

	public static void testGetAuthor(){
		System.out.println("\tTesting Post.getAuthor");
		Post p;
		try {
			p = new Post("author", "test text that will be tested by this test function");
		}catch(Exception e){
			System.err.println("\t\tSomething went wrong while creating the test post: " + e.getLocalizedMessage());
			return;
		}

		if("author".equals(p.getAuthor())){
			System.out.println("\t\t\tReturned correct value");
		}else{
			System.err.println("\t\t\tReturned wrong value");
		}
	}

	public static void testGetMentioned(){
		System.out.println("\tTesting Post.getMentioned");
		Post p;
		try {
			p = new Post("author", "Here's a list of some languages that get their names from people: Ada, Pascal, Haskell, Wolfram");
		}catch(Exception e){
			System.err.println("\t\tSomething went wrong while creating the test post: " + e.getLocalizedMessage());
			return;
		}

		System.out.println("\t\tPassing null as parameter");
		Set<String> foundNull = p.getMentioned(null);
		if(foundNull.isEmpty()){
			System.out.println("\t\t\tCorrect value returned");
		}else{
			System.err.println("\t\t\tWrong value returned");
		}

		System.out.println("\t\tPassing an empty list as parameter");
		Set<String> foundEmpty = p.getMentioned(new ArrayList<>(0));
		if(foundEmpty.isEmpty()){
			System.out.println("\t\t\tCorrect value returned");
		}else{
			System.err.println("\t\t\tWrong value returned");
		}

		List<String> users = new ArrayList<>();
		users.add("Ada");
		users.add("Pascal");
		users.add("Haskell");
		users.add("Wolfram");

		System.out.print("\t\tFinding valid users:\n\t\t\t");
		Set<String> foundUsers = p.getMentioned(users);
		for(String user : foundUsers){
			System.out.print(user + " ");
			if(!users.remove(user)){
				System.err.println("\n\t\tThis user shouldn't be returned from the method");
				break;
			}
		}
		System.out.print("\n");
	}

	public static void testGetText(){
		System.out.println("\tTesting Post.getText");
		Post p;
		try {
			p = new Post("author", "test text");
		}catch(Exception e){
			System.err.println("\t\tSomething went wrong while creating the test post: " + e.getLocalizedMessage());
			return;
		}

		if(p.getText().equals("test text")){
			System.out.println("\t\tCorrect text returned");
		}else{
			System.err.println("\t\tWrong text returned");
		}
	}
}
