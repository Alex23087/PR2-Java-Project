import java.util.*;

public class SocialNetworkTest {
	private SocialNetworkTest(){}

	public static void addUsers(SocialNetwork sn, String... users){
		for(String user : users){
			try {
				sn.addUser(user);
			}catch(Exception e){
				System.err.println(e.getLocalizedMessage());
			}
		}
	}

	public static void addPosts(SocialNetwork sn, String[] authors, String[] postTexts){
		if(authors.length != postTexts.length){
			System.err.println("Trying to invoke addPosts with arrays of different lengths");

		}
		for(int i = 0; i < authors.length; i++) {
			try {
				sn.createPost(authors[i], postTexts[i]);
			}catch(Exception e){
				System.err.println(e.getLocalizedMessage());
			}
		}
	}


	public static void testAddUser(){
		SocialNetwork sn = new SocialNetwork();

		System.out.println("\tTesting SocialNetwork.addUser:");
		try {
			System.out.println("\t\tAdding null username:");
			sn.addUser(null);
			System.err.println("\t\t\tNULL USERNAME WAS ADDED");
		}catch(InvalidUsernameException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
			System.out.println("\t\t\tUnable to add a null username");
		}catch(Exception e){
			System.err.println("\t\t\tUnknown exception: " + e.getLocalizedMessage());
		}

		try {
			System.out.println("\t\tAdding empty username:");
			sn.addUser("");
			System.err.println("\t\t\tEMPTY USERNAME WAS ADDED");
		}catch(InvalidUsernameException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
			System.out.println("\t\t\tUnable to add an empty username");
		}catch(Exception e){
			System.err.println("\t\t\tUnknown exception: " + e.getLocalizedMessage());
		}

		try {
			System.out.println("\t\tAdding username starting with whitespace:");
			sn.addUser(" test1");
			System.err.println("\t\t\tUSERNAME STARTING WITH WHITESPACE WAS ADDED");
		}catch(InvalidUsernameException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
			System.out.println("\t\t\tUnable to add an username that starts with whitespace");
		}catch(Exception e){
			System.err.println("\t\t\tUnknown exception: " + e.getLocalizedMessage());
		}

		try {
			System.out.println("\t\tAdding valid username:");
			sn.addUser("John");
			System.out.println("\t\t\tValid username accepted");
		}catch(InvalidUsernameException iue){
			System.err.println("\t\t\t" + iue.getLocalizedMessage());
			System.err.println("\t\t\tVALID USERNAME WAS NOT ACCEPTED");
		}catch(Exception e){
			System.err.println("\t\t\tUnknown exception: " + e.getLocalizedMessage());
		}

		try {
			System.out.println("\t\tAdding duplicate username:");
			sn.addUser("John");
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
		addUsers(sn, "Alice",
				"Bob",
				"Carrie",
				"Dylan");

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
		addUsers(sn, "Alice",
				"Bob",
				"Carrie",
				"Dylan");

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

	public static void testCreatePost(){
		System.out.println("\tTesting SocialNetwork.createPost");

		SocialNetwork sn = new SocialNetwork();
		addUsers(sn, "Alice",
				"Bob",
				"Carrie",
				"Dylan");

		System.out.println("\t\tTrying to create a post made by a non existent author");
		try{
			sn.createPost("Ethan", "test text");
			System.err.println("\t\t\tPost was created successfully");
		}catch(InvalidUsernameException iue){
			System.out.println("\t\t\t" + iue.getLocalizedMessage());
		}catch(Exception e){
			System.err.println("\t\t\t" + e.getLocalizedMessage());
		}

		System.out.println("\t\tTrying to create a valid post");
		try{
			sn.createPost("Alice", "test text");
			List<String> al = new ArrayList<String>();
			al.add("test");
			if(sn.containing(al).get(0).getText().equals("test text")){
				System.out.println("\t\t\tPost was created successfully");
			}else {
				System.err.println("\t\t\tPost was not created successfully");
			}
		} catch(Exception e){
			System.err.println("\t\t\t" + e.getLocalizedMessage());
		}


	}

	public static void testContaining(){
		System.out.println("\tTesting SocialNetwork.containing");

		SocialNetwork sn = new SocialNetwork();
		addUsers(sn, "Alice",
				"Bob",
				"Carrie",
				"Dylan");

		addPosts(sn, new String[]{
				"Alice",
				"Bob",
				"Carrie",
				"Dylan"
		}, new String[]{
				"This post contains a magical word",
				"This post contains the magical word too",
				"This post does not contain special words",
				"Neither does this one"
		});

		List<String> test0 = new ArrayList<>(1);
		List<String> test1 = new ArrayList<>(1);
		List<String> test2 = new ArrayList<>(2);
		List<String> test3 = new ArrayList<>(1);

		test0.add("magical");
		test1.add("one");
		test2.add("contains");
		test2.add("does");
		test3.add("none");

		List<Post> resultList = sn.containing(test0);
		if(resultList.size() == 2){
			System.out.println("\t\tTest 0 completed successfully");
		}else{
			System.err.println("\t\tTest 0 returned a wrong set of results");
		}

		resultList = sn.containing(test1);
		if(resultList.size() == 1){
			System.out.println("\t\tTest 1 completed successfully");
		}else{
			System.err.println("\t\tTest 1 returned a wrong set of results");
		}

		resultList = sn.containing(test2);
		if(resultList.size() == 4){
			System.out.println("\t\tTest 2 completed successfully");
		}else{
			System.err.println("\t\tTest 2 returned a wrong set of results");
		}

		resultList = sn.containing(test3);
		if(resultList.size() == 0){
			System.out.println("\t\tTest 3 completed successfully");
		}else{
			System.err.println("\t\tTest 3 returned a wrong set of results");
		}
	}

	public static void testWrittenBy(){
		System.out.println("\tTesting SocialNetwork.writtenBy");

		SocialNetwork sn = new SocialNetwork();
		addUsers(sn, "Alice",
				"Bob",
				"Carrie",
				"Dylan");

		addPosts(sn, new String[]{
				"Alice",
				"Alice",
				"Carrie",
				"Dylan"
		}, new String[]{
				"This post contains a magical word",
				"This post contains the magical word too",
				"This post does not contain special words",
				"Neither does this one"
		});

		List<Post> resultList = sn.writtenBy("Alice");
		if(resultList.size() == 2){
			System.out.println("\t\tTest 0 (valid input, nonempty result) completed successfully");
		}else{
			System.err.println("\t\tTest 0 (valid input, nonempty result) failed");
		}

		resultList = sn.writtenBy("Bob");
		if(resultList.size() == 0){
			System.out.println("\t\tTest 1 (valid input, empty result) completed successfully");
		}else{
			System.err.println("\t\tTest 1 (valid input, empty result) failed");
		}

		resultList = sn.writtenBy(null);
		if(resultList.size() == 0){
			System.out.println("\t\tTest 2 (nonempty post list, null username) completed successfully");
		}else{
			System.err.println("\t\tTest 2 (nonempty post list, null username) failed");
		}

		resultList = sn.writtenBy("");
		if(resultList.size() == 0){
			System.out.println("\t\tTest 3 (nonempty post list, empty username) completed successfully");
		}else{
			System.err.println("\t\tTest 3 (nonempty post list, empty username) failed");
		}

		resultList = sn.writtenBy( null, "Bob");
		if(resultList.size() == 0){
			System.out.println("\t\tTest 4 (null post list, valid username) completed successfully");
		}else{
			System.err.println("\t\tTest 4 (null post list, valid username) failed");
		}

		resultList = sn.writtenBy(new ArrayList<Post>(0), "Bob");
		if(resultList.size() == 0){
			System.out.println("\t\tTest 5 (empty post list, valid username) completed successfully");
		}else {
			System.err.println("\t\tTest 5 (empty post list, valid username) failed");
		}
	}

	public static void testGetMentionedUsers(){
		System.out.println("\tTesting SocialNetwork.getMentionedUsers");

		SocialNetwork sn = new SocialNetwork();
		addUsers(sn, "Alice",
				"Bob",
				"Carrie",
				"Dylan");

		addPosts(sn, new String[]{
				"Alice",
				"Bob",
				"Carrie",
				"Alice"
		}, new String[]{
				"This post was written by Alice",
				"I like Alice's post",
				"I wish Bob wrote more interesting posts",
				"I agree with Carrie, Bob's posts are boring"
		});

		System.out.println("\t\tPassing null post list");
		Set<String> result = sn.getMentionedUsers(null);
		if(result.size() == 0){
			System.out.println("\t\t\tReturned empty set correctly");
		}else{
			System.err.println("\t\t\tReturned set is not empty");
		}


		System.out.println("\t\tPassing empty post list");
		result = sn.getMentionedUsers(new ArrayList<Post>(0));
		if(result.size() == 0){
			System.out.println("\t\t\tReturned empty set correctly");
		}else{
			System.err.println("\t\t\tReturned set is not empty");
		}

		System.out.println("\t\tTesting valid case");
		result = sn.getMentionedUsers();
		if(result.size() == 3 && result.contains("Alice") && result.contains("Bob") && result.contains("Carrie")){
			System.out.println("\t\t\tReturned correct values");
		}else{
			System.err.println("\t\t\tWrong return values");
		}
	}

	public static void testInfluencers() {
		System.out.println("\tTesting SocialNetwork.influencers");

		SocialNetwork sn = new SocialNetwork();

		System.out.println("\t\tTesting empty followers map");
		List<String> result = sn.influencers();
		if(result != null && result.size() == 0){
			System.out.println("\t\t\tReturned empty list correctly");
		}

		addUsers(sn,
				"Alice",
				"Bob",
				"Carrie",
				"Dylan"
		);
		try {
			sn.follow("Bob", "Alice");
			sn.follow("Carrie", "Alice");
			sn.follow("Dylan", "Alice");
			sn.follow("Alice", "Bob");
			sn.follow("Carrie", "Bob");
			sn.follow("Alice", "Dylan");
		}catch(Exception e){
			System.err.println("\t\tSomething went wrong while creating the case for the test");
		}

		System.out.println("\t\tTesting generic case");
		result = sn.influencers();
		if(result != null && result.size() == 4 && result.get(0).equals("Alice") && result.get(1).equals("Bob") && result.get(2).equals("Dylan")){
			System.out.println("\t\t\tReturned correct values");
		}else{
			if (result != null) {
				System.err.println("\t\t\tReturned wrong values: " + result.toString());
			}else{
				System.err.println("\t\t\tReturned null");
			}
		}
	}

	public static void testGuessFollowers(){

		System.out.println("\tTesting SocialNetwork.guessFollowers");

		SocialNetwork sn = new SocialNetwork();
		addUsers(sn, new String[]{
				"Alice",
				"Bob",
				"Carrie",
				"Dylan",
				"Ethan"
		});

		System.out.println("\t\tPassing empty post list");
		Map<String, Set<String>> result = sn.guessFollowers();
		if(result.size() == 0){
			System.out.println("\t\t\tReturned empty map correctly");
		}else{
			System.err.println("\t\t\tReturned wrong value");
		}

		System.out.println("\t\tTesting generic case");
		addPosts(sn, new String[]{
				"Alice",
				"Bob",
				"Carrie",
				"Alice"
		}, new String[]{
				"This post was written by Alice",
				"I like Alice's post",
				"I wish Bob wrote more interesting posts",
				"I agree with Carrie, Bob's posts are boring, but I like Ethan's ones more"
		});

		result = sn.guessFollowers();
		Map<String, Set<String>> expected = new HashMap<>(4);
		expected.put("Alice", new HashSet<String>(Arrays.asList("Carrie", "Bob", "Ethan")));
		expected.put("Bob", new HashSet<String>(Collections.singletonList("Alice")));
		expected.put("Carrie", new HashSet<String>(Collections.singletonList("Bob")));
		expected.put("Ethan", new HashSet<String>(Collections.emptyList()));
		if(result.equals(expected)){
			System.out.println("\t\t\tReturned expected value");
		}else{
			System.err.println("\t\t\tWrong value returned: " + result.toString() + " expected: " + expected.toString());
		}
	}

	public static void testPublishedPostWithId(){
		System.out.println("\tTesting SocialNetwork.publishedPostWithId");
		SocialNetwork sn = new SocialNetwork();
		addUsers(sn, "Alice");
		Optional<String> postId = Optional.empty();
		try {
			postId = Optional.of(sn.createPost("Alice", "Test Text"));
		} catch (InvalidUsernameException | InvalidPostTextException e) {
			System.err.println("\t\tSomething went wrong while creating the test post: " + e.getLocalizedMessage());
		}

		System.out.println("\t\tTesting non contained post id");
		if(sn.publishedPostWithId("testID")){
			System.err.println("\t\t\tReturned true instead of false");
		}else{
			System.out.println("\t\t\tCorrect value returned");
		}

		System.out.println("\t\tTesting contained post id");
		if(postId.isEmpty()) {
			System.err.println("\t\t\tPost creation returned null id");
		}else{
			if(sn.publishedPostWithId(postId.get())){
				System.out.println("\t\t\tReturned correctly");
			}else{
				System.err.println("\t\t\tReturned value is false instead of true");
			}
		}
	}
}
