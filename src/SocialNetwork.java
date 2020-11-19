import java.util.*;
import java.util.stream.Collectors;

/**
 * Definition of the SocialNetwork class, representing the MicroBlog network.
 * Fields:
 * - Map<String, Set<String>> followers:	A structure that maps each user with the other users they follow.
 * - List<Post> posts:						A list of posts shared by the users of the network.
 * - List<String> users:					A list of users that signed up on the network.
 *
 * Representation Invariant:
 *   (followers != null) &&
 *   (forall u in followers.keySet(): (u != null) && (users.contains(u)) == true) && (followers.get(u) != null) && (followers.get(u).contains(u) == false)) &&
 *   (posts != null) &&
 *   (forall p in posts: (p != null) && (users.contains(p.getAuthor())) && (forall p1 in posts: p != p1 -> p.getId() != p1.getId())) &&
 *   (users != null) &&
 *   (forall u in users: u != null && u.length() > 0)
 *
 * @author Alessandro Scala
 * @version 1.0
 **/
public class SocialNetwork {
	private final Map<String, Set<String>> followers;
	protected final List<Post> posts;
	private final List<String> users;

	/**
	 * Class constructor.
	 **/
	public SocialNetwork(){
		followers = new HashMap<String, Set<String>>();
		posts = new ArrayList<Post>();
		users = new ArrayList<String>();
	}

	/**
	 * Returns a Map containing the guessed social network derived from the posts contained in the social network.
	 * The keys of the map are all the authors and the users mentioned in the posts.
	 * The values are the sets of users that have been mentioned in a post by the respective key.
	 *
	 * @return A Map made up of users and sets of followed users.
	 **/
	public Map<String, Set<String>> guessFollowers(){
		return guessFollowers(this.posts);
	}

	/**
	 * Returns a Map containing the guessed social network derived from the post list passed as parameter.
	 * The keys of the map are all the authors and the users mentioned in the posts in the list.
	 * The values are the sets of users that have been mentioned in a post by the respective key.
	 *
	 * @param ps A List of Posts from which to derive the social network.
	 *
	 * @return A Map made up of users and sets of followed users. Returns an empty map if ps == null or ps.size() == 0.
	 **/
	private Map<String, Set<String>> guessFollowers(List<Post> ps){
		Map<String, Set<String>> output = new HashMap<>();
		if(ps == null){
			return output;
		}
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


	/**
	 * Returns a List of users sorted by number of followers in decreasing order,
	 * taken from the current SocialNetwork state.
	 *
	 * @return A list of users sorted by decreasing follower count. Empty list if followers == null or followers.size() = 0.
	 **/
	public List<String> influencers(){
		return influencers(followers);
	}

	/**
	 * Returns a List of users sorted by number of followers in decreasing order,
	 * taken from the followers map passed as parameter.
	 *
	 * @param followers The social network in map format to analyze.
	 *
	 * @return A list of users sorted by decreasing follower count. Empty list if followers == null or followers.size() = 0.
	 **/
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

	/**
	 * Finds all users whose username appears in the posts in the social network,
	 * both authors and mentioned ones.
	 *
	 * @return The set of the found users.
	 **/
	public Set<String> getMentionedUsers(){
		return getMentionedUsers(posts);
	}

	/**
	 * Finds all users whose username appears in the list of posts passed as parameter,
	 * both authors and mentioned ones.
	 *
	 * @param ps The list of posts on which to perform the search.
	 *
	 * @return The set of the found users.
	 **/
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

	/**
	 * Returns a list of posts written by the user passed as parameter,
	 * contained in the list of posts passed as parameter.
	 *
	 * @ps
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

	public String createPost(String author, String text) throws InvalidUsernameException, InvalidPostTextException{
		if(!users.contains(author)){
			throw new InvalidUsernameException("User " + author + " is not registered in the social network.");
		}
		Post post = new Post(author, text);
		posts.add(post);
		return post.getId();
	}

	protected static boolean containsPost(String id, List<Post> posts){
		for(Post post : posts){
			if(post.getId().equals(id)){
				return true;
			}
		}
		return false;
	}

	public boolean publishedPostWithId(String id){
		return containsPost(id, posts);
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
