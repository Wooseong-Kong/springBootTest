package com.wskong.book.springboot.domain.posts;

import com.wskong.book.springboot.domain.Posts.PostsRepository;
import com.wskong.book.springboot.domain.Posts.Posts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void textLoad(){
        String title = "테스트게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder().title(title).content(content).author("rhddntjd611@naver.com").build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_load() {

        //given
        LocalDateTime now = LocalDateTime.of(2022, 1,17,2,18,0);
        postsRepository.save(Posts.builder().title("title").content("content").author("author").build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>>>>>>>>> createDate = "+ posts.getCreatedDate()+", modifiedDate =" + posts.getModifiedDate());

        assertThat(posts.getCreatedDate().isAfter(now));
        assertThat(posts.getModifiedDate().isBefore(now));
    }
}
