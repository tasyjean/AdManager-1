# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table ads_transaction (
  id_ads_transaction        integer not null,
  banner_placement_id_banner_placement integer,
  transaction_type          varchar(4),
  amount                    integer,
  current_balance           integer,
  timestamp                 timestamp,
  constraint ck_ads_transaction_transaction_type check (transaction_type in ('cpm','cpa','flat')),
  constraint pk_ads_transaction primary key (id_ads_transaction))
;

create table banner (
  id_banner                 integer not null,
  campaign_id_campaign      integer,
  banner_size_id_banner_size integer,
  banner_type               varchar(6),
  name                      varchar(255),
  description               TEXT,
  title                     varchar(255),
  content_text              varchar(255),
  content_link_id           integer,
  target                    varchar(400),
  alt_text                  varchar(400),
  weight                    integer,
  impression_count          integer,
  click_count               integer,
  like_count                integer,
  hide_count                integer,
  is_active                 boolean,
  is_deleted                boolean,
  constraint ck_banner_banner_type check (banner_type in ('banner','text')),
  constraint pk_banner primary key (id_banner))
;

create table banner_action (
  id_banner_action          bigint not null,
  impression_id_impression  bigint,
  action_type               varchar(5),
  timestamp                 timestamp,
  constraint ck_banner_action_action_type check (action_type in ('HIDE','LIKE','CLICK')),
  constraint pk_banner_action primary key (id_banner_action))
;

create table banner_placement (
  id_banner_placement       integer not null,
  banner_id_banner          integer,
  zone_id_zone              integer,
  is_active                 boolean,
  constraint pk_banner_placement primary key (id_banner_placement))
;

create table banner_size (
  id_banner_size            integer not null,
  name                      varchar(255),
  width                     integer,
  height                    integer,
  default_code              TEXT,
  description               TEXT,
  constraint pk_banner_size primary key (id_banner_size))
;

create table banner_type (
  id_banner_type            integer not null,
  name                      varchar(255),
  description               TEXT,
  code                      TEXT,
  constraint pk_banner_type primary key (id_banner_type))
;

create table campaign (
  id_campaign               integer not null,
  id_user_id_user           integer,
  campaign_name             varchar(255),
  campaign_type             varchar(9),
  start_date                timestamp,
  end_date                  timestamp,
  pricing_model             varchar(4),
  limit_impression          integer,
  limit_click               integer,
  current_impression        integer,
  current_click             integer,
  bid_price                 integer,
  is_activated              boolean,
  is_deleted                boolean,
  created_at                timestamp,
  description               TEXT,
  constraint ck_campaign_campaign_type check (campaign_type in ('contract','exclusive')),
  constraint ck_campaign_pricing_model check (pricing_model in ('cpm','cpa','flat')),
  constraint pk_campaign primary key (id_campaign))
;

create table deposito (
  id_deposito               integer not null,
  user_id_user              integer,
  current_balance           integer,
  amount                    integer,
  description               TEXT,
  timestamp                 timestamp,
  payment_method            varchar(400),
  constraint ck_deposito_payment_method check (payment_method in ('OTHER','TRANSFER')),
  constraint pk_deposito primary key (id_deposito))
;

create table file_upload (
  id                        integer not null,
  path                      varchar(255),
  url_path                  varchar(255),
  name                      varchar(255),
  constraint pk_file_upload primary key (id))
;

create table impression (
  id_impression             bigint not null,
  timestamp                 timestamp,
  banner_placement_id_banner_placement integer,
  viewer_ip                 varchar(255),
  viewer_source             varchar(255),
  constraint pk_impression primary key (id_impression))
;

create table notification (
  id_notification           integer not null,
  user_id_user              integer,
  timestamp                 timestamp,
  notification_type         varchar(15),
  param                     varchar(255),
  is_read                   boolean,
  constraint ck_notification_notification_type check (notification_type in ('NEW_USER','NEW_CAMPAIGN','SEE_REPORT','ACTIVE_ADS','EMPTY_DEPOSITO','NON_ACTIVE_ADS','SHOULD_ACTIVE','NEW_DEPOSITO','PLEASE_VALIDATE','VALIDATED')),
  constraint pk_notification primary key (id_notification))
;

create table system_preferences (
  id_system_preferences     integer not null,
  key                       varchar(255),
  value                     TEXT,
  name                      varchar(255),
  description               varchar(255),
  constraint pk_system_preferences primary key (id_system_preferences))
;

