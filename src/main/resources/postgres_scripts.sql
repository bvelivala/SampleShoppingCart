-- Table: public.accounts

DROP TABLE if exists public.accounts;

DROP TABLE if exists public.order_details, public.orders, public.products CASCADE;

CREATE TABLE public.accounts
(
  user_name character varying(20) NOT NULL,
  active bit(1) NOT NULL,
  password character varying(20) NOT NULL,
  user_role character varying(20) NOT NULL,
  CONSTRAINT accounts_pkey PRIMARY KEY (user_name)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.accounts
  OWNER TO postgres;

-- Table: public.products

-- DROP TABLE public.products;

CREATE TABLE public.products
(
  code character varying(20) NOT NULL,
  create_date timestamp without time zone NOT NULL,
  image bytea,
  name character varying(255) NOT NULL,
  price double precision NOT NULL,
  CONSTRAINT products_pkey PRIMARY KEY (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.products
  OWNER TO postgres;

-- Table: public.orders

-- DROP TABLE public.orders;

CREATE TABLE public.orders
(
  id character varying(50) NOT NULL,
  amount double precision NOT NULL,
  customer_address character varying(255) NOT NULL,
  customer_email character varying(128) NOT NULL,
  customer_name character varying(255) NOT NULL,
  customer_phone character varying(128) NOT NULL,
  order_date timestamp without time zone NOT NULL,
  order_num integer NOT NULL,
  CONSTRAINT orders_pkey PRIMARY KEY (id),
  CONSTRAINT orders_order_num_key UNIQUE (order_num)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.orders
  OWNER TO postgres;  

-- Table: public.order_details

-- DROP TABLE public.order_details;

CREATE TABLE public.order_details
(
  id character varying(50) NOT NULL,
  amount double precision NOT NULL,
  price double precision NOT NULL,
  quantity integer NOT NULL,
  order_id character varying(50) NOT NULL,
  product_id character varying(20) NOT NULL,
  CONSTRAINT order_details_pkey PRIMARY KEY (id),
  CONSTRAINT order_details_order_id_fkey FOREIGN KEY (order_id)
      REFERENCES public.orders (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT order_details_product_id_fkey FOREIGN KEY (product_id)
      REFERENCES public.products (code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.order_details
  OWNER TO postgres;

INSERT INTO public.accounts(
            user_name, active, password, user_role)
    VALUES ('bhavani', '1', '123', 'employee');

INSERT INTO public.accounts(
            user_name, active, password, user_role)
    VALUES ('anand', '1', '123', 'employee');
	
insert into public.products (CODE, NAME, PRICE, CREATE_DATE)
values ('S001', 'Core Java', 100, CURRENT_TIMESTAMP );
 
insert into public.products (CODE, NAME, PRICE, CREATE_DATE)
values ('S002', 'Spring for Beginners', 50, CURRENT_TIMESTAMP );
 
insert into public.products (CODE, NAME, PRICE, CREATE_DATE)
values ('S003', 'Swift for Beginners', 120, CURRENT_TIMESTAMP );
 
insert into public.products (CODE, NAME, PRICE, CREATE_DATE)
values ('S004', 'Oracle XML Parser', 120, CURRENT_TIMESTAMP );
 
insert into public.products (CODE, NAME, PRICE, CREATE_DATE)
values ('S005', 'CSharp Tutorial for Beginers', 110, CURRENT_TIMESTAMP );