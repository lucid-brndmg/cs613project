package com.cs613.smp.controller;

import com.cs613.smp.entity.ApiResponse;
import com.cs613.smp.entity.PostLocateReq;
import com.cs613.smp.entity.dto.Post;
import com.cs613.smp.entity.PostFeedReq;
import com.cs613.smp.entity.PostCreationReq;
import com.cs613.smp.entity.PostInteraction;
import com.cs613.smp.entity.PostInteractionReq;
import com.cs613.smp.exn.EmptyPIDExn;
import com.cs613.smp.exn.EmptyUIDExn;
import com.cs613.smp.exn.InvalidPostContentExn;
import com.cs613.smp.exn.InvalidUIDExn;
import com.cs613.smp.repo.PostRepo;
import com.cs613.smp.repo.PostInteractionRepo;
import com.cs613.smp.repo.UserRepo;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostRepo postRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    PostInteractionRepo interactionRepo;

    @Operation(description = "send a post, Post id (pid) is ignored, other fields are mandatory")
    @PostMapping("/create")
    public ApiResponse<Long> create(@RequestBody PostCreationReq req) throws Exception {
        String content = req.getContent();
        if (content == null || content.isEmpty() || content.length() > 250) {
            throw new InvalidPostContentExn();
        }

        Long uid = req.getUid();
        if (uid == null || !userRepo.existsById(uid)) {
            throw new InvalidUIDExn();
        }

        BloomFilter<Long> bf = BloomFilter.create(Funnels.longFunnel(), 1000, 0.01);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(bf);
        byte[] bfBytes = baos.toByteArray();

        LocalDateTime now = LocalDateTime.now();
        Post post = new Post(null, uid, content, now, req.getParentPid(), req.getReferencePid(), 0, 0, 0, bfBytes);
        Post dbPost = postRepo.save(post);
        return ApiResponse.ok(dbPost.getPid());
    }

    @Operation(description = "ONLY pid is meaningful, other fields are ignored")
    @PostMapping("/remove")
    public ApiResponse<Object> remove(@RequestBody PostLocateReq req) {
        if (req.getPid() != null) {
            postRepo.deleteById(req.getPid());
        }
        return ApiResponse.ok(null);
    }

    @Operation(description = "Locate a specific post by pid (other fields are ignored)")
    @PostMapping("/locate")
    public ApiResponse<Optional<Post>> locate(@RequestBody PostLocateReq req) throws Exception {
        Long pid = req.getPid();
        if (pid == null) {
            throw new EmptyPIDExn();
        }

        Optional<Post> postOpt = postRepo.findById(pid);
        if (postOpt.isEmpty() || req.getViewerUid() == null) {
            return ApiResponse.ok(postOpt);
        }

        enrichPosts(List.of(postOpt.get()), req.getViewerUid());
        return ApiResponse.ok(postOpt);
    }

    @Operation(description = "Like a post (toggle on/off)")
    @PostMapping("/like")
    public ApiResponse<Boolean> like(@RequestBody PostInteractionReq req) throws Exception {
        Long pid = req.getPid();
        Long uid = req.getUid();

        if (pid == null || uid == null) {
            return ApiResponse.ok(false);
        }

        Post post = postRepo.findById(pid).orElse(null);
        if (post == null) {
            return ApiResponse.ok(false);
        }

        List<PostInteraction> existingInteractions = interactionRepo.findByPidAndUid(pid, uid);
        boolean isLiked = !existingInteractions.isEmpty();

        if (isLiked) {
            interactionRepo.deleteById(existingInteractions.get(0).getId());
            post.setLikeCount(Math.max(0, post.getLikeCount() - 1));
        } else {
            interactionRepo.save(new PostInteraction(pid, uid, LocalDateTime.now()));
            post.setLikeCount(post.getLikeCount() + 1);
        }

        postRepo.save(post);
        return ApiResponse.ok(true);
    }

    private void enrichPosts(List<Post> posts, Long uid) {
        if (uid == null || posts.isEmpty()) return;

        for (Post post : posts) {
            List<PostInteraction> interactions = interactionRepo.findByPidAndUid(post.getPid(), uid);
            if (!interactions.isEmpty()) {
                post.setIsLiked(true);
            }
        }
    }

    @Operation(description = "Returns all posts, ranked with posts from followed users appearing first, then other posts by time. uid is mandatory for ranking.")
    @PostMapping("/feed")
    public ApiResponse<List<Post>> feed(@RequestBody PostFeedReq req) throws Exception {
        Long uid = req.getUid();
        if (uid == null) {
            throw new EmptyUIDExn();
        }

        LocalDateTime from = Objects.requireNonNullElse(req.getTimeFrom(), LocalDateTime.MIN);
        Integer limit = Objects.requireNonNullElse(req.getLimit(), 10);
        Integer offset = Objects.requireNonNullElse(req.getOffset(), 0);

        String content = req.getSearch();
        boolean hasSearch = content != null && !content.isEmpty();

        List<Post> posts = hasSearch
            ? postRepo.searchAllFeed(content, uid, from, limit, offset)
            : postRepo.findAllFeedFrom(uid, from, limit, offset);

        enrichPosts(posts, uid);
        return ApiResponse.ok(posts);
    }

    @Operation(description = "Search posts only of provided uid (optional), or globally search for posts")
    @PostMapping("/search")
    public ApiResponse<List<Post>> search(@RequestBody PostFeedReq req) {
        Long uid = req.getUid();
        LocalDateTime from = Objects.requireNonNullElse(req.getTimeFrom(), LocalDateTime.MIN);
        Integer limit = Objects.requireNonNullElse(req.getLimit(), 10);
        Integer offset = Objects.requireNonNullElse(req.getOffset(), 0);

        String content = req.getSearch();
        boolean hasSearch = content != null && !content.isEmpty();

        List<Post> posts;
        if (uid != null) {
            posts = hasSearch
                ? postRepo.searchUid(content, uid, limit, offset)
                : postRepo.findUid(uid, from, limit, offset);
        } else {
            posts = hasSearch
                ? postRepo.search(content, limit, offset)
                : postRepo.findAllRecent(limit, offset);
        }

        return ApiResponse.ok(posts);
    }
}
