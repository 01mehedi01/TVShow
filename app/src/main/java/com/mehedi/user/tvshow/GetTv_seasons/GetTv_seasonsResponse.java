package com.mehedi.user.tvshow.GetTv_seasons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 9/10/2018.
 */

public class GetTv_seasonsResponse {

    @SerializedName("_idd")
    @Expose
    private String idd;
    @SerializedName("air_date")
    @Expose
    private String airDate;
    @SerializedName("episodes")
    @Expose
    private List<Episode> episodes = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("season_number")
    @Expose
    private Integer seasonNumber;

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public class Episode {

        @SerializedName("air_date")
        @Expose
        private String airDate;
        @SerializedName("episode_number")
        @Expose
        private Integer episodeNumber;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("overview")
        @Expose
        private String overview;
        @SerializedName("production_code")
        @Expose
        private Object productionCode;
        @SerializedName("season_number")
        @Expose
        private Integer seasonNumber;
        @SerializedName("show_id")
        @Expose
        private Integer showId;
        @SerializedName("still_path")
        @Expose
        private String stillPath;
        @SerializedName("vote_average")
        @Expose
        private Float voteAverage;
        @SerializedName("vote_count")
        @Expose
        private Integer voteCount;
        @SerializedName("crew")
        @Expose
        private List<Object> crew = null;
        @SerializedName("guest_stars")
        @Expose
        private List<Object> guestStars = null;

        public String getAirDate() {
            return airDate;
        }

        public void setAirDate(String airDate) {
            this.airDate = airDate;
        }

        public Integer getEpisodeNumber() {
            return episodeNumber;
        }

        public void setEpisodeNumber(Integer episodeNumber) {
            this.episodeNumber = episodeNumber;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public Object getProductionCode() {
            return productionCode;
        }

        public void setProductionCode(Object productionCode) {
            this.productionCode = productionCode;
        }

        public Integer getSeasonNumber() {
            return seasonNumber;
        }

        public void setSeasonNumber(Integer seasonNumber) {
            this.seasonNumber = seasonNumber;
        }

        public Integer getShowId() {
            return showId;
        }

        public void setShowId(Integer showId) {
            this.showId = showId;
        }

        public String getStillPath() {
            return stillPath;
        }

        public void setStillPath(String stillPath) {
            this.stillPath = stillPath;
        }

        public Float getVoteAverage() {
            return voteAverage;
        }

        public void setVoteAverage(Float voteAverage) {
            this.voteAverage = voteAverage;
        }

        public Integer getVoteCount() {
            return voteCount;
        }

        public void setVoteCount(Integer voteCount) {
            this.voteCount = voteCount;
        }

        public List<Object> getCrew() {
            return crew;
        }

        public void setCrew(List<Object> crew) {
            this.crew = crew;
        }

        public List<Object> getGuestStars() {
            return guestStars;
        }

        public void setGuestStars(List<Object> guestStars) {
            this.guestStars = guestStars;
        }

    }

}
