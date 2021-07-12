<template>
	<div>
		<div class="crumbs">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item>
					<i class="el-icon-lx-cascades"></i> 组内具体信息
				</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="container">
			<el-description title="组内信息" style="margin-left: 20px;">
				<el-description-item label="组id" :span-map="{md:12}">
					<template #content>
						{{groupInfo.groupId}}
					</template>
				</el-description-item>
				<el-description-item label="组名" :span="12">
					<template #content>
						{{groupInfo.groupName}}
					</template>
				</el-description-item>
				<el-description-item label="学院" :span-map="{md:12}">
					<template #content>
						{{groupInfo.institute}}
					</template>
				</el-description-item>
				<el-description-item label="结业年级" :span="12">
					<template #content>
						{{groupInfo.gradeYear}}
					</template>
				</el-description-item>
				<el-description-item label="组长" :span-map="{md:12}">
					<template #content>
						{{groupInfo.leaderName}}
					</template>
				</el-description-item>
				<el-description-item label="秘书" :span="12">
					<template #content>
						{{groupInfo.secretaryName}}
					</template>
				</el-description-item>

				<el-description-item label="预估答辩日期" :span-map="{md:12}">
					<template #content>
						{{groupInfo.groupDefenceTime}}
					</template>
				</el-description-item>
				<el-description-item label="预估答辩地点" :span="12">
					<template #content>
						{{groupInfo.groupDefencePlace}}
					</template>
				</el-description-item>


			</el-description>

		</div>
		<div class="container" style="margin-top: 20px;">
			<div style="margin-bottom: 20px;">
				<h2 style="margin-left: 20px;margin-bottom: 20px;">所在组教师列表</h2>
				<!-- <el-select v-model="query.address" placeholder="地址" class="handle-select mr10">
								<el-option key="1" label="广东省" value="广东省"></el-option>
								<el-option key="2" label="湖南省" value="湖南省"></el-option>
							</el-select> -->
				
				<div style="margin-left: 20px;">
					<div style="float:left" v-if="myId==groupInfo.groupSecretaryId">
					<el-button type="primary" icon="el-icon-edit" class="handle-del mr10" @click="addTch()">添加或修改人员
					</el-button>
					</div>
				<label  class="mr10" style="width: 200px;margin-left: 50px;">搜索：
				</label>
				<el-input v-model="searchTch" placeholder="相关信息搜索,如教工号,姓名等" class="handle-input mr10"></el-input>
			</div>
			</div>
			
			<el-table :data="tchList" max-height="700px" border class="table" ref="multipleTable">
				
				<el-table-column type="index" width="50">
				</el-table-column>
				<el-table-column prop="account" label="教工号"></el-table-column>
				<el-table-column prop="userName" label="姓名"></el-table-column>
				<el-table-column prop="instituteName" label="学院"></el-table-column>
				<el-table-column prop="email" label="邮箱"></el-table-column>
			
			</el-table>


		</div>
		<div class="container" style="margin-top: 20px;">
			<div style="margin-bottom: 20px;">
				<h2 style="margin-left: 20px;margin-bottom: 20px;">所在组学生列表</h2>
				<div style="float:right;" v-if="myId==groupInfo.groupSecretaryId">
					<el-button type="success" round style="margin-left: 20px;" class="el-icon-lx-sort"
						@click="goEdit()">
						点击进入学生信息编辑
					</el-button>
					<el-button type="success" round style="margin-left: 20px;" class="el-icon-lx-sort"
						@click="goSort()">
						点击进入学生答辩排序
					</el-button>
							
							
				</div>
				<div style="margin-left: 20px;">
					<div style="float:left" v-if="myId==groupInfo.groupSecretaryId">
					<el-button type="primary" icon="el-icon-edit" class="handle-del mr10" @click="addStu()">添加或修改人员
					</el-button>
					</div>
				<label  class="mr10" style="width: 200px;margin-left: 50px;">搜索：
				</label>
				
				<el-input v-model="searchStu" placeholder="相关信息搜索,如学号,姓名,班级等" class="handle-input mr10"></el-input>
				
			</div>
			</div>
			
			<el-table :data="stuList" max-height="700px" border class="table" ref="multipleTable">
				
				<el-table-column type="index" width="50">
				</el-table-column>
				<el-table-column prop="account" label="学号"></el-table-column>
				<el-table-column prop="userName" label="姓名"></el-table-column>
				<el-table-column prop="institute" label="学院"></el-table-column>
				<el-table-column prop="major" label="专业"></el-table-column>
				<el-table-column prop="userClass" label="班级"></el-table-column>
				<el-table-column prop="userInstructor" label="指导老师"></el-table-column>
				
			</el-table>
		
		
		</div>

	</div>
