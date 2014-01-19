package me.wanzheng.gallery;

/**
 * Created on 18/1/14 by cos
 */
public class FileEntry {
    public String baseDir;
    public boolean isDir;
    public String filename;

    public FileEntry(String baseDir, String filename) {
        this.baseDir = baseDir;
        this.filename = filename;

        isDir = filename.endsWith("/");
    }

    @Override
    public String toString() {
        return filename;
    }
}