create table transfer_confirmation (
  id_transfer_confirmation  integer not null,
  user_id_user              integer,
  user_validator_id_user    integer,
  amount                    integer,
  description               TEXT,
  manager_message           TEXT,
  transfer_date             timestamp,
  sender_bank_account       varchar(255),
  timestamp_created         timestamp,
  timestamp_validated       timestamp,
  is_validated              boolean,
  is_deleted                boolean,
  constraint pk_transfer_confirmation primary key (id_transfer_confirmation))
;

create table user_data (
  id_user                   integer not null,
  email                     varchar(255),
  password                  varchar(255),
  front_name                varchar(255),
  last_name                 varchar(255),
  company                   varchar(255),
  role_id_role              integer,
  join_date                 timestamp,
  current_balance           integer,
  is_active                 boolean,
  user_description          varchar(255),
  profile_photo_id          integer,
  validation_key            varchar(255),
  city                      varchar(255),
  country                   varchar(255),
  constraint pk_user_data primary key (id_user))
;

create table user_contact (
  id_user_contact           integer not null,
  user_id_user              integer,
  contact_value             varchar(255),
  contact_type              varchar(17),
  contact_description       TEXT,
  constraint ck_user_contact_contact_type check (contact_type in ('bank_account','personal_website','social_profile','other','address','private_phone','email','company_website','alternative_phone','home_phone')),
  constraint pk_user_contact primary key (id_user_contact))
;

create table user_permission (
  id_permission             integer not null,
  permission_value          varchar(255),
  constraint pk_user_permission primary key (id_permission))
;

create table user_role (
  id_role                   integer not null,
  name                      varchar(13),
  constraint ck_user_role_name check (name in ('advertiser','manager','administrator')),
  constraint pk_user_role primary key (id_role))
;

create table zone (
  id_zone                   integer not null,
  zone_name                 varchar(255),
  description               TEXT,
  zone_channel_id_zone_channel integer,
  ads_size_id_banner_size   integer,
  zone_type                 varchar(6),
  zone_default_view         varchar(12),
  zone_default_code         TEXT,
  is_deleted                boolean,
  constraint ck_zone_zone_type check (zone_type in ('banner','text')),
  constraint ck_zone_zone_default_view check (zone_default_view in ('DEFAULT_ADS','DEFAULT_CODE','empty')),
  constraint pk_zone primary key (id_zone))
;

create table zone_channel (
  id_zone_channel           integer not null,
  channel_name              varchar(255),
  channel_description       TEXT,
  is_deleted                boolean,
  constraint pk_zone_channel primary key (id_zone_channel))
;


create table user_data_user_permission (
  user_data_id_user              integer not null,
  user_permission_id_permission  integer not null,
  constraint pk_user_data_user_permission primary key (user_data_id_user, user_permission_id_permission))
;
create sequence ads_transaction_seq;

create sequence banner_seq;

create sequence banner_action_seq;

create sequence banner_placement_seq;

create sequence banner_size_seq;

create sequence banner_type_seq;

create sequence campaign_seq;

create sequence deposito_seq;

create sequence file_upload_seq;

create sequence impression_seq;

create sequence notification_seq;

create sequence system_preferences_seq;

create sequence transfer_confirmation_seq;

create sequence user_data_seq;

create sequence user_contact_seq;

create sequence user_permission_seq;

create sequence user_role_seq;

create sequence zone_seq;

create sequence zone_channel_seq;

