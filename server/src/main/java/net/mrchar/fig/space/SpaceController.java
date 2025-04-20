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
    return this.spaceRepository.findAllForUser(pageable);
  }

  @GetMapping("/{code}")
  public SpaceEntity getSpaces(@PathVariable String code) {
    return this.spaceService.getSpace(code);
  }

  @Getter
  @Setter
  public static class AddOrUpdateSpaceParams {
    private String name;
  }

  @PostMapping
  public SpaceEntity addSpace(@RequestBody AddOrUpdateSpaceParams params) {
    return this.spaceService.addSpace(params.getName());
  }

  @PutMapping("{code}")
  public SpaceEntity updateSpace(
      @PathVariable String code, @RequestBody AddOrUpdateSpaceParams params) {
    return this.spaceService.updateSpace(code, params.getName());
  }

  @DeleteMapping("{code}")
  public SpaceEntity deleteSpace(@PathVariable String code) {
    return this.spaceService.deleteSpace(code);
  }
}
