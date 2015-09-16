package ru.ifmo.ctddev.korshikov.implementor;


import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.JarImpler;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;


/**
 * Created by delf on 05.03.15.
 */
public class Implementor implements JarImpler {


    private static final String FILE_NAME_SUFFIX = "Impl.java";
    private static final String CLASS_NAME_SUFFIX = FILE_NAME_SUFFIX.split("\\.")[0];
    private static final String PATH_SEPARATOR = File.separator;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String TABULATION = "    ";
    private static final String OPEN_BRACKET_CODE_BLOCK = "{";
    private static final String CLOSE_BRACKET_CODE_BLOCK = "}";
    private static final String END_CODE_LINE = ";";
    private static final String SPACE = " ";
    private static final String ARGUMENTS_SEPARATOR = ",";
    private Class<?> classToImplement;
    private FileWriter out;

    /**
     * Main function of Implementor
     *
     * @param args start arguments is array.
     *             length must be more than 1
     *             first arg - class for implement
     *             second ard - file name for new interface
     *             third arg - generate jar file or not
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("wrong args");
            return;
        }
        try {
            String name = args[0];
            String filename = args[1];
            File root = new File(filename);
            Class<?> token = Class.forName(name);
            Implementor implementor = new Implementor();
            if ((args.length >= 3) && (args[2].equals("jar"))) {
                implementor.implementJar(token, root);
            } else {
                implementor.implement(token, root);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        } catch (ImplerException e) {
            System.out.println("Some error");
        }
    }

    /**
     * compile class with default system compiler
     *
     * @param path path to class for compile
     * @throws IOException error write
     */
    private static void compileFile(String path) throws IOException {
        File f = new File(path);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        File[] files = {f};
        Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjectsFromFiles(Arrays.asList(files));
        compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();
        fileManager.close();
    }

