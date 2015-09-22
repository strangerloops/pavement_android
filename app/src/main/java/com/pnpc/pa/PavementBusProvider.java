package com.pnpc.pa;

import com.squareup.otto.Bus;

/**
 * Created by markusmcgee on 9/15/15.
 */
public class PavementBusProvider {
    private static final MainThreadBus INSTANCE  = new MainThreadBus();
    private PavementBusProvider(){}

    public static Bus getInstance(){
        return INSTANCE;
    }
}