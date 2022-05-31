package com.sviridov.icerockprac

fun UserInfoResponse.toUserInfo()
= UserInfo(login = this.login)