    /**
     * Create jar file
     *
     * @param jarPath   path for new jar file
     * @param classPath path to class in jar file
     * @throws IOException error write
     */
    private static void makeJar(String jarPath, String classPath) throws IOException {
        File jarFile = new File(jarPath);
        File parent = jarFile.getParentFile();
        if (parent != null) {
            parent.mkdirs();
        }
        jarFile.createNewFile();
        String newClassPath = classPath.substring(classPath.indexOf("tmp" + PATH_SEPARATOR) + 4);
        FileOutputStream fileOut = new FileOutputStream(jarFile);
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, newClassPath);
        JarOutputStream jarOut = new JarOutputStream(fileOut, manifest);
        jarOut.putNextEntry(new ZipEntry(newClassPath));
        FileInputStream classStream = new FileInputStream(classPath);
        BufferedInputStream classBufferStream = new BufferedInputStream(classStream);
        byte[] buff = new byte[10000];
        int bytesRead;
        while ((bytesRead = classBufferStream.read(buff)) != -1) {
            jarOut.write(buff, 0, bytesRead);
        }
        jarOut.closeEntry();
        jarOut.close();
        fileOut.close();
    }

    /**
     * Implement class, create work interface
     *
     * @param token token to class with mast be implement
     * @param root  file to write new interface
     * @throws ImplerException when Impliment can't be generate
     */
    public void implement(Class<?> token, File root) throws ImplerException {
        int mod = token.getModifiers();
        if (Modifier.isFinal(mod) || token.isPrimitive()) {
            throw new ImplerException();
        }
        try {
            String path = root.getName() + PATH_SEPARATOR
                    + token.getPackage().getName().replaceAll("\\.", PATH_SEPARATOR);
            File f = new File(path);
            f.mkdirs();
            f = new File(path + PATH_SEPARATOR + token.getSimpleName() + FILE_NAME_SUFFIX);
            try (FileWriter out = new FileWriter(f)) {
                this.classToImplement = token;
                this.out = out;
                writeClass();
                out.close();
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    /**
     * write to file out class with in classToImplement
     *
     * @throws IOException If a file could not be write
     * @see java.lang.reflect.Method
     */
    private void writeClass() throws IOException {
        List<Method> methods = Arrays.asList(classToImplement.getMethods());
        out.append("package").append(SPACE).append(classToImplement.getPackage().getName()).append(END_CODE_LINE).append(LINE_SEPARATOR);
        out.append(LINE_SEPARATOR);
        out.append("public class").append(SPACE).append(classToImplement.getSimpleName()).append(CLASS_NAME_SUFFIX).append(SPACE).append("implements").append(SPACE)
                .append(classToImplement.getCanonicalName()).append(SPACE).append(OPEN_BRACKET_CODE_BLOCK).append(LINE_SEPARATOR);
        for (Method method : methods) {
            writeMethod(method);
        }
        out.append(CLOSE_BRACKET_CODE_BLOCK);
    }

    /**
     * Add method to result interface
     * with default return value
     *
     * @param method object of method to add
     * @throws IOException error write
     * @see java.lang.reflect.Method
     */
    private void writeMethod(Method method) throws IOException {
        int modifiers = method.getModifiers();
        if (Modifier.isFinal(modifiers) || Modifier.isNative(modifiers) || Modifier.isPrivate(modifiers)
                || !Modifier.isAbstract(modifiers)) {
            return;
        }
        modifiers ^= Modifier.ABSTRACT;
        if (Modifier.isTransient(modifiers)) {
            modifiers ^= Modifier.TRANSIENT;
        }
        out.append(LINE_SEPARATOR);
        out.append(TABULATION).append("@Override").append(LINE_SEPARATOR).append(TABULATION);
        Class<?>[] args = method.getParameterTypes();
        out.append(Modifier.toString(modifiers));
        Class<?> returnType = method.getReturnType();
        out.append(SPACE).append(returnType.getCanonicalName());
        out.append(SPACE).append(method.getName());
        writeArgs(args);
        writeExceptions(method.getExceptionTypes());
        out.append(OPEN_BRACKET_CODE_BLOCK).append(LINE_SEPARATOR);
        out.append(TABULATION).append(TABULATION).append("return").append(SPACE);
        if (returnType.isPrimitive()) {
            if (returnType.equals(boolean.class)) {
                out.append("false");
            } else if (!returnType.equals(void.class)) {
                out.append("0");
            }
        } else {
            out.append("null");
        }
        out.append(END_CODE_LINE).append(LINE_SEPARATOR);
        out.append(TABULATION).append(CLOSE_BRACKET_CODE_BLOCK).append(LINE_SEPARATOR);
    }

    /**
     * write method arguments
     *
     * @param args array of type of arguments
     * @throws IOException error write
     */
    private void writeArgs(Class<?>[] args) throws IOException {
        out.append("(");
        for (int i = 0; i < args.length; ++i) {
            out.append(args[i].getCanonicalName()).append(SPACE).append("arg").append(Integer.toString(i));
            if (i < args.length - 1) {
                out.append(ARGUMENTS_SEPARATOR).append(SPACE);
            }
        }
        out.append(")");
    }

    /**
     * write exceptions
     *
     * @param exceptions array of type exception
     * @throws IOException error write
     */
    private void writeExceptions(Class<?>[] exceptions) throws IOException {
        if (exceptions.length == 0) {
            return;
        }
        out.append(SPACE).append("throws").append(SPACE);
        for (int i = 0; i < exceptions.length; ++i) {
            out.append(exceptions[i].getCanonicalName());
            if (i < exceptions.length - 1) {
                out.append(ARGUMENTS_SEPARATOR).append(SPACE);
            }
        }
    }

    /**
     * Implement class, compile and create jar file for this class
     *
     * @param token token to class for implement
     * @param jar   filename for new jar
     * @throws ImplerException
     */
    public void implementJar(Class<?> token, File jar) throws ImplerException {
        try {
            String fileAbsPath = "tmp" + PATH_SEPARATOR;
            fileAbsPath += token.getPackage().getName().replaceAll("\\.", PATH_SEPARATOR);
            fileAbsPath += (token.getPackage().getName().isEmpty()) ? "" : PATH_SEPARATOR;
            (new File(fileAbsPath)).mkdirs();
            fileAbsPath += token.getSimpleName() + "Impl.java";
            this.out = new FileWriter(fileAbsPath, true);
            this.classToImplement = token;
            writeClass();
            out.close();
            compileFile(fileAbsPath);
            String classAbsPath = fileAbsPath.substring(0, fileAbsPath.indexOf(".java")) + ".class";
            makeJar(jar.getPath(), classAbsPath);
            System.gc();
            recDelete(new File("tmp/"));
        } catch (IOException e) {
            System.out.println("Error occurred while working with files");
        }
    }

    /**
     * recursive remove file or dirs
     *
     * @param file element to remove
     */
    private void recDelete(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            if (list != null) {
                for (File f : list) {
                    recDelete(f);
                }
            }
        }
        file.delete();
    }


}
