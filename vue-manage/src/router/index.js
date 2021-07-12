import {
	createRouter,
	createWebHistory,
	createWebHashHistory
} from "vue-router";
import Home from "../views/Home.vue";

const routes = [{
	path: '/',
	redirect: '/dashboard'
}, {
	path: "/",
	name: "Home",
	component: Home,
	children: [{
			path: "/dashboard",
			name: "dashboard",
			meta: {
				title: '系统首页',
				icon: "el-icon-lx-home",
				role: "look",
				show: 1,
			},
			component: () => import(
				/* webpackChunkName: "dashboard" */
				"../views/Dashboard.vue"),

		}, {
			path: "/table",
			name: "table",
			meta: {
				title: '学生列表',
				permission: "manage",
				icon: "el-icon-lx-people"

			},
			component: () => import(
				/* webpackChunkName: "table" */
				"../views/table/StudentTable.vue")
		}, {
			path: "/tchList",
			name: "tchList",
			meta: {
				title: '教师列表',
				permission: "manage",
				icon: "el-icon-lx-profile"

			},
			component: () => import(
				/* webpackChunkName: "table" */
				"../views/table/TeacherTable.vue")
		}, {
			path: "/GroupList",
			name: "GroupList",
			meta: {
				title: '分组列表',
				permission: "manage",
				icon: "el-icon-lx-group"
			},
			component: () => import(
				/* webpackChunkName: "table" */
				"../views/table/GroupTable.vue")
		}, {
			path: "/charts",
			name: "charts",
			meta: {
				title: '图表',
				permission: "look",
				show: 0
			},
			component: () => import(
				/* webpackChunkName: "charts" */
				"../views/BaseCharts.vue")

		}, {
			path: "/form",
			name: "form",
			meta: {
				title: '表单',
				role: "look",
				show: 0
			},
			component: () => import(
				/* webpackChunkName: "form" */
				"../views/BaseForm.vue")
		}, {
			path: "/tabs",
			name: "tabs",
			meta: {
				title: 'tab标签',
				role: "read",
				show: 0
			},
			component: () => import(
				/* webpackChunkName: "tabs" */
				"../views/Tabs.vue")
		}, {
			path: "/donate",
			name: "donate",
			meta: {
				title: '鼓励作者',
			},
			component: () => import(
				/* webpackChunkName: "donate" */
				"../views/Donate.vue")
		}, {
			path: "/permission",
			name: "permission",
			meta: {
				title: '权限管理'
			},
			component: () => import(
				/* webpackChunkName: "permission" */
				"../views/Permission.vue")
		}, {
			path: "/i18n",
			name: "i18n",
			meta: {
				title: '国际化语言'
			},
			component: () => import(
				/* webpackChunkName: "i18n" */
				"../views/I18n.vue")
		}, {
			path: "/upload",
			name: "upload",
			meta: {
				title: '上传插件',
				permission: "ding"
			},
			component: () => import(
				/* webpackChunkName: "upload" */
				"../views/Upload.vue")
		}, {
			path: "/icon",
			name: "icon",
			meta: {
				title: '自定义图标',
				show: 1
			},
			component: () => import(
				/* webpackChunkName: "icon" */
				"../views/Icon.vue")
		}, {
			path: "/groupInfo",
			name: "groupInfo",
			meta: {
				title: '组内信息',
				group: "group",
				permission: "review",
				icon : 'el-icon-info'
			},
			component: () => import(
				/* webpackChunkName: "group" */
				"../views/group/GroupInfo.vue")


		},  {
			path: "/reviewStudent",
			name: "reviewStudent",
			meta: {
				title: '审阅学生',
				group: "group",
				permission: "review",
				icon : 'el-icon-view'
			},
			component: () => import(
				/* webpackChunkName: "group" */
				"../views/group/ReviewStudent.vue")


		},{
			path: "/group",
			name: "group",
			meta: {
				title: '分组',
				permission: "sort",
				show: 0
			},
			component: () => import(
				/* webpackChunkName: "group" */
				"../views/group/Group.vue"),


		},
		{
			path: "/groupTeacher",
			name: "groupTeacher",
			meta: {
				title: '教师分组',
				group: "group",
				permission: "sort",
				icon : 'el-icon-lx-profile'
			},
			component: () => import(
				/* webpackChunkName: "group" */
				"../views/group/GroupTeacher.vue")
		}, {
			path: "/groupStudent",
			name: "groupStudent",
			meta: {
				title: '学生分组',
				group: "group",
				permission: "sort",
				icon : 'el-icon-lx-people'
			},
			component: () => import(
				/* webpackChunkName: "group" */
				"../views/group/GroupStudent.vue")
		}, {
			path: "/myGroup",
			name: "myGroup",
			meta: {
				title: '我的组',
				permission: "review",
				icon: 'el-icon-lx-sort'
			},
			component: () => import(
				/* webpackChunkName: "group" */
				"../views/table/MyGroup.vue")
		},
		{
			path: "/groupStuEdit",
			name: "groupStuEdit",
			meta: {
				title: '组内学生编辑',
				group: "group",
				permission: "sort",
				icon : 'el-icon-lx-edit'
			},
			component: () => import(
				/* webpackChunkName: "group" */
				"../views/group/GroupStuEdit.vue")
		},
		{
			path: "/sort",
			name: "sort",
			meta: {
				title: '编排学生答辩顺序',
				group: "group",
				permission: "sort",
				icon : 'el-icon-mouse'
			},
			component: () => import(
				/* webpackChunkName: "group" */
				"../views/group/Sort.vue")
		},
		{
			path: "/startDefence",
			name: "startDefence",
			meta: {
				title: '开始答辩',
				group: "group",
				permission: "sort",
				icon : 'el-icon-video-play'
			},
			component: () => import(
				/* webpackChunkName: "group" */
				"../views/group/StartDefence.vue")
		},
		{
			path: "/test",
			name: "test",
			meta: {
				title: '测试',
				show: 0
			},
			component: () => import(
				/* webpackChunkName: "group" */
				"../views/group/Test.vue")
		}, {
			path: '/403',
			name: '403',
			meta: {
				title: '没有权限'
			},
			component: () => import( /* webpackChunkName: "403" */
				'../views/403.vue')
		}, {
			path: '/uploadMaterials',
			name: 'uploadMaterials',
			meta: {
				title: '上传查看论文材料',
				permission: "upload",
				icon:'el-icon-folder-add'
			},
			component: () => import( /* webpackChunkName: "403" */
				'../views/from/UploadMaterials.vue')
		}, {
			path: '/defences',
			name: 'defences',
			meta: {
				title: '答辩',
				permission: "upload",
				icon:'el-icon-video-play'
			},
			component: () => import( /* webpackChunkName: "403" */
				'../views/from/Defences.vue')
		}
	]
}, {
	path: "/login",
	name: "Login",
	meta: {
		title: '登录',
		show: 0
	},
	component: () => import(
		/* webpackChunkName: "login" */
		"../views/Login.vue")
}, {
	path: "/groupInfos",
	name: "groupInfos",
	meta: {
		title: '组内信息',
		permission: "sort",
		show: 0
	},
	component: () => import(
		/* webpackChunkName: "group" */
		"../views/group/GroupInfo.vue"),
}, {
			path: '/404',
			name: '404',
			meta: {
				title: '找不到页面'
			},
			component: () => import( /* webpackChunkName: "404" */
				'../views/404.vue')
		}, {
			path: "/video",
			name: "video",
			meta: {
				title: '播放视频',
				show: 1
			},
			component: () => import(
				/* webpackChunkName: "group" */
				"../views/from/VideoTest.vue")
		}];

const router = createRouter({
	history: createWebHashHistory(process.env.BASE_URL),
	routes
});

router.beforeEach((to, from, next) => {
	document.title = `${to.meta.title} | vue-manage-system`;
	const token = localStorage.getItem('token');

	console.log(token)

	to.meta.show = 1;
	var result = 0;

	if (!token && to.path != '/login') {
		next('/login');
	} else if (to.meta.permission) {
		const user = localStorage.getItem('user');
		const users = JSON.parse(user);


		// 如果是管理员权限则可进入，这里只是简单的模拟管理员权限而已
		for (const myrole of users.roles) {
			const permissions = myrole.permissions;
			for (const a of permissions) {

				if (a.permission == to.meta.permission)
					result = 1;
			}
		}

		result === 1 ?
			next() :
			next('/403');



	} else {
		next();
	}
});

export default router;
