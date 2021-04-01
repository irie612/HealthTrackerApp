package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Database<T> {

    protected List<T> data;
    protected BufferedWriter fw;
    private final File file;
    protected String url;
    protected BufferedReader fileReader;
    protected String delimiter;


    private final static String DEFAULT_DELIMITER = ",";

    public Database(String url) throws IOException {
        file = new File(url);
        this.url = url;
        delimiter = DEFAULT_DELIMITER;
        if (!file.exists()) {
            //file not found
            throw new FileNotFoundException(url + " file not found");
        } else if (!file.canWrite()) {
            //not writable
            throw new IOException("Cannot write to file: Change file permissions");
        } else if (!file.canRead()) {
            //not readable
            throw new IOException("Cannot read file: Change file permissions");
        }

        data = new ArrayList<>();
    }

    public Database(String url, String delimiter) throws IOException {
        file = new File(url);
        this.url = url;
        this.delimiter = delimiter;
        if (!file.exists()) {
            //file not found
            throw new FileNotFoundException();
        } else if (!file.canWrite()) {
            //not writable
            //TODO throw exception
        } else if (!file.canRead()) {
            //not readable
            //TODO throw exception
        }

        fileReader = new BufferedReader(new FileReader(file));
        data = new ArrayList<>();
    }

    public String getUrl() {
        return url;
    }

    abstract void insert(T t) throws IOException;

    abstract void loadElements() throws IOException;

}
