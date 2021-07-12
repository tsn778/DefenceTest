<template>
	<div>
		<div class="crumbs">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item>
					<i class="el-icon-lx-sort"></i> 学生
				</el-breadcrumb-item>
				<el-breadcrumb-item>学生答辩</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="container">
			<div style="margin-bottom: 30px;">
				<h2 style="display:inline">学员答辩</h2>
			</div>
			<div v-if="myGroups!=null">
				如果当前存在多个组，请联系指导老师进行确认选择
				<template v-for="group in myGroups" :key="group.groupId">
					<el-button type="primary" style="margin-left: 20px" @click="refreshDefence(group.groupId)">选择组
						"{{group.groupName}}"</el-button>
				</template>
			</div>
			<div v-else>
				{{msg}}
			</div>
			<div v-if="visible" style="margin:0 auto;text-align: center;margin-top: 40px;">
				<div style="font-size: 25px;color: #B94A48;margin-bottom: 20px;">
					你的位置为 ：{{mySortInfo}}
				</div>
				<div v-if="groupInfo.action==1">
					<div v-if="sortInfo.timePeriod==1">
						现在为上午场
					</div>
					<div v-else>
						现在为下午场
					</div>
					<div style="font-size: 25px;color: #B94A48;">
						答辩已开始，当前学生编号为 {{sortInfo.sort}} ({{sortInfo.account}} {{sortInfo.userName}})
					</div>
					<div style="font-size: 30px;">
						<div v-if="now==2">
							你已被跳过，请等待老师做出操作
						</div>
						<div v-else-if="now==1">
							您已答辩结束
						</div>
						<div v-else>
							<!-- <div v-if="!front">
								您已答辩结束
							</div> -->
							<div v-if="front==0">
								您已开始答辩，请进入考场答辩
							</div>
							
							<div v-else>
								你位置离当前学生还差： {{front}} 名，请做好准备
							</div>
							
						</div>
					</div>
					<p>上午场还剩 {{am}} 人，下午场还剩 {{pm}} 人</p>
				</div>
				<div v-else-if="groupInfo.action==2">
					<div style="font-size: 25px;color: #B94A48;">
						答辩已暂停，当前学生编号为 {{sortInfo.sort}} ({{sortInfo.account}} {{sortInfo.userName}})

					</div>
				</div>
				<div v-else-if="groupInfo.action==0">
					<div style="font-size: 25px;color: #B94A48;">
						答辩未开始
					</div>
				</div>
				<div v-else>
					<div style="font-size: 25px;color: #B94A48;">
						答辩已结束

					</div>

				</div>
				<el-button round @click="refreshDefence(this.groupId)" style="margin-top: 20px;"> 刷新</el-button>
			</div>
		</div>
	</div>

</template>

<script>
	import {
		GetStuGroup,
		GetRecord,
		GetGroupOne,
		GetRefreshDefence,
		GetMySortInfo
	} from "../../api/index";
	export default {
		data() {
			return {

				myHeaders: {
					token: ''
				},
				userUpload: {
					userId: '',

				},

				myGroups: [],
				msg: '',
				group: '',

				update: 0,
				userId: '',
				visible: false,
				sortInfo: {},
				am: '',
				pm: '',
				groupInfo: [],
				groupId: '',
				front:'',
				now:'',
				mySortInfo:''
			};
		},
		created() {
			this.myHeaders.token = localStorage.getItem("token");
			const userId = localStorage.getItem('userId');
			this.userId = userId;
			this.getMyGroup();

		},

		methods: {
			getMyGroup() {
				const _this = this;
				GetStuGroup().then(function(res) {
					if (res.data.code == 200)
						_this.myGroups = res.data.data;
					else
						_this.msg = res.data.msg;

				})
			},
			refreshDefence(groupId) {
				console.log(groupId)
				this.groupId = groupId;
				this.visible = true;
				const _this = this;
				GetMySortInfo({
					groupId: this.groupId,
					userId:this.userId
				}).then(function(res){
					console.log(res.data.data)
					if(res.data.data.timePeriod==1)
						_this.mySortInfo="上午场,序号为"+res.data.data.sort+",答辩地点："+res.data.data.defencePlace+"，答辩时间预计为："+res.data.data.defenceTime
					else
					_this.mySortInfo="下午场,序号为"+res.data.data.sort+",答辩地点："+res.data.data.defencePlace+"，答辩时间预计为："+res.data.data.defenceTime
					
				})
				GetGroupOne({
					groupId: this.groupId
				}).then(function(res) {
						_this.groupInfo = res.data.data;
						console.log(res.data.data)


					}

				)
				GetRefreshDefence({
					groupId: this.groupId,
					userId:this.userId
				}).then(function(res) {
					console.log(res.data)
					_this.sortInfo = res.data.data.object
					_this.am = res.data.data.am;
					_this.pm = res.data.data.pm;
					_this.front = res.data.data.front;
					_this.now = res.data.data.now;

				})
			},


		},
		watch: {
			update() {
				this.selectGroup(this.group)
			}
		}
	}
</script>

<style>
</style>
