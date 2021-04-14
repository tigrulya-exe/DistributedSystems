create table if not exists Nodes (
    id bigserial primary key,
    "user" text not null,
    latitude numeric not null,
    longitude numeric not null
);

create table if not exists Tags (
    id bigserial primary key,
    key text not null,
    value text not null,
    nodeId bigint references Nodes(id)
);