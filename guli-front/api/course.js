import request from '@/utils/request'
export default {
  getCourseInfo(page, limit,courseObj) {
    return request({
        url: `/edu/front/course/getCourseInfo/${page}/${limit}`,
        method: 'post',
        data:courseObj
      })
    },
  getSubject(){
    return request({
      url: `/edu/subject/list`,
      method: 'get',
    })
  },
  getDetailInfo(id){
    return request({
      url: `/edu/front/course/getDetailInfo/${id}`,
      method: 'get',
    })
  }
}