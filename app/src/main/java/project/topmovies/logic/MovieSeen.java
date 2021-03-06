package project.topmovies.logic;

public class MovieSeen {

    private String movieTitle;
    private String dateWatched;
    private String timeWatched;
    private String ticketsNumber;
    private String finalPrice;

    public MovieSeen(String dateWatched, String timeWatched, String ticketsNumber, String finalPrice) {

        this.dateWatched = dateWatched;
        this.timeWatched = timeWatched;
        this.ticketsNumber = ticketsNumber;
        this.finalPrice = finalPrice;

    }

    public MovieSeen(String movieTitle, String dateWatched, String timeWatched, String ticketsNumber, String finalPrice) {

        this.movieTitle = movieTitle;
        this.dateWatched = dateWatched;
        this.timeWatched = timeWatched;
        this.ticketsNumber = ticketsNumber;
        this.finalPrice = finalPrice;

    }

    public String getMovieTitle() { return movieTitle; }

    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }

    public String getDateWatched() { return dateWatched; }

    public void setDateWatched(String dateWatched) { this.dateWatched = dateWatched; }

    public String getTimeWatched() { return timeWatched; }

    public void setTimeWatched(String timeWatched) { this.timeWatched = timeWatched; }

    public String getTicketsNumber() { return ticketsNumber; }

    public void setTicketsNumber(String ticketsNumber) { this.ticketsNumber = ticketsNumber; }

    public String getFinalPrice() { return finalPrice; }

    public void setFinalPrice(String finalPrice) { this.finalPrice = finalPrice; }

}
