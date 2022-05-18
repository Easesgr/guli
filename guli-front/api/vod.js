import request from '@/utils/request'
export default {
  getPlayAuth(id) {
    return request({
        url: `/vod-service/getPlayAuth/${id}`,
        method: 'get'
      })
    }
}