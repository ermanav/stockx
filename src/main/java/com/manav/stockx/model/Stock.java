package com.manav.stockx.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Stock {

    int id;
    String name;
    long price;
}
