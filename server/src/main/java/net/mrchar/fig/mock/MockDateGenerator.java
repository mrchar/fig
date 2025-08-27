package net.mrchar.fig.mock;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import net.mrchar.fig.authentication.UserEntity;
import net.mrchar.fig.authentication.UserRepository;
import net.mrchar.fig.data.RecordEntity;
import net.mrchar.fig.data.RecordRepository;
import net.mrchar.fig.form.FormConcept;
import net.mrchar.fig.form.FormEntity;
import net.mrchar.fig.form.FormRepository;
import net.mrchar.fig.function.FunctionEntity;
import net.mrchar.fig.function.FunctionRepository;
import net.mrchar.fig.space.SpaceEntity;
import net.mrchar.fig.space.SpaceRepository;
import net.mrchar.fig.struct.StructConcept;
import net.mrchar.fig.struct.StructEntity;
import net.mrchar.fig.struct.StructRepository;
import net.mrchar.fig.vocabulary.VocabularyConcept;
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
    this.mockRegularData();
  }

  public void mockRegularData() {
    UserEntity userEntity =
        this.userRepository.findByUsername(securityProperties.getUser().getName()).orElseThrow();

    this.spaceRepository.save(new SpaceEntity("育才中学", userEntity));

    VocabularyEntity name =
        new VocabularyEntity(
            VocabularyConcept.builder()
                .name("姓名")
                .description("使用文本记录人的姓名")
                .definition(Map.of("type", "string", "title", "姓名"))
                .build());
    VocabularyEntity gender =
        new VocabularyEntity(
            VocabularyConcept.builder()
                .name("性别")
                .description("使用文本记录人的性别")
                .definition(Map.of("type", "string", "title", "性别", "enum", List.of("男", "女")))
                .build());
    VocabularyEntity age =
        new VocabularyEntity(
            VocabularyConcept.builder()
                .name("年龄")
                .description("使用正整数记录人的年龄")
                .definition(Map.of("type", "integer", "title", "年龄"))
                .build());
    this.vocabularyRepository.saveAll(List.of(name, gender, age));

    StructEntity studentEntity =
        new StructEntity(
            StructConcept.builder()
                .name("学生")
                .description("学生信息包含姓名、性别、年龄")
                .definition(Map.of())
                .build());
    StructEntity teacherEntity =
        new StructEntity(
            StructConcept.builder()
                .name("教师")
                .description("教师信息包含姓名、性别、年龄")
                .definition(Map.of())
                .build());
    StructEntity courseEntity =
        new StructEntity(
            StructConcept.builder()
                .name("课程")
                .description("课程信息包含名称")
                .definition(Map.of())
                .build());
    this.structRepository.saveAll(List.of(studentEntity, teacherEntity, courseEntity));

    FormEntity studentForm =
        new FormEntity(
            FormConcept.builder()
                .name("学生收集单")
                .description("用于登记学生信息")
                .struct(studentEntity)
                .uiSchema(Map.of())
                .build());
    FormEntity teacherForm =
        new FormEntity(
            FormConcept.builder()
                .name("教师信息收集表")
                .description("用于登记教师信息")
                .struct(teacherEntity)
                .uiSchema(Map.of())
                .build());
    FormEntity courseForm =
        new FormEntity(
            FormConcept.builder()
                .name("课程信息收集表")
                .description("用于登记课程信息")
                .struct(courseEntity)
                .uiSchema(Map.of())
                .build());
    this.formRepository.saveAll(List.of(studentForm, teacherForm, courseForm));
  }

  public void mockRandomData() {
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
