import java.util.Optional;

public class ReportableSocialNetworkTest {
	private ReportableSocialNetworkTest(){}

	public static void testReportPost(){
		System.out.println("\tTesting ReportableSocialNetowrk.reportPost");
		ReportableSocialNetwork sn = new ReportableSocialNetwork();
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

			System.out.println("\t\tTrying to report a non existent post");
			try{
				sn.reportPost("non existent post id");
				System.err.println("\t\t\tThe method call didn't throw an exception");
			}catch (NonExistentPostException e){
				System.out.println("\t\t\t" + e.getLocalizedMessage());
			}

			System.out.println("\t\tTrying to report an existing post");
			try {
				sn.reportPost(postId.get());
				if(sn.publishedPostWithId(postId.get()) || !sn.reportedPostWithID(postId.get())){
					System.err.println("\t\t\tThe operation hasn't been completed successfully");
				}else{
					System.out.println("\t\t\tOperation completed successfully");
				}
			} catch (NonExistentPostException e) {
				System.err.println("\t\t\t" + e.getLocalizedMessage());
			}
		}
	}

	public static void testRestorePost(){
		System.out.println("\tTesting ReportableSocialNetwork.restorePost");
		ReportableSocialNetwork sn = new ReportableSocialNetwork();
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
			String id = postId.get();
			try {
				sn.reportPost(id);
			}catch(Exception e){
				System.err.println("\t\tSomething went wrong while reporting the test post");
			}

			System.out.println("\t\tRestoring post with invalid ID");
			try{
				sn.restorePost("Random ID sdfhuio42554");
				System.err.println("\t\t\tThe call didn't throw an exception");
			}catch(NonExistentPostException e){
				System.out.println("\t\t\t" + e.getLocalizedMessage());
			}

			try {
				System.out.println("\t\tRestoring valid reported post");
				sn.restorePost(id);
				if(sn.publishedPostWithId(id) && !sn.reportedPostWithID(id)){
					System.out.println("\t\t\tPost restored successfully");
				}else{
					System.err.println("\t\t\tPost hasn't been restored");
				}
			}catch (NonExistentPostException e){
				System.err.println("\t\t\t" + e.getLocalizedMessage());
			}
		}
	}

	public static void testDeletePost(){
		System.out.println("\tTesting ReportableSocialNetwork.deletePost");
		ReportableSocialNetwork sn = new ReportableSocialNetwork();
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
		}else {
			String id = postId.get();
			try {
				sn.reportPost(id);
			}catch(Exception e){
				System.err.println("\t\tSomething went wrong while reporting the test post");
			}

			System.out.println("\t\tDeleting post with invalid ID");
			try{
				sn.deletePost("Random ID sdfhuio42554");
				System.err.println("\t\t\tThe call didn't throw an exception");
			}catch(NonExistentPostException e){
				System.out.println("\t\t\t" + e.getLocalizedMessage());
			}

			try {
				System.out.println("\t\tDeleting valid reported post");
				sn.deletePost(id);
				if(!sn.publishedPostWithId(id) && !sn.reportedPostWithID(id)){
					System.out.println("\t\t\tPost deleted successfully");
				}else{
					System.err.println("\t\t\tPost hasn't been deleted");
				}
			}catch (NonExistentPostException e){
				System.err.println("\t\t\t" + e.getLocalizedMessage());
			}
		}
	}
}