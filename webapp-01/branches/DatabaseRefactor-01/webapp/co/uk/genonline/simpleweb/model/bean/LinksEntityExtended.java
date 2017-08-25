package co.uk.genonline.simpleweb.model.bean;

import java.sql.Date;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
/**
 * Created by thomassecondary on 15/03/2017.
 */
public class LinksEntityExtended extends LinksEntity {
    long numDaysSinceChecked;
    boolean timeToCheck;
    Date dateOfNextCheck;

    LinksEntityExtended(LinksEntity linksEntity, LinksConfiguration linksConfiguration) {
        // Initialise core members from passed in linksEntity object
        this.number = linksEntity.getNumber();
        this.name = linksEntity.getName();
        this.bannerImageName = linksEntity.getBannerImageName();
        this.status = linksEntity.getStatus();
        this.url = linksEntity.getUrl();
        this.lastCheckedDate = linksEntity.getLastCheckedDate();
        this.nextActionType = linksEntity.getNextActionType();
        this.nextActionNotes = linksEntity.getNextActionNotes();
        this.sortKey = linksEntity.getSortKey();
        this.userName = linksEntity.getUserName();
        this.password = linksEntity.getPassword();
        this.bannerRequired = linksEntity.getBannerRequired();

        // Remaining fields are calculated fields - so calculate them.
        this.numDaysSinceChecked = numDaysSinceChecked();
        this.timeToCheck = timeToCheck(linksConfiguration.getDaysBetweenChecks());
        this.dateOfNextCheck = dateOfNextCheck(linksConfiguration.getDaysBetweenChecks());
    }

    private long numDaysSinceChecked() {
        //return ChronoUnit.DAYS.between(getLastCheckedDate().toLocalDate(), dateToday().toLocalDate());

        LocalDate lastChecked = getLastCheckedDate().toLocalDate();
        LocalDate today = LocalDate.now();

        return DAYS.between(lastChecked, today);
    }

    boolean timeToCheck(int daysBetweenChecks) {
        return numDaysSinceChecked() >= daysBetweenChecks;
    }

    Date dateOfNextCheck(int daysBetweenChecks) {
        LocalDate lastChecked = getLastCheckedDate().toLocalDate();
        return Date.valueOf(lastChecked.plusDays(daysBetweenChecks));
    }

    private Date dateToday() {
        java.util.Date utilDate = new java.util.Date();
        Date today = new Date(utilDate.getTime());
        return today;
    }
}
