--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.2
-- Dumped by pg_dump version 9.6.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
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
-- Name: checkins; Type: TABLE; Schema: public; Owner: Grace
--

CREATE TABLE checkins (
    id integer NOT NULL,
    userid integer,
    parkid integer,
    checkin timestamp without time zone,
    checkout timestamp without time zone
);


ALTER TABLE checkins OWNER TO "Grace";

--
-- Name: checkins_id_seq; Type: SEQUENCE; Schema: public; Owner: Grace
--

CREATE SEQUENCE checkins_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE checkins_id_seq OWNER TO "Grace";

--
-- Name: checkins_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Grace
--

ALTER SEQUENCE checkins_id_seq OWNED BY checkins.id;


--
-- Name: dogs; Type: TABLE; Schema: public; Owner: Grace
--

CREATE TABLE dogs (
    id integer NOT NULL,
    userid integer,
    name character varying,
    gender character varying,
    altered boolean,
    breed character varying
);


ALTER TABLE dogs OWNER TO "Grace";

--
-- Name: dogs_id_seq; Type: SEQUENCE; Schema: public; Owner: Grace
--

CREATE SEQUENCE dogs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dogs_id_seq OWNER TO "Grace";

--
-- Name: dogs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Grace
--

ALTER SEQUENCE dogs_id_seq OWNED BY dogs.id;


--
-- Name: parks; Type: TABLE; Schema: public; Owner: Grace
--

CREATE TABLE parks (
    id integer NOT NULL,
    name character varying,
    location character varying,
    size character varying,
    fenced boolean,
    small boolean,
    upvote integer,
    downvote integer,
    lat double precision,
    lng double precision
);


ALTER TABLE parks OWNER TO "Grace";

--
-- Name: parks_id_seq; Type: SEQUENCE; Schema: public; Owner: Grace
--

CREATE SEQUENCE parks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE parks_id_seq OWNER TO "Grace";

--
-- Name: parks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Grace
--

ALTER SEQUENCE parks_id_seq OWNED BY parks.id;


--
-- Name: reviews; Type: TABLE; Schema: public; Owner: Grace
--

CREATE TABLE reviews (
    id integer NOT NULL,
    userid integer,
    parkid integer,
    title character varying,
    content character varying
);


ALTER TABLE reviews OWNER TO "Grace";

--
-- Name: reviews_id_seq; Type: SEQUENCE; Schema: public; Owner: Grace
--

CREATE SEQUENCE reviews_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE reviews_id_seq OWNER TO "Grace";

--
-- Name: reviews_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Grace
--

ALTER SEQUENCE reviews_id_seq OWNED BY reviews.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: Grace
--

CREATE TABLE users (
    id integer NOT NULL,
    name character varying,
    username character varying,
    password character varying,
    admin boolean
);


ALTER TABLE users OWNER TO "Grace";

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: Grace
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO "Grace";

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Grace
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: votes; Type: TABLE; Schema: public; Owner: Grace
--

CREATE TABLE votes (
    id integer NOT NULL,
    userid integer,
    parkid integer,
    direction character varying
);


ALTER TABLE votes OWNER TO "Grace";

--
-- Name: votes_id_seq; Type: SEQUENCE; Schema: public; Owner: Grace
--

CREATE SEQUENCE votes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE votes_id_seq OWNER TO "Grace";

--
-- Name: votes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Grace
--

ALTER SEQUENCE votes_id_seq OWNED BY votes.id;


--
-- Name: checkins id; Type: DEFAULT; Schema: public; Owner: Grace
--

ALTER TABLE ONLY checkins ALTER COLUMN id SET DEFAULT nextval('checkins_id_seq'::regclass);


--
-- Name: dogs id; Type: DEFAULT; Schema: public; Owner: Grace
--

ALTER TABLE ONLY dogs ALTER COLUMN id SET DEFAULT nextval('dogs_id_seq'::regclass);


--
-- Name: parks id; Type: DEFAULT; Schema: public; Owner: Grace
--

ALTER TABLE ONLY parks ALTER COLUMN id SET DEFAULT nextval('parks_id_seq'::regclass);


--
-- Name: reviews id; Type: DEFAULT; Schema: public; Owner: Grace
--

