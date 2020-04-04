CREATE EXTENSION IF NOT EXISTS postgis;
-- DROP TABLE IF EXISTS nodes;
-- DROP TABLE IF EXISTS edges;

CREATE TABLE IF NOT EXISTS nodes(
    id bigint PRIMARY KEY,
    point GEOGRAPHY(Point) NOT NULL UNIQUE,
    elevation double precision
);

CREATE TABLE IF NOT EXISTS edges(
    src bigint references nodes(id),
    dest bigint references nodes(id),
    distance double precision
);

CREATE INDEX IF NOT EXISTS nodes_point_gix
ON nodes USING GIST (private);

-- INSERT INTO nodes(geog) values('POINT(-118.4079 33.9434)');
-- INSERT INTO nodes(geog) values('POINT(2.5559 49.0083)');

