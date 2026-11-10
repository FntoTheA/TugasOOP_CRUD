--
-- PostgreSQL database dump
--

\restrict cDb76N2dsleuVL9FCsMdDzWppQdRd1z2mcBXjCBEJwn9KWfEObW2DqB58o6vhGL

-- Dumped from database version 18.6
-- Dumped by pg_dump version 18.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: operasi; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.operasi (
    op_id integer NOT NULL,
    bil_1 integer NOT NULL,
    opr character(1) NOT NULL,
    bil_2 integer NOT NULL,
    CONSTRAINT operasi_opr_check CHECK ((opr = ANY (ARRAY['+'::bpchar, '-'::bpchar, 'x'::bpchar, '/'::bpchar])))
);


ALTER TABLE public.operasi OWNER TO postgres;

--
-- Name: operasi_op_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.operasi ALTER COLUMN op_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.operasi_op_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: operasi; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.operasi (op_id, bil_1, opr, bil_2) FROM stdin;
\.


--
-- Name: operasi_op_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.operasi_op_id_seq', 1, false);


--
-- Name: operasi operasi_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.operasi
    ADD CONSTRAINT operasi_pkey PRIMARY KEY (op_id);


--
-- PostgreSQL database dump complete
--

\unrestrict cDb76N2dsleuVL9FCsMdDzWppQdRd1z2mcBXjCBEJwn9KWfEObW2DqB58o6vhGL

