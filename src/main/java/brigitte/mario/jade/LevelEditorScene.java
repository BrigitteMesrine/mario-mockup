package brigitte.mario.jade;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;

import java.awt.event.KeyEvent;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.GL20.*;
import org.lwjgl.system.*;

public class LevelEditorScene extends Scene{

    private String vertexShaderSrc = "#version 330 core\r\n" + //
                "\r\n" + //
                "layout (location=0) in vec3 aPos;\r\n" + //
                "layout (location=1) in vec4 aColor;\r\n" + //
                "\r\n" + //
                "out vec4 fColor;\r\n" + //
                "\r\n" + //
                "void main() {\r\n" + //
                "    fColor = aColor;\r\n" + //
                "    gl_Position = vec4(aPos, 1.0);\r\n" + //
                "}";

    private String fragmentShaderSrc ="#version 330 core\r\n" + //
                "\r\n" + //
                "in vec4 fColor;\r\n" + //
                "\r\n" + //
                "out vec4 color;\r\n" + //
                "\r\n" + //
                "void main() {\r\n" + //
                "    color = fColor;\r\n" + //
                "}";

    private int vertexID, fragmentID, shaderProgram;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        // compile and link the shaders

        // First load and compile the vertex shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);

        // Pass the shader source to the GPU
        glShaderSource(vertexID, vertexShaderSrc);
        glCompileShader(vertexID);

        // Check for errors in compilation
        // glGetShaderi -> get shader info
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("C.ERROR : 'defaultShader.glsl \n\tVertex shader compilation failed.");
            System.out.println(glGetShaderInfoLog(vertexID, len));

            // terminate program
            assert false : "";
        }

        // First load and compile the fragment shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        // Pass the shader source to the GPU
        glShaderSource(fragmentID, fragmentShaderSrc);
        glCompileShader(fragmentID);

        // Check for errors in compilation
        // glGetShaderi -> get shader info
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("C.ERROR : 'defaultShader.glsl \n\tFragment shader compilation failed.");
            System.out.println(glGetShaderInfoLog(fragmentID, len));

            // terminate program
            assert false : "";
        }

        // Link shaders and check for errors
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        // CHeck for linking errors
        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (success  == GL_FALSE) {
            int len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println("C.ERROR : 'defaultShader.glsl \n\tLinking of shaders failed.");

            System.out.println(glGetProgramInfoLog(shaderProgram, len));

            // terminate program
            assert false : "";
        }
    }

    @Override
    public void update(float dt) {

    }
}
