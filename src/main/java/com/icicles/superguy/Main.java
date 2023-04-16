package com.icicles.superguy;

import org.lwjgl.opengl.GL;

import static com.icicles.superguy.MainWindow.log;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public class Main {
    static void loop(long window) {
        GL.createCapabilities();

        log("Entering loop...");

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
            handleInput(window);
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
        MainWindow window = new MainWindow();
    }
}