--
CREATE SCHEMA SHOPPING DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_uniPRODUCT_ID_ci;

-- Create table
create table USERS
(
    USER_NAME VARCHAR(20) not null,
    ACTIVE    BIT not null,
    ENCRYTED_PASSWORD  VARCHAR(128) not null,
    USER_ROLE VARCHAR(20) not null,
    FIRST_NAME VARCHAR(100),
    LAST_NAME VARCHAR(50),
    MOBILE VARCHAR(15),
    EMAIL VARCHAR(50),
    LAST_LOGIN datetime
) ;

alter table USERS
    add primary key (USER_NAME) ;
---------------------------------------

create table CATAGORY
(
    CAT_ID      VARCHAR(20) not null,
    IMAGE       longblob,
    NAME        VARCHAR(255) not null,
    CREATE_DATE datetime not null,
    PRIORITY    BIGINT
) ;

alter table CATAGORY
    add primary key (CAT_ID) ;
---------------------------------------

create table COLOUR
(
    COLOUR_ID   BIGINT NOT NULL AUTO_INCREMENT,
    NAME        VARCHAR(255) not null,
    CREATE_DATE datetime not null,
    PRIORITY    BIGINT
) ;

alter table COLOUR
    add primary key (COLOUR_ID) ;
---------------------------------------

create table BRAND
(
    BRAND_ID    BIGINT NOT NULL AUTO_INCREMENT,
    NAME        VARCHAR(255) not null,
    DESCRIPTION VARCHAR(4000),
    CREATE_DATE datetime not null,
    PRIORITY    BIGINT
) ;

alter table BRAND
    add primary key (BRAND_ID) ;
---------------------------------------

create table PRODUCTS
(
    PRODUCT_ID  BIGINT NOT NULL AUTO_INCREMENT,
    DESCRIPTION VARCHAR(4000),
    NAME        VARCHAR(255) not null,
    IMAGE       longblob, -- should URL
    PRICE       double precision not null,
    QUANITY	  SMALLINT(6) NOT NULL DEFAULT 0, --The available quantity of the product.
    PRIORITY    BIGINT,
    CREATE_DATE datetime not null
) ;

alter table PRODUCTS
    add primary key (PRODUCT_ID) ;
---------------------------------------


-- Create table
create table ORDERS
(
    ID               BIGINT NOT NULL AUTO_INCREMENT,
    AMOUNT           double precision not null,
    CUSTOMER_ADDRESS VARCHAR(255) not null,
    CUSTOMER_EMAIL   VARCHAR(128) not null,
    CUSTOMER_NAME    VARCHAR(255) not null,
    CUSTOMER_PHONE   VARCHAR(128) not null,
    ORDER_DATE       datetime not null,
    ORDER_NUM        INTEGER not null
) ;
alter table ORDERS
    add primary key (ID) ;
alter table ORDERS
    add constraint ORDER_UK unique (ORDER_NUM) ;
---------------------------------------

-- Create table
create table ORDER_DETAILS
(
    ID         BIGINT NOT NULL AUTO_INCREMENT,
    AMOUNT     double precision not null,
    PRICE      double precision not null,
    QUANITY    INTEGER not null,
    ORDER_ID   BIGINT NOT NULL,
    PRODUCT_ID BIGINT NOT NULL
) ;
--
alter table ORDER_DETAILS
    add primary key (ID) ;
alter table ORDER_DETAILS
    add constraint ORDER_DETAIL_ORD_FK foreign key (ORDER_ID)
        references ORDERS (ID);
alter table ORDER_DETAILS
    add constraint ORDER_DETAIL_PROD_FK foreign key (PRODUCT_ID)
        references PRODUCTS (PRODUCT_ID);

---------------------------------------
-- Create table
create table PRODUCTS_CAT
(
    ID          BIGINT NOT NULL AUTO_INCREMENT,
    CAT_ID      VARCHAR(20) NOT NULL,
    PRODUCT_ID  BIGINT NOT NULL
) ;
--
alter table PRODUCTS_CAT
    add primary key (ID) ;
alter table PRODUCTS_CAT
    add constraint PRODUCTS_CAT_CAT_FK foreign key (CAT_ID)
        references COLOUR (COLOUR_ID);
alter table PRODUCTS_CAT
    add constraint PRODUCTS_CAT_PROD_FK foreign key (PRODUCT_ID)
        references PRODUCTS (PRODUCT_ID);

---------------------------------------

-- Create table
create table PRODUCTS_COLOUR
(
    ID          BIGINT NOT NULL AUTO_INCREMENT,
    COLOUR_ID   BIGINT NOT NULL,
    PRODUCT_ID  BIGINT NOT NULL
) ;
--
alter table PRODUCTS_COLOUR
    add primary key (ID) ;
alter table PRODUCTS_COLOUR
    add constraint PRODUCTS_COLOUR_COLO_FK foreign key (COLOUR_ID)
        references COLOUR (COLOUR_ID);
alter table PRODUCTS_COLOUR
    add constraint PRODUCTS_COLOUR_PROD_FK foreign key (PRODUCT_ID)
        references PRODUCTS (PRODUCT_ID);

---------------------------------------

-- Create table
create table CATAGORY_BRAND
(
    ID          BIGINT NOT NULL AUTO_INCREMENT,
    BRAND_ID    BIGINT NOT NULL,
    CAT_ID      VARCHAR(20) NOT NULL
) ;
--
alter table PRODUCTS_BRAND
    add primary key (ID) ;
