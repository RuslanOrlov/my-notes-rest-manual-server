package notes.rest.server;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import notes.dto.NoteServerDTO;
import notes.dto.ServiceDTO;
//import lombok.extern.slf4j.Slf4j;
import notes.models.Note;
import notes.repositories.NotesRepository;

//@Slf4j
@RestController
@RequestMapping(path = "/api", produces = "application/json")
public class NotesRestController {
	
	private NotesRepository notesRepository;
	private ServiceDTO serviceDTO;
	
	public NotesRestController(NotesRepository notesRepository, ServiceDTO serviceDTO) {
		this.notesRepository = notesRepository;
		this.serviceDTO = serviceDTO;
	}
	
	@GetMapping(params = "sort")
	public List<NoteServerDTO> getAllNotes(@RequestParam String sort) {
		List<Note> notesList = this.notesRepository.findAll(Sort.by(sort));
		
		return this.serviceDTO.convertNotesListToDtoList(notesList);
	}
	
	@GetMapping(params = {"sort", "page", "size"})
	public List<NoteServerDTO> getAllNotes(@RequestParam("sort") String sort, 
									@RequestParam("page") int page, 
									@RequestParam("size") int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		List<Note> notesList = 
				this.notesRepository.findAll(pageable).getContent();
		return this.serviceDTO.convertNotesListToDtoList(notesList);
	}
	
	@GetMapping(params = "value")
	public List<NoteServerDTO> getAllNotesWithQuery(@RequestParam String value) {
		List<Note> notesList = 
				this.notesRepository.readNotesWithQuery(value);
		return this.serviceDTO.convertNotesListToDtoList(notesList);
	}
	
	@GetMapping(params = {"value", "offset", "limit"})
	public List<NoteServerDTO> getAllNotesWithQuery(@RequestParam String value, 
									@RequestParam int offset, 
									@RequestParam int limit) {
		List<Note> notesList = 
				this.notesRepository.readNotesWithPagingQuery(value, offset, limit);
		return this.serviceDTO.convertNotesListToDtoList(notesList);
	}
	
	@GetMapping("/count")
	public Integer countAll() {
		return this.notesRepository.countAll();
	}
	
	@GetMapping(path = "/count", params = "value")
	public Integer countAll(@RequestParam String value) {
		return this.notesRepository.countAllWithQuery(value);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<NoteServerDTO> getNoteById(@PathVariable Long id) {
		Note note = this.notesRepository.findById(id).orElse(null);
		if (note != null) {
			return ResponseEntity.ok(this.serviceDTO.convertNoteToDto(note));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<NoteServerDTO> postNote(@RequestBody NoteServerDTO dto) {
		Note note 	= this.serviceDTO.convertDtoToNote(dto);
		note 		= this.notesRepository.save(note);
		dto 		= this.serviceDTO.convertNoteToDto(note);
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}
	
	// Это первая версия метода patchNote, которая: 
	// - получает от REST клиента PATCH запрос с промежуточным DTO объектом 
	//   NoteServerDTO, содержащим изменения редактирования исходного объекта Note; 
	// - далее извлекает из БД исходный объект Note по его id и присваивает его 
	//   полям НЕНУЛЕВЫЕ значения соответствующих полей промежуточного объекта 
	//   NoteServerDTO; 
	// - далее сохраняет изменения исходного объекта в БД и возвращает его в REST 
	//   клиент в форме DTO объекта NoteServerDTO. 
	/*
	@PatchMapping(path = "/{id}", consumes = "application/json")
	public ResponseEntity<NoteServerDTO> patchNote(@PathVariable Long id, 
											@RequestBody NoteServerDTO patch) {
		Note note = this.notesRepository.findById(id).orElse(null);
		
		if (note == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		if (patch.getIsDeleted() != null) {
			note.setIsDeleted(patch.getIsDeleted());
		}
		if (patch.getName() != null && patch.getName().trim().length() > 0) {
			note.setName(patch.getName().trim());
		}
		if (patch.getDescription() != null && patch.getDescription().trim().length() > 0) {
			note.setDescription(patch.getDescription().trim());
		}
		if (patch.getUpdatedAt() != null) {
			note.setUpdatedAt(patch.getUpdatedAt());
		}
		
		return ResponseEntity.ok(
				this.serviceDTO.convertNoteToDto(
						this.notesRepository.save(note)) );
	}*/

	// Это вторая версия метода patchNote, которая: 
	// - получает от REST клиента PATCH запрос с промежуточным ассоциативным 
	//   массивом Map, содержащим изменения редактирования исходного объекта Note; 
	// - далее извлекает из БД исходный объект Note по его id и присваивает его 
	//   полям НЕНУЛЕВЫЕ значения из ассоциативного массива Map с соответствующими 
	//   значениями ключей (преобразуя полученное текстовое значение поля даты и 
	//   времени изменения в LocalDateTime); 
	// - далее сохраняет изменения исходного объекта в БД и возвращает его в REST 
	//   клиент в форме DTO объекта NoteServerDTO. 
	/**/
	@PatchMapping(path = "/{id}", consumes = "application/json")
	public ResponseEntity<NoteServerDTO> patchNote(@PathVariable Long id, 
												@RequestBody Map<String, Object> patch) {
		Note note = this.notesRepository.findById(id).orElse(null);
		
		if (note == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		if (patch.get("isDeleted") != null) {
			note.setIsDeleted( (Boolean) patch.get("isDeleted"));
		}
		if (patch.get("name") != null && ( (String) patch.get("name")).length() > 0) {
			note.setName( (String) patch.get("name"));
		}
		if (patch.get("description") != null && ( (String) patch.get("description")).length() > 0) {
			note.setDescription( (String) patch.get("description"));
		}
		if (patch.get("updatedAt") != null && ( (String) patch.get("updatedAt")).length() > 0) {
			note.setUpdatedAt(LocalDateTime.parse( (String) patch.get("updatedAt")));
		}
		
		return ResponseEntity.ok(
				this.serviceDTO.convertNoteToDto(
						this.notesRepository.save(note)) );
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteNote(@PathVariable Long id) {
		this.notesRepository.deleteById(id);
	}
	
}
