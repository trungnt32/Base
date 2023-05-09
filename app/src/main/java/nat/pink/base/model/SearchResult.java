package nat.pink.base.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResult {

    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public class Result {

        @SerializedName("searchtext")
        @Expose
        private String searchtext;

        public String getSearchtext() {
            return searchtext;
        }

        public void setSearchtext(String searchtext) {
            this.searchtext = searchtext;
        }

    }
}
