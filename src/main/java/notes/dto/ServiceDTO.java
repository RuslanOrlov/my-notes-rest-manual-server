package notes.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import notes.models.Note;

@Service
public class ServiceDTO {
	
	public NoteServerDTO convertNoteToDto(Note note) {
		NoteServerDTO dto = new NoteServerDTO();
		
		if (note.getId() != null) {
			dto.setId(note.getId());
		}
		if (note.getName() != null) {
			dto.setName(note.getName());
		}
		if (note.getDescription() != null) {
			dto.setDescription(note.getDescription());
		}
		if (note.getIsDeleted() != null) {
			dto.setIsDeleted(note.getIsDeleted());
		}
		if (note.getCreatedAt() != null) {
			dto.setCreatedAt(note.getCreatedAt());
		}
		if (note.getUpdatedAt() != null) {
			dto.setUpdatedAt(note.getUpdatedAt());
		}
		return dto;
	}
	
	public List<NoteServerDTO> convertNotesListToDtoList(List<Note> notesList) {
		List<NoteServerDTO> dtoList = new ArrayList<>();
		
		for (Note note : notesList) {
			dtoList.add(this.convertNoteToDto(note));
		}
		return dtoList;
	}
	
	
	
	public Note convertDtoToNote(NoteServerDTO dto) {
		Note note = new Note(null, null, null, null, null, null);
		
		if (dto.getId() != null) {
			note.setId(dto.getId());
		}
		if (dto.getName() != null) {
			note.setName(dto.getName());
		}
		if (dto.getDescription() != null) {
			note.setDescription(dto.getDescription());
		}
		if (dto.getIsDeleted() != null) {
			note.setIsDeleted(dto.getIsDeleted());
		}
		if (dto.getCreatedAt() != null) {
			note.setCreatedAt(dto.getCreatedAt());
		}
		if (dto.getUpdatedAt() != null) {
			note.setUpdatedAt(dto.getUpdatedAt());
		}
		return note;
	}
	
	public List<Note> convertDtoListToNotesList(List<NoteServerDTO> dtoList) {
		List<Note> notesList = new ArrayList<>();
		
		for (NoteServerDTO dto : dtoList) {
			notesList.add(this.convertDtoToNote(dto));
		}
		return notesList;
	}
}
