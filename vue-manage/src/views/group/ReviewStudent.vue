<template>
	<div>
	<div class="crumbs">
		<el-breadcrumb separator="/">
			<el-breadcrumb-item>
				<i class="el-icon-lx-sort"></i> 我的组
			</el-breadcrumb-item>
			<el-breadcrumb-item>审阅学生</el-breadcrumb-item>
		</el-breadcrumb>
	</div>
	<div class="container">
		<h2 style="margin-left: 20px;margin-bottom: 20px;">审阅论文材料</h2>
		<div style="margin-bottom: 20px;" v-if="myId==groupInfo.groupSecretaryId">
		<label  class="mr10" style="width: 200px;margin-left: 20px;">搜索：
		</label>
		<el-input v-model="search" placeholder="相关信息搜索,如学号,姓名等" style="width: 400px;"></el-input>
		<div style="float:right;">
			<el-button type="primary" round style="margin-left: 20px;" class="el-icon-lx-sort"
				@click="getRecordList()">
				我的
			</el-button>
			<el-button type="success" round style="margin-left: 20px;" class="el-icon-lx-sort"
				@click="getAll()">
				查看全部
			</el-button>
					
					
		</div>
		</div>
		<el-table :data="recordLists" border class="table" ref="multipleTable">
			<el-table-column prop="account" label="学号" sortable align="center"></el-table-column>
			<el-table-column prop="userName" label="姓名" align="center"></el-table-column>
			<el-table-column prop="thesisTitle" width="180" label="论文题目" align="center"></el-table-column>
			<el-table-column label="分配教师"  width="120" align="center" prop="teacherName" sortable>
			</el-table-column>
			<el-table-column label="论文审阅" width="180" align="center">
				<template #default="scope">
					<div v-if="scope.row.userThesis">
						<el-button type="success" plain size="medium" @click="preview(scope.row.userThesis)">预览
						</el-button>
						<el-button type="danger" size="medium" @click="download(scope.row.userThesis)">下载
						</el-button>
					</div>
					<div v-else>
						<el-tag type="danger">未上传</el-tag>
					</div>
				</template>
			</el-table-column>
			<el-table-column label="PPT审阅" width="180" align="center">
				<template #default="scope">
					<div v-if="scope.row.userPpt">
						<el-button type="success" plain size="medium" @click="preview(scope.row.userPpt)">预览
						</el-button>
						<el-button type="danger" size="medium" @click="download(scope.row.userPpt)">下载
						</el-button>
					</div>
					<div v-else>
						<el-tag type="danger">未上传</el-tag>
					</div>
				</template>
			</el-table-column>
			<el-table-column label="演示视频审阅" width="180" align="center">
				<template #default="scope">
					<div v-if="scope.row.userVideo">
						<el-button type="success" plain size="medium" @click="previewVideo(scope.row.userVideo)">预览
						</el-button>
						<el-button type="danger" size="medium" @click="download(scope.row.userVideo)">下载
						</el-button>
					</div>
					<div v-else>
						<el-tag type="danger">未上传</el-tag>
					</div>
				</template>
			</el-table-column>
			<el-table-column label="材料审阅" width="120" align="center">
				<template #default="scope">
					<div v-if="scope.row.userData">
						<el-button type="danger" size="medium" @click="download(scope.row.userData)">下载
						</el-button>
					</div>
					<div v-else>
						<el-tag type="danger">未上传</el-tag>
					</div>
				</template>
			</el-table-column>
			<el-table-column label="答辩记录审阅" width="180" align="center">
				<template #default="scope">
					<div v-if="scope.row.userRecord">
						<el-button type="success" plain size="medium" @click="preview(scope.row.userRecord)">预览
						</el-button>
						<el-button type="danger" size="medium" @click="download(scope.row.userRecord)">下载
						</el-button>
					</div>
					<div v-else>
						<el-tag type="danger">未上传</el-tag>
					</div>
				</template>
			</el-table-column>

		</el-table>
		<el-dialog title="预览" v-model="dialogVisible" width="70%" :before-close="handleClose">
			<p style="line-height: 20px;font-size: 15px;margin-bottom: 10px;">若界面未出来，请静待几秒</p>
			<iframe :src="url" type="application/x-google-chrome-pdf" width="100%" height="600px" />
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="dialogVisible = false">关闭</el-button>
					<el-button type="primary" @click="dialogVisible = false">确 定</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</div>	
</template>

<script>
	import {
		GetGroupOne,
		GetRecordList
	} from "../../api/index";
	import {
		ElMessage
	} from 'element-plus';
	export default {
		data() {
			return {
				groupId: '',
				tableData: [],
				value: '',
				multipleSelection: [],
				update: 0,
				noTch: 0,
				myId: '',
				recordList: [],
				dialogVisible: false,
				url:'',
				search:'',
				groupInfo:''
			}
		},
		methods: {
			getGroupInfo(){
				const _this = this;
				console.log(this.groupInfo)
				GetGroupOne({
						groupId: this.groupId
					}).then(function(res) {
							if (res.data.code == 200)
						
								_this.groupInfo = res.data.data;
							
						}
				
					)
			},
			getRecordList() {
				const _this = this;
				GetRecordList({
					groupId: this.groupId,
					teacherId: this.myId
				}).then(function(res) {
					_this.recordList = res.data;
				})
			},
			getAll(){
				const _this = this;
				GetRecordList({
					groupId: this.groupId,
					teacherId: 0
				}).then(function(res) {
					_this.recordList = res.data;
				})
			},
			handleClose(done) {
				this.$confirm('确认关闭？')
					.then(_ => {
						done();
					})
					.catch(_ => {});
			},
			preview(path){
				this.dialogVisible=true;
				this.url="http://10.90.61.250:8088/defence/demo/record/testPreview?path="+path
			},
			previewVideo(path){
				const h = this.$router.resolve({
						name: 'video',
						query: {
							path: this.$Base64.encode(path)
						}});
						window.open(h.href, '_blank')
				
				
			},
			download(path){
				window.open("http://10.90.61.250:8088/defence/demo/record/download?fileName="+path)
			}



		},
		created() {
			if (!sessionStorage.getItem("groupId")) {
				this.$router.push({
					name: '404'
				})
			} else {
				this.groupId = sessionStorage.getItem("groupId");
				this.myId = localStorage.getItem("userId")
				this.getRecordList();
				this.getGroupInfo();

			}
		},
		computed: {
			// 模糊搜索
			recordLists() {
				const search = this.search
				if (search) {
					return this.recordList.filter(data => {
						return Object.keys(data).some(key => {
							return String(data[key]).toLowerCase().indexOf(search) > -1
						})
					})
				}
				return this.recordList
			}
		}
	

	}
</script>

<style>
</style>
