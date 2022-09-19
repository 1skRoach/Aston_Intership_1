package com.example.pickup_pic.data.db.contracts.mappers

import com.example.pickup_pic.data.db.contracts.entities.LinkEntity
import com.example.pickup_pic.domain.models.photo.Link

class LinkEntityToLinkMapper : Mapper<LinkEntity, Link> {
    override fun map(from: LinkEntity): Link =
        Link(
            self = from.self,
            html = from.html,
            download = from.download,
            downloadLocation = from.downloadLocation
        )
}