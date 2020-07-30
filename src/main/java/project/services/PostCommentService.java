package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.dto.requestDto.AddCommentToPostDto;
import project.models.Post;
import project.models.PostComment;
import project.models.User;
import project.repositories.PostCommentRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PostCommentService {
    private PostCommentRepository postCommentRepository;

    @PostConstruct
    private void init(){
        List<PostComment> postCommentList = new ArrayList<>();

        PostComment postComment = new PostComment();
        Post post = new Post();
        post.setId(1);
        User user = new User();
        user.setId(1);
        postComment.setUserId(user);
        postComment.setTime(LocalDateTime.now());
        postComment.setText("test");
        postComment.setPostId(post);
        postCommentList.add(postComment);

        PostComment postComment2 = new PostComment();
        post.setId(1);
        User user2 = new User();
        user2.setId(2);
        postComment2.setUserId(user2);
        postComment2.setTime(LocalDateTime.now());
        postComment2.setText("test");
        postComment2.setPostId(post);
        postCommentList.add(postComment2);

        PostComment postComment3 = new PostComment();
        Post post3 = new Post();
        post3.setId(2);
        User user3 = new User();
        user3.setId(3);
        postComment3.setUserId(user3);
        postComment3.setTime(LocalDateTime.now());
        postComment3.setText("test");
        postComment3.setPostId(post3);
        postCommentList.add(postComment3);

        postCommentRepository.saveAll(postCommentList);
    }

    /**
     * Добавление нового комментария к посту
     */
    public Integer addNewCommentToPost(AddCommentToPostDto addCommentToPostDto, Post post, User user){
        PostComment postComment = new PostComment();
        postComment.setPostId(post);
        postComment.setParentId(addCommentToPostDto.getParentId());
        postComment.setText(addCommentToPostDto.getText());
        postComment.setTime(LocalDateTime.now());
        postComment.setUserId(user);
        postCommentRepository.save(postComment);
        return postComment.getId();
    }

    /**
     * Получение комментария по айди поста и айди комментария-родителя
     */
    public PostComment getPostCommentByParentIdAndPostId(Integer parentId, Integer postId){
        return postCommentRepository.findByIdAndPostId(parentId, postId);
    }
}
