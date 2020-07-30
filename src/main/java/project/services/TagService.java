package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.models.Tag;
import project.repositories.TagRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class TagService {

    private TagRepository tagRepository;

    @PostConstruct
    private void init(){

        List<Tag> tagList = new ArrayList<>();

        Tag tag = new Tag();
        tag.setName("java");
        tagList.add(tag);

        Tag tag2 = new Tag();
        tag2.setName("spring");
        tagList.add(tag2);

        Tag tag3 = new Tag();
        tag3.setName("skillbox");
        tagList.add(tag3);

        tagRepository.saveAll(tagList);
    }

    /**
     * метод сохраняет новые теги и возвращает лист тегов, для дальнейшего сохранения связей с постами.
     * Используется для добавления или изменения поста
     */
    public List<Tag> tagsToPost(List<String> tags){
        return tags.stream().map(s -> {
            Tag tag = tagRepository.findByName(s);
            if (tag == null){
                Tag newTag = new Tag();
                newTag.setName(s);
                tagRepository.save(newTag);
                return newTag;
            } else {
                return tag;
            }
        }).collect(toList());
    }
}
