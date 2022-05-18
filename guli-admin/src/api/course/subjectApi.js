import request from '@/utils/request'
export default{
    getSubjectList(){
        return request({
            url: `/edu/subject/list`,
            method: 'get',
          })
    }
}