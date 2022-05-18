import request from '@/utils/request'
export default{
  getTeacherList(page, limit, teachQuery) {
    return request({
      url: `/edu/teacher/condition/${page}/${limit}`,
      method: 'post',
      // 这里是因为后端放在requestBody中
      data: teachQuery
    })
  },
  deleteById(id) {
    return request({
      url: `/edu/teacher/${id}`,
      method: 'delete',
    })
  },
  addTeacher(teacher){
    return request({
      url: `/edu/teacher/create`,
      method: 'post',
      data: teacher
    })
  },
  getTeacherById(id){
    return request({
      url: `/edu/teacher/${id}`,
      method: 'get',
    })
  },
  updateTeacherById(teacher){
    return request({
      url: `/edu/teacher/update`,
      method: 'post',
      data:teacher
    })
  }
}
