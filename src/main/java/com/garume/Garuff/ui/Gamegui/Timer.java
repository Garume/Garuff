package com.garume.Garuff.ui.Gamegui;

public class Timer {
    public long ms;
    public Timer(){ this.ms = 0; }
    public boolean hasPassed(int ms){ return System.currentTimeMillis() - this.ms >= ms; }
    public void reset(){this.ms = System.currentTimeMillis();}
}
