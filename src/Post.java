import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Definition of the immutable Post class. A Post object represents a post of the social network MicroBlog, which is
 * made up of four data fields:
 * - String id:			A unique string that identifies the post.
 * - String author:		A string identifying the user who published the Post.
 * - String text:		The text the user wrote in the post.
 * - long timestamp:	A timestamp in UNIX format of the time the Post was created.
 *
 * Representation Invariant:
 *   ((id != null) && (id.length() > 1) && ((author != null) && (author.length() > 1) && ((text != null) && (0 < text.length() <= 140))
 */

public class Post {
	private final String id;
	private final String author;
	private final String text;
	private final long timestamp;

	/**
	 * Class constructor.
	 *
	 * @requires	author != null && author.length() > 1
	 * 				text != null && 0 < text.length() <= 140
	 *
	 * @exception InvalidUsernameException if the author conditions aren't met
	 * @exception InvalidPostTextException if the post text conditions aren't met
	 *
	 * @param author
	 * @param text
	 */
	public Post(String author, String text) throws InvalidUsernameException, InvalidPostTextException {
		if(author == null || author.length() < 1){
			throw new InvalidUsernameException("Called Post constructor with null/empty author");
		}
		if(text == null || text.length() < 1){
			throw new InvalidPostTextException("Called Post constructor with null text");
		}
		//TODO: Check text.length() <= 10

		this.author = author;
		this.text = text;
		this.timestamp = System.currentTimeMillis();
		this.id = timestamp + author;
	}

	/**
	 * Checks if the post text contains at least one of the words passed as parameter.
	 *

	 */
	public boolean containsAny(List<String> words){
		if(words == null || words.size() == 0){
			return true;
		}

		for(String word : words){
			if(text.contains(word)){
				return true;
			}
		}
		return false;
	}

	public String getId(){
		return this.id;
	}

	public String getAuthor(){
		return this.author;
	}

	public String getText(){
		return this.text;
	}

	public Set<String> getMentioned(List<String> validUsers){
		Set<String> mentioned = new HashSet<>();
		if(validUsers == null || validUsers.isEmpty()){
			return mentioned;
		}
		for(String user : validUsers){
			if(text.contains(user)){
				mentioned.add(user);
			}
		}
		return mentioned;
	}
}
