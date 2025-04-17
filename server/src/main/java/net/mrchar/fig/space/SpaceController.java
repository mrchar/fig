package net.mrchar.fig.space;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spaces")
public class SpaceController {
  private final SpaceRepository spaceRepository;
  private final SpaceService spaceService;

  @GetMapping
  public Page<SpaceEntity> listSpaces(Pageable pageable) {
    return spaceRepository.findAll(pageable);
  }

  @Getter
  @Setter
  public static class AddOrUpdateParams {
    private String name;
  }

  @PostMapping
  public SpaceEntity addSpace(@RequestBody AddOrUpdateParams params) {
    return this.spaceService.addSpace(params.getName());
  }

  @PutMapping("{code}")
  public SpaceEntity updateSpace(@PathVariable String code, @RequestBody AddOrUpdateParams params) {
    return this.spaceService.updateSpace(code, params.getName());
  }

  @DeleteMapping("{code}")
  public SpaceEntity deleteSpace(@PathVariable String code) {
    return this.spaceService.deleteSpace(code);
  }
}
