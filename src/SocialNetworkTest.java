public class SocialNetworkTest {
	public SocialNetwork SN;

	public SocialNetworkTest(){
		SN = new SocialNetwork();
	}

	public static void addUsers(SocialNetwork sn, String[] users){
		for(String user : users){
			try {
				sn.addUser(user);
			}catch(Exception e){}
		}
	}

	public void addPosts(String[] authors, String[] postTexts){
		if(authors.length != postTexts.length){
			System.err.println("Trying to invoke addPosts with arrays of different lengths");

		}
		for(int i = 0; i < authors.length; i++) {
			try {
				SN.createPost(authors[i], postTexts[i]);
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
			System.out.println("\t\tAdding username starting with whitespace:");
			SN.addUser(" test1");
			System.err.println("\t\t\tUSERNAME STARTING WITH WHITESPACE WAS ADDED");
		}catch(InvalidUsernameException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
			System.out.println("\t\t\tUnable to add an username that starts with whitespace");
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

	public static void testFollow(){
		System.out.println("\tTesting SocialNetwork.follow");
		SocialNetwork sn = new SocialNetwork();
		addUsers(sn, new String[]{
			"Alice",
			"Bob",
			"Carrie",
			"Dylan"
		});

		System.out.println("\t\tTrying to get a non existent user to follow someone");
		try{
			sn.follow("Ethan", "Dylan");
			System.err.println("\t\t\tSuccesfully followed");
		}catch(InvalidUsernameException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
			System.out.println("\t\t\tPrevented from following");
		}

		System.out.println("\t\tTrying to follow a non existent user");
		try{
			sn.follow("Alice", "Ethan");
			System.err.println("\t\t\tSuccesfully followed");
		}catch(InvalidUsernameException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
			System.out.println("\t\t\tPrevented from following");
		}

		System.out.println("\t\tTrying to make users follow themselves");
		try{
			sn.follow("Alice", "Alice");
			System.err.println("\t\t\tSuccesfully followed");
		}catch(InvalidUsernameException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
			System.out.println("\t\t\tPrevented from following");
		}

		System.out.println("\t\tTrying a valid follow operation");
		try{
			sn.follow("Alice", "Bob");
			if(sn.isFollowedBy("Bob", "Alice")) {
				System.out.println("\t\t\tSuccesfully followed");
			}else{
				System.err.println("\t\t\tAlice is not following Bob");
			}
		}catch(InvalidUsernameException iue){
			System.err.println("\t\t\t" + iue.getLocalizedMessage());
			System.err.println("\t\t\tPrevented from following");
		}

		System.out.println("\t\tTrying to follow an already followed person");
		try{
			sn.follow("Alice", "Bob");
			if(sn.isFollowedBy("Bob", "Alice")) {
				System.out.println("\t\t\tNothing happened");
			}else{
				System.err.println("\t\t\tAlice is not following Bob");
			}
		}catch(InvalidUsernameException iue){
			System.err.println("\t\t\t" + iue.getLocalizedMessage());
			System.err.println("\t\t\tPrevented from following");
		}
	}

	public static void testIsFollowedBy(){
		System.out.println("\tTesting SocialNetwork.isFollowedBy");
		SocialNetwork sn = new SocialNetwork();
		addUsers(sn, new String[]{
				"Alice",
				"Bob",
				"Carrie",
				"Dylan"
		});

		try {
			sn.follow("Alice", "Bob");
		}catch(InvalidUsernameException iue){
			System.err.println("\t\t" + iue.getLocalizedMessage());
		}

		System.out.println("\t\tTesting null user");
		try{
			sn.isFollowedBy(null, "Alice");
			System.err.println("\t\t\tOperation completed successfully");
		}catch(InvalidUsernameException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
		}

		System.out.println("\t\tTesting empty user");
		try{
			sn.isFollowedBy("", "Alice");
			System.err.println("\t\t\tOperation completed successfully");
		}catch(InvalidUsernameException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
		}

		System.out.println("\t\tTesting null follower");
		try{
			sn.isFollowedBy("Bob", null);
			System.err.println("\t\t\tOperation completed successfully");
		}catch(InvalidUsernameException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
		}

		System.out.println("\t\tTesting empty follower");
		try{
			sn.isFollowedBy("Bob", "");
			System.err.println("\t\t\tOperation completed successfully");
		}catch(InvalidUsernameException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
		}

		System.out.println("\t\tTesting user not in social network");
		try{
			sn.isFollowedBy("Bob", "Ethan");
			System.err.println("\t\t\tOperation completed successfully");
		}catch(InvalidUsernameException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
		}


		System.out.println("\t\tTesting valid input, not followed");
		try{
			if(sn.isFollowedBy("Dylan", "Alice")){
				System.err.println("\t\t\tReturned true");
			}else{
				System.out.println("\t\t\tReturned false");
			}
		}catch(InvalidUsernameException iue){
			System.err.println("\t\t\t" + iue.getLocalizedMessage());
		}

		System.out.println("\t\tTesting valid input, followed");
		try{
			if(sn.isFollowedBy("Bob", "Alice")){
				System.out.println("\t\t\tReturned true");
			}else{
				System.err.println("\t\t\tReturned false");
			}
		}catch(InvalidUsernameException iue){
			System.err.println("\t\t\t" + iue.getLocalizedMessage());
		}
	}
}
