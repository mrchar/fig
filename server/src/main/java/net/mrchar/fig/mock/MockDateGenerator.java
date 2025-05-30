package net.mrchar.fig.mock;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.mrchar.fig.authentication.UserEntity;
import net.mrchar.fig.authentication.UserRepository;
import net.mrchar.fig.data.RecordEntity;
import net.mrchar.fig.data.RecordRepository;
import net.mrchar.fig.form.FormEntity;
import net.mrchar.fig.form.FormRepository;
import net.mrchar.fig.function.FunctionEntity;
import net.mrchar.fig.function.FunctionRepository;
import net.mrchar.fig.space.SpaceEntity;
import net.mrchar.fig.space.SpaceRepository;
import net.mrchar.fig.struct.StructEntity;
import net.mrchar.fig.struct.StructRepository;
import net.mrchar.fig.vocabulary.VocabularyEntity;
import net.mrchar.fig.vocabulary.VocabularyRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "mock.enabled", havingValue = "true")
public class MockDateGenerator {
  private final SecurityProperties securityProperties;
  private final UserRepository userRepository;
  private final SpaceRepository spaceRepository;
  private final VocabularyRepository vocabularyRepository;
  private final StructRepository structRepository;
  private final FormRepository formRepository;
  private final RecordRepository recordRepository;
  private final FunctionRepository functionRepository;

  @PostConstruct
  public void init() {
    UserEntity userEntity =
        this.userRepository.findByUsername(securityProperties.getUser().getName()).orElseThrow();

    List<SpaceEntity> spaceEntities = SpaceEntityGenerator.generate(userEntity, 10);
    this.spaceRepository.saveAll(spaceEntities);

    List<VocabularyEntity> vocabularyEntities = VocabularyEntityGenerator.generate(10);
    this.vocabularyRepository.saveAll(vocabularyEntities);

    List<StructEntity> structEntities = StructEntityGenerator.generate(10);
    this.structRepository.saveAll(structEntities);

    List<FormEntity> formEntities = FormEntityGenerator.generate(structEntities.get(0), 10);
    this.formRepository.saveAll(formEntities);

    List<RecordEntity> recordEntities = RecordEntityGenerator.generate(formEntities.get(0), 10);
    this.recordRepository.saveAll(recordEntities);

    List<FunctionEntity> functionEntities = FunctionEntityGenerator.generate(10);
    this.functionRepository.saveAll(functionEntities);
  }
}
