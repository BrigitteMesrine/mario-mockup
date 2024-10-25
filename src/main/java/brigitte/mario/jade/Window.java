package brigitte.mario.jade;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

// Singleton window
public class Window {

    private int width, height;
    private String title;
    private long glfwWindow;

    private static Window window = null;

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Mario";
    }

    // Singleton condition
    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public void run() {
        System.out.println("Hello LWJGL" + Version.getVersion() + "!");

        init();
        loop();

        // free the memory once the loop has ended
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        // -- setup an error callback --
        GLFWErrorCallback.createPrint(System.err).set();

        // GLFW = Graphics Library FrameWork

        // -- initialize GLFW --
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // -- configure GLFW --
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        // window hint are here to parameter the window
        // here window visibility is set to false, because we want to set the window
        // before displaying it
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        // resizable true
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
        // max size

        // -- create the window --
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        // !!!! NULL is part of enum

        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create de GLFW window.");
        }

        // forward this callback to mousePosCallback
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);

        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);

        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);

        // -- make the OpenGL context current --
        glfwMakeContextCurrent(glfwWindow);
        // enable v-sync
        glfwSwapInterval(1);

        // ^^^^refresh rate

        // -- make the window visible now it is set up--
        glfwShowWindow(glfwWindow);

        // THis line is critical for LWJGL's interoperation with GLFW's OpenGL context,
        // or an context that is managed externally. LWJGL detects the context that is
        // current in the current thread, creates the GLCapabilities instance and makes
        // the OPenGL bindings available for use.
        GL.createCapabilities();

    }

    public void loop() {
        while (!glfwWindowShouldClose(glfwWindow)) {
            // poll events (click, move, hover...)
            glfwPollEvents();

            // define a color
            glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
            // flush that color to the entire window
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(glfwWindow);
        }
    }
}
