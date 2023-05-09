package nat.pink.base.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ObjectList {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }


    public class Result {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name_project")
        @Expose
        private String nameProject;
        @SerializedName("name_in_store")
        @Expose
        private String nameInStore;
        @SerializedName("app_id")
        @Expose
        private String appId;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("size")
        @Expose
        private String size;
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("link_ios")
        @Expose
        private Object linkIos;
        @SerializedName("link_android")
        @Expose
        private String linkAndroid;
        @SerializedName("time_update_store")
        @Expose
        private String timeUpdateStore;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("name")
        @Expose
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNameProject() {
            return nameProject;
        }

        public void setNameProject(String nameProject) {
            this.nameProject = nameProject;
        }

        public String getNameInStore() {
            return nameInStore;
        }

        public void setNameInStore(String nameInStore) {
            this.nameInStore = nameInStore;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Object getLinkIos() {
            return linkIos;
        }

        public void setLinkIos(Object linkIos) {
            this.linkIos = linkIos;
        }

        public String getLinkAndroid() {
            return linkAndroid;
        }

        public void setLinkAndroid(String linkAndroid) {
            this.linkAndroid = linkAndroid;
        }

        public String getTimeUpdateStore() {
            return timeUpdateStore;
        }

        public void setTimeUpdateStore(String timeUpdateStore) {
            this.timeUpdateStore = timeUpdateStore;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
