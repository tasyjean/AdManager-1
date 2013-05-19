
CREATE SCHEMA  adsmanager;

-- -----------------------------------------------------
-- Table adsmanager.user
-- -----------------------------------------------------
CREATE SEQUENCE adsmanager.id_user_seq;
CREATE TYPE adsmanager.user_role_enum AS ENUM('administrator','management','advertiser');

CREATE  TABLE IF NOT EXISTS adsmanager.user (
  id_user INT PRIMARY KEY DEFAULT NEXTVAL('adsmanager.id_user_seq') ,
  email VARCHAR(45) NULL ,
  password VARCHAR(80) NULL ,
  front_name VARCHAR(45) NULL ,
  last_name VARCHAR(45) NULL ,
  company VARCHAR(45) NULL ,
  role adsmanager.user_role_enum NULL ,
  join_date DATE NULL,
  current_balance INT NULL ,
  isActive BOOLEAN DEFAULT TRUE,
  profile_photo VARCHAR(200) NULL ,
  city VARCHAR(45) NULL ,
  country VARCHAR(45) NULL );

-- -----------------------------------------------------
-- Table adsmanager.user_contact
-- -----------------------------------------------------

CREATE SEQUENCE adsmanager.id_user_contact_seq;
CREATE TYPE adsmanager.contact_type_enum AS ENUM('address''email','private_phone','company_phone','alternative_phone','social_profile','other');

CREATE  TABLE IF NOT EXISTS adsmanager.user_contact (
  id_user_contact INT PRIMARY KEY DEFAULT NEXTVAL('adsmanager.id_user_contact_seq')  ,
  id_user INT DEFAULT 1 REFERENCES adsmanager.user ON DELETE CASCADE ON UPDATE CASCADE ,
  contact_value VARCHAR(100) NULL ,
  contact_type adsmanager.contact_type_enum NULL ,
  contact_description TEXT
  );

-- -----------------------------------------------------
-- Table adsmanager.campaign
-- -----------------------------------------------------
CREATE SEQUENCE adsmanager.id_campaign_seq;
CREATE TYPE adsmanager.campaign_type_enum AS ENUM('contract','exclusive');
CREATE TYPE adsmanager.pricing_model_enum AS ENUM('cpm','cpa','flat');


CREATE  TABLE IF NOT EXISTS adsmanager.campaign (
  id_campaign INT PRIMARY KEY DEFAULT NEXTVAL('adsmanager.id_campaign_seq') ,
  id_user INT DEFAULT 1 REFERENCES adsmanager.user ON DELETE SET DEFAULT ,
  campaign_type adsmanager.campaign_type_enum ,
  start_date DATE NULL ,
  end_date DATE NULL ,
  pricing_model adsmanager.pricing_model_enum ,
  limit_impression INT NULL ,
  limit_click INT NULL ,
  current_impression INT NULL ,
  current_click INT NULL ,
  bid_price INT NULL ,
  isActivated BOOLEAN DEFAULT TRUE,
  isDeleted BOOLEAN DEFAULT FALSE);
  
-- -----------------------------------------------------
-- Table adsmanager.ads_size
-- -----------------------------------------------------
CREATE SEQUENCE adsmanager.ads_size_seq;

CREATE  TABLE IF NOT EXISTS adsmanager.ads_size (
  id_ads_size INT PRIMARY KEY DEFAULT NEXTVAL('adsmanager.ads_size_seq'),
  name VARCHAR(45) NULL ,
  width INT NULL ,
  height INT NULL ,
  description TEXT NULL 
  );
-- -----------------------------------------------------
-- Table adsmanager.ad_type
-- -----------------------------------------------------
CREATE SEQUENCE adsmanager.ads_type_seq;

CREATE  TABLE IF NOT EXISTS adsmanager.ads_type (
  id_ads_type INT PRIMARY KEY DEFAULT NEXTVAL('adsmanager.ads_type_seq') ,
  name VARCHAR(45) NULL ,
  description TEXT NULL ,
  code TEXT NULL 
  );


