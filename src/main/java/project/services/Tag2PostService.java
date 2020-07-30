package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.responseDto.TagDto;
import project.models.Tag;
import project.models.Tag2Post;
import project.repositories.Tag2PostRepository;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class Tag2PostService {

    private Tag2PostRepository tag2PostRepository;

    @PostConstruct
    private void init(){

        List<Tag2Post> tag2PostList = new ArrayList<>();

        Tag2Post tag2Post = new Tag2Post();
        tag2Post.setTagId(1);
        tag2Post.setPostId(1);
        tag2PostList.add(tag2Post);

        Tag2Post tag2Post2 = new Tag2Post();
        tag2Post2.setTagId(1);
        tag2Post2.setPostId(2);
        tag2PostList.add(tag2Post2);

        Tag2Post tag2Post3 = new Tag2Post();
        tag2Post3.setTagId(1);
        tag2Post3.setPostId(3);
        tag2PostList.add(tag2Post3);

        Tag2Post tag2Post4 = new Tag2Post();
        tag2Post4.setTagId(2);
        tag2Post4.setPostId(1);
        tag2PostList.add(tag2Post4);

        Tag2Post tag2Post5 = new Tag2Post();
        tag2Post5.setTagId(2);
        tag2Post5.setPostId(2);
        tag2PostList.add(tag2Post5);

        Tag2Post tag2Post6 = new Tag2Post();
        tag2Post6.setTagId(3);
        tag2Post6.setPostId(4);
        tag2PostList.add(tag2Post6);
        tag2PostRepository.saveAll(tag2PostList);
    }

    /**
     * сохранение связей постов и тегов
     */
    @Transactional
    public void addNewTags2Posts(Integer postId, List<Tag> tagList){
        List<Tag2Post> tag2PostList = new ArrayList<>();
        tag2PostRepository.deleteAllByPostId(postId);
        tagList.forEach(tag -> {
                Tag2Post tag2Post = new Tag2Post();
                tag2Post.setPostId(postId);
                tag2Post.setTagId(tag.getId());
                tag2PostList.add(tag2Post);
        });
        tag2PostRepository.saveAll(tag2PostList);
    }

    /**
     * получение списка постов, отсортированных по количеству использований в постах
     */
    public List<TagDto> getTagsSortedByUsingInPost() {
        List<List> list = tag2PostRepository.findTagsAndSortByCountOfPosts();
        if (list.size() == 0){
            return null;
        }
        List<TagDto> tagDtos = new ArrayList<>();
        float k = (float) ((int) ((1.0 / ((BigInteger) list.get(0).get(1)).floatValue()) * 100) / 100.0);
        list.forEach(e -> {
            float weight = ((BigInteger) e.get(1)).floatValue() * k;
            tagDtos.add(new TagDto((String) e.get(0), weight < 0.3 ? 0.3f : weight));
        });
        return tagDtos;
    }
}
