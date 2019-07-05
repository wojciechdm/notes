package com.wojciechdm.rest.notes;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE)
@Getter
public class NoteSaveDto {

	private String title;
	private String content;
}
