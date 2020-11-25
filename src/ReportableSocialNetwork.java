import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Definition of the ReportableSocialNetwork class, extension of SocialNetwork that allows for posts to be reported and then deleted.
 *
 * Representation Invariant:
 *     SocialNetwork Representation Invariant &&
 *     reportedPosts != null
 *
 * @author Alessandro Scala
 * @version 1.0
 **/
public class ReportableSocialNetwork extends SocialNetwork{
	private List<Post> reportedPosts;

	/**
	 * Class constructor.
	 **/
	public ReportableSocialNetwork(){
		super();
		reportedPosts = new ArrayList<Post>();
	}

	/**
	 * Reports a post in the social network.
	 *
	 * @param id The ID of the Post to be reported.
	 *
	 * @throws NonExistentPostException When a Post with the specified ID is not found in the social network.
	 **/
	public void reportPost(String id) throws NonExistentPostException {
		Optional<Post> postToReport = findPost(posts, id);
		if(postToReport.isEmpty()){
			throw new NonExistentPostException();
		}else{
			Post foundPost = postToReport.get();
			posts.remove(foundPost);
			reportedPosts.add(foundPost);
		}
	}

	/**
	 * Restores a Post that has been reported to its normal state.
	 *
	 * @param id The ID of the Post to be restored.
	 *
	 * @throws NonExistentPostException When a Post with the specified ID is not found in the list of reported posts.
	 **/
	public void restorePost(String id) throws NonExistentPostException{
		Optional<Post> postToRestore = findPost(reportedPosts, id);
		if(postToRestore.isEmpty()){
			throw new NonExistentPostException();
		}else{
			Post foundPost = postToRestore.get();
			reportedPosts.remove(foundPost);
			posts.add(foundPost);
		}
	}

	/**
	 * Permanently deletes a Post that has been previously reported.
	 *
	 * @param id The ID of the Post to be deleted.
	 *
	 * @throws NonExistentPostException When a Post with the specified ID is not found in the list of reported posts.
	 **/
	public void deletePost(String id) throws NonExistentPostException{
		Optional<Post> postToDelete = findPost(reportedPosts, id);
		if(postToDelete.isEmpty()){
			throw new NonExistentPostException();
		}else{
			Post foundPost = postToDelete.get();
			reportedPosts.remove(foundPost);
		}
	}

	/**
	 * Checks if a post has been reported.
	 *
	 * @param id The ID of the post to look for.
	 *
	 * @return True if and only if a Post with the specified ID is contained in the list of reported posts. False otherwise.
	 **/
	public boolean reportedPostWithID(String id){
		return containsPost(id, reportedPosts);
	}

}
