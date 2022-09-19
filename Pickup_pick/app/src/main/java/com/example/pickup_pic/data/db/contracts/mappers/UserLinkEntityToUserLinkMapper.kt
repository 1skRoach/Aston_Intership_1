package com.example.pickup_pic.data.db.contracts.mappers

import com.example.pickup_pic.data.db.contracts.entities.UserLinkEntity
import com.example.pickup_pic.domain.models.user.UserLink

class UserLinkEntityToUserLinkMapper  : Mapper<UserLinkEntity, UserLink> {
    override fun map(from: UserLinkEntity): UserLink =
        UserLink(
            self = from.self,
            html = from.html,
            photos = from.photos,
            likes = from.likes,
            portfolio = from.portfolio
        )
}