package com.icicles.superguy;

import org.lwjgl.bgfx.BGFXInit;
import org.lwjgl.bgfx.BGFXPlatformData;
import org.lwjgl.bgfx.BGFXResolution;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.bgfx.BGFXPlatform.bgfx_set_platform_data;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFWNativeWin32.glfwGetWin32Window;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static com.icicles.superguy.Main.loop;
import static org.lwjgl.bgfx.BGFX.*;

public class MainWindow {
    private static long window;

    public MainWindow(int width, int height) {
        log("Main window clas initialized.");
        init(width, height);
        loop(window, width, height);

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public static void log(String message) {
        System.out.printf("LOG: %s\n", message);
    }

    private static void init(int width, int height) {
        // Set up an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        log("Initializing GLFW..");
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        log("Creating window...");
        // Hopefully I don't have to go digging in the old code to change the window name.
        window = glfwCreateWindow(width, height, "Super Guy", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Set up a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the windowS
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }
        log("initalizing bgfx...");
        initbgfx(window, width, height);
    }

    static void initbgfx(long window, int width, int height) {
        log("allocating buffers because bgfx is a little baby and cant allocate its own memory");
        ByteBuffer pdbuffer = ByteBuffer.allocate(40); //TODO: fix access violation
        ByteBuffer initbuffer = ByteBuffer.allocate(40);
        ByteBuffer resbuffer = ByteBuffer.allocate(40);
        log("setting up platform data...");
        BGFXPlatformData pd = new BGFXPlatformData(pdbuffer);
        pd.nwh(glfwGetWin32Window(window));
        bgfx_set_platform_data(pd);

        BGFXInit bgfxInit = new BGFXInit(initbuffer);

        bgfxInit.type(BGFX_RENDERER_TYPE_OPENGL); ////////////////////////////////////////////////// BGFX RENDER BACKEND

        bgfxInit.resolution(new BGFXResolution(resbuffer).width(width).height(height).reset(BGFX_RESET_VSYNC));

        log("Running bgfx_init()...");
        bgfx_init(bgfxInit);
    }

}
