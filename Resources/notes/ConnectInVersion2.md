# ConnectInVersion2.pdf
Pages: 2

---


## Page 1

users comments
11111
id bigint id bigint
teams team_members
0..1 *
email varchar(255) user_id bigint
id bigint 1 id bigint
*
first_name varchar(255) * post_id bigint
name varchar(255) team_id bigint
last_name varchar(255) * text text
description text user_id bigint
password varchar(255) * created_at datetime
owner_id bigint role varchar(20)
role enum(USER,ADMIN,ROOT) updated_at datetime
created_at datetime
profil_pic varchar(255) deleted_at datetime
updated_at datetime
posts
created_at datetime
11
id bigint
updated_at datetime
* likes
user_id bigint
* id bigint
team_id bigint
*
user_id bigint
captions text
*
post_id bigint
image_path varchar(255)
like_counter int
created_at datetime
updated_at datetime
deleted_at datetime

## Page 2

users comments
11111
id bigint id bigint
teams team_members
0..1 *
email varchar(255) user_id bigint
id bigint 1 id bigint
*
first_name varchar(255) * post_id bigint
name varchar(255) team_id bigint
last_name varchar(255) * text text
description text user_id bigint
password varchar(255) * created_at datetime
owner_id bigint role varchar(20)
role enum(USER,ADMIN,ROOT) updated_at datetime
created_at datetime
profil_pic varchar(255) deleted_at datetime
updated_at datetime
posts
created_at datetime
11
id bigint
updated_at datetime
* likes
user_id bigint
* id bigint
team_id bigint
*
user_id bigint
captions text
*
post_id bigint
image_path varchar(255)
like_counter int
created_at datetime
updated_at datetime
deleted_at datetime