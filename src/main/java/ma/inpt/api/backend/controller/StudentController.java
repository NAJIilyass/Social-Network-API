package ma.inpt.api.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ma.inpt.api.backend.model.Student;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tp1")
public class StudentController {

	private static List<Student> list = new ArrayList<>();
	
	static {
		list.add(new Student(1l, "Nom 1", "Casablanca"));
		list.add(new Student(2l, "Nom 2", "Rabat"));
		list.add(new Student(3l, "Nom 3", "Sale"));
		list.add(new Student(4l, "Nom 4", "Tanger"));
		list.add(new Student(5l, "Nom 5", "Fes"));
	}

	{/*@GetMapping("/students")
	public ResponseEntity<List<Student>> getAllStudents() {
		if(list != null && !list.isEmpty()) {
			return new ResponseEntity<List<Student>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Student>>(HttpStatus.NO_CONTENT);
		}
	}*/}


	@GetMapping("/students")
	public ResponseEntity<List<Student>> getStudents(
			@RequestParam(defaultValue = "0") int offset,
			@RequestParam(defaultValue = "5") int limit) {
		if (offset < 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		int startIndex = offset * limit;
		List<Student> paginatedList = list.subList(startIndex, Math.min(startIndex + limit, list.size()));
		if (paginatedList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(paginatedList, HttpStatus.OK);
	}

	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
		Optional<Student> student = list.stream().filter(s -> s.getId().equals(id)).findFirst();
		return student.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/students/first")
	public ResponseEntity<Student> getFirstStudent() {
		if (!list.isEmpty()) {
			return new ResponseEntity<>(list.get(0), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/students/last")
	public ResponseEntity<Student> getLastStudent() {
		if (!list.isEmpty()) {
			return new ResponseEntity<>(list.get(list.size() - 1), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/students/count")
	public ResponseEntity<Long> getStudentCount() {
		return new ResponseEntity<>((long) list.size(), HttpStatus.OK);
	}

	@PostMapping("/students")
	public ResponseEntity<Student> createStudent(@RequestBody Student student) {
		Long newId = list.isEmpty() ? 1L : list.get(list.size() - 1).getId() + 1;
		student.setId(newId);
		list.add(student);
		return new ResponseEntity<>(student, HttpStatus.CREATED);
	}

	@PutMapping("/students/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
		Optional<Student> existingStudent = list.stream().filter(s -> s.getId().equals(id)).findFirst();
		if (existingStudent.isPresent()) {
			Student student = existingStudent.get();
			student.setName(updatedStudent.getName());
			student.setCity(updatedStudent.getCity());
			return new ResponseEntity<>(student, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping("/students/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
		Optional<Student> studentToRemove = list.stream().filter(s -> s.getId().equals(id)).findFirst();
		if (studentToRemove.isPresent()) {
			list.remove(studentToRemove.get());
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}