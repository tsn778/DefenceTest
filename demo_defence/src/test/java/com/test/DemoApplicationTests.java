package com.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.test.demo.entity.*;
import com.test.demo.mapper.*;
import com.test.demo.service.*;
import com.test.demo.shiroAuth.TokenGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupTeacherService gtService;
    @Autowired
    private GroupSortService groupSortService;
    @Autowired
    private GroupSortMapper groupSortMapper;
    @Autowired
    private SysTokenMapper sysTokenMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private GroupTeacherMapper gtMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private GroupStudentMapper groupStudentMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private InstituteService instituteService;
    @Autowired
    private UserRoleService userRoleService;
    @Test
    void contextLoads() {
        SysToken byToken = sysTokenMapper.selectById(1);
        //生成一个token

        //修改token
         byToken.setToken("123123");

        sysTokenMapper.insert(byToken);
    }
    @Test
    void contextLoad1s() {
//          userService.selectSomeone(null,"1").forEach(System.out::println);
        Group group=new Group();
        group.setGroupLeaderId(1);
        groupService.save(group);
    }
    @Test
    void contextLoad2s() {
        System.out.println(userService.selectByPage(2,1,2));

    }
    @Test
    void contextLoad3s() {
//        System.out.println(userService.selectByPage(2,1,2));

//        gtMapper.selectTeacher(3).forEach(System.out::println);
//        gtService.selectByPage(null,2,2);
        Page<User> page = new Page<>(2,2);
        IPage<User> result = gtMapper.selectTchInG(page,2);
        // 获取数据
        List<User> records = result.getRecords();
        records.forEach(System.out::println);
        System.out.println("总页数 = "+ result.getPages());
    }
    @Test
    void contextLoad4s() {
//        System.out.println(userService.selectByPage(2,1,2));

//        gtMapper.selectTeacher(3).forEach(System.out::println);
//        gtService.selectByPage(null,2,2);
        groupService.RandomSort(1);
    }
    @Test
    void contextLoad5s() {
//        System.out.println(userService.selectByPage(2,1,2));

//        gtMapper.selectTeacher(3).forEach(System.out::println);
//        gtService.selectByPage(null,2,2);
        List<GroupSort> groupSorts = groupSortMapper.selectSort(1);
        groupSorts.forEach(System.out::println);
    }
    @Test
    void contextLoad6s() {
//        System.out.println(userService.selectByPage(2,1,2));

//        gtMapper.selectTeacher(3).forEach(System.out::println);
//        gtService.selectByPage(null,2,2);
//        List<GroupSort> groupSorts = groupSortService.RandomSort(1);
//        groupSorts.forEach(System.out::println);
        gtMapper.selectTeacher(2);

    }
    @Test
    void contextLoad7() {
//        System.out.println(userService.selectByPage(2,1,2));

//        gtMapper.selectTeacher(3).forEach(System.out::println);
//        gtService.selectByPage(null,2,2);
//        List<GroupSort> groupSorts = groupSortService.RandomSort(1);
//        groupSorts.forEach(System.out::println);
//        System.out.println(userInfoMapper.selectOneInfo(7));
//        Page<UserInfo> page = new Page<>(1,5);
//        IPage<UserInfo> result = userInfoMapper.selectUserInfo(page,1);
//        // 获取数据
//        List<UserInfo> records = result.getRecords();
//        records.forEach(System.out::println);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("sdas");
        userInfoMapper.insert(userInfo);
    }
    @Test
    void contextLoad8() {
        Page<Group> page = new Page<>(1,5);
        IPage<Group> groupIPage = groupMapper.selectGroupInfo(page, 0);
//        IPage<UserInfo> result = userInfoMapper.selectUserInfo(page,1);
//        // 获取数据
        List<Group> records = groupIPage.getRecords();
        records.forEach(System.out::println);
    }
    @Test
    void contextLoad9() {
        groupMapper.selectMyGroup(7);
    }
    @Test
    void sd(){
        QueryWrapper<GroupStudent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("group_id",3).and(i->i.eq("user_id",10));
        System.out.println(groupStudentMapper.selectCount(queryWrapper));
    }
    @Test
    void test(){
        List<Record> records = recordMapper.selectRecordByTch(5, null);
        records.forEach(System.out::println);

    }
    @Test
    void test2(){
        List<Institute> list = instituteService.list();
        list.forEach(System.out::println);

    }
    @Test
    void test3(){
        User user = userMapper.selectById(25);
        User user1 = new User();
        user1.setEmail("sdsd");



    }
    @Test
    void t5(){

        List<GroupSort> groupSorts = groupSortMapper.selectSortByGroup(25);
        int am=0;
        int pm=0;
        GroupSort myGroupSort = new GroupSort();
        for(GroupSort groupSort:groupSorts){
            if(groupSort.getState()==null||groupSort.getState()==0) {
                System.out.println(groupSort);
            }



        }
    }
}
