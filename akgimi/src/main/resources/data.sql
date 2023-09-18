use akgimi;
delete from users where kakao_profile_nickname = '가나다';
delete from users where kakao_profile_nickname = '라마바';

insert into users(id,kakao_profile_nickname, nickname, oauth_id, oidc_provider, user_state) values (9999,'가나다','nick1','1','KAKAO','PENDING');
insert into users(id,kakao_profile_nickname, nickname, oauth_id, oidc_provider, user_state) values (10000,'라마바','nick2','2','KAKAO','ACTIVE');

insert into product(id, name, thumbnail, image, detail, url, price, is_deleted)
values (1, '닌텐도DS', '썸네일', '이미지', '디테일', '링크', 20000, 0);

insert into challenge(accumulated_amount,achievement_date,
                      achievement_state,challenge_end_date,
                      challenge_period,challenge_start_date,
                      is_in_progress,created_at) values (0, null, false, now(),50,now(), true,now());