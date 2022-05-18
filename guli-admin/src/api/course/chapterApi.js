import request from '@/utils/request'
export default{
    getChapterAndVideo(courseId){
        return request({
            url: `/edu/chapter/getChapterAndVideo/${courseId}`,
            method: 'get'
          })
    },
    getChapterById(chapterId){
        return request({
            url: `/edu/chapter/${chapterId}`,
            method: 'get'
          })
    },
    updateChapter(chapterInfo){
        return request({
            url: `/edu/chapter/update`,
            method: 'put',
            data:chapterInfo
          })
    },
    addChapter(chapterInfo){
        return request({
            url: `/edu/chapter/add`,
            method: 'post',
            data:chapterInfo
          })
    },
    deleteChapter(chapterId){
        return request({
            url: `/edu/chapter/${chapterId}`,
            method: 'delete',
          })
    }
}