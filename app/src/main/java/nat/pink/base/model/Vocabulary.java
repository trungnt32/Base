package nat.pink.base.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Vocabulary {

    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("phonetic")
    @Expose
    private String phonetic;
    @SerializedName("phonetics")
    @Expose
    private List<Phonetic> phonetics = null;
    @SerializedName("meanings")
    @Expose
    private List<Meaning> meanings = null;
    @SerializedName("license")
    @Expose
    private License__1 license;
    @SerializedName("sourceUrls")
    @Expose
    private List<String> sourceUrls = null;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public List<Phonetic> getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(List<Phonetic> phonetics) {
        this.phonetics = phonetics;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<Meaning> meanings) {
        this.meanings = meanings;
    }

    public License__1 getLicense() {
        return license;
    }

    public void setLicense(License__1 license) {
        this.license = license;
    }

    public List<String> getSourceUrls() {
        return sourceUrls;
    }

    public void setSourceUrls(List<String> sourceUrls) {
        this.sourceUrls = sourceUrls;
    }

    public class Phonetic {

        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("audio")
        @Expose
        private String audio;
        @SerializedName("sourceUrl")
        @Expose
        private String sourceUrl;
        @SerializedName("license")
        @Expose
        private License license;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getAudio() {
            return audio;
        }

        public void setAudio(String audio) {
            this.audio = audio;
        }

        public String getSourceUrl() {
            return sourceUrl;
        }

        public void setSourceUrl(String sourceUrl) {
            this.sourceUrl = sourceUrl;
        }

        public License getLicense() {
            return license;
        }

        public void setLicense(License license) {
            this.license = license;
        }

    }

    public class Meaning {

        @SerializedName("partOfSpeech")
        @Expose
        private String partOfSpeech;
        @SerializedName("definitions")
        @Expose
        private List<Definition> definitions = null;
        @SerializedName("synonyms")
        @Expose
        private List<String> synonyms = null;
        @SerializedName("antonyms")
        @Expose
        private List<String> antonyms = null;

        public String getPartOfSpeech() {
            return partOfSpeech;
        }

        public void setPartOfSpeech(String partOfSpeech) {
            this.partOfSpeech = partOfSpeech;
        }

        public List<Definition> getDefinitions() {
            return definitions;
        }

        public void setDefinitions(List<Definition> definitions) {
            this.definitions = definitions;
        }

        public List<String> getSynonyms() {
            return synonyms;
        }

        public void setSynonyms(List<String> synonyms) {
            this.synonyms = synonyms;
        }

        public List<String> getAntonyms() {
            return antonyms;
        }

        public void setAntonyms(List<String> antonyms) {
            this.antonyms = antonyms;
        }

    }

    public class License__1 {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("url")
        @Expose
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }

    public class License {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("url")
        @Expose
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }

    public class Definition {

        @SerializedName("definition")
        @Expose
        private String definition;
        @SerializedName("synonyms")
        @Expose
        private List<String> synonyms = null;
        @SerializedName("antonyms")
        @Expose
        private List<String> antonyms = null;
        @SerializedName("example")
        @Expose
        private String example;

        public String getDefinition() {
            return definition;
        }

        public void setDefinition(String definition) {
            this.definition = definition;
        }

        public List<String> getSynonyms() {
            return synonyms;
        }

        public void setSynonyms(List<String> synonyms) {
            this.synonyms = synonyms;
        }

        public List<String> getAntonyms() {
            return antonyms;
        }

        public void setAntonyms(List<String> antonyms) {
            this.antonyms = antonyms;
        }

        public String getExample() {
            return example;
        }

        public void setExample(String example) {
            this.example = example;
        }

    }

}
