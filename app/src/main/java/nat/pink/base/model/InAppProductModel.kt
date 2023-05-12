package nat.pink.base.model

enum class SkuState {
    SKU_STATE_UNPURCHASED, SKU_STATE_PENDING, SKU_STATE_PURCHASED, SKU_STATE_PURCHASED_AND_ACKNOWLEDGED, SKU_STATE_EXPIRED
}

data class InAppProductModel(
    var id: String,
    var name: String,
    var description: String,
    var offerToken: String = "",
    var price: String = "",
    var purchaseTime: Long = 0,
    var billingCycle: Int = 0,
    var billingPeriod: String = "",
    var priceCurrencyCode: String = "",
    var type: String = "", //String INAPP = "inapp" (one time); SUBS = "subs" (time: weekly, monthly, yearly);
    var state: SkuState = SkuState.SKU_STATE_UNPURCHASED,
    var rootPrice : Long = 0,
    var salePrice : Int = 0
)