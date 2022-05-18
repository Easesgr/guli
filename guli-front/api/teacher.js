import request from '@/utils/request'
export default {
  getPageList(page, limit) {
    return request({
        url: `/edu/front/teacher/${page}/${limit}`,
        method: 'get'
      })
    },
  getTeacherAndCourse(id) {
    return request({
        url: `/edu/front//teacherAndCourse/${id}`,
        method: 'get'
      })
    }
}