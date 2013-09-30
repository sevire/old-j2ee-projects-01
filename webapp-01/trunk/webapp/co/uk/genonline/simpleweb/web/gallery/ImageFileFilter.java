package co.uk.genonline.simpleweb.web.gallery;

import java.io.File;
import java.io.FileFilter;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 08/07/2012
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
public class ImageFileFilter implements FileFilter {
    String[] extensionList;

    public ImageFileFilter(String[] extensionList) {
        this.extensionList = extensionList;
    }

    public boolean accept(File file) {
        boolean accept = false;
        if (file.isFile()) {
            String name = file.getName();
            int mid = name.lastIndexOf(".");
            String extension = name.substring(mid+1,name.length());
            extension = extension.toLowerCase();
            for (String ext : extensionList) {
                if (ext.equals(extension)) {
                    accept = true;
                }
            }
        }
        return accept;
    }
}
