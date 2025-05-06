package com.c2h6s.tinkers_advanced.content.misc;

import appeng.menu.locator.MenuLocators;

public class AELocators {
    public static void init(){}
    static {
        MenuLocators.register(PocketCellLocator.class,PocketCellLocator::writeToPacket,PocketCellLocator::readFromPacket);
    }
}
