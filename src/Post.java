import java.util.List;

public class Post {
	private final String id;
	private final String author;
	private final String text;
	private final long timestamp;


	public Post(String author, String text){
		if(author == null){
			throw new IllegalArgumentException("Called Post constructor with null author");
		}
		if(text == null){
			throw new IllegalArgumentException("Called Post constructor with null text");
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

	public String getAuthor(){
		return this.author;
	}

}
