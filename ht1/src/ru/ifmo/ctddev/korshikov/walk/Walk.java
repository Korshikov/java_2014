package ru.ifmo.ctddev.korshikov.walk;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by delf on 18.02.15.
 */
public class Walk {

    static final int BLOCK_SIZE = 2048;
    static final int INIT_HASH_VALUE = 0x811c9dc5;
    static final int NEXT_HASH_VALUE = 0x01000193;


    static private int fvnHash(final byte[] bytes) {
        int h = INIT_HASH_VALUE;
        for (final byte b : bytes) {
            h = (h * NEXT_HASH_VALUE) ^ (b & 0xff);
        }
        return h;
    }

    static private int fvnHash(final byte[] bytes, int h) {
        for (final byte b : bytes) {
            h = (h * NEXT_HASH_VALUE) ^ (b & 0xff);
        }
        return h;
    }

    private static int getFileHash(String filename) {
        int hash = INIT_HASH_VALUE;
        try (FileInputStream reader = new FileInputStream(filename)) {
            byte[] block = new byte[BLOCK_SIZE];
            int length;
            while ((length = reader.read(block)) > 0) {
                if (length == BLOCK_SIZE) {
                    hash = fvnHash(block, hash);
                } else {
                    hash = fvnHash(Arrays.copyOf(block, length), hash);
                }
            }
        } catch (FileNotFoundException e) {
            hash = 0;
            System.err.println("file not found " + filename);
        } catch (IOException e) {
            hash = 0;
            System.err.println("Some error with reading file " + filename);
        }


        return hash;
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("restart with 2 arguments\njava Walk <input_file_name> <output_file_name>");
            return;
        }

        PrintWriter writer;
        try {
            writer = new PrintWriter(args[1], "UTF8");
        } catch (FileNotFoundException e) {
            System.out.println("output file unreachable");
            return;
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported encoding");
            return;
        }

        String path;
        try (Scanner scanner = new Scanner(new File(args[0]), "UTF8")) {
            while ((path = scanner.nextLine()) != null) {
                writer.println(String.format("%08x", getFileHash(path)) + " " + path);
            }
        } catch (FileNotFoundException someException) {
            System.err.println("input file unreachable");
            return;
        } catch (Exception ignore) {
            System.err.println("input file io error");
        }
        writer.close();
    }

}
