package com.example.pickup_pic.data.db.contracts

object UserLinkContract {
    const val TABLE_NAME = "user_links"

    object Columns {
        const val ID = "id"
        const val SELF = "self"
        const val HTML = "html"
        const val PHOTOS = "photos"
        const val LIKES = "likes"
        const val PORTFOLIO = "portfolio"
    }
}