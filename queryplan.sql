-- readAllMy
explain
select post.id,
       post.name,
       post.contents,
       post.views,
       post.is_admin,
       post.created_at,
       post.updated_at,
       post.category_id,
       post.user_id
from (select id
      from post
      where user_id = 1
      order by created_at desc) t
         join post on t.id = post.id;

-- read comment
explain
select id, contents, post_id, sub_comment_id, user_id
from comment
where id = 5
  and post_id = 3;

-- readAll comment
explain
select id, contents, post_id, sub_comment_id, user_id
from comment
where post_id = 3
order by id desc;