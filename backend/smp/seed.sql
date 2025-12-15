-- Dummy Users
INSERT INTO t_user (username, time_created, time_login) VALUES
('alice', NOW(), NOW()),
('bob', NOW(), NOW()),
('charlie', NOW(), NOW()),
('david', NOW(), NOW()),
('eve', NOW(), NOW());

-- Dummy Posts
-- Assuming UIDs 1-5 for the above users
INSERT INTO t_post (uid, content, time_created, parent_pid, reference_pid, like_count, repost_count, reply_count) VALUES
(1, 'Just setting up my twitter clone!', NOW() - INTERVAL '1 day', NULL, NULL, 0, 0, 0),
(1, 'Loving the new features.', NOW() - INTERVAL '1 hour', NULL, NULL, 0, 0, 0),
(2, 'Hello world! Bob is here.', NOW() - INTERVAL '2 days', NULL, NULL, 0, 0, 0),
(2, 'Anyone want to grab coffee?', NOW() - INTERVAL '30 minutes', NULL, NULL, 0, 0, 0),
(3, 'Charlie checking in. This app is smooth.', NOW() - INTERVAL '5 hours', NULL, NULL, 0, 0, 0),
(4, 'David here. Testing the feed.', NOW() - INTERVAL '3 days', NULL, NULL, 0, 0, 0),
(5, 'Eve is watching...', NOW() - INTERVAL '1 minute', NULL, NULL, 0, 0, 0);

-- Dummy Follows
-- Alice follows Bob and Charlie
INSERT INTO t_user_follow (uid_source, uid_target, time_created) VALUES
(1, 2, NOW()),
(1, 3, NOW());

-- Bob follows Alice
INSERT INTO t_user_follow (uid_source, uid_target, time_created) VALUES
(2, 1, NOW());

-- Charlie follows Alice, Bob, David
INSERT INTO t_user_follow (uid_source, uid_target, time_created) VALUES
(3, 1, NOW()),
(3, 2, NOW()),
(3, 4, NOW());

-- Eve follows everyone
INSERT INTO t_user_follow (uid_source, uid_target, time_created) VALUES
(5, 1, NOW()),
(5, 2, NOW()),
(5, 3, NOW()),
(5, 4, NOW());
