import java.util.Optional;

public class DeletableSocialNetworkTest {
	private DeletableSocialNetworkTest(){}

	public static void testDeletePost(){
		System.out.println("\tTesting DeletableSocialNetowrk.deletePost");
		DeletableSocialNetwork sn = new DeletableSocialNetwork();
		try {
			sn.addUser("Alice");
		} catch (InvalidUsernameException invalidUsernameException) {
			System.err.println("\t\tSomething went wrong while adding the test user");
		}

		Optional<String> postId = Optional.empty();

		try {
			postId = Optional.of(sn.createPost("Alice", "Test text"));
		} catch (InvalidUsernameException | InvalidPostTextException e) {
			System.err.println("\t\tSomething went wrong while creating the test post");
		}

		if(postId.isEmpty()){
			System.err.println("\t\tPost creation returned null ID");
		}else{

			System.out.println("\t\tTrying to delete a non existent post");
			try{
				sn.deletePost("non existent post id");
				System.err.println("\t\t\tThe method call didn't throw an exception");
			}catch (NonExistentPostException e){
				System.out.println("\t\t\t" + e.getLocalizedMessage());
			}

			System.out.println("\t\tTrying to delete an existing post");
			try {
				sn.deletePost(postId.get());
				if(sn.publishedPostWithId(postId.get()) || !sn.postHasBeenDeleted(postId.get())){
					System.err.println("\t\t\tThe operation hasn't been completed successfully");
				}else{
					System.out.println("\t\t\tOperation completed successfully");
				}
			} catch (NonExistentPostException e) {
				System.err.println("\t\t\t" + e.getLocalizedMessage());
			}
		}
	}
}