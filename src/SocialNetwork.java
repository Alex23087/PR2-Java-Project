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
		if(ps == null || ps.size() == 0){
			return output;
		}
		for(Post post : ps){
			String author = post.getAuthor();
			Set<String> mentions = post.getMentioned(users);

			if(!output.containsKey(author)){
				//Create new entry in the output map, with author as key and an empty set as value
				output.put(author, new HashSet<>());
			}

			Set<String> followed = output.get(author);
			//Add all users mentioned in the post to the set of users followed by the author of the post
			for(String user : mentions){
				if(user.equals(author)){
					continue;
				}
				if(!output.containsKey(user)){
					//All users have to have an entry in the social network map, even those who are only mentioned
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

		//Calculate a Map that associates each user with the count of other users who follow them
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

		//Sort the list of users based on the count of followers in decreasing order
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


	/**
	 * Returns a list of posts written by the user passed as parameter,
	 * contained in the social network.
	 *
	 * @param username The author whose posts have to be returned
	 *
	 * @return A List of Posts with username.equals(post.getAuthor())
	 **/
	public List<Post> writtenBy(String username){
		return writtenBy(posts, username);
	}

	/**
	 * Returns a list of posts written by the user passed as parameter,
	 * contained in the list of posts passed as parameter.
	 *
	 * @param ps The list of posts in which to search.
	 * @param username The author whose posts have to be returned
	 *
	 * @return A List of Posts with username.equals(post.getAuthor())
	 **/
	public List<Post> writtenBy(List<Post> ps, String username){
		if(ps == null || ps.size() == 0 || username == null || username.length() < 1){
			return new ArrayList<Post>(0);
		}
		return ps.stream().filter(post -> post.getAuthor().equals(username)).collect(Collectors.toList());
	}

	/**
	 * Finds all the posts containing in their text at least one of the words in the list passed as parameter.
	 *
	 * @param words The list of words to search for.
	 *
	 * @return A List of Posts for which post.containsAny(words) == true
	 **/
	public List<Post> containing(List<String> words){
		return posts.stream().filter(
				post -> post.containsAny(words)
		).collect(Collectors.toList());
	}

	/**
	 * Adds a Post to the SocialNetwork.
	 *
	 * @requires author is in users
	 * @requires 0 < text.length() <= 140
	 *
	 * @param author The author of the post to be created.
	 * @param text The text of the post to be created.
	 *
	 * @modifies this.posts
	 *
	 * @effects this.posts = this.posts U {new Post(author, text)}
	 *
	 * @return The ID of the newly created Post.
	 *
	 * @throws InvalidUsernameException If the user is not registered in the social network, or if the username does not respect the username validity conditions: 0 < username.length() && Character.isWhitespace(username.charAt[0]) == false
	 * @throws InvalidPostTextException If the text does not fulfill the post text validity conditions: 0 < text.length() <= 140
	 **/
	public String createPost(String author, String text) throws InvalidUsernameException, InvalidPostTextException{
		if(!users.contains(author)){
			throw new InvalidUsernameException("User " + author + " is not registered in the social network.");
		}
		Post post = new Post(author, text);
		posts.add(post);
		return post.getId();
	}

	/**
	 * Checks if a post with a specified ID is contained in a list of Posts.
	 *
	 * @param id The ID of the post to look for.
	 * @param posts The List of Posts to perform the check on.
	 *
	 * @return True if and only if the list contains a post with the specified ID, false otherwise, or if posts == null || posts.size() == 0 || id == null
	 */
	protected static boolean containsPost(String id, List<Post> posts){
		if(posts == null || posts.size() == 0 || id == null || id.length() < 1){
			return false;
		}
		for(Post post : posts){
			if(post.getId().equals(id)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if a Post with the specified ID hasa been published.
	 *
	 * @param id The ID of the Post to look for.
	 *
	 * @return True if and only if the SocialNetwork contains a post with the specified ID, false otherwise, or if posts == null || posts.size() == 0 || id == null
	 **/
	public boolean publishedPostWithId(String id){
		return containsPost(id, posts);
	}

	/**
	 * Registers a user into the SocialNetwork.
	 *
	 * @requires username not in users && username is a valid username
	 *
	 * @param username The username of the user to add to the social network
	 *
	 * @modifies this.users
	 * @modifies this.followers
	 *
	 * @effects this.users = this.users U {username}
	 * @effects this.followers = this.followers U {(username, {})}
	 *
	 * @throws InvalidUsernameException When there already is a user registered with this username, or when the
	 *                                  username doesn't respect the username constraints:
	 *                                      username != null && username.length() >= 1 && Character.isWhitespace(username.charAt(0)) == false
	 **/
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

	/**
	 * Adds a user to the set of users followed by another user.
	 *
	 * @requires follower is in users
	 * @requires followed is in users
	 * @requires follower != followed
	 *
	 * @param follower The user who's following.
	 * @param followed The user who's being followed.
	 *
	 * @modifies this.followers
	 *
	 * @effects this.followers.get(follower) = this.followers.get(follower) U {followed}
	 *
	 * @throws InvalidUsernameException When either follower or followed is not a user registered in the social network, or when users are trying to follow themselves.
	 **/
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

	/**
	 * Checks if a user is currently being followed by another user.
	 *
	 * @requires user not empty
	 * @requires follower not empty
	 * @requires follower is in users
	 *
	 * @param user The user who is being searched for in the follower's following set.
	 * @param follower The user in which set the search is happening.
	 *
	 * @return True if and only if follower.follows(user). False otherwise.
	 *
	 * @throws InvalidUsernameException When user or follower are null or empty, or when follower is not a user registered in the social network.
	 **/
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

	/**
	 * Searches in the list passed as parameter a Post with the ID passed as parameter.
	 *
	 * @param posts The list of posts in which to perform the search.
	 * @param id The ID of the Post that is being searched.
	 *
	 * @return An Optional containing the Post that has been found, or empty if no Posts with the specified ID were found in the List.
	 **/
	protected Optional<Post> findPost(List<Post> posts, String id){
		Optional<Post> found = Optional.empty();
		for(Post post : posts){
			if(post.getId().equals(id)){
				found = Optional.of(post);
				break;
			}
		}
		return found;
	}
}
