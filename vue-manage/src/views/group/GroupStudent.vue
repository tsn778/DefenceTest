<template>
	<div>
		<div class="crumbs">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item>
					<i class="el-icon-lx-sort"></i> 分组
				</el-breadcrumb-item>
				<el-breadcrumb-item>学生分组</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="container">
			<div style="margin-bottom: 30px">
				<h2 style="display:inline;margin-bottom: 10px">当前所在组{{groupId}}</h2>
				<div style="float:right;">
					<el-button type="success" round style="margin-left: 20px;" class="el-icon-lx-sort"
						@click="goEdit()">
						点击进入学生信息编辑
					</el-button>
					<el-button type="success" round style="margin-left: 20px;" class="el-icon-lx-sort"
						@click="goSort()">
						点击进入学生答辩排序
					</el-button>
			
			
				</div>
				<p style="line-height:20px;color: #72767B;">
						请选择下面的学生，进行添加进组。</p>
			</div>
			<el-collapse v-model="activeNames" @change="handleChange">
				<el-collapse-item title="选择人员(点击隐藏)" name="1" line-height:50px>
					<div style="margin-bottom: 20px;">
						
						<label  class="mr10" style="width: 100px;margin-left: 20px;">搜索：
						</label>
						<el-input v-model="searchStu" placeholder="相关信息搜索,如学号,姓名,班级等" class="handle-input mr10"></el-input>
					</div>
					<el-table ref="multipleTable" :data="stuList" tooltip-effect="dark" stripe style="width: 100%"
						:header-cell-style="{background:'#409EFF',color:'#606266'}"
						@selection-change="handleSelectionChange" max-height="700px">
						<el-table-column type="selection" width="55">
						</el-table-column>
						<el-table-column prop="account" label="学号"></el-table-column>
						<el-table-column prop="userName" label="姓名"></el-table-column>
						<el-table-column prop="institute" label="学院"></el-table-column>
						<el-table-column prop="gradeYear" label="结业年级"></el-table-column>
						<el-table-column prop="major" label="专业"></el-table-column>
						<el-table-column prop="userClass" label="班级"></el-table-column>
						<el-table-column prop="userInstructor" label="指导老师"></el-table-column>
						<el-table-column label="状态" align="center">
							<template #default="scope">
								<el-tag v-if="scope.row.state==1" type="warning">已被选</el-tag>
								<el-tag v-if="scope.row.state==0" type="success">正常，可被选</el-tag>
							</template>
						</el-table-column>
						<el-table-column fixed="right" label="操作" width="200" align="center">
							<template #default="scope">
								<el-button size="mini" type="primary" icon="el-icon-edit-outline"
									@click="addOne(scope.$index, scope.row)">添加进组</el-button>
							</template>
						</el-table-column>
					</el-table>
					<el-row :gutter="20">
						<el-col :span="15">
							<div style="margin-top: 20px">
								<el-button type="primary" icon="el-icon-edit-outline" @click="add()">批量添加进组</el-button>
								<el-button @click="toggleSelection()">取消选择</el-button>
							</div>
						</el-col>

						<el-col :span="5">
							<div class="pagination">
								<el-pagination background layout="total, prev, pager, next" :page-size=pageSize
									:total=total @current-change="handlePageChange"></el-pagination>
							</div>
						</el-col>
					</el-row>


				</el-collapse-item>
			</el-collapse>
			<h1 style="color: #1890FF;line-height: 100px;">已选人员</h1>
			
				<div style="margin-bottom: 20px;">
					
					<label  class="mr10" style="width: 100px;margin-left: 20px;">搜索：
					</label>
					<el-input v-model="searchInGroup" placeholder="相关信息搜索,如学号,姓名,班级等" class="handle-input mr10"></el-input>
				</div>			

			<el-table ref="multipleTable1" :data="stuInGroup" tooltip-effect="dark" stripe style="width: 100%"
				:header-cell-style="{background:'#409EFF',color:'#606266'}" @selection-change="handleSelectionChange1" max-height="700px">
				<el-table-column type="selection" width="55">
				</el-table-column>
				<el-table-column type="index" width="100" label="编号">
				</el-table-column>
				<el-table-column prop="account" label="学号"></el-table-column>
				<el-table-column prop="userName" label="姓名"></el-table-column>
				<el-table-column prop="institute" label="学院"></el-table-column>
				<el-table-column prop="gradeYear" label="结业年级"></el-table-column>
				<el-table-column prop="major" label="专业"></el-table-column>
				<el-table-column prop="userClass" label="班级"></el-table-column>
				<el-table-column prop="userInstructor" label="指导老师"></el-table-column>
				<el-table-column fixed="right" label="操作" width="200" align="center">
					<template #default="scope">
						<el-button size="mini" type="danger" icon="el-icon-s-release"
							@click="deleteOne(scope.$index, scope.row)">移除人员</el-button>
					</template>
				</el-table-column>
			</el-table>
			<el-row :gutter="20">
				<el-col :span="15">
					<div style="margin-top: 20px">
						<el-button type="danger" icon="el-icon-s-release" @click="deleteall()">批量移除</el-button>
						<el-button @click="toggleSelection()">取消选择</el-button>
					</div>
				</el-col>

				<el-col :span="5">
					<div class="pagination">
						<el-pagination background layout="total, prev, pager, next" :page-size=pageSize :total=totalT
							@current-change="handlePageChange"></el-pagination>
					</div>
				</el-col>
			</el-row>
		</div>
	</div>


