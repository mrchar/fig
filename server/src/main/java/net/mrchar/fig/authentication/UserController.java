package net.mrchar.fig.authentication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
  private final UserRepository userRepository;
  private final UserService userService;

  @GetMapping
  public Page<UserEntity> listUsers(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  @Getter
  @Setter
  public static class AddOrUpdateUserParams {
    private String username;
  }

  @PostMapping
  public UserEntity addUser(@RequestBody AddOrUpdateUserParams params) {
    return this.userService.addUser(params.getUsername());
  }

  @PutMapping("/{code}")
  public UserEntity updateUser(
      @PathVariable String code, @RequestBody UserService.UpdateUserParams params) {
    return this.userService.updateUserName(code, params);
  }

  @DeleteMapping("/{code}")
  public UserEntity deleteUser(@PathVariable String code) {
    return this.userService.deleteUser(code);
  }
}
