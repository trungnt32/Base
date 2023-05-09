package nat.pink.base.model

import android.graphics.drawable.Drawable
import nat.pink.base.base.BaseModel

data class PageOnboard(
    var imageDrawable: Drawable?,
    var title: String,
    var content: String
): BaseModel()