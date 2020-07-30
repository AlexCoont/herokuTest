package project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.models.Post;
import project.models.PostVote;
import project.repositories.PostVotesRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostVotesService {
    private PostVotesRepository postVotesRepository;

    @PostConstruct
    private void init(){

        List<PostVote> postVoteList = new ArrayList<>();

        PostVote postVote = new PostVote();
        Post post = new Post();
        post.setId(1);
        postVote.setPostId(post);
        postVote.setUserId(1);
        postVote.setValue((byte)1);
        postVote.setTime(LocalDateTime.now());
        postVoteList.add(postVote);

        PostVote postVote2 = new PostVote();
        postVote2.setPostId(post);
        postVote2.setUserId(2);
        postVote2.setValue((byte)1);
        postVote2.setTime(LocalDateTime.now());
        postVoteList.add(postVote2);

        PostVote postVote3 = new PostVote();
        Post post3 = new Post();
        post3.setId(2);
        postVote3.setPostId(post3);
        postVote3.setUserId(3);
        postVote3.setValue((byte)1);
        postVote3.setTime(LocalDateTime.now());
        postVoteList.add(postVote3);

        PostVote postVote4 = new PostVote();
        Post post4 = new Post();
        post4.setId(3);
        postVote4.setPostId(post4);
        postVote4.setUserId(1);
        postVote4.setValue((byte)-1);
        postVote4.setTime(LocalDateTime.now());
        postVoteList.add(postVote4);

        postVotesRepository.saveAll(postVoteList);
    }

    /**
     * добавление лайка или дизлайка к посту, возвращает false, если лайк или дизлайк уже стоит,
     * а пользователь пытается поставить во второй раз
     */
    public boolean takeLikeOrDislikeToPost(Post post, Integer userId, int likeOrDis){
        Optional<PostVote> postVoteOptional = postVotesRepository.findByPostIdAndUserId(post.getId(), userId);

        if (!postVoteOptional.isPresent()){
            saveLikeOrDislike(post, userId, likeOrDis);
            return true;
        }
        if (postVoteOptional.get().getValue() == likeOrDis){
            return false;
        }
            PostVote postVote = postVoteOptional.get();
            postVote.setValue((byte)likeOrDis);
            postVote.setTime(LocalDateTime.now());
            postVotesRepository.save(postVote);
            return true;
    }

    /**
     * сохранение лайка или дизлайка
     */
    private void saveLikeOrDislike(Post post, Integer userId, int likeOrDis){
        PostVote postVote = new PostVote();
        postVote.setTime(LocalDateTime.now());
        postVote.setValue((byte)likeOrDis);
        postVote.setPostId(post);
        postVote.setUserId(userId);
        postVotesRepository.save(postVote);
    }
}