-- -----------------------------------------------------
-- Table adsmanager.ads
-- -----------------------------------------------------
CREATE SEQUENCE adsmanager.ads_seq;

CREATE  TABLE IF NOT EXISTS adsmanager.ads (
  id_ads INT PRIMARY KEY DEFAULT NEXTVAL('adsmanager.ads_seq') ,
  id_campaign INT DEFAULT 1 REFERENCES adsmanager.campaign ON DELETE CASCADE,
  id_ads_size INT DEFAULT 1 REFERENCES adsmanager.ads_size,
  id_ads_type INT DEFAULT 1 REFERENCES adsmanager.ads_type,
  name VARCHAR(45) NULL ,
  description TEXT NULL ,
  title VARCHAR(45) NULL ,
  content_text VARCHAR(45) NULL ,
  content_link VARCHAR(200) NULL ,
  target VARCHAR(200) NULL ,
  alt_text VARCHAR(250) NULL ,
  weight INT NULL ,
  impression_count INT NULL ,
  click_count INT NULL ,
  like_count INT NULL ,
  hide_count INT NULL ,
  isActive BOOLEAN DEFAULT TRUE NULL ,
  isDeleted BOOLEAN DEFAULT FALSE NULL
  );


-- -----------------------------------------------------
-- Table adsmanager.ads_transaction
-- -----------------------------------------------------
CREATE SEQUENCE adsmanager.id_ads_transaction_seq;

CREATE  TABLE IF NOT EXISTS adsmanager.ads_transaction (
  id_ads_transaction INT PRIMARY KEY DEFAULT NEXTVAL('adsmanager.id_ads_transaction_seq') ,
  id_user INT DEFAULT 1 REFERENCES adsmanager.user ON DELETE CASCADE,
  id_ads INT DEFAULT 1 REFERENCES adsmanager.ads ON DELETE CASCADE ,
  transaction_type adsmanager.pricing_model_enum  ,
  amount INT NULL ,
  current_balance INT NULL ,
  timestamp TIMESTAMP NULL ,
  isDeleted BOOLEAN DEFAULT FALSE 
 );


-- -----------------------------------------------------
-- Table adsmanager.deposito
-- -----------------------------------------------------
CREATE SEQUENCE adsmanager.id_deposito_seq;

CREATE  TABLE IF NOT EXISTS adsmanager.deposito (
  id_deposito INT PRIMARY KEY DEFAULT NEXTVAL('adsmanager.id_deposito_seq'),
  id_user INT DEFAULT 1 REFERENCES adsmanager.user on DELETE CASCADE ,
  id_user_validator INT DEFAULT 1 REFERENCES adsmanager.user on DELETE SET DEFAULT ,
  amount INT NULL ,
  current_balance INT NULL ,
  description INT NULL ,
  timestamp_created TIMESTAMP NULL ,
  timestamp_validated TIMESTAMP NULL ,
  transfer_evidence_file VARCHAR(200) NULL ,
  payment_method VARCHAR(45) NULL ,
  isValidated BOOLEAN DEFAULT FALSE ,
  isDeleted BOOLEAN DEFAULT FALSE
 );


-- -----------------------------------------------------
-- Table adsmanager.zone_channel
-- -----------------------------------------------------
CREATE SEQUENCE adsmanager.id_zone_channel_seq;

CREATE  TABLE IF NOT EXISTS adsmanager.zone_channel (
  id_zone_channel INT PRIMARY KEY DEFAULT NEXTVAL('adsmanager.id_zone_channel_seq') ,
  channel_name VARCHAR(45) NULL ,
  channel_description TEXT NULL ,
  isDeleted BOOLEAN DEFAULT FALSE
  );


-- -----------------------------------------------------
-- Table adsmanager.zone
-- -----------------------------------------------------

