package controllers;

import play.*;
import play.cache.Cache;
import play.data.validation.Required;
import play.libs.Codec;
import play.libs.Images;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

	@Before
	static void addDefaults() {
		renderArgs.put("blogTitle",
				Play.configuration.getProperty("blog.title"));
		renderArgs.put("blogBaseline",
				Play.configuration.getProperty("blog.blogBaseline"));
	}

	public static void index() {
		Post frontPost = Post.find("order by postedAt desc").first();
		List<Post> olderPosts = Post.find("order by postedAt desc").from(1)
				.fetch(10);
		render(frontPost, olderPosts);
	}

	public static void show(Long id) {
		Post post = Post.findById(id);
		String randomID = Codec.UUID();
		render(post, randomID);
	}

	public static void postComment(Long postId,
			@Required(message = "Authorは必須です") String author,
			@Required(message = "コメントは必須です") String content,
			@Required(message = "Please type the code") String code,
			String randomID) {
		Post post = Post.findById(postId);

		validation.equals(code, Cache.get(randomID)).message(
				"文字列が正しくありません。再度入力してください");
		if (validation.hasErrors()) {
			render("Application/show.html", post, randomID);
		}

		post.addComment(author, content);
		flash.success("%s コメントありがとうございます。", author);
		Cache.delete(randomID);
		show(postId);
	}

	public static void captcha(String id) {
		Images.Captcha captcha = Images.captcha();

		String code = captcha.getText("#E4EAFD");
		Cache.set(id, code, "10min");
		renderBinary(captcha);
	}

	public static void listTagged(String tag) {
		List<Post> posts = Post.findTaggedWith(tag);
		render(tag, posts);

	}

}