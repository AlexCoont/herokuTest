package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.dto.requestDto.AddCommentToPostDto;
import project.models.Post;
import project.models.PostComment;
import project.models.User;
import project.repositories.PostCommentRepository;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PostCommentService {
    private PostCommentRepository postCommentRepository;

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
