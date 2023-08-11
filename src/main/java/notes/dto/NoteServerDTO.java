package notes.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NoteServerDTO {
	private Long id;
	private String name;
	private String description;	
	private Boolean isDeleted;	
	private LocalDateTime createdAt;	
	private LocalDateTime updatedAt;
}
