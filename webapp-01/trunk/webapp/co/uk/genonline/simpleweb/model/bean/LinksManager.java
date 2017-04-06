package co.uk.genonline.simpleweb.model.bean;

import sun.awt.image.ImageWatched;

import java.util.List;

/**
 * Created by thomassecondary on 14/03/2017.
 */
public interface LinksManager {
    boolean addLink(LinksEntity linkData);

    boolean deleteLink(String linkName);

    boolean deleteLink(int linkNumber);

    LinksEntityExtended getLink(String linkName);

    List<LinksEntityExtended> getAllLinks();

    List<LinksEntityExtended> getLiveLinks();

    List<LinksEntityExtended> getLiveBannerLinks();

    LinksEntity initialiseLinksEntity();

}
