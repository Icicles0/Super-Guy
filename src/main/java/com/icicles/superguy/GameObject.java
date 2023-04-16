package com.icicles.superguy;

public class GameObject {
    float x;
    float y;
    int width;
    int height;

    public GameObject(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        // TODO: add texture support to the engine
    }
}