</template>

<script>
	import {
		ElMessage
	} from 'element-plus'
	import {
		GetStuInGroup,
		GetAllStudent,
		GetAllStu,
		GetStuInG,
		PostAddStudent,
		PostDeleteStudent
	} from "../../api/index";
	export default {
		name: 'groupStudent',
		data() {
			return {
				activeNames: ['1'],
				tableData: [
				],
				searchInGroup:'',
				searchStu:'',
				tchInGroup: [],
				multipleSelection: [],
				multipleSelection1: [],
				total: null,
				totalT: null,
				update:0,
				pageSize: 7,
				info: {
					groupId: 2,
					groupName: "组1",
					data: null,
					status: 1

				},
				allStudent:[],
				stuInGroups:[],
				groupId:'',
				stuToGList:{
					groupId:'',
					data:[]
				}

			};
		},
		created() {
			if(!sessionStorage.getItem("groupId")){
				this.$router.push({
					name:'404'
				})
				}
				else{
					
					this.groupId = sessionStorage.getItem("groupId");
					const _this = this;
					this.AllStu(this.groupId);
					this.AllStuInGroup(this.groupId);
							
					GetAllStu({groupId: this.groupId},1, 10000).then(function(res) {
						console.log(res);
						_this.tableData = res.data.records;
						_this.total = res.data.total;
					
					})
				}
			
			

		},
		methods: {
			AllStu(groupId){
				const _this = this;
				GetAllStu({groupId: groupId},1, this.pageSize).then(function(res) {
					console.log(res);
					_this.allStudent = res.data.records;
					_this.total = res.data.total;
				
				})
			},
			AllStuInGroup(groupId){
				const _this = this;
				GetStuInG({
					groupId: groupId
				}).then(function(res) {
					console.log(res)
						if (res.data.code == 200)
						{
							_this.stuInGroups = res.data.data;
						}
						
					}
				
				)
			},
			filterTag(value, row) {
				return row.state === value;
			},

			toggleSelection(rows) {
				if (rows) {
					rows.forEach(row => {
						this.$refs.multipleTable.toggleRowSelection(row);
					});
				} else {
					this.$refs.multipleTable.clearSelection();
				}
			},
			handleSelectionChange(val) {
				console.log(val)
				this.multipleSelection = val;
			},
			handleSelectionChange1(val) {
				console.log(val)
				this.multipleSelection1 = val;
			},
			handleChange(val) {
				console.log(val);
			},
			addOne(index, row) {
				console.log(row);
				var arr1 = new Array();
				arr1.push(row);
				this.stuToGList.groupId=this.groupId;
				this.stuToGList.data=arr1;
				const _this=this;
				PostAddStudent(this.stuToGList).then(function(res) {
					if(res.data.code==500)
					{
						ElMessage.warning({
							showClose: true,
							message: res.data.msg,
							type: 'warning'
						});
					}
					else{
					ElMessage.success({
						showClose: true,
						message: res.data.msg,
						type: 'success'
					});
					_this.update++;
					}


				})
				

			},
			deleteOne(index, row) {
				console.log(row);
				var arr1 = new Array();
				arr1.push(row);
				this.stuToGList.groupId=this.groupId;
				this.stuToGList.data=arr1;
				const _this=this;
				PostDeleteStudent(this.groupId,{groupId:1,
				data:this.stuToGList}).then(function(res) {
					console.log(res);
					if(res.data.code==500)
					{
						ElMessage.warning({
							showClose: true,
							message: res.data.msg,
							type: 'warning'
						});
					}
					else{
					ElMessage.success({
						showClose: true,
						message: res.data.msg,
						type: 'success'
					});
					_this.update++;
					}
				})
			},
			deleteall() {
				this.stuToGList.groupId=this.groupId;
				this.stuToGList.data = this.multipleSelection1;
				const _this=this;
				console.log(this.stuToGList)
				PostDeleteStudent(this.groupId,{groupId:1,
				data:this.stuToGList}).then(function(res) {
					console.log(res);
					if(res.data.code==500)
					{
						ElMessage.warning({
							showClose: true,
							message: res.data.msg,
							type: 'warning'
						});
					}
					else{
					ElMessage.success({
						showClose: true,
						message: res.data.msg,
						type: 'success'
					});
					_this.update++;
					}
				})
			},
			add() {
				this.stuToGList.groupId=this.groupId;
				this.stuToGList.data = this.multipleSelection;
				const _this=this;
				console.log(this.stuToGList)
				PostAddStudent(this.stuToGList).then(function(res) {
					console.log(res);
					if(res.data.code==500)
					{
						ElMessage.warning({
							showClose: true,
							message: res.data.msg,
							type: 'warning'
						});
					}
					else{
					ElMessage.success({
						showClose: true,
						message: res.data.msg,
						type: 'success'
					});
					_this.update++;
					}
				})
				

			},
			handlePageChange(val) {
				const _this = this;
				GetAllStu({groupId: this.groupId},val, this.pageSize).then(function(res) {
					console.log(res);
					_this.allStudent = res.data.records;
					_this.total = res.data.total;
				
				})
			},
			goEdit(){
				this.$router.push({
					path:"/groupStuEdit",
					name:"groupStuEdit",
					query:{
						id: this.groupId
					}})
			},
			goSort(){
				this.$router.push({
					path:"/sort",
					name:"sort",
					query:{
						id: this.groupId
					}})
			}
		},
		computed: {
			// 模糊搜索
			stuInGroup() {
				const searchStuInG = this.searchInGroup
				if (searchStuInG) {
					return this.stuInGroups.filter(data => {
						return Object.keys(data).some(key => {
							return String(data[key]).toLowerCase().indexOf(searchStuInG) > -1
						})
					})
				}
				return this.stuInGroups
			},
			stuList() {
				const searchStu = this.searchStu
				
				if (searchStu) {
					return this.tableData.filter(data => {
						return Object.keys(data).some(key => {
							return String(data[key]).toLowerCase().indexOf(searchStu) > -1
						})
					})
				}
				return this.allStudent
			}
		},
		watch:{
			update(){
				this.AllStu(this.groupId);
				this.AllStuInGroup(this.groupId);
			}
		}
		// methods: {
		// 	
		// 	}
		// }
	}
</script>

<style>
	.handle-input {
		width: 500px;
		display: inline-block;
	}
	.mr10 {
		margin-right: 10px;
	}
</style>
