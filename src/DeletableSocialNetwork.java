import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeletableSocialNetwork extends SocialNetwork{
	private List<Post> deletedPosts;

	public DeletableSocialNetwork(){
		super();
		deletedPosts = new ArrayList<Post>();
	}

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

	public boolean postHasBeenDeleted(String id){
		return containsPost(id, deletedPosts);
	}
}
