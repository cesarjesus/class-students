package cfg.samples;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/class")
public class ClaszAPI {

	private final ClaszService claszService;
	
	@GetMapping
	public ResponseEntity<List<Clasz>> findAll() {
		return ResponseEntity.ok(claszService.findAll());
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<Clasz> findById(@PathVariable String code) {
		Optional<Clasz> clasz = claszService.findById(code);
		if (!clasz.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(clasz.get());
	}
	
	@PostMapping
	public ResponseEntity<Clasz> create(@Valid @RequestBody Clasz clasz) {
		return ResponseEntity.ok(claszService.save(clasz));
	}
	
	@PutMapping("/{code}")
	public ResponseEntity<Clasz> update(@PathVariable String code, @Valid @RequestBody Clasz clasz) {
		Optional<Clasz> cl = claszService.findById(code);
		if (!cl.isPresent() ) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(claszService.save(clasz));
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("/{code}")
	public ResponseEntity delete(@PathVariable String code) {
		Optional<Clasz> cl = claszService.findById(code);
		if (!cl.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		claszService.deleteById(code);
		return ResponseEntity.ok().build();
	}
}
