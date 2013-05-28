# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table ads (
  id_ads                    integer not null,
  campaign_id_campaign      integer,
  ads_size_id_ads_size      integer,
  ads_type_id_ad_type       integer,
  name                      varchar(255),
  description               TEXT,
  title                     varchar(255),
  content_text              varchar(255),
  content_link              varchar(400),
  target                    varchar(400),
  alt_text                  varchar(400),
  weight                    integer,
  impression_count          integer,
  click_count               integer,
  like_count                integer,
  hide_count                integer,
  is_active                 boolean,
  is_deleted                boolean,
  is_ganteng                boolean,
  constraint pk_ads primary key (id_ads))
;

create table ads_action (
  id_ads_action             bigint not null,
  impression_id_impression  bigint,
  action_type               varchar(5),
  timestamp                 timestamp,
  constraint ck_ads_action_action_type check (action_type in ('hide','like','click')),
  constraint pk_ads_action primary key (id_ads_action))
;

create table ads_placement (
  id_ads_placement          integer not null,
  ads_id_ads                integer,
  zone_id_zone              integer,
  constraint pk_ads_placement primary key (id_ads_placement))
;

create table ads_size (
  id_ads_size               integer not null,
  name                      varchar(255),
  width                     integer,
  height                    integer,
  description               TEXT,
  constraint pk_ads_size primary key (id_ads_size))
;

create table ads_transaction (
  id_ads_transaction        integer not null,
  ads_id_ads                integer,
  transaction_type          varchar(4),
  amount                    integer,
  current_balance           integer,
  timestamp                 timestamp,
  is_deleted                boolean,
  constraint ck_ads_transaction_transaction_type check (transaction_type in ('cpm','cpa','flat')),
  constraint pk_ads_transaction primary key (id_ads_transaction))
;

create table ads_type (
  id_ad_type                integer not null,
  name                      varchar(255),
  description               TEXT,
  code                      TEXT,
  constraint pk_ads_type primary key (id_ad_type))
;

create table campaign (
  id_campaign               integer not null,
  id_user_id_user           integer,
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
  budget                    integer,
  constraint ck_campaign_campaign_type check (campaign_type in ('contract','exclusive')),
  constraint ck_campaign_pricing_model check (pricing_model in ('cpm','cpa','flat')),
  constraint pk_campaign primary key (id_campaign))
;

create table deposito (
  id_deposito               integer not null,
  user_id_user              integer,
  user_validator_id_user    integer,
  current_balance           integer,
  description               TEXT,
  timestamp_created         timestamp,
  timestamp_validated       timestamp,
  transfer_evidence         varchar(400),
  payment_method            varchar(255),
  is_validated              boolean,
  is_deleted                boolean,
  constraint pk_deposito primary key (id_deposito))
;

create table impression (
  id_impression             bigint not null,
  timestamp                 timestamp,
  ads_placement_id_ads_placement integer,
  viewer_ip                 varchar(255),
  viewer_source             varchar(255),
  constraint pk_impression primary key (id_impression))
;

create table notificaton (
  id_notification           integer not null,
  user_id_user              integer,
  notification_type         varchar(255),
  link                      varchar(400),
  is_read                   boolean,
  constraint pk_notificaton primary key (id_notification))
;

create table system_preferences (
  id_system_preferences     integer not null,
  key                       varchar(255),
  value                     varchar(255),
  constraint pk_system_preferences primary key (id_system_preferences))
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
  profile_photo             varchar(255),
  city                      varchar(255),
  country                   varchar(255),
  validation_key            varchar(255),
  constraint pk_user_data primary key (id_user))
;

