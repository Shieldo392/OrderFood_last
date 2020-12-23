package data.remote;

import java.util.List;

import data.AdversitedObject;
import data.FoodObject;
import data.VoucherObject;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SOService {
    @GET("/drinktea")
    Call<List<FoodObject>> getAnswers_drinktea();

    @GET("/mainmeals")
    Call<List<FoodObject>> getAnswers_mainmeals();

    @GET("/listfood")
    Call<List<FoodObject>> getAnswers_listfood();

    @GET("/adviseted")
    Call<List<AdversitedObject>> getAnswers_adver();
    @GET("/welcome")
    Call<List<AdversitedObject>> getAnswers_wel();

    @GET("/bigsales")
    Call<List<VoucherObject>> getAnswers_voucher();
}
