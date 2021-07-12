import request from '../utils/request';

export const fetchData = query => {
    return request({
        url: './table.json',
        method: 'get',
        params: query
    });
};
export const GetUserListAPI = () => request.get('/User/List')

export const PostLoginAPI = (params) => request.post('/sys/login', params)

export const PostIsSvipUser = (params) => request.post('/demo/user/svip',params)

export const GetUserList = (params,page,size) => request.get('/demo/user/userList/'+page+'/'+size,{params})

export const GetTeacher = (page,size) => request.get('/demo/user/teacherList/'+page+'/'+size)

export const GetStudent = (page,size) => request.get('/demo/user/studentList/'+page+'/'+size)

export const GetTeacherList = () => request.get('/demo/user/selectTeacher')

export const PostSomeoneList = (params) => request.get('/demo/user/selectSome',{params})

export const PostManageGroup = (params) => request.post('/demo/group/manageGroup',params)

export const PostAddTeacher = (params) => request.post('/demo/group/addTeacher',params)

export const GetTeacherInGroup = (page,size) => request.get('/demo/group/teacherList/'+page+'/'+size)

export const GetTchAllNoG = (page,size) => request.get('/demo/group//userList/'+page+'/'+size)

export const GetStuInGroup = (page,size) => request.get('/demo/group/stuInGList/'+page+'/'+size)

export const GetAllStudent = (page,size) => request.get('/demo/group/studentList/'+page+'/'+size)

export const GetSortList = (params) => request.get('/demo/group/getSortList?groupId=1',{params})

export const GetResetSort = (params) => request.get('/demo/group/resetSort',{params})

export const GetRandomSort = (params) => request.get('/demo/group/randomSort',{params})

export const PostUploadTest = (params) => request.post('/demo/record/upload',params)

export const GetTchList = (page,size) => request.get('/demo/user/tchList/'+page+'/'+size)

export const GetGroupList = (page,size) => request.get('/demo/group/groupList/'+page+'/'+size)

export const GetGroupOne = (params) => request.get('/demo/group/getGroupOne',{params})

export const GetTchInGroup = (params) => request.get('/demo/group/tchInGroup',{params})

export const GetMyGroup = (params) => request.get('/demo/group/myGroup',{params})

export const GetStuInG = (params) => request.get('/demo/group/stuInGroup',{params})

export const GetAllStu = (params,page,size) => request.get('/demo/group/getAllStu/'+page+'/'+size,{params})

export const PostAddStudent = (params) => request.post('/demo/group/addStudent',params)

export const PostDeleteStudent = (groupId,params) => request.delete('/demo/group/deleteStudent/'+groupId,params)

export const GetAllTch = (params,page,size) => request.get('/demo/group/getAllTch/'+page+'/'+size,{params})

export const PutDistribute = (params) => request.put('/demo/group/distribute',params)

export const PostDistributeTime = (params) => request.post('/demo/group/distributeTime',params)

export const GetStuSortByTime = (params) => request.get('/demo/group/stuSortByTime',{params})

export const PutSortOrder = (params) => request.put('/demo/group/sortOrder',params)

export const GetStuGroup = (params) => request.get('/demo/group/studentGroup')

export const GetRecord = (params) => request.get('/demo/record/recordInfo',{params})

export const GetToPdf = (params) => request.get('/demo/record/testPreview',{params})

export const GetRecordList = (params) => request.get('/demo/record/recordList',{params})

export const PutChanceDefence = (params) => request.put('/demo/group/chanceDefence',params)

export const PutSetStuState = (params) => request.put('/demo/group/setStuState',params)

export const GetRefreshDefence = (params) => request.get('/demo/group/refreshDefence',{params})

export const GetInstituteList = () => request.get('/demo/user/instituteList')

export const PostAddStu = (params) => request.post('/demo/user/addStu',params)

export const PutUpdateStu = (params) => request.put('/demo/user/updateStu',params)

export const PostAddTchInfo = (params) => request.post('/demo/user/addTchInfo',params)

export const PutUpdateTch = (params) => request.put('/demo/user/updateTch',params)

export const GetMySortInfo = (params) => request.get('/demo/group/getMySortInfo',{params})

export const DeleteTeacher = (params) => request.delete('/demo/group/deleteTeacher',params)