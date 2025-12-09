package com.cs613.smp.controller;

import com.cs613.smp.entity.ApiResponse;
import com.cs613.smp.entity.dto.Post;
import com.cs613.smp.entity.PostFeedReq;
import com.cs613.smp.entity.PostReq;
import com.cs613.smp.exn.EmptyPIDExn;
import com.cs613.smp.exn.EmptyUIDExn;
import com.cs613.smp.exn.InvalidPostContentExn;
import com.cs613.smp.exn.InvalidUIDExn;
import com.cs613.smp.repo.PostRepo;
import com.cs613.smp.repo.UserRepo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(description = "send a post, Post id (pid) is ignored, other fields are mandatory")
    @PostMapping("/create")
    public ApiResponse<Long> create(@RequestBody PostReq req) throws Exception {
        String content = req.getContent();
        if (content == null || content.isEmpty() || content.length() > 250) {
            throw new InvalidPostContentExn();
        }

        Long uid = req.getUid();

        if (uid == null || !userRepo.existsById(uid)) {
            throw new InvalidUIDExn();
        }
        LocalDateTime now = LocalDateTime.now();
        Post post = new Post(null, uid, content, now);
        Post dbPost = postRepo.save(post);
        return ApiResponse.ok(dbPost.getPid());
    }

    @Operation(description = "ONLY pid is meaningful, other fields are ignored")
    @PostMapping("/remove")
    public ApiResponse<Object> remove(@RequestBody PostReq req) {
        if (req.getPid() != null) {
            postRepo.deleteById(req.getPid());
        }
        return ApiResponse.ok(null);
    }

    @Operation(description = "Locate a specific post by pid (other fields are ignored)")
    @PostMapping("/locate")
    public ApiResponse<Optional<Post>> locate(@RequestBody PostReq req) throws Exception {
        Long pid = req.getPid();
        if (pid == null) {
            throw new EmptyPIDExn();
        }

        return ApiResponse.ok(postRepo.findById(pid));
    }

    @Operation(description = "Locate posts from uid's following users, where uid is mandatory")
    @PostMapping("/feed")
    public ApiResponse<List<Post>> feed(@RequestBody PostFeedReq req) throws Exception {
        Long uid = req.getUid();
        LocalDateTime from = Objects.requireNonNullElse(req.getTimeFrom(), LocalDateTime.MIN);
        Integer limit = Objects.requireNonNullElse(req.getLimit(), 10);
        Integer offset = Objects.requireNonNullElse(req.getOffset(), 0);

        if (uid == null) {
            throw new EmptyUIDExn();
        }
        String content = req.getSearch();
        if (content == null || content.isEmpty()) {
            return ApiResponse.ok(postRepo.findAllFeedFrom(uid, from, limit, offset));
        } else {
            return ApiResponse.ok(postRepo.searchAllFeed(content, uid, from, limit, offset));
        }
    }

    @Operation(description = "Search posts only of provided uid (optional), or globally search for posts")
    @PostMapping("/search")
    public ApiResponse<List<Post>> search(@RequestBody PostFeedReq req) {
        Long uid = req.getUid();

        LocalDateTime from = Objects.requireNonNullElse(req.getTimeFrom(), LocalDateTime.MIN);

        Integer limit = Objects.requireNonNullElse(req.getLimit(), 10);
        Integer offset = Objects.requireNonNullElse(req.getOffset(), 0);

        String content = req.getSearch();
        boolean emptyContent = content == null || content.isEmpty();

        if (uid != null) {
            if (emptyContent) {
                return ApiResponse.ok(postRepo.findUid(uid, from, limit, offset));
            } else {
                return ApiResponse.ok(postRepo.searchUid(content, uid, limit, offset));
            }
        } else {
            if (emptyContent) {
                return ApiResponse.ok(postRepo.findAllRecent(limit, offset));
            } else {
                return ApiResponse.ok(postRepo.search(content, limit, offset));
            }
        }

    }
}
