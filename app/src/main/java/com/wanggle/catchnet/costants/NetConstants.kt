package com.wanggle.catchnet.costants

class NetConstants {
    companion object {
        @JvmField
        var netMap = HashMap<String, String>()
        init {
            netMap["/xes/course/getHomePageSet"] = "选课信息Tab"
            netMap["/xes/course/getSubjectList"] = "获取学科列表"
            netMap["/xes/school/list"] = "获取校区列表"
            netMap["/xes/subscribe/submit"] = "预约课程"
            netMap["/xes/course/getCourseList"] = "获取课程列表"
            netMap["/xes/course/getFilterList"] = "获取筛选项"
            netMap["/xes/home/getHomeInfo"] = "获取首页数据"
            netMap["/xes/subscribe/list"] = "获取已预约列表"
            netMap["/xes/subscribe/cancel"] = "取消预约"
            netMap["/xes/user/info"] = "获取个人信息"
            netMap["/xes/course/getLiveBroadcast"] = "获取直播信息"
            netMap["/xes/abtest/getHomePage"] = "获取首页AB测信息"
        }
    }
}
