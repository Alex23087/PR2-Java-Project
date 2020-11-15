import java.util.*;
import java.util.stream.Collectors;

public class SocialNetwork {
	private final Map<String, Set<String>> followers;
	private final List<Post> posts;
	private final List<String> users;

	public SocialNetwork(){
		followers = new HashMap<String, Set<String>>();
		posts = new ArrayList<Post>();
		users = new ArrayList<String>();
	}

	public Map<String, Set<String>> guessFollowers(){
		return guessFollowers(this.posts);
	}

	private Map<String, Set<String>> guessFollowers(List<Post> ps){
		Map<String, Set<String>> output = new HashMap<>();
		for(Post post : ps){
			String author = post.getAuthor();
			Set<String> mentions = post.getMentioned(users);

			if(!output.containsKey(author)){
				output.put(author, new HashSet<>());
			}

			Set<String> followed = output.get(author);
			for(String user : mentions){
				if(user.equals(author)){
					continue;
				}
				if(!output.containsKey(user)){
					output.put(user, new HashSet<>());
				}
				followed.add(user);
			}
		}
		return output;
	}

	public List<String> influencers(){
		return influencers(followers);
	}

	private static List<String> influencers(Map<String, Set<String>> followers){
		if(followers == null || followers.size() == 0){
			return new ArrayList<>(0);
		}
		List<String> output = new ArrayList<String>(followers.size());
		output.addAll(followers.keySet());

		Map<String, Integer> follows = new HashMap<>(followers.size());
		for(String user : followers.keySet()){
			int count = 0;
			for(String u : followers.keySet()){
				if(followers.get(u).contains(user)){
					count++;
				}
			}
			follows.put(user, count);
		}

		output.sort((String lUsername, String rUsername) -> {
			int lFollowCount = follows.get(lUsername);
			int rFollowCount = follows.get(rUsername);
			return -Integer.compare(lFollowCount, rFollowCount);
		});
		return output;
	}

	public Set<String> getMentionedUsers(){
		return getMentionedUsers(posts);
	}

	public Set<String> getMentionedUsers(List<Post> ps){
		if(ps == null || ps.size() < 1){
			return new HashSet<String>(0);
		}
		Set<String> users = new HashSet<String>();
		ps.forEach(post -> {
			users.add(post.getAuthor());
			users.addAll(post.getMentioned(this.users));
		});
		return users;
	}

	public List<Post> writtenBy(String username){
		return writtenBy(posts, username);
	}

	public List<Post> writtenBy(List<Post> ps, String username){
		if(ps == null || ps.size() == 0 || username == null || username.length() < 1){
			return new ArrayList<Post>(0);
		}
		return ps.stream().filter(post -> post.getAuthor().equals(username)).collect(Collectors.toList());
	}

	public List<Post> containing(List<String> words){
		return posts.stream().filter(
				post -> post.containsAny(words)
		).collect(Collectors.toList());
	}

	public void createPost(String author, String text) throws InvalidUsernameException, InvalidPostTextException{
		if(!users.contains(author)){
			throw new InvalidUsernameException("User " + author + " is not registered in the social network.");
		}
		Post post = new Post(author, text);
		posts.add(post);
	}

	public void addUser(String username) throws InvalidUsernameException{
		if(username == null || username.length() < 1){
			throw new InvalidUsernameException("Trying to add user with a nonexistent username");
		}

		if(Character.isWhitespace(username.charAt(0))){
			throw new InvalidUsernameException("Username cannot start with whitespace");
		}

		if(users.contains(username)){
			throw new InvalidUsernameException("Duplicate username");
		}

		users.add(username);
		followers.put(username, new HashSet<String>());
	}

	public void follow(String follower, String followed) throws InvalidUsernameException{
		if(!users.contains(follower)){
			throw new InvalidUsernameException("Follower username isn't in the social network");
		}

		if(!users.contains(followed)){
			throw new InvalidUsernameException("Followed username isn't in the social network");
		}

		if(follower.equals(followed)){
			throw new InvalidUsernameException("Users cannot follow themselves (follower == followed)");
		}

		followers.get(follower).add(followed);
	}

	public boolean isFollowedBy(String user, String follower) throws InvalidUsernameException{
		if(user == null || user.length() < 1 || follower == null || follower.length() < 1){
			throw new InvalidUsernameException("Null/empty user");
		}

		if(users.contains(follower)){
			return followers.get(follower).contains(user);
		}else{
			throw new InvalidUsernameException("User " + follower + " is not contained in the social network");
		}
	}
}
