drop table if exists Tags;

drop table if exists Nodes;

create table Nodes (
    id bigserial primary key,
    "user" text not null,
    uid numeric not null,
    lat numeric not null,
    lon numeric not null,
    version bigint not null,
    changeset bigint not null,
    "timestamp" timestamp not null
);

create table Tags (
    id bigserial primary key,
    key text not null,
    value text not null,
    nodeId bigint references Nodes(id)
);