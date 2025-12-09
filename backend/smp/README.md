# Backend

This is the minimal backend implementation in Spring and consists the APIs for users and posts. **There is no user authentication of any sort.** No session-related logics involved. The frontend ought to manually store (or `/upsert`) the *current* user. 

## Configuration

Install Java 17, PostgreSQL and modify the corresponding fields at `resources/application.properties`. Database tables are defined at `tables.sql` which should be created before running. Default port is 8080 and OpenAPI documentation is at `/swagger-ui/index.html`.

## Design
The basic idea is to first *sign in* (not actually) by providing a unique username. Then the user can send / search posts, search / (un)follow other users. 

There are 2 APIs for locating posts: `/search` and `/feed` where both support full-text search and filtering by date. The differences are:
- `/feed` requires the 'current' user ID (by providing from frontend, again, there is NO SESSION) then locate all (or selected) posts from all users that the current user follows (like twitter). 
- `/search` does not require the current user ID. If an UID is provided then API locates the posts of provided user.

All `limit`, `offset` fields of requests are for pagination where the usage is exactly like SQL. All date-time fields are in ISO-8601 format and use `LocalDateTime` to handle. All response are in format of 
```
{
  "success": Boolean,
  "content": T,
  "msg": String
}
```

If `success`, the `msg` is always `success` and the content is any format of JSON response content (i.e. can be `null`, simple Boolean, or composite structures depending on the location). If `!success`, `content` is always `null` and `msg` indicates the error message in format of `A:B:C` where `A` is Java class path of exception, `B` is requested URI, `C` is error message. Example: `com.cs613.smp.exn.InvalidUIDExn:/post/create:Invalid UID` when `uid` not provided when sending posts.

For implementing notifications, it is recommended to first `/user/upsert` a username (to simulate login), then polling `/post/feed` with appropriate time. Pseudocode:
```
let uid = upsert_uid;
let time_from = time_of_upsert_request;

// the front-end performs this in background to keep user notified
while true {
	
	// repeatedly poll latest posts
	let posts = await request({uid, time_from, limit: 99});
	
	if posts.content.length > 0 {
		// there are newer posts
		add_posts_to_screen();
		send_alert_for_each_post(); // or alert once with the count
		
		time_from = now(); // update the timestamp
	}
}
```