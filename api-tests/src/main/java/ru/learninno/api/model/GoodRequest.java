package ru.learninno.api.model;

import java.math.BigDecimal;

/** JSON contract of the create/update product endpoint. */
public record GoodRequest(String name, BigDecimal price) { }
