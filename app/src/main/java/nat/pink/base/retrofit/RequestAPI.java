package nat.pink.base.retrofit;

import nat.pink.base.model.ObjectCountry;
import nat.pink.base.model.ObjectFeedback;
import nat.pink.base.model.ObjectList;
import nat.pink.base.model.ObjectTranslate;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestAPI {
    @GET("language/list")
    Call<ObjectCountry> getCountrys();

    @POST("translate/content")
    Call<ObjectTranslate> translates(@Query("content_translate") String content_translate, @Query("language_code") String language_code);

    @GET("apps/list")
    Call<ObjectList> getList();

    @GET("feedback/list")
    Call<ObjectFeedback> getFeedBack();

    
    @POST("feedback/create")
    Call<ObjectTranslate> feedback(@Query("content_feedback") String content_feedback, @Query("app_id") String app_id);
}
