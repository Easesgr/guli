import request from '@/utils/request'
export default {
  createOrder(courseId) {
    return request({
        url: `/order-service/createOrder/${courseId}`,
        method: 'post'
      })
  },
  getOrderById(orderId) {
    return request({
        url: `/order-service/getOrderInfo/${orderId}`,
        method: 'get'
      })
  }
}