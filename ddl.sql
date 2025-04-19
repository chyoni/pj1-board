create table user
(
    id          int          not null auto_increment primary key,
    user_id     varchar(45)  not null unique,
    password    varchar(150) not null,
    nickname    varchar(45)  not null,
    is_admin    tinyint      not null,
    is_withdraw tinyint      not null,
    created_at  datetime     not null,
    updated_at  datetime     not null
);

create table category
(
    id   int         not null auto_increment primary key,
    name varchar(45) not null
);

create table post
(
    id          int          not null auto_increment primary key,
    name        varchar(45)  not null,
    is_admin    tinyint      not null,
    contents    varchar(500) not null,
    created_at  datetime     not null,
    updated_at  datetime     not null,
    views       int default 0,
    category_id int,
    user_id     int,
    constraint fk_post_category foreign key (category_id) references category (id) on delete set null,
    constraint fk_post_user foreign key (user_id) references user (id) on delete cascade
);

-- MySQL index 생성은 WHERE -> ORDER BY 순서로 인덱스를 타게 설계하면 좋다.
create unique index idx_user_id_created_at on post (user_id asc, created_at desc);
create index idx_category_id_created_at on post (category_id asc, created_at desc);
create index idx_name on post (name);
create index idx_name_category_id_created_at on post (name, category_id, created_at desc);

create table tag
(
    id   int         not null auto_increment primary key,
    name varchar(45) not null
);

create index idx_tag_name on tag (name);

create table post_tag
(
    post_id int not null,
    tag_id  int not null,
    primary key (post_id, tag_id),
    constraint fk_post_tag_post foreign key (post_id) references post (id) on delete cascade,
    constraint fk_post_tag_tag foreign key (tag_id) references tag (id) on delete cascade
);

create table file
(
    id        int          not null auto_increment primary key,
    path      varchar(255) not null,
    name      varchar(45)  not null,
    extension varchar(45)  not null,
    post_id   int,
    constraint fk_file_post foreign key (post_id) references post (id) on delete set null
);

create unique index idx_path on file (path);

create table comment
(
    id             int          not null auto_increment primary key,
    contents       varchar(300) not null,
    sub_comment_id int,
    post_id        int          not null,
    user_id        int          not null,
    constraint fk_comment_sub_comment foreign key (sub_comment_id) references comment (id) on delete set null,
    constraint fk_comment_post foreign key (post_id) references post (id) on delete cascade,
    constraint fk_comment_user foreign key (user_id) references user (id) on delete cascade
);

create unique index idx_id_post_id on comment (post_id asc, id desc);
