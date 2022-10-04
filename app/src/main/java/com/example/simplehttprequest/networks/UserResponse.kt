package com.example.simplehttprequest.networks

import com.example.simplehttprequest.models.User

data class UserResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val data: List<User>
)
