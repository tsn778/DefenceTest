<template>
	<div>
		<div class="crumbs">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item>
					<i class="el-icon-lx-cascades"></i> 分组管理
				</el-breadcrumb-item>
				<el-breadcrumb-item>组内人员信息编辑</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="container">
			<div style="margin-bottom: 30px">
				<h2 style="display:inline">组内人员信息编辑</h2>
				<div style="float:right;">
					<el-button type="success" round style="margin-left: 20px;" class="el-icon-lx-sort"
						@click="goSort()">
						点击进入学生答辩排序
					</el-button>


				</div>
			</div>

			<div style="margin-bottom: 20px;">
				<div style="font-size: 20px;width: 150px;color: #B94A48;font-weight: normal;display:inline">分配教师：</div>
				<el-select placeholder="请选择教师" style="width: 200px;" v-model="value">
					<el-option v-for="item in tchInGroups" :key="item.userId" :label="item.userName"
						:value="item.userId">
					</el-option>
				</el-select>
				<el-button type="primary" style="margin-left: 20px;" class="el-icon-folder-add" @click="distribute()">
					分配
				</el-button>
				<div style="float:right">
					<el-button style="margin-left: 20px;" class="el-icon-refresh" @click="refresh()">
						清空
					</el-button>
					<el-button type="primary" style="margin-left: 20px;" class="el-icon-folder-add"
						@click="distributeTime(1)">
						批量选择上午
					</el-button>
					<el-button type="info" style="margin-left: 20px;" class="el-icon-folder-add"
						@click="distributeTime(2)">
						批量选择下午
					</el-button>

				</div>
				<div style="line-height: 40px">
					<p style="color: #72767B;display:inline">
							请勾选下面的学生，在下拉框中选择教师分配。（注：新分配和重新分配时间段的学生不会进行排序，请进入排序界面重新排序）</p>
					<p style="color: #b52a2a;display:inline;float:right">
						提示：当前还有未分配教师学生 {{noTch}} 名
					</p>
					
				</div>
				
			</div>
			<el-table :data="stuInGroups" max-height="700px" border class="table" ref="multipleTable"
				@selection-change="handleSelectionChange">
				<el-table-column type="expand">
					<template #default="props">
						<el-form label-position="left" inline class="demo-table-expand">
							<el-form-item label="学号">
								<span>{{ props.row.account }}</span>
							</el-form-item>
							<el-form-item label="姓名">
								<span>{{ props.row.userName }}</span>
							</el-form-item>
							<el-form-item label="学院">
								<span>{{ props.row.institute }}</span>
							</el-form-item>
							<el-form-item label="结业年级">
								<span>{{ props.row.gradeYear }}</span>
							</el-form-item>
							<el-form-item label="专业">
								<span>{{ props.row.major }}</span>
							</el-form-item>
							<el-form-item label="班级">
								<span>{{ props.row.userClass }}</span>
							</el-form-item>
							<el-form-item label="论文题目">
								<span>{{ props.row.thesisTitle }}</span>
							</el-form-item>
							<el-form-item label="指导老师">
								<span>{{ props.row.userInstructor }}</span>
							</el-form-item>
							<el-form-item label="预计答辩时间">
								<span>{{ props.row.defenceTime }}</span>
							</el-form-item>
							<el-form-item label="预计答辩地点">
								<span>{{ props.row.defencePlace }}</span>
							</el-form-item>


						</el-form>
					</template>
				</el-table-column>
				<el-table-column type="selection" width="55">
				</el-table-column>
				<el-table-column type="index" width="50">
				</el-table-column>

				<el-table-column prop="account" label="学号" sortable></el-table-column>
				<el-table-column prop="userName" label="姓名"></el-table-column>
				<el-table-column prop="thesisTitle" label="论文题目"></el-table-column>
				<el-table-column label="分配教师" align="center" prop="teacherName" sortable>
					<template #default="scope">
						<el-tag v-if="!scope.row.teacherId" type="warning">未分配教师</el-tag>
						<el-tag v-else type="success">{{scope.row.teacherName}}</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="时间段" align="center" prop="timePeriod"
					:filters="[{ text: '未分配', value: null }, { text: '上午', value: 1 }, { text: '下午', value: 2 }]"
					:filter-method="filterTag">
					<template #default="scope">
						<el-tag v-if="!scope.row.timePeriod" type="warning">未分配时间段</el-tag>
						<el-tag v-else-if="scope.row.timePeriod==1" type="primary">上午</el-tag>
						<el-tag v-else-if="scope.row.timePeriod==2" type="info">下午</el-tag>
					</template>
				</el-table-column>
				<el-table-column label="排序" align="center">
					<template #default="scope">
						<el-tag v-if="!scope.row.sort" type="warning">未排序</el-tag>
						<el-tag v-else type="success">当前排序位：{{scope.row.sort}}</el-tag>

					</template>
				</el-table-column>
				<el-table-column label="分配时间段" width="200" align="center">
					<template #default="scope">
						<div v-if="!scope.row.timePeriod">
							<el-button type="primary" @click="handleEdit(1, scope.row)">上午
							</el-button>
							<el-button type="info" @click="handleEdit(2, scope.row)">下午
							</el-button>
						</div>
						<div v-else-if="scope.row.timePeriod==2">

							<el-button type="primary" @click="handleEdit(1, scope.row)">上午
							</el-button>
							<el-button type="info" disabled>下午
							</el-button>
						</div>
						<div v-else-if="scope.row.timePeriod==1">

							<el-button type="primary" disabled>上午
							</el-button>
							<el-button type="info" @click="handleEdit(2, scope.row)">下午
							</el-button>
						</div>
					</template>
				</el-table-column>

			</el-table>
		</div>
	</div>




