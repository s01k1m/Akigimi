use akgimi;
delete from users where kakao_profile_nickname = '가나다';
delete from users where kakao_profile_nickname = '라마바';

insert into users(id,kakao_profile_nickname, nickname, oauth_id, oidc_provider, user_state) values (9999,'가나다','nick1','1','KAKAO','PENDING');
insert into users(id,kakao_profile_nickname, nickname, oauth_id, oidc_provider, user_state) values (10000,'라마바','nick2','2','KAKAO','ACTIVE');

insert into account(id, account_type, is_deleted, is_password_registered, balance, created_at, updated_at, user_id, account_number, bank, password)
    values (1, 0, false, true, 50000, now(),now(), 9999, '1231-4515-112', 'MULTI','1234');
insert into account(id, account_type, is_deleted, is_password_registered, balance, created_at, updated_at, user_id, account_number, bank, password)
    values (2, 1, false, true, 50000, now(),now(), 9999, '1231-4515-114', 'MULTI','1234');

insert into product(id, name, thumbnail, image, detail, url, price, is_deleted)
values (1, '닌텐도DS', '/images/nintendo.png', '/images/nintendo.png', '디테일', '/images/nintendo.png', 20000, 0);
insert into product(id, name, thumbnail, image, detail, url, price, is_deleted)
values (2, '시그니엘숙박권', '/images/nintendo.png', '/images/nintendo.png', '디테일', '/images/nintendo.png', 30000, 0);

insert into challenge(id, user_id, product_id, accumulated_amount, achievement_state, challenge_period, is_in_progress, achievement_date, created_at, updated_at)
values (999, 9999, 2, 20000, true, 30, false, '2023-08-08', current_timestamp, current_timestamp);
