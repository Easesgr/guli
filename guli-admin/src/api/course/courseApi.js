import request from '@/utils/request'
export default{
    // 添加课程信息
    addCourseInfo(courseInfo){
        return request({
            url: `/edu/course/addCourseInfo`,
            method: 'post',
            data:courseInfo
          })
    },
    // 获取讲师列表
    getTeacherList(){
        return request({
            url: `/edu/teacher/findAll`,
            method: 'get',
          })
    },
    // 根据id获取课程信息
    getCourseById(courseId){
        return request({
            url: `/edu/course/${courseId}`,
            method: 'get',
          })
    }, 
    // 更新课程信息
    updateCourse(courseInfo){
        return request({
            url: `/edu/course/update`,
            method: 'put',
            data:courseInfo
          })
    },
    // 获取发布课程信息
    getPublishCourse(id){
        return request({
            url: `/edu/course/publishCourseInfo/${id}`,
            method: 'get',
          })
    },
    publishCourse(id){
        return request({
            url: `/edu/course/updateStatus/${id}`,
            method: 'post',
          })
    },
    // 获取课程列表
    getList(page,limit,searchInfo){
        return request({
            url: `/edu/course/getCoursesPage/${page}/${limit}`,
            method: 'post',
            data:searchInfo
          })
    },
    deleteCourse(id){
        return request({
            url: `/edu/course/${id}`,
            method: 'delete',
          })
    }
}