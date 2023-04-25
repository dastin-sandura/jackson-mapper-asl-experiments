package com.blogspot.dastinsandurasoftwaredeveloper;

import com.blogspot.dastinsandurasoftwaredeveloper.model.Root;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.impl.ReaderBasedParser;
import org.codehaus.jackson.io.IOContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.SimpleType;
import org.codehaus.jackson.sym.CharsToNameCanonicalizer;
import org.codehaus.jackson.util.BufferRecycler;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
//        readerExample();
        objectMapperExample();
    }

    private static void objectMapperExample() {
        ObjectMapper objectMapper = new ObjectMapper();

        Root deserializedObject = new Root();
        boolean b = objectMapper.canDeserialize(SimpleType.construct(Root.class));
        System.out.println(b);
        String fileName = ".\\src\\main\\resources\\generated.json";

        try {
            ArrayList<Root> root = objectMapper.readValue(new File(fileName), ArrayList.class);
            System.out.println(root);
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    private static void readerExample() {
        /**
         * If true, it is, and is to be closed by parser/generator;
         *if false, calling application has to do closing (unless auto-closing
         *feature is enabled for the parser/generator in question; in which
         *case it acts like the owner).
         */
        boolean isManagedByParser = true;
        /**
         * Reference to the source object,
         * which can be used for displaying location information
         */
        Object sourceRef = new Object();
        /**
         * Recycler used for actual allocation/deallocation/reuse
         */
        BufferRecycler bufferRecycler = new BufferRecycler();

        /**
         * To limit number of configuration and state objects to pass,
         * all contextual objects that need to be passed by the factory to readers
         * and writers are combined under this object.
         * One instance is created for each reader and writer.
         */
        IOContext ioContext = new IOContext(
                bufferRecycler,
                sourceRef,
                isManagedByParser
        );
        FileReader in = null;
        try {
            String fileName = ".\\src\\main\\resources\\generated.json";
            Path x = Paths.get(fileName).toAbsolutePath();
            System.out.println(x);
            in = new FileReader(fileName);
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        }
        BufferedReader reader = new BufferedReader(in);
        ObjectMapper codec = new ObjectMapper();
        CharsToNameCanonicalizer charsToNameCanonicalizer = CharsToNameCanonicalizer.createRoot();

        ReaderBasedParser readerBasedParser = new ReaderBasedParser(
                ioContext,
                1,
                reader,
                codec,
                charsToNameCanonicalizer
        );
        String text = "";
        try {
            List<String> collect = reader.lines().collect(Collectors.toList());
            System.out.println("Collected lines: ");
            System.out.println(collect);
            readerBasedParser.nextToken();
            text = readerBasedParser.getText();
            System.out.println(text);
        } catch (JsonParseException jpe) {
            System.err.println(jpe);
        } catch (IOException ioe) {
            System.err.println("IOException");
            System.err.println(ioe);
        }

    }
}
