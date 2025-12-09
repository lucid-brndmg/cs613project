create table t_user
(
    uid          bigserial  not null
        constraint user_pk
            primary key,
    username     text                                             not null
        constraint username
            unique,
    time_created timestamp                                        not null,
    time_login   timestamp                                        not null
);

create table t_user_follow (
    uid_source bigserial not null,
    uid_target bigserial not null,
    time_created timestamp not null,
    primary key (uid_source, uid_target)
);

create table t_post (
    pid bigserial not null,
    uid bigint not null,
    content text not null,
    time_created timestamp not null,
    primary key pid
);