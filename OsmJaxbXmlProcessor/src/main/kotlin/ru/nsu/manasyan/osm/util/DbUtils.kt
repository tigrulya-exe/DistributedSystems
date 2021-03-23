package ru.nsu.manasyan.osm.util

import org.postgresql.core.Utils

fun String.escapeQuotes(): String = Utils.escapeLiteral(
    null,
    this,
    true
).toString()