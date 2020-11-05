import java.util.*;
import java.util.stream.Collectors;

public class SocialNetwork {
	Map<String, Set<String>> followers;
	List<Post> posts;
	List<String> users;

	public SocialNetwork(){
		followers = new HashMap<String, Set<String>>();
		posts = new ArrayList<Post>();
		users = new ArrayList<String>();
	}

	public Map<String, Set<String>> guessFollowers(List<Post> ps){
		//TODO: Implement method
		return null;
	}

	public List<String> influencers(Map<String, Set<String>> followers){
		//TODO: Implement method
		return null;
	}

	public Set<String> getMentionedUsers(){
		//TODO: Implement method
		return null;
	}

	public Set<String> getMentionedUsers(List<Post> ps){
		//TODO: Implement method
		return null;
	}

	public List<Post> writtenBy(String username){
		return writtenBy(posts, username);
	}

	public List<Post> writtenBy(List<Post> ps, String username){
		if(ps == null){
			return null;
		}
		return ps.stream().filter(post -> post.getAuthor().equals(username)).collect(Collectors.toList());
	}

	public List<Post> containing(List<String> words){
		/*List<Post> result = new ArrayList<Post>();
		posts.forEach(post -> {
			if(post.containsAny(words)){
				result.add(post);
			}
		});
		return result;*/
		return posts.stream().filter(
				post -> post.containsAny(words)
		).collect(Collectors.toList());
	}

	public void createPost(String author, String text){
		//TODO: Implement method
	}

	public void addUser(String username){
		if(username == null || username.length() < 1){
			throw new IllegalArgumentException("Trying to add user with a nonexistent username");
		}

		if(Character.isWhitespace(username.charAt(0))){
			throw new IllegalArgumentException("Username cannot start with whitespace");
		}

		if(users.contains(username)){
			throw new IllegalArgumentException("Duplicate username");
		}

		users.add(username);
		followers.put(username, new HashSet<String>());
	}

	public void follow(String follower, String followed){
		if(!users.contains(follower)){
			throw new IllegalArgumentException("Follower username isn't in the social network");
		}

		if(!users.contains(followed)){
			throw new IllegalArgumentException("Followed username isn't in the social network");
		}

		if(follower.equals(followed)){
			throw new IllegalArgumentException("Users cannot follow themselves (follower == followed)");
		}

		followers.get(follower).add(followed);
	}
}
