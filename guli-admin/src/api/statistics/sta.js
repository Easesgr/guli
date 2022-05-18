import request from '@/utils/request'
export default{
  createSta(date) {
    return request({
      url: `/sta-service/createSta/${date}`,
      method: 'get',
    })
  },
  getData(searchObj) {
    return request({
      url: `/sta-service//getData/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
      method: 'post',
    })
  }
}
