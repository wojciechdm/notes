package com.wojciechdm.rest.notes;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="notes")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE)
class Note {
	
	@Id
	@Column(name="note_id", nullable=false)
	private Long noteId;
	@Column(nullable=false)
	private String title;
	@Column(nullable=false)
	private String content;
	@Column(nullable=false)
	private LocalDate created;
	@Column(nullable=false)
	private LocalDate modified;
	@Column(nullable=false)
	private Long version;

}