CREATE SEQUENCE adsmanager.id_zone_seq;
CREATE TYPE adsmanager.zone_default_view_enum AS ENUM('empty','default_code','default_ads');

CREATE  TABLE IF NOT EXISTS adsmanager.zone (
  id_zone INT PRIMARY KEY DEFAULT NEXTVAL('adsmanager.id_zone_seq'),
  id_zone_channel INT DEFAULT 1 REFERENCES adsmanager.zone_channel on DELETE SET DEFAULT,
  id_ads_size  INT DEFAULT 1 REFERENCES adsmanager.ads_size on DELETE SET DEFAULT,
  zone_type VARCHAR(40) NULL ,
  description TEXT NULL ,
  zone_default_view adsmanager.zone_default_view_enum ,
  zone_default_code TEXT NULL ,
  isDeleted BOOLEAN DEFAULT FALSE );


-- -----------------------------------------------------
-- Table adsmanager.ads_placement
-- -----------------------------------------------------
CREATE SEQUENCE adsmanager.ads_placement_seq;

CREATE  TABLE IF NOT EXISTS adsmanager.ads_placement (
  id_ads_placement INT PRIMARY KEY DEFAULT NEXTVAL('adsmanager.ads_placement') ,
  id_ads INT DEFAULT 1 REFERENCES adsmanager.ads on DELETE CASCADE,
  id_zone INT DEFAULT 1 REFERENCES adsmanager.zone on DELETE CASCADE 
  );

-- -----------------------------------------------------
-- Table adsmanager.impression
-- -----------------------------------------------------
CREATE SEQUENCE adsmanager.id_impression_seq;

CREATE  TABLE IF NOT EXISTS adsmanager.impression (
  id_impression INT PRIMARY KEY DEFAULT NEXTVAL('adsmanager.id_impression_seq') ,
  id_ads INT DEFAULT 1 REFERENCES adsmanager.ads on DELETE CASCADE,
  id_zone INT DEFAULT 1 REFERENCES adsmanager.zone on DELETE CASCADE,
  timestamp TIMESTAMP NULL ,
  viewer_ip VARCHAR(45) NULL ,
  viewer_source VARCHAR(400) NULL 
  );


-- -----------------------------------------------------
-- Table adsmanager.ads_action
-- -----------------------------------------------------
CREATE SEQUENCE adsmanager.id_ads_action_seq;
CREATE TYPE adsmanager.action_type_enum AS ENUM('click','hide','like');

CREATE  TABLE IF NOT EXISTS adsmanager.ads_action (
  id_ads_action INT PRIMARY KEY DEFAULT NEXTVAL('adsmanager.id_ads_action_seq') ,
  id_impression INT DEFAULT 1 REFERENCES adsmanager.impression ON DELETE CASCADE ,
  action_type adsmanager.action_type_enum ,
  timestamp TIMESTAMP NULL 
  );


-- -----------------------------------------------------
-- Table adsmanager.system_preferences
-- -----------------------------------------------------
CREATE SEQUENCE adsmanager.system_preferences_seq;

CREATE  TABLE IF NOT EXISTS adsmanager.system_preferences (
  id_system_preferences INT PRIMARY KEY DEFAULT NEXTVAL('adsmanager.system_preferences_seq') ,
  system_preference_key VARCHAR(45) NULL ,
  value TEXT NULL
  );

-- -----------------------------------------------------
-- Table adsmanager.notification
-- -----------------------------------------------------
CREATE SEQUENCE adsmanager.notification_seq;

CREATE  TABLE IF NOT EXISTS adsmanager.notification (
  id_notification INT PRIMARY KEY DEFAULT NEXTVAL('adsmanager.notification_seq') ,
  id_user INT REFERENCES adsmanager.user ON DELETE CASCADE ,
  notification_type INT NULL ,
  timestamp TIMESTAMP NULL ,
  link VARCHAR(200) NULL ,
  isRead BOOLEAN DEFAULT FALSE );

