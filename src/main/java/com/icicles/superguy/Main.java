package com.icicles.superguy;

import org.lwjgl.opengl.GL;

import static com.icicles.superguy.MainWindow.log;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.bgfx.BGFX.*;

public class Main {
    static void loop(long window, int width, int height) {

        bgfx_set_view_clear(0, BGFX_CLEAR_COLOR | BGFX_CLEAR_DEPTH, 0x443355FF, 1.0f, 0);
        bgfx_set_view_rect(0,0,0, width, height);

        log("Entering loop...");

        int counter = 0;
        while (!glfwWindowShouldClose(window)) {
            System.out.printf("Frame %s", counter);
            bgfx_frame(false);


            // Graphics code here


            glfwSwapBuffers(window);
            glfwPollEvents();
            handleInput(window);

            counter++;
        }
    }

    static void handleInput(long window) {
        int W = glfwGetKey(window, GLFW_KEY_W);
        int S = glfwGetKey(window, GLFW_KEY_S);
        int D = glfwGetKey(window, GLFW_KEY_D);
        int A = glfwGetKey(window, GLFW_KEY_A);

        if (W == GLFW_PRESS) {System.out.println("W was pressed!");}
        if (S == GLFW_PRESS) {System.out.println("S was pressed!");}
        if (D == GLFW_PRESS) {System.out.println("D was pressed!");}
        if (A == GLFW_PRESS) {System.out.println("A was pressed!");}
    }

    public static void main(String[] args) {
        int width = 5000;
        int height = 5000;
        MainWindow window = new MainWindow(width, height);
    }
}