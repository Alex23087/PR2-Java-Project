import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Definition of the DeletableSocialNetwork class, extension of SocialNetwork that allows for posts to be deleted.
 *
 * Representation Invariant:
 *     SocialNetwork Representation Invariant &&
 *     deletedPosts != null
 *
 * @author Alessandro Scala
 * @version 1.0
 **/
public class DeletableSocialNetwork extends SocialNetwork{
	private List<Post> deletedPosts;

	/**
	 * Class constructor.
	 **/
	public DeletableSocialNetwork(){
		super();
		deletedPosts = new ArrayList<Post>();
	}

	/**
	 * Deletes (non permanently) a post from the social network.
	 *
	 * @param id The ID of the Post to be deleted.
	 *
	 * @throws NonExistentPostException When a Post with the specified ID is not found in the social network.
	 **/
	public void deletePost(String id) throws NonExistentPostException {
		Optional<Post> found = Optional.empty();
		for(Post post : posts){
			if(post.getId().equals(id)){
				found = Optional.of(post);
				break;
			}
		}
		if(found.isEmpty()){
			throw new NonExistentPostException();
		}else{
			Post foundPost = found.get();
			posts.remove(foundPost);
			deletedPosts.add(foundPost);
		}
	}

	/**
	 * Checks if a post has been deleted from the social network.
	 *
	 * @param id The ID of the post to look for.
	 *
	 * @return True if and only if a Post with the specified ID is contained in the list of deleted posts. False otherwise.
	 **/
	public boolean postHasBeenDeleted(String id){
		return containsPost(id, deletedPosts);
	}
}
