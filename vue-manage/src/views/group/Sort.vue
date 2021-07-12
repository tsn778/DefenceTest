<template>
	<div>
		<div class="crumbs">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item>
					<i class="el-icon-lx-sort"></i> 分组
				</el-breadcrumb-item>
				<el-breadcrumb-item>学生答辩排序</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="container">
			<h2 style="margin-bottom: 20px">学生答辩排序</h2>
			<el-row :gutter="20">
				<el-col :span="10">
					<el-button type="primary"  @click="getSortList(1)">上午组排序</el-button>
					<el-button type="success"  @click="getSortList(2)">下午组排序</el-button>
				</el-col>
				<el-col :span="5"> <div style="margin-bottom: 20px;float:right ">
					<el-button type="primary" :disabled="dis"  @click="reset()">重置</el-button>
					<el-button type="success" :disabled="dis" plain @click="random()">随机排序</el-button>
					</div>
				</el-col>
				<el-col :span="5"> <div style="margin-bottom: 20px;float:right ">
					<el-button type="success" :disabled="dis" class="el-icon-check" @click="goSort()"> 确认排序</el-button>
					</div>
				</el-col>
			</el-row>
			<el-table ref="singleTable" :data="tableData" highlight-current-row @current-change="handleCurrentChange" 
				style="width: 100%" row-key="id" >
				<el-table-column type="index" width="150" label="当前设置位置" align="center">
					<template #default="scope">
						
						<el-tag :type="'primary'" effect="dark">{{scope.$index+1}}</el-tag>
					
					</template>
				</el-table-column>

				<el-table-column property="account" label="学号" align="center">
				</el-table-column>
				<el-table-column property="userName" label="姓名" align="center">
				</el-table-column>
				<el-table-column prop="thesisTitle" label="论文题目" align="center"></el-table-column>
				<el-table-column label="已排序位置" align="center">
					<template #default="scope">
						<el-tag :type="'danger'" v-if="!scope.row.sort"> 未排序</el-tag>

						<el-tag :type="'success'" v-else>{{scope.row.sort}}</el-tag>

					</template>
				</el-table-column>
				<el-table-column property="defenceTime" label="预计答辩时间" align="center">
					<template #default="scope">
						<div v-if="!scope.row.defenceTime">
							未排序
						</div>
						<div v-else>
							{{scope.row.defenceTime}}
						</div>
					</template>
				</el-table-column>
				<el-table-column property="defencePlace" label="预计答辩地址" align="center">
				</el-table-column>
			</el-table>
			<div style="margin-top: 20px">
				<el-button @click="getData()">得到全部</el-button>
				<el-button @click="paixu()">排序</el-button>

			</div>
		</div>
	</div>


</template>

<script>
	import {
		ElMessage
	} from 'element-plus'
	import {
		GetSortList,
		GetResetSort,
		GetRandomSort,
		PutSortOrder,
		GetStuSortByTime
	} from "../../api/index";
	import Sortable from 'sortablejs';
	export default {
		name: 'sort',
		data() {
			return {
				options: [],
				value: '',
				
				tableData: [],
				resetTime:'',
				currentRow: null,
				groupId:'',
				dis: true

			};
		},
		created() {
			if(!sessionStorage.getItem("groupId")){
				this.$router.push({
					name:'404'
				})
				}
				else{
			const _this = this;
			this.groupId = sessionStorage.getItem("groupId");
			// GetSortList({
			// 	groupId: 3
			// }).then(function(res) {
			// 	console.log(res);
			// 	_this.tableData = res.data.data;

			// })
			}
		},
		mounted() {
			this.setSort();
		},
		methods: {
			getSortList(time){
				const _this=this;
				GetStuSortByTime({
					groupId: this.groupId,
					timePeriod: time
				}).then(function(res) {
					console.log(res);
					_this.tableData=res.data;
					_this.resetTime=time;
					_this.dis=false
				})
			},
			setCurrent(row) {
				this.$refs.singleTable.setCurrentRow(row);
			},
			handleCurrentChange(val) {
				this.currentRow = val;
			},
			setSort() {
				const el = this.$refs.singleTable.$el.querySelectorAll('.el-table__body-wrapper > table > tbody')[0]
				this.sortable = Sortable.create(el, {
					ghostClass: 'sortable-ghost',
					setData: function(dataTransfer) {
						dataTransfer.setData('Text', '')
					},
					onEnd: evt => {
						const targetRow = this.tableData.splice(evt.oldIndex, 1)[0];
						this.tableData.splice(evt.newIndex, 0, targetRow);
					}
				});

			},
			getData() {
				alert("确定提交？")
				console.log(this.tableData);
			},
			reset() {
				// this.tableData=this.resetData
				const _this=this;
				GetStuSortByTime({
					groupId: this.groupId,
					timePeriod: this.resetTime
				}).then(function(res) {
					console.log(res);
					_this.tableData=res.data;
				})
			},
			random() {
				const _this = this;
				// GetRandomSort({
				// 	groupId: 3
				// }).then(function(res) {
				// 	console.log(res);
				// 	_this.tableData = res.data.data;

				// })
				alert("测试中！");
				
			},
			goSort(){
				alert("确定提交？")
				const _this = this;
				PutSortOrder({
					time: this.resetTime,
					groupId: this.groupId,
					data: this.tableData
				}).then(function(res) {
					console.log(res);
					_this.reset()
					
				})
			}
			// methods: {
			// 	
			// 	}
			// }
		},
	}
</script>

<style>
</style>
