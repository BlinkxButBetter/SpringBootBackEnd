//package io.b3.Models;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import java.util.List;
//import java.util.Map;
//
//@Document(collection = "mobi")
//public class Movie {
//    @Id
//    private String id;
//    private String plot;
//    private List<String> genres;
//    private int runtime;
//    private List<String> cast;
//    private int numMflixComments;
//    private String poster;
//    private String title;
//    private String lastUpdated;
//    private List<String> languages;
//    private long released; // Using long for the date timestamp
//    private List<String> directors;
//    private List<String> writers;
//    private Awards awards;
//    private int year;
//    private Imdb imdb;
//    private List<String> countries;
//    private String type;
//    private Tomatoes tomatoes;
//
//    // Getters and Setters
//
//    // Nested classes for complex properties
//    public static class Awards {
//        private int wins;
//        private int nominations;
//        private String text;
//
//        // Getters and Setters
//        public int getWins() { return wins; }
//        public void setWins(int wins) { this.wins = wins; }
//
//        public int getNominations() { return nominations; }
//        public void setNominations(int nominations) { this.nominations = nominations; }
//
//        public String getText() { return text; }
//        public void setText(String text) { this.text = text; }
//    }
//
//    public static class Imdb {
//        private double rating;
//        private int votes;
//        private int id;
//
//        // Getters and Setters
//        public double getRating() { return rating; }
//        public void setRating(double rating) { this.rating = rating; }
//
//        public int getVotes() { return votes; }
//        public void setVotes(int votes) { this.votes = votes; }
//
//        public int getId() { return id; }
//        public void setId(int id) { this.id = id; }
//    }
//
//    public static class Tomatoes {
//        private Viewer viewer;
//        private String dvd;
//        private String website;
//        private String production;
//        private String lastUpdated;
//
//        // Getters and Setters
//        public Viewer getViewer() { return viewer; }
//        public void setViewer(Viewer viewer) { this.viewer = viewer; }
//
//        public String getDvd() { return dvd; }
//        public void setDvd(String dvd) { this.dvd = dvd; }
//
//        public String getWebsite() { return website; }
//        public void setWebsite(String website) { this.website = website; }
//
//        public String getProduction() { return production; }
//        public void setProduction(String production) { this.production = production; }
//
//        public String getLastUpdated() { return lastUpdated; }
//        public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }
//    }
//
//    public static class Viewer {
//        private double rating;
//        private int numReviews;
//        private int meter;
//
//        // Getters and Setters
//        public double getRating() { return rating; }
//        public void setRating(double rating) { this.rating = rating; }
//
//        public int getNumReviews() { return numReviews; }
//        public void setNumReviews(int numReviews) { this.numReviews = numReviews; }
//
//        public int getMeter() { return meter; }
//        public void setMeter(int meter) { this.meter = meter; }
//    }
//
//    // Getters and Setters for Movie properties
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getPlot() {
//        return plot;
//    }
//
//    public void setPlot(String plot) {
//        this.plot = plot;
//    }
//
//    public List<String> getGenres() {
//        return genres;
//    }
//
//    public void setGenres(List<String> genres) {
//        this.genres = genres;
//    }
//
//    public int getRuntime() {
//        return runtime;
//    }
//
//    public void setRuntime(int runtime) {
//        this.runtime = runtime;
//    }
//
//    public List<String> getCast() {
//        return cast;
//    }
//
//    public void setCast(List<String> cast) {
//        this.cast = cast;
//    }
//
//    public int getNumMflixComments() {
//        return numMflixComments;
//    }
//
//    public void setNumMflixComments(int numMflixComments) {
//        this.numMflixComments = numMflixComments;
//    }
//
//    public String getPoster() {
//        return poster;
//    }
//
//    public void setPoster(String poster) {
//        this.poster = poster;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getLastUpdated() {
//        return lastUpdated;
//    }
//
//    public void setLastUpdated(String lastUpdated) {
//        this.lastUpdated = lastUpdated;
//    }
//
//    public List<String> getLanguages() {
//        return languages;
//    }
//
//    public void setLanguages(List<String> languages) {
//        this.languages = languages;
//    }
//
//    public long getReleased() {
//        return released;
//    }
//
//    public void setReleased(long released) {
//        this.released = released;
//    }
//
//    public List<String> getDirectors() {
//        return directors;
//    }
//
//    public void setDirectors(List<String> directors) {
//        this.directors = directors;
//    }
//
//    public List<String> getWriters() {
//        return writers;
//    }
//
//    public void setWriters(List<String> writers) {
//        this.writers = writers;
//    }
//
//    public Awards getAwards() {
//        return awards;
//    }
//
//    public void setAwards(Awards awards) {
//        this.awards = awards;
//    }
//
//    public int getYear() {
//        return year;
//    }
//
//    public void setYear(int year) {
//        this.year = year;
//    }
//
//    public Imdb getImdb() {
//        return imdb;
//    }
//
//    public void setImdb(Imdb imdb) {
//        this.imdb = imdb;
//    }
//
//    public List<String> getCountries() {
//        return countries;
//    }
//
//    public void setCountries(List<String> countries) {
//        this.countries = countries;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public Tomatoes getTomatoes() {
//        return tomatoes;
//    }
//
//    public void setTomatoes(Tomatoes tomatoes) {
//        this.tomatoes = tomatoes;
//    }
//}
