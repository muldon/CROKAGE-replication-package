package com.ufu.crokage.to;

import java.beans.Transient;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.Expose;

@XmlRootElement
public class Post {
	private static final long serialVersionUID = -111652190111815641L;
	@Expose
    private Integer id;
	
	@Expose
	private String body;
	
	private String processedBody;
	
	@Expose
	private String processedBodyLemma;
	
	@Expose
	private String title;
	
	private String processedTitle;
	
	@Expose
	private String processedTitleLemma;
	
	@Expose
	private String tags;
	
	private String code;
	
	@Expose
	private String processedCode;
    	
	private Integer postTypeId;
	
	private Integer acceptedAnswerId;
	
	@Expose
	private Integer parentId;
	
	private Timestamp creationDate;
	
	private Integer score;
		
	private Integer viewCount;
	    	
	private Integer ownerUserId;
	
	
	private Integer lastEditorUserId;
	
	private String lastEditorDisplayName;
	
	private Timestamp lastEditDate;
	
	private Timestamp lastActivityDate;
		
	private Integer answerCount;
	
	private Integer commentCount;
	
	private Integer favoriteCount;
	
	private Timestamp closedDate;
	
	private Timestamp communityOwnedDate;
	
	private String topics;
	
	private String hotTopics;
	
	private String topScoredAnswers;
	
	private Integer tagId;
	
	

	public Post() {
	}
	
	public Post(Integer id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getProcessedBody() {
		return processedBody;
	}

	public void setProcessedBody(String processedBody) {
		this.processedBody = processedBody;
	}

	public String getProcessedBodyLemma() {
		return processedBodyLemma;
	}

	public void setProcessedBodyLemma(String processedBodyLemma) {
		this.processedBodyLemma = processedBodyLemma;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProcessedTitle() {
		return processedTitle;
	}

	public void setProcessedTitle(String processedTitle) {
		this.processedTitle = processedTitle;
	}

	public String getProcessedTitleLemma() {
		return processedTitleLemma;
	}

	public void setProcessedTitleLemma(String processedTitleLemma) {
		this.processedTitleLemma = processedTitleLemma;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProcessedCode() {
		return processedCode;
	}

	public void setProcessedCode(String processedCode) {
		this.processedCode = processedCode;
	}

	public Integer getPostTypeId() {
		return postTypeId;
	}

	public void setPostTypeId(Integer postTypeId) {
		this.postTypeId = postTypeId;
	}

	public Integer getAcceptedAnswerId() {
		return acceptedAnswerId;
	}

	public void setAcceptedAnswerId(Integer acceptedAnswerId) {
		this.acceptedAnswerId = acceptedAnswerId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(Integer ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public Integer getLastEditorUserId() {
		return lastEditorUserId;
	}

	public void setLastEditorUserId(Integer lastEditorUserId) {
		this.lastEditorUserId = lastEditorUserId;
	}

	public String getLastEditorDisplayName() {
		return lastEditorDisplayName;
	}

	public void setLastEditorDisplayName(String lastEditorDisplayName) {
		this.lastEditorDisplayName = lastEditorDisplayName;
	}

	public Timestamp getLastEditDate() {
		return lastEditDate;
	}

	public void setLastEditDate(Timestamp lastEditDate) {
		this.lastEditDate = lastEditDate;
	}

	public Timestamp getLastActivityDate() {
		return lastActivityDate;
	}

	public void setLastActivityDate(Timestamp lastActivityDate) {
		this.lastActivityDate = lastActivityDate;
	}

	public Integer getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(Integer favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public Timestamp getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Timestamp closedDate) {
		this.closedDate = closedDate;
	}

	public Timestamp getCommunityOwnedDate() {
		return communityOwnedDate;
	}

	public void setCommunityOwnedDate(Timestamp communityOwnedDate) {
		this.communityOwnedDate = communityOwnedDate;
	}

	public String getTopics() {
		return topics;
	}

	public void setTopics(String topics) {
		this.topics = topics;
	}

	public String getHotTopics() {
		return hotTopics;
	}

	public void setHotTopics(String hotTopics) {
		this.hotTopics = hotTopics;
	}

	public String getTopScoredAnswers() {
		return topScoredAnswers;
	}

	public void setTopScoredAnswers(String topScoredAnswers) {
		this.topScoredAnswers = topScoredAnswers;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", body=" + body + ", title=" + title + "]";
	}

	

	
	
    
}