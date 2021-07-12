<template>
	<div>
		<div class="crumbs">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item>
					<i class="el-icon-lx-sort"></i> 分组
				</el-breadcrumb-item>
				<el-breadcrumb-item>教师分组</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="container">
			<h2>你当前所在的组为1组</h2>
			<p style="line-height: 50px;">
				若你为管理员或者多组的秘书，点击切换</p>
			<el-collapse v-model="activeNames" @change="handleChange">
				<el-collapse-item title="选择人员(点击隐藏)" name="1" line-height:50px>
					<div style="margin-bottom: 20px;">

						<label class="mr10" style="width: 100px;margin-left: 20px;">搜索：
						</label>
						<el-input v-model="searchTch" placeholder="相关信息搜索,如学号,姓名,班级等" class="handle-input mr10">
						</el-input>
					</div>
					<el-table ref="multipleTable" :data="tchList" tooltip-effect="dark" stripe style="width: 100%"
						:header-cell-style="{background:'#409EFF',color:'#606266'}"
						@selection-change="handleSelectionChange">
						<el-table-column type="selection" width="55">
						</el-table-column>
						<el-table-column type="index" width="100" label="编号">
						</el-table-column>
						<el-table-column prop="account" label="教工号" width="200">
						</el-table-column>
						<el-table-column prop="userName" label="姓名" width="200">
						</el-table-column>
						<el-table-column prop="state" label="类型" width="200"
							:filters="[{ text: '老师', value: 1 }, { text: '学生', value: 0 }]" :filter-method="filterTag">
						</el-table-column>
						<el-table-column prop="email" label="邮箱" show-overflow-tooltip>
						</el-table-column>
						<el-table-column fixed="right" label="操作" width="200" align="center">
							<template #default="scope">
								<el-button size="mini" type="primary" icon="el-icon-edit-outline"
									@click="handleEdit(scope.$index, scope.row)">添加进组</el-button>
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

				<label class="mr10" style="width: 100px;margin-left: 20px;">搜索：
				</label>
				<el-input v-model="searchInGroup" placeholder="相关信息搜索,如学号,姓名,班级等" class="handle-input mr10"></el-input>
			</div>
			<el-table ref="multipleTable1" :data="TchInGroup" tooltip-effect="dark" stripe style="width: 100%"
				:header-cell-style="{background:'#409EFF',color:'#606266'}" @selection-change="handleSelectionChange1">
				<el-table-column type="selection" width="55">
				</el-table-column>
				<el-table-column type="index" width="100" label="编号">
				</el-table-column>
				<el-table-column prop="account" label="教工号" width="200">
				</el-table-column>
				<el-table-column prop="userName" label="姓名" width="200">
				</el-table-column>
				<el-table-column prop="email" label="邮箱" show-overflow-tooltip>
				</el-table-column>
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
			</el-row>
		</div>
	</div>


</template>

<script>
	import {
		ElMessage
	} from 'element-plus'
	import {
		GetTeacher,
		PostAddTeacher,
		GetTeacherInGroup,
		GetTchAllNoG,
		GetAllTch,
		GetTchInGroup,
		DeleteTeacher
	} from "../../api/index";
	export default {
		name: 'groupTeacher',
		data() {
			return {
				activeNames: ['1'],
				tableData: [

				],
				allTeacher: [],
				tchInGroups: [],
				multipleSelection: [],
				multipleSelection1: [],
				total: null,
				totalT: null,
				pageSize: 7,
				info: {
					groupId: 2,
					groupName: "组1",
					data: null,
					status: 1

				},
				searchTch: "",
				searchInGroup: "",
				update: 0


			};
		},
		created() {
			if (!sessionStorage.getItem("groupId")) {
				this.$router.push({
					name: '404'
				})
			} else {
				this.groupId = sessionStorage.getItem("groupId");
				const _this = this;
				this.AllTch(this.groupId);
				this.info.groupId = sessionStorage.getItem("groupId");
				this.AllTchInGroup(this.groupId);
				GetAllTch({
					groupId: this.groupId
				}, 1, 10000).then(function(res) {
					console.log(res);
					_this.tableData = res.data.records;
					_this.total = res.data.total;

				})
			}
			// for (var user in _this.tableData) {
			// 				user.already == 0
			// 				for (var tch in _this.tchInGroup) {
			// 					if (user.userId == tch.userId)
			// 						user.already == 1

			// 				}
			// 			}
			// 			console.log(_this.tableData);

			//     axios.get('http://localhost:8088/book/findAll/1/6').then(function (resp){

			//       _this.tableData=resp.data.content;
			//      _this.total=resp.data.totalElements
			//     })

		},
		methods: {
			AllTch(groupId) {
				const _this = this;
				GetAllTch({
					groupId: groupId
				}, 1, this.pageSize).then(function(res) {
					console.log(res);
					_this.allTeacher = res.data.records;
					_this.total = res.data.total;

				})
			},
			AllTchInGroup(groupId) {
				const _this = this;
				GetTchInGroup({
					groupId: groupId
				}).then(function(res) {
						if (res.data.code == 200) {
							_this.tchInGroups = res.data.data;

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
				
				this.multipleSelection1 = val;
			},
			handleChange(val) {
				console.log(val);
			},
			handleEdit(index, row) {
				console.log(row);
				var arr1 = new Array();
				arr1.push(row);
				this.info.data = arr1;
				const _this = this;
				PostAddTeacher(this.info).then(function(res) {
					if (res.data.code == 500) {
						ElMessage.warning({
							showClose: true,
							message: res.data.msg,
							type: 'warning'
						});
					} else {
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
				
				this.info.data = arr1;
				const _this = this;
				DeleteTeacher({
					
					data: this.info
				}).then(function(res) {
					console.log(res);
					if (res.data.code == 500) {
						ElMessage.warning({
							showClose: true,
							message: res.data.msg,
							type: 'warning'
						});
					} else {
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
				this.info.data = this.multipleSelection1;
				console.log(this.info)
				const _this=this;
				DeleteTeacher({
					
					data: this.info
				}).then(function(res) {
					console.log(res);
					if (res.data.code == 500) {
						ElMessage.warning({
							showClose: true,
							message: res.data.msg,
							type: 'warning'
						});
					} else {
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
				const _this = this;
				console.log(this.multipleSelection)
				this.info.data = this.multipleSelection;
				PostAddTeacher(this.info).then(function(res) {
					if (res.data.code == 500) {
						ElMessage.warning({
							showClose: true,
							message: res.data.msg,
							type: 'warning'
						});
					} else {
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
				GetAllTch({
					groupId: this.groupId
				}, val, this.pageSize).then(function(res) {
					console.log(res);
					_this.allTeacher = res.data.records;
					_this.total = res.data.total;

				})
			}
		},
		computed: {
			// 模糊搜索
			TchInGroup() {
				const searchTchInG = this.searchInGroup
				if (searchTchInG) {
					return this.tchInGroups.filter(data => {
						return Object.keys(data).some(key => {
							return String(data[key]).toLowerCase().indexOf(searchTchInG) > -1
						})
					})
				}
				return this.tchInGroups
			},
			tchList() {
				const searchTch = this.searchTch

				if (searchTch) {
					return this.tableData.filter(data => {
						return Object.keys(data).some(key => {
							return String(data[key]).toLowerCase().indexOf(searchTch) > -1
						})
					})
				}
				return this.allTeacher
			}
		},
		watch: {
			update() {
				this.AllTch(this.groupId);
				this.AllTchInGroup(this.groupId);
			}
		}
		// methods: {
		// 	
		// 	}
		// }
	}
</script>

<style>
</style>
