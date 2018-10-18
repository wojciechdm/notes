package com.wojciechdm.rest.notes;

import javax.validation.constraints.NotNull;

public class NoteDto {
	
	@NotNull
	private String title;
	@NotNull
	private String content;
	
	public NoteDto() {
		super();		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
