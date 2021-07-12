<template>
	<div>
		<div class="crumbs">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item>
					<i class="el-icon-lx-cascades"></i> 学生管理
				</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="container">
			<div class="handle-box">
				<el-button type="success" round icon="el-icon-circle-plus-outline" class="handle-del mr10"
					@click="add()">点击添加</el-button>
				<el-button type="primary" icon="el-icon-delete" class="handle-del mr10" @click="delAllSelection">批量删除
				</el-button>
				<!-- <el-select v-model="query.address" placeholder="地址" class="handle-select mr10">
					<el-option key="1" label="广东省" value="广东省"></el-option>
					<el-option key="2" label="湖南省" value="湖南省"></el-option>
				</el-select> -->
				<el-input v-model="search" placeholder="相关信息搜索,如学号,姓名,班级等" class="handle-input mr10"></el-input>
				<el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
			</div>
			<el-table :data="tableDatas" max-height="700px" border class="table" ref="multipleTable"
				header-cell-class-name="table-header" @selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55" align="center"></el-table-column>
				<el-table-column type="index" width="50">
				</el-table-column>

				<el-table-column prop="account" label="学号"></el-table-column>
				<el-table-column prop="userName" label="姓名"></el-table-column>
				<el-table-column prop="institute" label="学院"></el-table-column>
				<el-table-column prop="gradeYear" label="结业年级"></el-table-column>
				<el-table-column prop="major" label="专业"></el-table-column>
				<el-table-column prop="userClass" label="班级"></el-table-column>
				<el-table-column prop="userInstructor" label="指导老师"></el-table-column>
				<el-table-column prop="thesisTitle" label="论文题目"></el-table-column>

				<!--      <el-table-column label="账户余额">
                    <template #default="scope">￥{{ scope.row.money }}</template>
                </el-table-column>
                <el-table-column label="头像(查看大图)" align="center">
                    <template #default="scope">
                        <el-image
                            class="table-td-thumb"
                            :src="scope.row.thumb"
                            :preview-src-list="[scope.row.thumb]"
                        ></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="address" label="地址"></el-table-column>
 -->
				<!-- <el-table-column label="状态" align="center">
					<template #default="scope">
						<el-tag :type="
                                scope.row.state === '成功'
                                    ? 'success'
                                    : scope.row.state === '失败'
                                    ? 'danger'
                                    : ''
                            ">{{ scope.row.state }}</el-tag>
					</template>
				</el-table-column> -->
				<el-table-column label="操作" width="180" align="center">
					<template #default="scope">
						<el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑
						</el-button>
						<el-button type="text" icon="el-icon-delete" class="red"
							@click="handleDelete(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div class="pagination">
				<el-pagination background layout="total, prev, pager, next" :page-size=pageSize :total=total
					@current-change="handlePageChange"></el-pagination>
			</div>
		</div>

		<!-- 编辑弹出框 -->
		<el-dialog :title="title" v-model="editVisible" width="40%">

			<el-form ref="form" :model="student" label-width="70px">
				<el-input type="hidden" v-model="student.Id"></el-input>
				<el-input type="hidden" v-model="student.userId"></el-input>
				<el-form-item label="学号">
					<el-input v-model="student.account"></el-input>
				</el-form-item>
				<el-form-item label="姓名">
					<el-input v-model="student.userName"></el-input>
				</el-form-item>
				<el-form-item label="学院">
					<el-select placeholder="请选择学院" style="width: 200px;" v-model="student.instituteId">
						<el-option v-for="item in instituteList" :key="item.id" :label="item.name" :value="item.id">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="毕业年级">
					<el-col :span="10">
						<el-date-picker type="year" placeholder="选择年级" v-model="student.gradeYear" style="width: 100%;">
						</el-date-picker>
					</el-col>
				</el-form-item>
				<el-form-item label="专业">
					<el-input v-model="student.major"></el-input>
				</el-form-item>
				<el-form-item label="班级">
					<el-input v-model="student.userClass"></el-input>
				</el-form-item>
				<el-form-item label="指导老师">
					<el-input v-model="student.userInstructor"></el-input>
				</el-form-item>
				<el-form-item label="论文题目">
					<el-input v-model="student.thesisTitle"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="editInfo(edit)">提交</el-button>
					<el-button @click="editVisible = false">取消</el-button>
				</el-form-item>
			</el-form>

		</el-dialog>
	</div>
</template>

