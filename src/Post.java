import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Post {
	private final String id;
	private final String author;
	private final String text;
	private final long timestamp;


	public Post(String author, String text) throws InvalidUsernameException, InvalidPostTextException {
		if(author == null || author.length() < 1){
			throw new InvalidUsernameException("Called Post constructor with null/empty author");
		}
		if(text == null || text.length() < 1){
			throw new InvalidPostTextException("Called Post constructor with null text");
		}

		this.author = author;
		this.text = text;
		this.timestamp = System.currentTimeMillis();
		this.id = timestamp + author;
	}


	/*private Post(Post post){
		this.id = post.id;
		this.author = post.author
	}*/


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
