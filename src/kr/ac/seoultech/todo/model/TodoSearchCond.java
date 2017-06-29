package kr.ac.seoultech.todo.model;

public class TodoSearchCond {

	private Long maxId;
	
	private Long sinceId;
	
	private int limit = 20;

	private Long userId;
	
	public Long getMaxId() {
		return maxId;
	}

	public void setMaxId(Long maxId) {
		this.maxId = maxId;
	}

	public Long getSinceId() {
		return sinceId;
	}

	public void setSinceId(Long sinceId) {
		this.sinceId = sinceId;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
