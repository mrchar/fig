package net.mrchar.fig.vocabulary;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.mrchar.fig.authentication.UserEntity;
import net.mrchar.fig.authentication.UserRepository;
import net.mrchar.fig.common.ResourceNotExistsException;
import net.mrchar.fig.space.SpaceEntity;
import net.mrchar.fig.space.SpaceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class VocabularyService {
  private final UserRepository userRepository;
  private final SpaceRepository spaceRepository;
  private final VocabularyRepository vocabularyRepository;

  @Getter
  @AllArgsConstructor
  public static class ListVocabulariesParams {
    private String keyword;
    @NotBlank private String space;
  }

  public Page<VocabularyEntity> listVocabulariesSpace(
      @Valid ListVocabulariesParams params, Pageable pageable) {
    if (StringUtils.hasText(params.keyword)) {
      return this.vocabularyRepository.searchByNameContainsKeyword(
          params.getSpace(), params.getKeyword(), pageable);
    }

    return this.vocabularyRepository.findAllBySpaceId(params.getSpace(), pageable);
  }

  public VocabularyEntity getVocabulary(@NotNull Long id) {
    return this.vocabularyRepository
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotExistsException("Vocabulary with id %d not found".formatted(id)));
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AddVocabularyParams {
    @JsonUnwrapped private VocabularyConcept vocabulary;
    @NotNull private String spaceCode;
  }

  public VocabularyEntity addVocabulary(@Valid AddVocabularyParams params) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity userEntity =
        this.userRepository
            .findByUsername(username)
            .orElseThrow(
                () -> new ResourceNotExistsException("User not found by username: " + username));

    SpaceEntity spaceEntity =
        this.spaceRepository
            .findByOwnerAndCode(userEntity, params.getSpaceCode())
            .orElseThrow(
                () ->
                    new ResourceNotExistsException(
                        "Space with code %s not found".formatted(params.getSpaceCode())));

    VocabularyEntity entity = new VocabularyEntity(params.getVocabulary(), spaceEntity);
    return this.vocabularyRepository.save(entity);
  }

  public VocabularyEntity updateVocabulary(@NotNull Long id, @Valid VocabularyConcept vocabulary) {
    VocabularyEntity entity = this.getVocabulary(id);
    entity.setVocabulary(vocabulary);
    return this.vocabularyRepository.save(entity);
  }

  public VocabularyEntity deleteVocabulary(@Valid Long id) {
    VocabularyEntity entity = this.getVocabulary(id);

    this.vocabularyRepository.delete(entity);
    return entity;
  }
}
