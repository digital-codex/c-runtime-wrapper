package dev.codex.java.wrapper.library;

import dev.codex.java.wrapper.exception.IllegalArgumentException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;

public class NativeLibraryLoader {
    private NativeLibraryLoader() {
        super();
    }

    // TODO: add java.library.path search
    public static void load(Class<?> provider, String library) throws IOException {
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
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }
}