<template>
	<div>
		<div class="crumbs">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item>
					<i class="el-icon-lx-sort"></i> 我的组
				</el-breadcrumb-item>
				<el-breadcrumb-item>开始答辩</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="container">
			<h1 style="margin: 0px 0px 20px 20px;">开始答辩</h1>
			<div style="margin:0 auto;text-align: center;">
				<div>
					<el-button type="danger" class="el-icon-video-play" circle
						style="height: 100px;width: 100px;font-size: 20px;font-weight:bold;" @click="setAction(1)"> 开始
					</el-button>
					<el-button circle class="el-icon-video-pause"
						style="margin-left: 20px; height: 100px;width: 100px;font-size: 20px;font-weight:bold;"
						@click="setAction(2)"> 暂停
					</el-button>
				</div>
				<p style="margin-top: 20px;margin-bottom: 20px;">答辩一旦开始，不允许停止。当前学生完成答辩，请先点击完成，再点下一个！</p>
				<div>
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
						<p>上午场还剩 {{am}} 人，下午场还剩 {{pm}} 人</p>
					</div>
					<div v-else-if="groupInfo.action==2">
						<div v-if="sortInfo.timePeriod==1">
							现在为上午场
						</div>
						<div v-else>
							现在为下午场
						</div>
						<div style="font-size: 25px;color: #B94A48;">
							答辩暂停中，当前学生编号为{{sortInfo.sort}} ({{sortInfo.account}} {{sortInfo.userName}})
						</div>
					</div>
					<div v-else-if="groupInfo.action==3">
						<div style="font-size: 25px;color: #B94A48;">
							答辩已结束
						</div>

					</div>
					<div v-else>
						<div style="font-size: 25px;color: #B94A48;">
							答辩未结束,请点击开始按钮开始答辩
						</div>
					</div>
					<!-- 	<el-button  round type="primary"> 上一个</el-button>
			<el-button round > 返回</el-button>
			<el-button round type="primary"> 下一个</el-button> -->
					<div style="margin-top: 30px;" v-if="groupInfo.action==1">
						<el-button round type="success" @click="completeDefence(1)"> 完成</el-button>
						<el-button round type="primary" @click="completeDefence(2)"> 下一个开始</el-button>
						<el-button round @click="refreshDefence()"> 刷新</el-button>
						<el-button round type="primary" @click="setAction(3)"> 结束答辩</el-button>
					</div>
				</div>

			</div>

		</div>
		<!-- <div class="container" style="margin-top: 10px;">
		<h2 style="margin-left: 20px;margin-bottom: 20px;">学生信息</h2>
		<el-description  style="margin-left: 20px;">
			<el-description-item label="学号" :span-map="{md:6}">
				<template #content>
					{{groupInfo.groupId}}
				</template>
			</el-description-item>
			<el-description-item label="姓名" :span="6">
				<template #content>
					{{groupInfo.groupName}}
				</template>
			</el-description-item>
			<el-description-item label="论文题目" :span="6">
				<template #content>
					{{groupInfo.groupName}}
				</template>
			</el-description-item>
			<el-description-item label="当前状态" :span="6">
				<template #content>
					{{groupInfo.groupName}}
				</template>
			</el-description-item>
			<el-description-item label="论文" :span-map="{md:6}">
				<template #content>
					{{groupInfo.institute}}
				</template>
			</el-description-item>
			<el-description-item label="PPT" :span="6">
				<template #content>
					{{groupInfo.gradeYear}}
				</template>
			</el-description-item>
			<el-description-item label="演示视频" :span="6">
				<template #content>
					{{groupInfo.groupName}}
				</template>
			</el-description-item>
			<el-description-item label="其他材料" :span="6">
				<template #content>
					{{groupInfo.groupName}}
				</template>
			</el-description-item>
			
		
		
		</el-description>
	</div> -->
	</div>
</template>

