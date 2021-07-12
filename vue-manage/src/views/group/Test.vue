<template>
	<div>
		<div class="crumbs">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item>
					<i class="el-icon-lx-sort"></i> 分组
				</el-breadcrumb-item>
				<el-breadcrumb-item>测试</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="container">
			<el-row :gutter="20">
				<el-col :span="8">
					选择人员

					<el-table :data="gridData" max-height="700" border style="width: 400px;">
						<el-table-column property="date" label="日期" width="150"></el-table-column>
						<el-table-column property="name" label="姓名" width="200"></el-table-column>
						<el-table-column property="address" label="地址"></el-table-column>
						<el-table-column fixed="right" label="操作" width="100">
							<template #default="scope">
								<el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
								<el-button type="text" size="small">编辑</el-button>
							</template>
						</el-table-column>
					</el-table>

				</el-col>
				<el-col :span="16">
					<el-collapse v-model="activeNames" @change="handleChange">
						<el-collapse-item title="上午" name="1" style="line-height: 70px; padding: 0 21px;cursor: pointer;">
							<el-table ref="singleTable" :data="tableData" highlight-current-row
								@current-change="handleCurrentChange" style="width: 100%" row-key="id"
								max-height="700px">
								<el-table-column type="index" width="150" label="当前答辩位置">
								</el-table-column>

								<el-table-column property="account" label="学号" width="220">
								</el-table-column>
								<el-table-column property="userName" label="姓名" width="220">
								</el-table-column>
								<el-table-column label="当前是否排序" width="220">
									<template #default="scope">
										<el-tag :type="'danger'" v-if="scope.row.sort==0"> 未排序</el-tag>

										<el-tag :type="'success'" v-else>已排序</el-tag>

									</template>
								</el-table-column>
								<el-table-column property="defenceTime" label="答辩时间" width="220">
								</el-table-column>
								<el-table-column property="defencePlace" label="答辩地址" width="220">
								</el-table-column>
								<el-table-column fixed="right" label="操作" width="100">
									<template #default="scope">
										<el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
										
									</template>
								</el-table-column>
								
							</el-table>
						</el-collapse-item>
						<el-collapse-item title="下午" name="2" style="line-height: 70px; padding: 0 21px;cursor: pointer;">
							<el-table ref="singleTable" :data="tableData" highlight-current-row
								@current-change="handleCurrentChange" style="width: 100%" row-key="id"
								max-height="700px">
								<el-table-column type="index" width="150" label="当前答辩位置">
								</el-table-column>

								<el-table-column property="account" label="学号" width="220">
								</el-table-column>
								<el-table-column property="userName" label="姓名" width="220">
								</el-table-column>
								<el-table-column label="当前是否排序" width="220">
									<template #default="scope">
										<el-tag :type="'danger'" v-if="scope.row.sort==0"> 未排序</el-tag>

										<el-tag :type="'success'" v-else>已排序</el-tag>

									</template>
								</el-table-column>
								<el-table-column property="defenceTime" label="答辩时间" width="220">
								</el-table-column>
								<el-table-column property="defencePlace" label="答辩地址" width="220">
								</el-table-column>
								<el-table-column fixed="right" label="操作" width="100">
									<template #default="scope">
										<el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
										
									</template>
								</el-table-column>
								
							</el-table>
						</el-collapse-item>
					</el-collapse>


				</el-col>
			</el-row>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'test',
		data() {
			return {
				table: false,
				dialog: false,
				loading: false,
				gridData: [{
					date: '2016-05-02',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-04',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-01',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-03',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-02',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-04',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-01',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-03',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-02',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-04',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-01',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-03',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-02',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-04',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-01',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-03',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-02',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-04',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-01',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-03',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-02',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-04',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-01',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-03',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-02',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-04',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-01',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-03',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-02',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-04',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-01',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-03',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-02',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-04',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-01',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-03',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-02',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-04',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-01',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-03',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-02',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-04',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-01',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-03',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-02',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-04',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-01',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-03',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-02',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-04',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-01',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-03',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-02',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-04',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-01',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-03',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-02',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-04',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-01',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}, {
					date: '2016-05-03',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1518 弄'
				}],
				form: {
					name: '',
					region: '',
					date1: '',
					date2: '',
					delivery: false,
					type: [],
					resource: '',
					desc: ''
				},
				formLabelWidth: '80px',
				timer: null,
			};
		},
		methods: {
			handleClose(done) {
				if (this.loading) {
					return;
				}
				this.$confirm('确定要提交表单吗？')
					.then(_ => {
						this.loading = true;
						this.timer = setTimeout(() => {
							done();
							// 动画关闭需要一定的时间
							setTimeout(() => {
								this.loading = false;
							}, 400);
						}, 2000);
					})
					.catch(_ => {});
			},
			cancelForm() {
				this.loading = false;
				this.dialog = false;
				clearTimeout(this.timer);
			}
		}
	};
</script>
