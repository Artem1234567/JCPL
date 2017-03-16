package com.jcplproject.multi;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    
    private AtomicInteger value = new AtomicInteger(0);
    
    public void increment() {
        value.incrementAndGet();
    }
    
    public int value() {
        return value.get();
    }
}
