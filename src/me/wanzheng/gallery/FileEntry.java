package me.wanzheng.gallery;

/**
 * Created on 18/1/14 by cos
 */
public class FileEntry {
    public String baseDir;
    public boolean isDir;
    public String filename;

    @Override
    public String toString() {
        return filename;
    }
}
