create table t_user
(
    uid          bigserial  not null constraint user_pk primary key,
    username     text                                             not null constraint username unique,
    time_created timestamp                                        not null,
    time_login   timestamp                                        not null
);

create table t_user_follow (
    uid_source bigserial not null,
    uid_target bigserial not null,
    time_created timestamp not null,
    constraint user_follow_pk primary key (uid_source, uid_target)
);

create table t_post (
    pid bigserial not null constraint post_pk primary key,
    uid bigint not null,
    content text not null,
    time_created timestamp not null,
    parent_pid bigint,
    reference_pid bigint,
    like_count int default 0,
    repost_count int default 0,
    reply_count int default 0,
    likers_bf bytea
);

create table t_post_interaction (
    id bigserial not null constraint interaction_pk primary key,
    pid bigint not null,
    uid bigint not null,
    time_created timestamp not null,
    constraint unique_interaction unique (pid, uid)
);