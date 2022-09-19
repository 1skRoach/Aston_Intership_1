package com.example.pickup_pic.data.db.contracts.mappers

import com.example.pickup_pic.data.db.contracts.entities.UrlEntity
import com.example.pickup_pic.domain.models.photo.Url

class UrlEntityToUrlMapper: Mapper<UrlEntity, Url> {
    override fun map(from: UrlEntity): Url =
        Url(
            raw = from.raw,
            full = from.full,
            regular = from.regular,
            small = from.small,
            thumb = from.thumb
        )
}