package nat.pink.base.model

import nat.pink.base.base.BaseModel

data class Language(
    var language: String? = null,
    var isSelected: Boolean = false,
    var value: String? = null,
    var flags: Int? = null
) : BaseModel()