package dev.codex.java.runtime.library;

import dev.codex.java.io.file.FileTreeWalker;
import dev.codex.java.runtime.exception.IllegalArgumentException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;

public class NativeLibraryLoader {
    public static final String WORKSPACE = System.getProperty("user.dir");
    public static final String LIBRARY_PATH
            = System.getProperty("java.library.path");

    private NativeLibraryLoader() {
        super();
    }

    public static void load(String directories, String library) {
        FileTreeWalker.resolve(directories, library)
                .ifSuccessOrElse(
                        path -> System.load(path.toString()),
                        () -> NativeLibraryLoader.load(
                                NativeLibraryLoader.class, library
                        )
                );
    }

    public static void load(Class<?> provider, String library) {
        if (!library.startsWith("/")) {
            library = "/" + library;
        }

        URL url = provider.getResource(library);
        if (url == null) {
            throw new IllegalArgumentException("path", "must be a valid resource-path accessible by `provider`");
        }

        try {
            String[] split = library.split("/");
            Path tmp = Files.createTempFile(split[split.length - 1].split("\\.")[0], ".so");

            URI uri = url.toURI();
            String[] parts = uri.toString().split("!");
            FileSystem fs = FileSystems.newFileSystem(URI.create(parts[0]), System.getenv());
            Path src = fs.getPath(parts[1]);
            Files.copy(src, tmp, StandardCopyOption.REPLACE_EXISTING);
            fs.close();

            System.load(tmp.toString());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}