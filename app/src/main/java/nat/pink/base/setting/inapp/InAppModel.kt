package nat.pink.base.setting.inapp

import android.app.Activity
import androidx.lifecycle.MutableLiveData

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nat.pink.base.base.BaseViewModel
import nat.pink.base.model.InAppProductModel
import nat.pink.base.utils.InAppPurchase

class InAppModel : BaseViewModel() {

    var updateProducts = MutableLiveData<List<InAppProductModel>>()
    var statusBuy = MutableLiveData<Boolean>()

    fun getProducts() {
        InAppPurchase.getInstance().getProducts()
            .onEach {
                var priceOneMonth = 0L
                it.forEach {
                    it.rootPrice =
                        it.price.replace("đ", "").replace("Đ", "").replace("$", "").replace(".", "")
                            .replace(",", "").replace("₫", "")
                            .toLong()
                    if (it.id.contains(InAppPurchase.IN_APP_PROD_1m))
                        priceOneMonth = it.rootPrice
                }
                val sortItems = it.sortedBy {
                    it.rootPrice
                }
                sortItems.onEach {
                    if (!it.id.contains(InAppPurchase.IN_APP_PROD_1m) && !it.id.contains(
                            InAppPurchase.IN_APP_PROD_all
                        )
                    ) {
                        it.salePrice = -(1 - (it.rootPrice * 100 / (priceOneMonth * 12))).toInt();
                    }
                }
                updateProducts.postValue(sortItems)
            }
            .launchIn(GlobalScope)
    }

    fun buyProduct(id: String, activity: Activity) {
        InAppPurchase.getInstance().launchBillingFlow(activity, id, { result ->
            statusBuy.postValue(result)
        })
    }
}