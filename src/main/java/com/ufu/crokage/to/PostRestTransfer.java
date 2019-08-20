package com.ufu.crokage.to;

import java.util.List;
import java.util.Set;

import com.google.gson.annotations.Expose;

public class PostRestTransfer extends GenericRestTransfer{
	@Expose
	public List<Post> posts;
	public Set<String> tags;
	public Integer queryId;
	public List<Integer> recommendedIds;
	


	public PostRestTransfer() {
		this.descricao=null;
		this.infoMessage=null;
		this.id=null;
		this.errorMessage=null;
	}
	

	public Integer getId() {
		return id;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public String getInfoMessage() {
		return infoMessage;
	}


	public String getDescricao() {
		return descricao;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Integer getQueryId() {
		return queryId;
	}

	public void setQueryId(Integer queryId) {
		this.queryId = queryId;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public List<Integer> getRecommendedIds() {
		return recommendedIds;
	}

	public void setRecommendedIds(List<Integer> recommendedIds) {
		this.recommendedIds = recommendedIds;
	}

		
	
	

}