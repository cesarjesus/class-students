package cfg.samples;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClaszService {

	private final ClaszRepository claszRepository;
	
	public List<Clasz> findAll() {
		return claszRepository.findAll();
	}
	
	public Optional<Clasz> findById(String code) {
		return claszRepository.findById(code);
	}
	
	public Clasz save(Clasz clasz) {
		return claszRepository.save(clasz);
	}
	
	public void deleteById(String code) {
		claszRepository.deleteById(code);
	}
}