import request from '@/utils/request'
export default {
  getCommentList(page, limit) {
    return request({
        url: `/edu/comment/getList/${page}/${limit}`,
        method: 'get',
      })
    },
  saveComment(commentObj){
    return request({
      url: `/edu/comment/publishComment`,
      method: 'post',
      data:commentObj
    })
  },
}