alter table PRODUCTS_BRAND
    add constraint PRODUCTS_BRAND_BRAN_FK foreign key (BRAND_ID)
        references BRAND (BRAND_ID);
alter table PRODUCTS_BRAND
    add constraint PRODUCTS_BRAND_PROD_FK foreign key (CAT_ID)
        references CATAGORY (CAT_ID);

---------------------------------------
insert into USERS (USER_NAME, ACTIVE, ENCRYTED_PASSWORD, USER_ROLE)
values ('employee1', 1,
        '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'ROLE_EMPLOYEE');

insert into USERS (USER_NAME, ACTIVE, ENCRYTED_PASSWORD, USER_ROLE)
values ('manager1', 1,
        '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'ROLE_MANAGER');

----------------
insert into CATAGORY (CAT_ID, NAME, CREATE_DATE)
values ('BOOK01', 'Book', sysdate);

insert into CATAGORY (CAT_ID, NAME, CREATE_DATE)
values ('CAR01', 'Car', sysdate);
----------------

insert into COLOUR (COLOUR_ID, NAME, CREATE_DATE)
values (1, 'Red', sysdate);

insert into COLOUR (COLOUR_ID, NAME, CREATE_DATE)
values (2, 'Blue', sysdate);

insert into COLOUR (COLOUR_ID, NAME, CREATE_DATE)
values (3, 'Black', sysdate);
----------------
insert into BRAND (BRAND_ID, NAME, CREATE_DATE)
values (1, 'Thai Ha Book', sysdate);

insert into BRAND (BRAND_ID, NAME, CREATE_DATE)
values (2, 'Audi', sysdate);

insert into BRAND (BRAND_ID, NAME, CREATE_DATE)
values (3, 'Phuong Nam Book', sysdate);

insert into BRAND (BRAND_ID, NAME, CREATE_DATE)
values (4, 'Suzuki', sysdate);

------------------

insert into products (PRODUCT_ID, NAME, PRICE, QUANITY, CREATE_DATE)
values (1, 'Book1', 100, 6, sysdate);

insert into products (PRODUCT_ID, NAME, PRICE, QUANITY, CREATE_DATE)
values (2, 'Spring for Beginners', 50, 4, sysdate);

insert into products (PRODUCT_ID, NAME, PRICE, QUANITY, CREATE_DATE)
values (3, 'EQ for you', 120, 10, sysdate);

insert into products (PRODUCT_ID, NAME, PRICE, QUANITY, CREATE_DATE)
values (4, 'Audi A8', 200000, 3, sysdate);

insert into products (PRODUCT_ID, NAME, PRICE, QUANITY, CREATE_DATE)
values (5, 'Suzuki XL7', 20000, 6, sysdate);

insert into products (PRODUCT_ID, NAME, PRICE, QUANITY, CREATE_DATE)
values (6, 'Audi A7', 150000, 5, sysdate);

insert into products (PRODUCT_ID, NAME, PRICE, QUANITY, CREATE_DATE)
values (7, 'Suzuki Vitara', 30000, 4, sysdate);

---------------------
insert into CATAGORY_BRAND (ID, BRAND_ID, CAT_ID)
values (1, 1, 'BOOK01');
insert into CATAGORY_BRAND (ID, BRAND_ID, CAT_ID)
values (2, 3, 'BOOK01');

insert into CATAGORY_BRAND (ID, BRAND_ID, CAT_ID)
values (3, 2, 'CAR01');
insert into CATAGORY_BRAND (ID, BRAND_ID, CAT_ID)
values (4, 4, 'CAR01');
--------------------
insert into PRODUCTS_CAT (ID, CAT_ID, PRODUCT_ID)
values (1, 'BOOK01', 1);
insert into PRODUCTS_CAT (ID, CAT_ID, PRODUCT_ID)
values (2, 'BOOK01', 2);
insert into PRODUCTS_CAT (ID, CAT_ID, PRODUCT_ID)
values (3, 'BOOK01', 3);

insert into PRODUCTS_CAT (ID, CAT_ID, PRODUCT_ID)
values (4, 'CAR01', 4);
insert into PRODUCTS_CAT (ID, CAT_ID, PRODUCT_ID)
values (5, 'CAR01', 5);
insert into PRODUCTS_CAT (ID, CAT_ID, PRODUCT_ID)
values (6, 'CAR01', 6);
insert into PRODUCTS_CAT (ID, CAT_ID, PRODUCT_ID)
values (7, 'CAR01', 7);

-----------------------
insert into PRODUCTS_COLOUR (ID, PRODUCT_ID, COLOUR_ID)
values (1, 4, 1);
insert into PRODUCTS_COLOUR (ID, PRODUCT_ID, COLOUR_ID)
values (2, 4, 2);
insert into PRODUCTS_COLOUR (ID, PRODUCT_ID, COLOUR_ID)
values (3, 5, 1);
insert into PRODUCTS_COLOUR (ID, PRODUCT_ID, COLOUR_ID)
values (4, 5, 2);
insert into PRODUCTS_COLOUR (ID, PRODUCT_ID, COLOUR_ID)
values (5, 5, 3);
insert into PRODUCTS_COLOUR (ID, PRODUCT_ID, COLOUR_ID)
values (6, 6, 2);
insert into PRODUCTS_COLOUR (ID, PRODUCT_ID, COLOUR_ID)
values (6, 7, 3);
