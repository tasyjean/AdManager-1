# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table adsmanager.ads_size (
  id_ads_size               integer not null,
  name                      varchar(255),
  width                     integer,
  height                    integer,
  description               varchar(255),
  constraint pk_ads_size primary key (id_ads_size))
;

create table adsmanager.campaign (
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
  ganteng                   boolean,
  constraint ck_campaign_campaign_type check (campaign_type in ('contract','exclusive')),
  constraint ck_campaign_pricing_model check (pricing_model in ('cpm','cpa','flat')),
  constraint pk_campaign primary key (id_campaign))
;

create table adsmanager.user (
  id_user                   integer not null,
  email                     varchar(255),
  password                  varchar(255),
  front_name                varchar(255),
  last_name                 varchar(255),
  company                   varchar(255),
  role                      varchar(13),
  join_date                 timestamp,
  current_balance           integer,
  is_active                 boolean,
  profile_photo             varchar(255),
  city                      varchar(255),
  country                   varchar(255),
  validation_key            varchar(255),
  constraint ck_user_role check (role in ('management','advertiser','administrator')),
  constraint pk_user primary key (id_user))
;

create table adsmanager.user_contact (
  id_user_contact           bigint not null,
  id_user_id_user           integer,
  contact_value             varchar(255),
  contact_type              varchar(17),
  contact_description       TEXT,
  constraint ck_user_contact_contact_type check (contact_type in ('social_profile','other','address','private_phone','email','alternative_phone','home_phone')),
  constraint pk_user_contact primary key (id_user_contact))
;

create sequence adsmanager.ads_size_seq;

create sequence adsmanager.campaign_seq;

create sequence adsmanager.user_seq;

create sequence adsmanager.user_contact_seq;

alter table adsmanager.campaign add constraint fk_adsmanager.campaign_id_user_1 foreign key (id_user_id_user) references adsmanager.user (id_user);
create index ix_adsmanager.campaign_id_user_1 on adsmanager.campaign (id_user_id_user);
alter table adsmanager.user_contact add constraint fk_adsmanager.user_contact_id__2 foreign key (id_user_id_user) references adsmanager.user (id_user);
create index ix_adsmanager.user_contact_id__2 on adsmanager.user_contact (id_user_id_user);



# --- !Downs

drop table if exists adsmanager.ads_size cascade;

drop table if exists adsmanager.campaign cascade;

drop table if exists adsmanager.user cascade;

drop table if exists adsmanager.user_contact cascade;

drop sequence if exists adsmanager.ads_size_seq;

drop sequence if exists adsmanager.campaign_seq;

drop sequence if exists adsmanager.user_seq;

drop sequence if exists adsmanager.user_contact_seq;

