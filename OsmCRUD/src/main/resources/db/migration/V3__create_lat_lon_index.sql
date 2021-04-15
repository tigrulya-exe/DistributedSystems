create index lat_lon_index on nodes using gist(ll_to_earth(nodes.longitude, nodes.latitude));
