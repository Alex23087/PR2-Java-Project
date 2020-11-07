import javafx.util.Pair;

public class SocialNetworkTest {
	public SocialNetwork SN;

	public SocialNetworkTest(){
		SN = new SocialNetwork();
	}

	public void addUsers(String[] users){
		for(String user : users){
			try {
				SN.addUser(user);
			}catch(Exception e){}
		}
	}

	public void addPosts(Pair<String, String>[] posts){
		for(Pair<String, String> post : posts) {
			try {
				SN.createPost(post.getKey(), post.getValue());
			}catch(Exception e){}
		}
	}

	public void resetSocialNetwork(){
		SN = new SocialNetwork();
	}

	public void testAddUser(){
		System.out.println("\tTesting SocialNetwork.addUser:");
		try {
			System.out.println("\t\tAdding null username:");
			SN.addUser(null);
			System.err.println("\t\t\tNULL USERNAME WAS ADDED");
		}catch(InvalidUsernameException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
			System.out.println("\t\t\tUnable to add a null username");
		}catch(Exception e){
			System.err.println("\t\t\tUnknown exception: " + e.getLocalizedMessage());
		}

		try {
			System.out.println("\t\tAdding empty username:");
			SN.addUser("");
			System.err.println("\t\t\tEMPTY USERNAME WAS ADDED");
		}catch(InvalidUsernameException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
			System.out.println("\t\t\tUnable to add an empty username");
		}catch(Exception e){
			System.err.println("\t\t\tUnknown exception: " + e.getLocalizedMessage());
		}

		try {
			System.out.println("\t\tAdding username starting with whitespae:");
			SN.addUser(" test1");
			System.err.println("\t\t\tUSERNAME STARTING WITH WHITESPACE WAS ADDED");
		}catch(InvalidUsernameException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
			System.out.println("\t\t\tUnable to add an username that starts whit whitespace");
		}catch(Exception e){
			System.err.println("\t\t\tUnknown exception: " + e.getLocalizedMessage());
		}

		try {
			System.out.println("\t\tAdding valid username:");
			SN.addUser("John");
			System.out.println("\t\t\tValid username accepted");
		}catch(InvalidUsernameException iue){
			System.err.println("\t\t\t" + iue.getLocalizedMessage());
			System.err.println("\t\t\tVALID USERNAME WAS NOT ACCEPTED");
		}catch(Exception e){
			System.err.println("\t\t\tUnknown exception: " + e.getLocalizedMessage());
		}

		try {
			System.out.println("\t\tAdding duplicate username:");
			SN.addUser("John");
			System.err.println("\t\t\tDUPLICATE USERNAME WAS ADDED");
		}catch(InvalidUsernameException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
			System.out.println("\t\t\tUnable to add a duplicate username");
		}catch(Exception e){
			System.err.println("\t\t\tUnknown exception: " + e.getLocalizedMessage());
		}
	}
}
