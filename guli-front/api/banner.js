import request from '@/utils/request'
export default {
  getBannerList(page,limit) {
    return request({
        url: `edu/crm/${page}/${limit}`,
        method: 'get'
      })
  },
  getTeacherAndCourse(){
    return request({
      url: `/edu/front/getHotTeacherAndCourse`,
      method: 'get'
    })
  }
}