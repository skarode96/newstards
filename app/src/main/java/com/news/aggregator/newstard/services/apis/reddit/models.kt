package com.news.aggregator.newstard.services.apis.reddit

/**
 * Data classes to Represent API response of Reddit
 */

data class ResponseData(var data: ChildrenList)
data class ChildrenList(var children: List<ChildrenData>)
data class ChildrenData(var data: PostData)
data class PostData(var name: String,
                    var title: String,
                    var author: String,
                    var created_utc: Long,
                    var permalink: String,
                    var num_comments: Int)
