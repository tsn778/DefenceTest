<template>
	<div>
		<div class="crumbs">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item>
					<i class="el-icon-lx-cascades"></i> 分组管理
				</el-breadcrumb-item>
				<el-breadcrumb-item>我的组</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="container">
			<el-table :data="tableData" max-height="700px" border class="table" ref="multipleTable">
							<el-table-column type="expand">
								<template #default="props">
									<el-form label-position="left" inline class="demo-table-expand">
										<el-form-item label="组编号">
											<span>{{ props.row.groupId }}</span>
										</el-form-item>
										<el-form-item label="组名">
											<span>{{ props.row.groupName }}</span>
										</el-form-item>
										<el-form-item label="学院">
											<span>{{ props.row.institute }}</span>
										</el-form-item>
										<el-form-item label="结业年级">
											<span>{{ props.row.gradeYear }}</span>
										</el-form-item>
										<el-form-item label="组长">
											<span>{{ props.row.leaderName }}</span>
										</el-form-item>
										<el-form-item label="秘书">
											<span>{{ props.row.secretaryName }}</span>
										</el-form-item>
										<el-form-item label="预计答辩日期">
											<span>{{ props.row.groupDefenceTime }}</span>
										</el-form-item>
										<el-form-item label="预计答辩地点">
											<span>{{ props.row.groupDefencePlace }}</span>
										</el-form-item>
									</el-form>
								</template>
							</el-table-column>
							
							<el-table-column type="index" width="50">
							</el-table-column>
			
							<el-table-column prop="groupId" label="组号"></el-table-column>
							<el-table-column prop="groupName" label="组名"></el-table-column>
							<el-table-column prop="gradeYear" label="结业年级"></el-table-column>
							<el-table-column label="操作1" width="200" align="center">
								<template #default="scope">
									<el-button type="success" plain round icon="el-icon-lx-attention" @click="goGroupInfo(scope.row)">查看具体信息
									</el-button>
								</template>
							</el-table-column>
						</el-table>
		</div>
	</div>




</template>

<script>
	import {
		GetMyGroup
	} from "../../api/index";
	export default {
		data() {
			return {
				tableData: []
			}
		},
		methods: {
			getMyGroup(){
				var userId = this.$route.query.id;
				const _this = this;
				GetMyGroup({
					teacherId:userId
				}).then(function(res) {
					console.log(res);
					_this.tableData = res.data;
					console.log(res.data)

				})
				
			},
			goGroupInfo(row){
			
				const news = this.$router.resolve({name: 'groupInfo', query: {id:this.$Base64.encode(row.groupId) ,
				secretary:this.$Base64.encode(row.groupSecretaryId)}})
				window.open(news.href,'_blank')
				
			}
			
		},
		created() {
			this.getMyGroup();
		}
	}
</script>

<style>
.demo-table-expand {
		font-size: 10;
	}

	.el-form-item{
		
		width: 200px;
		color: #99a9bf;
	}

	.demo-table-expand .el-form-item {
		margin-right: 0;
		margin-bottom: 0;
		width: 50%;
	}
</style>
