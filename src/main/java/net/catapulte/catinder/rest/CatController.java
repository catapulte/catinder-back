package net.catapulte.catinder.rest;

import net.catapulte.catinder.model.CatCandidate;
import net.catapulte.catinder.model.CatProfile;
import net.catapulte.catinder.repository.CatRepository;
import net.catapulte.catinder.repository.MockCatRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CatController {

    private final CatRepository repository = new MockCatRepository();

    @GetMapping("/cats/{name}")
    public CatProfile cats(@PathVariable String name) {
        return repository.get(name);
    }

    @PutMapping("/cats/{name}")
    public void edit(@PathVariable String name, @RequestBody CatProfile updates) {
        CatProfile p = repository.get(name);
        p.getPatounes().addAll(updates.getPatounes());
        p.getGriffounes().addAll(updates.getGriffounes());
    }

    @GetMapping("/cats/{name}/candidates")
    public List<CatCandidate> candidates(@PathVariable String name) {
        return repository.getCandidatesFor(name);
    }

    @GetMapping("/cats/{name}/candidates/first")
    public CatCandidate nextCandidate(@PathVariable String name) {
        List<CatCandidate> candidates = repository.getCandidatesFor(name);
        if (candidates.isEmpty()) {
            return null;
        }
        return candidates.get(0);
    }
}