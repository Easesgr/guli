import request from '@/utils/request'
export default {
  getCode(moblie) {
    return request({
        url: `/edu/member/getCode/${moblie}`,
        method: 'get'
      })
  },
  register(resgisterInfo){
    return request({
      url: `/edu/member/register`,
      method: 'post',
      data:resgisterInfo
    })
  }
}