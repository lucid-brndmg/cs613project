package com.cs613.smp.controller;

import com.cs613.smp.entity.*;
import com.cs613.smp.entity.dto.UidPair;
import com.cs613.smp.entity.dto.User;
import com.cs613.smp.entity.dto.UserFollow;
import com.cs613.smp.exn.InvalidFollowRelationExn;
import com.cs613.smp.exn.InvalidRegistrationRequestExn;
import com.cs613.smp.exn.InvalidUIDExn;
import com.cs613.smp.repo.PostRepo;
import com.cs613.smp.repo.UserFollowRepo;
import com.cs613.smp.repo.UserRepo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    UserFollowRepo userFollowRepo;

    @Autowired
    PostRepo postRepo;

    @Operation(description = "simulates a registration process, where if the username not exists then is inserted to database, otherwise update login time. Returns the details of a user (including user id)")
    @PostMapping("/upsert")
    public ApiResponse<UserLocateResp> upsert(@RequestBody UserLocateReq req) throws InvalidRegistrationRequestExn {
        LocalDateTime now = LocalDateTime.now();
        String username = req.getUsername();

        if (username != null && !username.isEmpty()) {
            if (req.getUidIdentity() != null || req.getUid() != null) {
                throw new InvalidRegistrationRequestExn();
            }
            userRepo.upsert(new User(null, username, now, now));
        }
        User u = userRepo.findByUsername(username);
        return ApiResponse.ok(locateDetail(u, null));
    }

    @Operation(description = "locate a specific user by username or user id (uid)")
    @PostMapping("/locate")
    public ApiResponse<Optional<UserLocateResp>> locate(@RequestBody UserLocateReq req) {
        String username = req.getUsername();
        Long session = req.getUidIdentity();

        Long uid = req.getUid();
        User found = null;

        if (username != null) {
            found = userRepo.findByUsername(username);
        } else if (uid != null) {
            Optional<User> optionalUser = userRepo.findById(uid);
            if (optionalUser.isPresent()) {
                found = optionalUser.get();
            }
        }

        if (found == null) {
            return ApiResponse.ok(Optional.empty());
        }

        return ApiResponse.ok(Optional.of(locateDetail(found, session)));
    }
    private UserLocateResp locateDetail(User user, Long uid) {
        Long targetUid = user.getUid();
        Long following = userFollowRepo.countFollowingByUid(targetUid);
        Long follower = userFollowRepo.countFollowerByUid(targetUid);
        Long posts = postRepo.countPostsByUid(targetUid);
        Boolean isFollowing = null, isFollower = null;

        if (uid != null && !uid.equals(targetUid)) {
            UidPair p = new UidPair(uid, targetUid);
            isFollowing = userFollowRepo.existsById(p);
            isFollower = userFollowRepo.existsById(p.invert());
        }

        return UserLocateResp.ofUser(user, following, follower, posts, isFollowing, isFollower);
    }

    private List<UserLocateResp> locateDetails(List<User> us, Long uid) {
        List<UserLocateResp> results = new ArrayList<>();
        for (User user : us) {
            results.add(locateDetail(user, uid));
        }
        return results;
    }

    @Operation(description = "globally search users. If uidIdentity is provided then each user's follow relationship with uidIdentity is shown as Booleans, otherwise null")
    @PostMapping("/search")
    public ApiResponse<List<UserLocateResp>> search(@RequestBody UserSearchReq req) {
        String search = req.getUsername();
        List<User> us = null;
        Integer limit = Objects.requireNonNullElse(req.getLimit(), 10);
        Integer offset = Objects.requireNonNullElse(req.getOffset(), 0);
        if (search == null || search.isEmpty()) {
            us = userRepo.findAllRecent(limit, offset);
        } else {
            us = userRepo.searchByUsername(search, limit, offset);
        }

        Long session = req.getUidIdentity();

        List<UserLocateResp> results = this.locateDetails(us, session);

        return ApiResponse.ok(results);
    }

    @Operation(description = "list all followings of provided uid, where username is optional for searching")
    @PostMapping("/following")
    public ApiResponse<List<UserLocateResp>> findFollowing(@RequestBody UserSearchReq req) throws InvalidUIDExn {
        Long session = req.getUidIdentity();
        Integer limit = Objects.requireNonNullElse(req.getLimit(), 10);
        Integer offset = Objects.requireNonNullElse(req.getOffset(), 0);

        if (session == null) {
            throw new InvalidUIDExn();
        }
        String username = req.getUsername();

        List<User> following = username == null || username.isEmpty()
                ? userFollowRepo.findAllFollowingsByUid(session, limit, offset)
                : userFollowRepo.searchFollowingByUid(username, session, limit, offset);
        return ApiResponse.ok(this.locateDetails(following, session));
    }

    @Operation(description = "list all followers of provided uid, where username is optional for searching")
    @PostMapping("/follower")
    public ApiResponse<List<UserLocateResp>> findFollower(@RequestBody UserSearchReq req) throws InvalidUIDExn {
        Long session = req.getUidIdentity();
        Integer limit = Objects.requireNonNullElse(req.getLimit(), 10);
        Integer offset = Objects.requireNonNullElse(req.getOffset(), 0);

        if (session == null) {
            throw new InvalidUIDExn();
        }
        String username = req.getUsername();


        List<User> follower = username == null || username.isEmpty()
                ? userFollowRepo.findAllFollowersByUid(session, limit, offset)
                : userFollowRepo.searchFollowerByUid(username, session, limit, offset);
        return ApiResponse.ok(this.locateDetails(follower, session));
    }

    @PostMapping("/follow/toggle")
    @Operation(description = "TOGGLE user's following status, which means, let s, t be 2 users (source, target), if currently s is following t then after this request s cancels the following of t, and vice versa. Returns a single Boolean indicates 'is s following t' after this action")
    public ApiResponse<Boolean> toggleFollow(@RequestBody UidPair pair) throws Exception {
        if (pair.uidSource() == null || pair.uidTarget() == null || Objects.equals(pair.uidSource(), pair.uidTarget())) {
            throw new InvalidFollowRelationExn();
        }

        if (!userRepo.existsById(pair.uidSource()) || !userRepo.existsById(pair.uidTarget())) {
            throw new InvalidUIDExn();
        }

        if (userFollowRepo.existsById(pair)) {
            userFollowRepo.deleteById(pair);
            return ApiResponse.ok(false);
        } else {
            LocalDateTime now = LocalDateTime.now();
            userFollowRepo.insert(pair.uidSource(), pair.uidTarget(), now);
            return ApiResponse.ok(true);
        }
    }
}
