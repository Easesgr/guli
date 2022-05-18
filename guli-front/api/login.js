import request from '@/utils/request'
export default {
  login(loginInfo) {
    return request({
        url: `/edu/member/login`,
        method: 'post',
        data:loginInfo
      })
  },
  getUserInfo() {
    return request({
        url: `/edu/member/getInfo`,
        method: 'get'
      })
  },
  
}