create table user_contact (
  id_user_contact           integer not null,
  user_id_user              integer,
  contact_value             varchar(255),
  contact_type              varchar(17),
  contact_description       TEXT,
  constraint ck_user_contact_contact_type check (contact_type in ('personal_website','social_profile','other','address','private_phone','email','company_website','alternative_phone','home_phone')),
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
  zone_channel_id_zone_channel integer,
  ads_size_id_ads_size      integer,
  zone_type                 varchar(6),
  zone_default_view         varchar(12),
  zone_default_code         TEXT,
  is_deleted                boolean,
  constraint ck_zone_zone_type check (zone_type in ('text','object','image')),
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
create sequence ads_seq;

create sequence ads_action_seq;

create sequence ads_placement_seq;

create sequence ads_size_seq;

create sequence ads_transaction_seq;

create sequence ads_type_seq;

create sequence campaign_seq;

create sequence deposito_seq;

create sequence impression_seq;

create sequence notificaton_seq;

create sequence system_preferences_seq;

create sequence user_data_seq;

create sequence user_contact_seq;

create sequence user_permission_seq;

create sequence user_role_seq;

create sequence zone_seq;

create sequence zone_channel_seq;

alter table ads add constraint fk_ads_campaign_1 foreign key (campaign_id_campaign) references campaign (id_campaign);
create index ix_ads_campaign_1 on ads (campaign_id_campaign);
alter table ads add constraint fk_ads_adsSize_2 foreign key (ads_size_id_ads_size) references ads_size (id_ads_size);
create index ix_ads_adsSize_2 on ads (ads_size_id_ads_size);
alter table ads add constraint fk_ads_adsType_3 foreign key (ads_type_id_ad_type) references ads_type (id_ad_type);
create index ix_ads_adsType_3 on ads (ads_type_id_ad_type);
alter table ads_action add constraint fk_ads_action_impression_4 foreign key (impression_id_impression) references impression (id_impression);
create index ix_ads_action_impression_4 on ads_action (impression_id_impression);
alter table ads_placement add constraint fk_ads_placement_ads_5 foreign key (ads_id_ads) references ads (id_ads);
create index ix_ads_placement_ads_5 on ads_placement (ads_id_ads);
alter table ads_placement add constraint fk_ads_placement_zone_6 foreign key (zone_id_zone) references zone (id_zone);
create index ix_ads_placement_zone_6 on ads_placement (zone_id_zone);
alter table ads_transaction add constraint fk_ads_transaction_ads_7 foreign key (ads_id_ads) references ads (id_ads);
create index ix_ads_transaction_ads_7 on ads_transaction (ads_id_ads);
alter table campaign add constraint fk_campaign_id_user_8 foreign key (id_user_id_user) references user_data (id_user);
create index ix_campaign_id_user_8 on campaign (id_user_id_user);
alter table deposito add constraint fk_deposito_user_9 foreign key (user_id_user) references user_data (id_user);
create index ix_deposito_user_9 on deposito (user_id_user);
alter table deposito add constraint fk_deposito_user_validator_10 foreign key (user_validator_id_user) references user_data (id_user);
create index ix_deposito_user_validator_10 on deposito (user_validator_id_user);
alter table impression add constraint fk_impression_adsPlacement_11 foreign key (ads_placement_id_ads_placement) references ads_placement (id_ads_placement);
create index ix_impression_adsPlacement_11 on impression (ads_placement_id_ads_placement);
alter table notificaton add constraint fk_notificaton_user_12 foreign key (user_id_user) references user_data (id_user);
create index ix_notificaton_user_12 on notificaton (user_id_user);
alter table user_data add constraint fk_user_data_role_13 foreign key (role_id_role) references user_role (id_role);
create index ix_user_data_role_13 on user_data (role_id_role);
alter table user_contact add constraint fk_user_contact_user_14 foreign key (user_id_user) references user_data (id_user);
create index ix_user_contact_user_14 on user_contact (user_id_user);
alter table zone add constraint fk_zone_zone_channel_15 foreign key (zone_channel_id_zone_channel) references zone_channel (id_zone_channel);
create index ix_zone_zone_channel_15 on zone (zone_channel_id_zone_channel);
alter table zone add constraint fk_zone_ads_size_16 foreign key (ads_size_id_ads_size) references ads_size (id_ads_size);
create index ix_zone_ads_size_16 on zone (ads_size_id_ads_size);



alter table user_data_user_permission add constraint fk_user_data_user_permission__01 foreign key (user_data_id_user) references user_data (id_user);

alter table user_data_user_permission add constraint fk_user_data_user_permission__02 foreign key (user_permission_id_permission) references user_permission (id_permission);

# --- !Downs

drop table if exists ads cascade;

drop table if exists ads_action cascade;

drop table if exists ads_placement cascade;

drop table if exists ads_size cascade;

drop table if exists ads_transaction cascade;

drop table if exists ads_type cascade;

drop table if exists campaign cascade;

drop table if exists deposito cascade;

drop table if exists impression cascade;

drop table if exists notificaton cascade;

drop table if exists system_preferences cascade;

drop table if exists user_data cascade;

drop table if exists user_data_user_permission cascade;

drop table if exists user_contact cascade;

drop table if exists user_permission cascade;

drop table if exists user_role cascade;

drop table if exists zone cascade;

drop table if exists zone_channel cascade;

drop sequence if exists ads_seq;

drop sequence if exists ads_action_seq;

drop sequence if exists ads_placement_seq;

drop sequence if exists ads_size_seq;

drop sequence if exists ads_transaction_seq;

drop sequence if exists ads_type_seq;

drop sequence if exists campaign_seq;

drop sequence if exists deposito_seq;

drop sequence if exists impression_seq;

drop sequence if exists notificaton_seq;

drop sequence if exists system_preferences_seq;

drop sequence if exists user_data_seq;

drop sequence if exists user_contact_seq;

drop sequence if exists user_permission_seq;

drop sequence if exists user_role_seq;

drop sequence if exists zone_seq;

drop sequence if exists zone_channel_seq;

