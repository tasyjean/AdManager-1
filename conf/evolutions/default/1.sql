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

create table adsmanager.user (
  id_user                   integer not null,
  email                     varchar(255),
  password                  varchar(255),
  front_name                varchar(255),
  last_name                 varchar(255),
  company                   varchar(255),
  role                      varchar(1),
  join_date                 timestamp,
  current_balance           integer,
  is_active                 boolean,
  profile_photo             varchar(255),
  city                      varchar(255),
  country                   varchar(255),
  validation_key            varchar(255),
  constraint ck_user_role check (role in ('M','P','A')),
  constraint pk_user primary key (id_user))
;

create sequence adsmanager.ads_size_seq;

create sequence adsmanager.user_seq;




# --- !Downs

drop table if exists adsmanager.ads_size cascade;

drop table if exists adsmanager.user cascade;

drop sequence if exists adsmanager.ads_size_seq;

drop sequence if exists adsmanager.user_seq;

