import request from '@/utils/request'
export default{
    getVideoById(VideoId){
        return request({
            url: `/edu/video/${VideoId}`,
            method: 'get'
          })
    },
    updateVideo(VideoInfo){
        return request({
            url: `/edu/video/update`,
            method: 'put',
            data:VideoInfo
          })
    },
    addVideo(VideoInfo){
        return request({
            url: `/edu/video/add`,
            method: 'post',
            data:VideoInfo
          })
    },
    deleteVideo(VideoId){
        return request({
            url: `/edu/video/${VideoId}`,
            method: 'delete',
          })
    },
    removeVideo(id){
        return request({
            url: `/vod-service/deleteVideo/${id}`,
            method: 'delete',
          })
    }
}