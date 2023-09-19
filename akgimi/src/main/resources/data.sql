use akgimi;
-- delete from users where kakao_profile_nickname = '가나다';
-- delete if exists from users where kakao_profile_nickname = '라마바';

insert into users(id,kakao_profile_nickname, nickname, oauth_id, oidc_provider, user_state) values (9999,'가나다','nick1','1','KAKAO','PENDING');
insert into users(id,kakao_profile_nickname, nickname, oauth_id, oidc_provider, user_state) values (10000,'라마바','nick2','2','KAKAO','ACTIVE');

insert into account(id, account_type, is_deleted, is_password_registered, balance, created_at, updated_at, user_id, account_number, bank, password)
    values (1, 0, false, true, 50000, now(),now(), 9999, '1231-4515-112', 'MULTI','1234');
insert into account(id, account_type, is_deleted, is_password_registered, balance, created_at, updated_at, user_id, account_number, bank, password)
    values (2, 1, false, true, 50000, now(),now(), 9999, '1231-4515-114', 'MULTI','1234');

insert into product(id, name, thumbnail, image, detail, url, price, is_deleted)
values (1, '닌텐도DS', '썸네일', '이미지', '디테일', '링크', 20000, 0);

insert into challenge(id, accumulated_amount,achievement_date,
                      achievement_state,challenge_end_date,
                      challenge_period,challenge_start_date,
                      is_in_progress,created_at,user_id) values (1, 0, null, false, now(),50,now(), true,now(),9999);

insert into feed(is_deleted,is_public,accumulated_amount,created_at,price,
    updated_at, image_url, not_purchased_item, place,content,user_id,challenge_id)
values (false, true, 30000, now(), 3000, now(), "hsdfa.com","item","place","content",9999,1);

insert into feed(is_deleted,is_public,accumulated_amount,created_at,price,
                     updated_at, image_url, not_purchased_item, place,content,user_id,challenge_id)
values (false, true, 33000, now(), 3000, now(), "hsdfa.com","item","place","content",9999,1);

insert into feed(is_deleted,is_public,accumulated_amount,created_at,price,
                 updated_at, image_url, not_purchased_item, place,content,user_id,challenge_id)
values (false, true, 36000, now(), 3000, now(), "hsdfa.com","item","place","content",9999,1);

insert into feed(is_deleted,is_public,accumulated_amount,created_at,price,
                 updated_at, image_url, not_purchased_item, place,content,user_id,challenge_id)
values (false, true, 39000, now(), 3000, now(), "hsdfa.com","item","place","content",9999,1);

insert into feed(is_deleted,is_public,accumulated_amount,created_at,price,
                 updated_at, image_url, not_purchased_item, place,content,user_id,challenge_id)
values (false, true, 42000, now(), 3000, now(), "hsdfa.com","item","place","content",9999,1);

insert into feed(is_deleted,is_public,accumulated_amount,created_at,price,
                 updated_at, image_url, not_purchased_item, place,content,user_id,challenge_id)
values (false, true, 45000, now(), 3000, now(), "hsdfa.com","item","place","content",9999,1);