<script>
	// import ElDescription from '../../components/ElDescription.vue'
	// import ElDescriptionItem from '../../components/ElDescriptionItem.vue'
	import {
		GetGroupOne,
		GetTchInGroup,
		GetStuInG,
		PutChanceDefence,
		PutSetStuState,
		GetRefreshDefence
	} from "../../api/index";
	import {
		ElMessage
	} from 'element-plus';
	export default {
		// 声明组件
		// components: {
		// 	ElDescription,
		// 	ElDescriptionItem
		// },
		data() {
			return {
				groupInfo: [],
				groupId: '',
				updateGroup: 0,
				updateStu: 0,
				dis: true,
				object: {
					groupId: '',
					state: '',
					groupStudentId: ''
				},
				sortInfo: {},
				am: '',
				pm: '',
				look:false
			}
		},
		created() {
			if (!sessionStorage.getItem("groupId")) {
				this.$router.push({
					name: '404'
				})
			} else {
				const _this = this;
				this.groupId = sessionStorage.getItem("groupId");
				this.getGroup()
				this.refreshDefence()
			}

		},
		methods: {
			getGroup() {
				const _this = this;
				GetGroupOne({
					groupId: this.groupId
				}).then(function(res) {
						if (res.data.code == 500)
							_this.$router.push({
								name: "myGroup"
							})
						else {
							_this.groupInfo = res.data.data;
							if (!_this.groupInfo.action)
								_this.dis = false
						}
					}

				);

			},
			setAction(state) {
				if(this.groupInfo.action==3)
				{
					alert("答辩已结束，请勿再操作")
					return ;
				}
				this.object.groupId = this.groupId;
				this.object.state = state;
				const _this = this;
				PutChanceDefence(this.object).then(function(res) {
						if (res.data.code == 500)
							ElMessage.warning({
								message: res.data.msg,
								type: 'warning'
							});
						else {
							_this.groupInfo = res.data.data;
							console.log(_this.groupInfo)

						}
					}

				);
				if (state == 1){
					GetRefreshDefence({
						groupId: this.groupId
					}).then(function(res) {
						console.log(res.data)
						_this.sortInfo = res.data.data.object
					})
					this.look=true
					}
					else
					this.look=false

			},
			completeDefence(state) {
				this.tips();
				const _this = this;
				this.object.groupId = this.groupId;
				this.object.state = state;
				this.object.groupStudentId = this.sortInfo.groupStudentId
				console.log(this.sortInfo)
				
					if (state == 2) {
						this.$confirm('确认要跳过此学生？', '确认信息', {
								distinguishCancelAndClose: true,
								confirmButtonText: '确定',
								cancelButtonText: '取消'
							})
							.then(() => {
							PutSetStuState(this.object).then(function(res) {
								console.log(res.data)
								_this.sortInfo = res.data.data.object
								_this.am = res.data.data.am;
								_this.pm = res.data.data.pm;
							
							})
							})
							.catch(action => {
							return ;
							});
					}
					else{
				PutSetStuState(this.object).then(function(res) {
					console.log(res.data)
					_this.sortInfo = res.data.data.object
					_this.am = res.data.data.am;
					_this.pm = res.data.data.pm;

				})
				}
			},
			refreshDefence() {
				const _this = this;
				GetRefreshDefence({
					groupId: this.groupId
				}).then(function(res) {
					console.log(res.data)
					_this.sortInfo = res.data.data.object
					_this.am = res.data.data.am;
					_this.pm = res.data.data.pm;

				})
			},
			tips(){
				if(this.sortInfo.timePeriod==1)
				{
					if(this.am==1){
					this.$confirm('上午场所剩人数为0，是否先暂停答辩？', '确认信息', {
							distinguishCancelAndClose: true,
							confirmButtonText: '确定',
							cancelButtonText: '取消'
						})
						.then(() => {
							this.setAction(2)
						})
						.catch(action => {
						return ;
						});
						}
				}
				else
				{
					if(this.pm==1){
						this.$confirm('下午场所剩人数为0，是否结束答辩？', '确认信息', {
								distinguishCancelAndClose: true,
								confirmButtonText: '确定',
								cancelButtonText: '取消'
							})
							.then(() => {
								this.setAction(3)
							})
							.catch(action => {
							return ;
							});
						}
				}
			}
		},
		watch: {
			updateGroup() {

			},
			updateStu() {

			}
		}
	}
</script>

<style>
</style>