</template>

<script>
	import ElDescription from '../../components/ElDescription.vue'
	import ElDescriptionItem from '../../components/ElDescriptionItem.vue'
	import {
		GetGroupOne,
		GetTchInGroup,
		GetStuInG
	} from "../../api/index";
	export default {
		// 声明组件
		components: {
			ElDescription,
			ElDescriptionItem
		},
		data() {
			return {
				name: "sds",
				groupId: '',
				groupInfo: {},
				teacherList: [],
				studentList: [],
				searchStu:'',
				searchTch:'',
				tableData: [],
				a:1,
				myId: ''
			}
		},
		methods: {
			getInfo() {
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
							
							}
						}

					),
					GetTchInGroup({
						groupId: this.groupId
					}).then(function(res) {
							if (res.data.code == 200)
								{
								_this.teacherList = res.data.data;
								
							}
							
						}

					),
					GetStuInG({
						groupId: this.groupId
					}).then(function(res) {
							if (res.data.code == 200)
							{
								_this.studentList = res.data.data;
							}
							
						}

					)
					
			},
			addStu(){
				this.groupId = this.$route.query.id;
				this.$router.push({
					path:"/groupStudent",
					name:"groupStudent",
				
				})
			},
			addTch(){
				this.groupId = this.$route.query.id;
				this.$router.push({
					path:"/groupTeacher",
					name:"groupTeacher",
					
				})
			},
			goEdit(){
				this.$router.push({
					path:"/groupStuEdit",
					name:"groupStuEdit",
					
					})
			},
			goSort(){
				this.$router.push({
					path:"/sort",
					name:"sort",
					})
			}
		},
		created() {
			
			if(!sessionStorage.getItem("a")){
				sessionStorage.setItem("a",this.a);
				
				if(this.$route.query.id){
				let params = JSON.parse(this.$Base64.decode(this.$route.query.id));
				if(this.$route.query.secretary){
					let secretary=JSON.parse(this.$Base64.decode(this.$route.query.secretary));
					sessionStorage.setItem("secretaryId",secretary);
				}
				
				sessionStorage.setItem("groupId",params);
				this.groupId=params;
				
				
				
				}
				
				location.reload()
				}
				console.log(sessionStorage.getItem("secretaryId"))
				if(!sessionStorage.getItem("groupId")){
					this.$router.push({
						name:'404'
					})
					}
					else{
						this.groupId = sessionStorage.getItem("groupId");
						this.myId = localStorage.getItem("userId")
						this.getInfo();
						
						
					}
				
			
			
		},
		computed: {
			// 模糊搜索
			tchList() {
				const searchTch = this.searchTch
				if (searchTch) {
					return this.teacherList.filter(data => {
						return Object.keys(data).some(key => {
							return String(data[key]).toLowerCase().indexOf(searchTch) > -1
						})
					})
				}
				return this.teacherList
			},
			stuList() {
				const searchStu = this.searchStu
				
				if (searchStu) {
					return this.studentList.filter(data => {
						return Object.keys(data).some(key => {
							return String(data[key]).toLowerCase().indexOf(searchStu) > -1
						})
					})
				}
				return this.studentList
			}
		},
	}
</script>

<style>
	.handle-input {
		width: 300px;
		display: inline-block;
	}
	.mr10 {
		margin-right: 10px;
	}
</style>
