use akgimi;
delete from users where kakao_profile_nickname = '가나다';
delete from users where kakao_profile_nickname = '라마바';

insert into users(id,kakao_profile_nickname, nickname, oauth_id, oidc_provider, user_state) values (9999,'가나다','nick1','1','KAKAO','PENDING');
insert into users(id,kakao_profile_nickname, nickname, oauth_id, oidc_provider, user_state) values (10000,'라마바','nick2','2','KAKAO','ACTIVE');

insert into product(id, thumbnail, image, detail, url, price, is_deleted)
values (1, '썸네일', '이미지', '디테일', '링크', 20000, 0);