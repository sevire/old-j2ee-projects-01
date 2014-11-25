package uk.co.genonline.ldav03.model;

import uk.co.genonline.ldav03.web.LinkData;

/**
 * Used as supertype for all page related data objects.  Encapsulates what is common between all pages.
 */
public interface PageData {
    public LinkData getLinkData();

    // Not sure whether I need to pull out any more fields as common. Not for now
}