alter table ads_transaction add constraint fk_ads_transaction_bannerPlace_1 foreign key (banner_placement_id_banner_placement) references banner_placement (id_banner_placement);
create index ix_ads_transaction_bannerPlace_1 on ads_transaction (banner_placement_id_banner_placement);
alter table banner add constraint fk_banner_campaign_2 foreign key (campaign_id_campaign) references campaign (id_campaign);
create index ix_banner_campaign_2 on banner (campaign_id_campaign);
alter table banner add constraint fk_banner_bannerSize_3 foreign key (banner_size_id_banner_size) references banner_size (id_banner_size);
create index ix_banner_bannerSize_3 on banner (banner_size_id_banner_size);
alter table banner add constraint fk_banner_content_link_4 foreign key (content_link_id) references file_upload (id);
create index ix_banner_content_link_4 on banner (content_link_id);
alter table banner_action add constraint fk_banner_action_impression_5 foreign key (impression_id_impression) references impression (id_impression);
create index ix_banner_action_impression_5 on banner_action (impression_id_impression);
alter table banner_placement add constraint fk_banner_placement_banner_6 foreign key (banner_id_banner) references banner (id_banner);
create index ix_banner_placement_banner_6 on banner_placement (banner_id_banner);
alter table banner_placement add constraint fk_banner_placement_zone_7 foreign key (zone_id_zone) references zone (id_zone);
create index ix_banner_placement_zone_7 on banner_placement (zone_id_zone);
alter table campaign add constraint fk_campaign_id_user_8 foreign key (id_user_id_user) references user_data (id_user);
create index ix_campaign_id_user_8 on campaign (id_user_id_user);
alter table deposito add constraint fk_deposito_user_9 foreign key (user_id_user) references user_data (id_user);
create index ix_deposito_user_9 on deposito (user_id_user);
alter table impression add constraint fk_impression_bannerPlacement_10 foreign key (banner_placement_id_banner_placement) references banner_placement (id_banner_placement);
create index ix_impression_bannerPlacement_10 on impression (banner_placement_id_banner_placement);
alter table notification add constraint fk_notification_user_11 foreign key (user_id_user) references user_data (id_user);
create index ix_notification_user_11 on notification (user_id_user);
alter table transfer_confirmation add constraint fk_transfer_confirmation_user_12 foreign key (user_id_user) references user_data (id_user);
create index ix_transfer_confirmation_user_12 on transfer_confirmation (user_id_user);
alter table transfer_confirmation add constraint fk_transfer_confirmation_user_13 foreign key (user_validator_id_user) references user_data (id_user);
create index ix_transfer_confirmation_user_13 on transfer_confirmation (user_validator_id_user);
alter table user_data add constraint fk_user_data_role_14 foreign key (role_id_role) references user_role (id_role);
create index ix_user_data_role_14 on user_data (role_id_role);
alter table user_data add constraint fk_user_data_profile_photo_15 foreign key (profile_photo_id) references file_upload (id);
create index ix_user_data_profile_photo_15 on user_data (profile_photo_id);
alter table user_contact add constraint fk_user_contact_user_16 foreign key (user_id_user) references user_data (id_user);
create index ix_user_contact_user_16 on user_contact (user_id_user);
alter table zone add constraint fk_zone_zone_channel_17 foreign key (zone_channel_id_zone_channel) references zone_channel (id_zone_channel);
create index ix_zone_zone_channel_17 on zone (zone_channel_id_zone_channel);
alter table zone add constraint fk_zone_ads_size_18 foreign key (ads_size_id_banner_size) references banner_size (id_banner_size);
create index ix_zone_ads_size_18 on zone (ads_size_id_banner_size);



alter table user_data_user_permission add constraint fk_user_data_user_permission__01 foreign key (user_data_id_user) references user_data (id_user);

alter table user_data_user_permission add constraint fk_user_data_user_permission__02 foreign key (user_permission_id_permission) references user_permission (id_permission);

# --- !Downs

drop table if exists ads_transaction cascade;

drop table if exists banner cascade;

drop table if exists banner_action cascade;

drop table if exists banner_placement cascade;

drop table if exists banner_size cascade;

drop table if exists banner_type cascade;

drop table if exists campaign cascade;

drop table if exists deposito cascade;

drop table if exists file_upload cascade;

drop table if exists impression cascade;

drop table if exists notification cascade;

drop table if exists system_preferences cascade;

drop table if exists transfer_confirmation cascade;

drop table if exists user_data cascade;

drop table if exists user_data_user_permission cascade;

drop table if exists user_contact cascade;

drop table if exists user_permission cascade;

drop table if exists user_role cascade;

drop table if exists zone cascade;

drop table if exists zone_channel cascade;

drop sequence if exists ads_transaction_seq;

drop sequence if exists banner_seq;

drop sequence if exists banner_action_seq;

drop sequence if exists banner_placement_seq;

drop sequence if exists banner_size_seq;

drop sequence if exists banner_type_seq;

drop sequence if exists campaign_seq;

drop sequence if exists deposito_seq;

drop sequence if exists file_upload_seq;

drop sequence if exists impression_seq;

drop sequence if exists notification_seq;

drop sequence if exists system_preferences_seq;

drop sequence if exists transfer_confirmation_seq;

drop sequence if exists user_data_seq;

drop sequence if exists user_contact_seq;

drop sequence if exists user_permission_seq;

drop sequence if exists user_role_seq;

drop sequence if exists zone_seq;

drop sequence if exists zone_channel_seq;

