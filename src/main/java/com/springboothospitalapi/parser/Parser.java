package com.springboothospitalapi.parser;

public interface Parser<T> {
    T parse(String str);
}
