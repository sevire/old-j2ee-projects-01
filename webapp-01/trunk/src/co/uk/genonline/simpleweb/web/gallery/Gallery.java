package co.uk.genonline.simpleweb.web.gallery;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 01/07/2012
 * Time: 17:59
 * To change this template use File | Settings | File Templates.
 */
public class Gallery {

    private String webRoot;
    private String galleryName;
    private File galleryFile;
    private Logger logger;
    private File thumbnailFolderFile;
    private String html;
    private int maxWidth;
    private int maxHeight;
    private int imagesAdded = 0;
    private int numColumns;

    Gallery(
            String webRoot,
            String galleryPath,
            String thumbnailPath,
            String galleryName,
            int maxWidth,
            int maxHeight,
            int numColumns) {
        logger = Logger.getLogger(this.getClass().getName());
        logger.setLevel(Level.ALL);

        this.webRoot = webRoot;
        this.galleryName = galleryName;
        this.galleryFile = new File(galleryPath);
        this.thumbnailFolderFile = new File(thumbnailPath);
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.numColumns= numColumns;
        html = null;
    }

    public void generateThumbnail(File image, File thumbnail, boolean force) {
        if (!force && image.isFile()) {
            logger.info(String.format("Thumbnail for <%s> exists, nothing to do", thumbnail));
        } else {
            BufferedImage bufferedImage;
            BufferedImage thumbnailImage;
            logger.info(String.format("About to read image for resizing to thumbnail <%s>", image));

            try {
                bufferedImage = ImageIO.read(image);
            } catch (IOException e) {
                logger.error("Error reading image");
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return;
            }

            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();
            float widthScaleFactor = maxWidth / width;
            float heightScaleFactor = maxHeight / height;

            float scaleFactor = Math.min(widthScaleFactor, heightScaleFactor);

            thumbnailImage = getScaledInstance(bufferedImage, (int) (width * scaleFactor), (int) (height * scaleFactor), RenderingHints.VALUE_INTERPOLATION_BICUBIC, true);
            logger.info(String.format("thumbnailImage is <%s>", thumbnailImage));
            try {
                thumbnail.createNewFile();
            } catch (IOException e) {
                logger.error("Error creating file for thumbnail");
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return;
            }
            logger.info(String.format("About to write thumbnail file <%s>", thumbnail));
            logger.info(String.format("(File) thumbnail is <%s>", thumbnail));
            try {
                ImageIO.write(thumbnailImage, "jpg", thumbnail);
                thumbnailImage.flush();
            } catch (IOException e) {
                logger.error("Error writing thumbnail");
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                return;
            }
        }
    }

    public String getHTML() {
        if (html == null) {
            File galleryFullPath = new File(webRoot, galleryFile.getPath());
            if (!galleryFullPath.isDirectory()) {
                logger.error(String.format("Gallery path <%s> isn't a directory, can't generate gallery", galleryFile.getAbsolutePath()));
            } else {
                String[] extensions = {"jpg", "png"};
                FileFilter filter = new ImageFileFilter(extensions);
                File list[] = galleryFullPath.listFiles(filter);

                html = String.format("<table class='gallery'>%n");

                for (File file : list) {
                    logger.info(String.format("Processing file <%s> within gallery <%s>", file, galleryName));
                    File imageFile = new File(galleryFile, file.getName());
                    File thumbnail = new File(thumbnailFolderFile.getPath() + File.separator + file.getName());
                    generateThumbnail(file, thumbnail, false);
                    addImageToHTML(imageFile, thumbnail);
                }

                html += String.format("</table>%n");
            }
        }
        logger.debug("html = \n"+html);
        return html;
    }

    private void addImageToHTML(File image, File thumbnail) {
        if (imagesAdded % numColumns == 0) {
            // Last row is full so starting new row
            // Begin by terminating previous row, but only if there is one

            if (imagesAdded != 0) {
                html += String.format("</tr>%n");
            }
            html += String.format("<tr>%n");
        }

        // add this image as td element

        String img = String.format("<img src='%s' />%n", thumbnail.getPath());
        String anchor = String.format("<a href='/viewImage?gallery=%s&image=%s'>%s</a>%n", galleryName, image.getName(), img);

        html += String.format("<td>%n%s</td>%n", anchor);
        imagesAdded++;
    }

/**
 * Convenience method that returns a scaled instance of the
 * provided {@code BufferedImage}.
 *
 * @param img the original image to be scaled
 * @param targetWidth the desired width of the scaled instance,
 *    in pixels
 * @param targetHeight the desired height of the scaled instance,
 *    in pixels
 * @param hint one of the rendering hints that corresponds to
 *    {@code RenderingHints.KEY_INTERPOLATION} (e.g.
 *    {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
 *    {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
 *    {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
 * @param higherQuality if true, this method will use a multi-step
 *    scaling technique that provides higher quality than the usual
 *    one-step technique (only useful in downscaling cases, where
 *    {@code targetWidth} or {@code targetHeight} is
 *    smaller than the original dimensions, and generally only when
 *    the {@code BILINEAR} hint is specified)
 * @return a scaled version of the original {@code BufferedImage}
 */
    public BufferedImage getScaledInstance(BufferedImage img,
                                           int targetWidth,
                                           int targetHeight,
                                           Object hint,
                                           boolean higherQuality)
    {
        int type = (img.getTransparency() == Transparency.OPAQUE) ?
            BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage)img;
        int w, h;
        if (higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth();
            h = img.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }

        do {
            if (higherQuality && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }

            if (higherQuality && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);

        return ret;
    }
}