ALTER TABLE ONLY reviews ALTER COLUMN id SET DEFAULT nextval('reviews_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: Grace
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Name: votes id; Type: DEFAULT; Schema: public; Owner: Grace
--

ALTER TABLE ONLY votes ALTER COLUMN id SET DEFAULT nextval('votes_id_seq'::regclass);


--
-- Data for Name: checkins; Type: TABLE DATA; Schema: public; Owner: Grace
--

COPY checkins (id, userid, parkid, checkin, checkout) FROM stdin;
3	9	3	2017-04-13 09:58:59.806376	2017-04-13 09:59:07.440834
4	9	4	2017-04-13 09:59:19.649735	2017-04-13 09:59:47.908807
5	9	4	2017-04-13 09:59:33.535217	2017-04-13 09:59:47.908807
6	17	3	2017-04-13 10:45:33.807731	2017-04-13 10:45:41.119245
7	17	3	2017-04-13 10:45:40.046346	2017-04-13 10:45:41.119245
8	17	3	2017-04-13 10:45:42.550758	\N
9	10	4	2017-04-13 12:22:36.611656	\N
10	11	4	2017-04-13 12:22:46.70817	\N
11	12	4	2017-04-13 12:23:45.566718	\N
12	13	4	2017-04-13 12:23:51.550909	\N
13	14	5	2017-04-13 12:23:58.70291	\N
14	15	5	2017-04-13 12:24:03.047344	\N
15	16	3	2017-04-13 12:24:16.671981	\N
\.


--
-- Name: checkins_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Grace
--

SELECT pg_catalog.setval('checkins_id_seq', 15, true);


--
-- Data for Name: dogs; Type: TABLE DATA; Schema: public; Owner: Grace
--

COPY dogs (id, userid, name, gender, altered, breed) FROM stdin;
3	9	Butch	male	t	Pitbull
4	9	Daisy	female	t	French Bulldog
5	9	Sasha	female	t	Mutt
6	10	Hopper	female	t	Whippet
7	10	Harriet	female	f	Boxer
8	11	Henry	male	f	Mutt
9	12	Bean	male	f	Mutt
10	13	Toad	male	f	Mutt
11	14	Dakota	male	f	Mutt
12	15	Texas	male	f	Mutt
13	16	California	female	f	Mutt
\.


--
-- Name: dogs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Grace
--

SELECT pg_catalog.setval('dogs_id_seq', 13, true);


--
-- Data for Name: parks; Type: TABLE DATA; Schema: public; Owner: Grace
--

COPY parks (id, name, location, size, fenced, small, upvote, downvote, lat, lng) FROM stdin;
4	Mt Tabor Dog Park	6336 SE Lincoln St, Portland, OR 97215	large	f	f	11	3	45.5078928000000005	-122.597968699999996
5	Gabriel Park	SW Vermont St, Portland, OR	large	f	f	17	4	45.4761518999999979	-122.714955200000006
3	Fernhill Park Dog Off-leash Area	4050 NE Holman St, Portland, OR 97211	large	t	f	33	11	45.5685919000000013	-122.622199300000005
10	Alberta Park	5751 NE 22nd Ave, Portland, OR, US, 97211	medium	f	f	3	39	45.5644338000000033	-122.643489000000002
9	North Clackamas Dog Park	5440 SE Kellogg Creek Dr, Portland, OR, US	medium	t	t	11	33	45.4266221999999971	-122.606066900000002
8	Willamette Park Off Leash Dog Area	SW Macadam Ave & Nebraska St, Portland, OR, US, 97212	large	f	f	6	19	45.4772439999999989	-122.672154699999993
7	Brentwood Park	 SE 60th Ave & Duke St	small	t	f	1	9	45.4758258000000026	-122.602232299999997
6	Wallace Park	Wallace City Park (at Raleigh St), Portland, OR	large	f	f	15	41	45.5333076999999875	-122.703689499999996
12	Wilshire Park	4049 NE 35th Ave, Portland, OR, US, 97212	medium	t	t	22	12	45.5522064999999969	-122.6286518
11	Creston Park	4454 SE Powell Blvd, Portland, OR, US	large	f	f	7	2	45.4965632999999983	-122.616583300000002
\.


--
-- Name: parks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Grace
--

SELECT pg_catalog.setval('parks_id_seq', 12, true);


--
-- Data for Name: reviews; Type: TABLE DATA; Schema: public; Owner: Grace
--

COPY reviews (id, userid, parkid, title, content) FROM stdin;
\.


--
-- Name: reviews_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Grace
--

SELECT pg_catalog.setval('reviews_id_seq', 1, false);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: Grace
--

COPY users (id, name, username, password, admin) FROM stdin;
17	Joey	jojo	*	t
13	Joey	jojo	*	f
12	Carol	Karl	*	f
14	Ryan	gothicRyan	*	f
15	Leslie	wizzer	*	f
16	Raymond	redman	*	t
20	Shelly	bigmomma	*	f
9	Jahan	databaseKiller	*	t
8	Blake	SpacePack	*	t
18	Billy	yellowfish	*	f
19	Bob	grayellow	*	f
10	Sirius	dogstar	*	t
11	Chris	TheFinney	*	t
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Grace
--

SELECT pg_catalog.setval('users_id_seq', 20, true);


--
-- Data for Name: votes; Type: TABLE DATA; Schema: public; Owner: Grace
--

COPY votes (id, userid, parkid, direction) FROM stdin;
1	6	1	up
2	6	2	down
4	9	3	up
5	9	4	down
8	17	3	up
9	17	5	down
11	17	4	up
\.


--
-- Name: votes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Grace
--

SELECT pg_catalog.setval('votes_id_seq', 11, true);


--
-- Name: checkins checkins_pkey; Type: CONSTRAINT; Schema: public; Owner: Grace
--

ALTER TABLE ONLY checkins
    ADD CONSTRAINT checkins_pkey PRIMARY KEY (id);


--
-- Name: dogs dogs_pkey; Type: CONSTRAINT; Schema: public; Owner: Grace
--

ALTER TABLE ONLY dogs
    ADD CONSTRAINT dogs_pkey PRIMARY KEY (id);


--
-- Name: parks parks_pkey; Type: CONSTRAINT; Schema: public; Owner: Grace
--

ALTER TABLE ONLY parks
    ADD CONSTRAINT parks_pkey PRIMARY KEY (id);


--
-- Name: reviews reviews_pkey; Type: CONSTRAINT; Schema: public; Owner: Grace
--

ALTER TABLE ONLY reviews
    ADD CONSTRAINT reviews_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: Grace
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: votes votes_pkey; Type: CONSTRAINT; Schema: public; Owner: Grace
--

ALTER TABLE ONLY votes
    ADD CONSTRAINT votes_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

