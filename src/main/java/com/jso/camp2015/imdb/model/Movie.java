package com.jso.camp2015.imdb.model;

/**
 * {"MovieID":"\"The Good Wife\" (2009)",
 * "SeriesID":"\"The Good Wife\" (2009)",
 * "SeriesType":"S",
 * "SeriesEndYear":"",
 * "ReleaseYear":"2009",
 * "AltTitles":["\"Good Wife\" (2009)"],
 * "Parental":{"Certificates":
 *      ["Australia:M","China:(Banned)\t(internet) (2014)",
 *      "Finland:K-12","Netherlands:9\t(seasons 1 and 2)",
 *      "Netherlands:12\t(seasons 3 and 4)",
 *      "Singapore:NC-16\t(season 1)",
 *      "Singapore:M18\t(season 2) (season 3) (season 5)",
 *      "USA:TV-14\t(some episodes)",
 *      "USA:TV-PG\t(some episodes)"
 *      ]},
 *  "Technical":{"Colors":["Color"],
 *  "Sounds":["Dolby Digital"]}
 */
public class Movie {
    private final String _id;
    private String objectId;
    private String movieID;
    private String seriesID;
    private String seriesType;
    private String seriesEndYear;
    private String releaseYear;
    private String alternativeTitles;

    public Movie(final String _id){
        this._id = _id;
        this.objectId=_id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String id() {
        return _id;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public String getSeriesID() {
        return seriesID;
    }

    public void setSeriesID(String seriesID) {
        this.seriesID = seriesID;
    }

    public String getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(String seriesType) {
        this.seriesType = seriesType;
    }

    public String getSeriesEndYear() {
        return seriesEndYear;
    }

    public void setSeriesEndYear(String seriesEndYear) {
        this.seriesEndYear = seriesEndYear;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getAlternativeTitles() {
        return alternativeTitles;
    }

    public void setAlternativeTitles(String alternativeTitles) {
        this.alternativeTitles = alternativeTitles;
    }

}