</template>

<script>
	import {
		GetStuInG,
		GetTchInGroup,
		PutDistribute,
		PostDistributeTime
	} from "../../api/index";
	import {
		ElMessage
	} from 'element-plus';
	export default {
		data() {
			return {
				groupId: '',
				tableData: [],
				stuInGroups: [],
				tchInGroups: [],
				value: '',
				multipleSelection: [],
				update: 0,
				noTch:0

			}
		},
		methods: {
			
			getGroupInfo(groupId) {
				this.noTch=0;
				const _this = this;
				GetStuInG({
					groupId: groupId
				}).then(function(res) {
						if (res.data.code == 200) {
							_this.stuInGroups = res.data.data;
							_this.stuInGroups.forEach(info => {
								if (!info.teacherId)
									_this.noTch++;
							})

						}

					}

				)

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
			goGroupInfo(row) {

				this.$router.push({
					path: "/groupInfo",
					name: "groupInfo",
					query: {
						id: row.groupId
					}
				})
			},
			refresh() {
				this.$refs.multipleTable.clearSelection();
			},
			handleSelectionChange(val) {
				this.multipleSelection = val;
			},
			distribute() {
				const _this = this;
				if (this.value == "") {
					ElMessage.warning({
						message: '您还未选择教师，请选择！',
						type: 'warning'
					});
				} else {
					PutDistribute({
						teacherId: this.value,
						data: this.multipleSelection
					}).then(function(res) {
							if (res.data.code == 200) {

								ElMessage.success({
									message: res.data.msg,
									type: 'success'
								});
								_this.update++;
							}

						}

					)
				}

			},
			distributeTime(time) {
				const _this = this;
				PostDistributeTime({
					time: time,
					data: this.multipleSelection
				}).then(function(res) {
						if (res.data.code == 200) {

							ElMessage.success({
								message: res.data.msg,
								type: 'success'
							});
							_this.update++;
						}

					}

				)
			},
			filterTag(value, row) {

				return row.timePeriod === value;
			},
			handleEdit(time, row) {
				const _this = this;
				this.multipleSelection.push(row)
				PostDistributeTime({
					time: time,
					data: this.multipleSelection
				}).then(function(res) {
						if (res.data.code == 200) {

							ElMessage.success({
								message: res.data.msg,
								type: 'success'
							});
							_this.update++;
						}

					}

				)
			},
			goSort() {
				var a = 0;
				this.stuInGroups.forEach(info => {
					if (!info.timePeriod)
						a++;
				})
				if (a != 0) {
					this.$confirm('当前还有学生未分配时间段，不会出现在排序列表中', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {
						this.$router.push({
							path:"/sort",
							name:"sort",
							})
					}).catch(() => {
						this.$message({
							type: 'info',
							message: '已取消'
						});
					});
				}
				else{
					
						this.$router.push({
							path:"/sort",
							name:"sort",
							})
					
				}
			}


		},
		created() {
			if(!sessionStorage.getItem("groupId")){
				this.$router.push({
					name:'404'
				})
				}
				else{
			this.groupId = sessionStorage.getItem("groupId");
			this.getGroupInfo(this.groupId);
			this.AllTchInGroup(this.groupId);
			}
		},
		watch: {
			update() {

				this.getGroupInfo(this.groupId)
			}
		}

	}
</script>

<style>
	.demo-table-expand {
		font-size: 10;
	}

	.el-form-item {

		width: 200px;
		color: #99a9bf;
	}

	.demo-table-expand .el-form-item {
		margin-right: 0;
		margin-bottom: 0;
		width: 25%;
	}
</style>
