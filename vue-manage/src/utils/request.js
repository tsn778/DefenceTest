import axios from 'axios';
import {
	ElMessage,
	ElMessageBox,
	ElLoading
} from 'element-plus'
import Router from '@/router/index.js'
const service = axios.create({
    // process.env.NODE_ENV === 'development' 来判断是否开发环境
    // easy-mock服务挂了，暂时不使用了
    // baseURL: 'https://www.easy-mock.com/mock/592501a391470c0ac1fab128',
	baseURL: 'http://localhost:8088/defence',
    timeout: 5000
});
const options = {
	lock: true,
	text: "处理中...",
	background: 'rgba(0,0,0,0.7)'
}
// service.interceptors.request.use(
//     config => {
//         return config;
//     },
//     error => {
//         console.log(error);
//         return Promise.reject();
//     }
// );

// service.interceptors.response.use(
//     response => {
//         if (response.status === 200) {
//             return response.data;
//         } else {
//             Promise.reject();
//         }
//     },
//     error => {
//         console.log(error);
//         return Promise.reject();
//     }
// );
/**
 * @param {Object} config
 * 请求拦截(发送时)
 */
service.interceptors.request.use(function(config) {
	//部分接口需要token (这个token是有shiro生成的  )
	const token = localStorage.getItem('token')
	if (token != null && token != '') {
		//config.headers.token = token
		config.headers = {
			token: token
		}
		console.log('request：' + config.headers.token)
	}
	return config
}, function(error) {
	ElMessage({
		showClose: true,
		message: '警告!请求拦截器错误',
		type: 'warning',
		center: true
	})
	console.log('请求拦截器错误')
	return Promise.reject(error)
})

/**
 * @param {Object} response
 * 响应拦截(接受时候)
 */
service.interceptors.response.use(function(response) {
	if (response.data.code === 403) {
		console.log('登录凭证token失效')
		//首先 移除token
		localStorage.removeItem("userId");
		localStorage.removeItem("userName");
		localStorage.removeItem("user");
		localStorage.removeItem("token");
		ElMessageBox({
			title: '信息提示',
			type: 'info',
			message: '登录状态失效请重新登录',
			callback: function(e) {
				let loading = ElLoading.service(options)
				setTimeout(() => {
					loading.close();
					Router.push({
						path: '/login'
					})
				}, 1000);
			}
		})

	}
	return response
}, function(error) {
	ElMessage({
		showClose: true,
		message: '响应拦截器错误',
		type: 'error',
		center: true
	})
	console.log('响应拦截器错误')
	return Promise.reject(error)
})
export default service;
