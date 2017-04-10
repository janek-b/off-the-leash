--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.3
-- Dumped by pg_dump version 9.5.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: checkins; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE checkins (
    id integer NOT NULL,
    userid integer,
    parkid integer,
    checkin timestamp without time zone,
    checkout timestamp without time zone
);


ALTER TABLE checkins OWNER TO "Guest";

--
-- Name: checkins_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE checkins_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE checkins_id_seq OWNER TO "Guest";

--
-- Name: checkins_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE checkins_id_seq OWNED BY checkins.id;


--
-- Name: dogs; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE dogs (
    id integer NOT NULL,
    userid integer,
    name character varying,
    gender character varying,
    altered boolean,
    breed character varying
);


ALTER TABLE dogs OWNER TO "Guest";

--
-- Name: dogs_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE dogs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dogs_id_seq OWNER TO "Guest";

--
-- Name: dogs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE dogs_id_seq OWNED BY dogs.id;


--
-- Name: parks; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE parks (
    id integer NOT NULL,
    name character varying,
    location character varying,
    size character varying,
    fenced boolean,
    small boolean,
    upvote integer,
    downvote integer
);


ALTER TABLE parks OWNER TO "Guest";

--
-- Name: parks_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE parks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE parks_id_seq OWNER TO "Guest";

--
-- Name: parks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE parks_id_seq OWNED BY parks.id;


--
-- Name: reviews; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE reviews (
    id integer NOT NULL,
    userid integer,
    parkid integer,
    title character varying,
    review character varying
);


ALTER TABLE reviews OWNER TO "Guest";

--
-- Name: reviews_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE reviews_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE reviews_id_seq OWNER TO "Guest";

--
-- Name: reviews_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE reviews_id_seq OWNED BY reviews.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE users (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE users OWNER TO "Guest";

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO "Guest";

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: votes; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE votes (
    id integer NOT NULL,
    userid integer,
    parkid integer,
    direction character varying
);


ALTER TABLE votes OWNER TO "Guest";

--
-- Name: votes_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE votes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE votes_id_seq OWNER TO "Guest";

--
-- Name: votes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE votes_id_seq OWNED BY votes.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY checkins ALTER COLUMN id SET DEFAULT nextval('checkins_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY dogs ALTER COLUMN id SET DEFAULT nextval('dogs_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY parks ALTER COLUMN id SET DEFAULT nextval('parks_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY reviews ALTER COLUMN id SET DEFAULT nextval('reviews_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY votes ALTER COLUMN id SET DEFAULT nextval('votes_id_seq'::regclass);


--
-- Data for Name: checkins; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY checkins (id, userid, parkid, checkin, checkout) FROM stdin;
\.


--
-- Name: checkins_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('checkins_id_seq', 1, false);


--
-- Data for Name: dogs; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY dogs (id, userid, name, gender, altered, breed) FROM stdin;
\.


--
-- Name: dogs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('dogs_id_seq', 1, false);


--
-- Data for Name: parks; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY parks (id, name, location, size, fenced, small, upvote, downvote) FROM stdin;
\.


--
-- Name: parks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('parks_id_seq', 1, false);


--
-- Data for Name: reviews; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY reviews (id, userid, parkid, title, review) FROM stdin;
\.


--
-- Name: reviews_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('reviews_id_seq', 1, false);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY users (id, name) FROM stdin;
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('users_id_seq', 1, false);


--
-- Data for Name: votes; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY votes (id, userid, parkid, direction) FROM stdin;
\.


--
-- Name: votes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('votes_id_seq', 1, false);


--
-- Name: checkins_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY checkins
    ADD CONSTRAINT checkins_pkey PRIMARY KEY (id);


--
-- Name: dogs_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY dogs
    ADD CONSTRAINT dogs_pkey PRIMARY KEY (id);


--
-- Name: parks_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY parks
    ADD CONSTRAINT parks_pkey PRIMARY KEY (id);


--
-- Name: reviews_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY reviews
    ADD CONSTRAINT reviews_pkey PRIMARY KEY (id);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: votes_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY votes
    ADD CONSTRAINT votes_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