<script>
	import {
		fetchData,
		GetUserList,
		GetInstituteList,
		PostAddStu,
		PutUpdateStu
	} from "../../api/index";
	import {
		ElMessage
	} from 'element-plus';
	export default {
		name: "basetable",
		data() {
			return {
				total: null,
				pageIndex: 1,
				pageSize: 5,
				search: '',
				query: {


				},
				tableData: [{
					userId: "1",
					account: "2"
				}],
				AllData: [],
				multipleSelection: [],
				delList: [],
				editVisible: false,

				form: {},
				idx: -1,
				id: -1,
				student: {},
				instituteList: [],
				test: "2021",
				edit: 1, //2为修改；1为添加
				update: 0, //修改变量更新,
				title: '',
				value1: ''
			};
		},


		// created() {
		//     this.getData();
		// },
		methods: {

			// 获取 easy-mock 的模拟数据
			// getData() {
			//     fetchData(this.query).then(res => {
			//         console.log(res);
			//         this.tableData = res.list;
			//         this.pageTotal = res.pageTotal || 50;
			//     });
			// },
			// 触发搜索按钮

			handleSearch() {
				// this.$set(this.query, "pageIndex", 1);
				// this.getData();



			},
			// 删除操作
			handleDelete(row) {
				// 二次确认删除
				
			},
			// 多选操作
			handleSelectionChange(val) {
				this.multipleSelection = val;
			},
			delAllSelection() {
				console.log(this.multipleSelection)
				// const length = this.multipleSelection.length;
				// let str = "";
				// this.delList = this.delList.concat(this.multipleSelection);
				// for (let i = 0; i < length; i++) {
				// 	str += this.multipleSelection[i].name + " ";
				// }
				// this.$message.error(`删除了${str}`);
				// this.multipleSelection = [];
			},
			// 编辑操作
			handleEdit(index, row) {
				this.student = row;
				this.edit = 2;
				this.editVisible = true;
				this.title = "修改学生"


			},
			add() {
				this.student = {
					instituteId: '',
					gradeYear: ''
				};

				this.edit = 1;
				this.editVisible = true;
				this.title = "添加学生"
			},
			// 保存编辑
			saveEdit() {
				this.editVisible = false;
				this.$message.success(`修改第 ${this.idx + 1} 行成功`);
				this.$set(this.tableData, this.idx, this.form);
			},
			// 分页导航
			handlePageChange(val) {
				const _this = this;
				GetUserList({
					gradeYear: ''
				}, val, this.pageSize).then(function(res) {
					console.log(res);
					_this.tableData = res.data.records;
					_this.total = res.data.total;

				})
			},
			getAllData() {
				const _this = this;
				GetUserList({
					gradeYear: ''
				}, 1, 1000).then(function(res) {
					console.log(res);
					_this.AllData = res.data.records;


				})
			},
			editInfo(edit) {
				// console.log(this.student);
				// PostAddStudentInfo(this.student).then(function(res) {
				// 	console.log(res);

				// })
				
				const _this = this;
				if(edit==1){
				PostAddStu(this.student).then(function(res) {
					if (res.data.code == 500) {
						ElMessage.error(res.data.msg);
					}
					else{
					ElMessage.success({
						message: res.data.msg,
						type: 'success'
					});
					_this.update++;
					_this.editVisible = false
					}

				})
				}
				else{
					PutUpdateStu(this.student).then(function(res) {
						if (res.data.code == 500) {
							ElMessage.error(res.data.msg);
						}
						else{
						ElMessage.success({
							message: res.data.msg,
							type: 'success'
						});
						_this.update++;
						_this.editVisible = false
						}
					
					})
				}
			}
		},
		computed: {
			// 模糊搜索
			tableDatas() {

				const search = this.search
				if (search) {
					// filter() 方法创建一个新的数组，新数组中的元素是通过检查指定数组中符合条件的所有元素。
					// 注意： filter() 不会对空数组进行检测。
					// 注意： filter() 不会改变原始数组。
					return this.AllData.filter(data => {
						// some() 方法用于检测数组中的元素是否满足指定条件;
						// some() 方法会依次执行数组的每个元素：
						// 如果有一个元素满足条件，则表达式返回true , 剩余的元素不会再执行检测;
						// 如果没有满足条件的元素，则返回false。
						// 注意： some() 不会对空数组进行检测。
						// 注意： some() 不会改变原始数组。
						return Object.keys(data).some(key => {
							// indexOf() 返回某个指定的字符在某个字符串中首次出现的位置，如果没有找到就返回-1；
							// 该方法对大小写敏感！所以之前需要toLowerCase()方法将所有查询到内容变为小写。
							return String(data[key]).toLowerCase().indexOf(search) > -1
						})
					})
				}
				return this.tableData
			}
		},

		created() {

			const _this = this;
			GetUserList({
					gradeYear: ''
				}, 1, this.pageSize).then(function(res) {
					console.log(res);
					_this.tableData = res.data.records;
					_this.total = res.data.total;

				}),
				GetInstituteList().then(function(res) {
					console.log(res);
					_this.instituteList = res.data;

				})
			//     axios.get('http://localhost:8088/book/findAll/1/6').then(function (resp){

			//       _this.tableData=resp.data.content;
			//      _this.total=resp.data.totalElements
			//     })

		},
		mounted() {
			this.getAllData()
		},
		watch: {
			update() {
				this.getAllData()
			}
		}
	};
</script>

<style scoped>
	.handle-box {
		margin-bottom: 20px;
	}

	.handle-select {
		width: 120px;
	}

	.handle-input {
		width: 300px;
		display: inline-block;
	}

	.table {
		width: 100%;
		font-size: 14px;
	}

	.red {
		color: #ff0000;
	}

	.mr10 {
		margin-right: 10px;
	}

	.table-td-thumb {
		display: block;
		margin: auto;
		width: 40px;
		height: 40px;
	}
</style>
