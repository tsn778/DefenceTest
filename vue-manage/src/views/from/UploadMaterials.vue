<template>
	<div>
		<div class="crumbs">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item>
					<i class="el-icon-lx-sort"></i> 学生
				</el-breadcrumb-item>
				<el-breadcrumb-item>学员论文材料上传与查看</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="container">
			<div style="margin-bottom: 30px;">
				<h2 style="display:inline">学员论文材料上传与查看</h2>
			</div>
			<div v-if="myGroups!=null">
				如果当前存在多个组，请联系指导老师进行确认选择
				<template v-for="group in myGroups" :key="group.groupId">
					<el-button type="primary" style="margin-left: 20px" @click="selectGroup(group)">选择组
						"{{group.groupName}}"</el-button>
				</template>
			</div>
			<div v-else>
				{{msg}}
			</div>
			<div v-if="group">
				<h3>当前所在组"{{group.groupName}}"</h3>
				<el-divider></el-divider>
				<el-row style="margin-bottom: 30px;">
					<el-col :span="4">
						<h3 style="margin-left: 20px;">论文</h3>
					</el-col>
					<el-col :span="8">当前状态：
						<el-tag type="success" v-if="record.userThesis">已上传，可预览下载</el-tag>
						<el-tag type="danger" v-else>未上传，请抓紧上传</el-tag>
					</el-col>
					<el-col :span="12">操作：

						<el-button type="success" style="margin-left: 10px;width: 150px;" @click="openDialog(1)">点击上传论文
						</el-button>
						<el-button type="primary" style="margin-left: 20px" @click="look(record.userThesis)">点击查看预览
						</el-button>
						<el-button type="danger" style="margin-left: 20px" @click="download(record.userThesis)">点击下载
						</el-button>
					</el-col>
				</el-row>

				<el-divider></el-divider>
				<el-row style="margin-bottom: 30px;">
					<el-col :span="4">
						<h3 style="margin-left: 20px;">PPT</h3>
					</el-col>
					<el-col :span="8">当前状态：
						<el-tag type="success" v-if="record.userPpt">已上传，可预览下载</el-tag>
						<el-tag type="danger" v-else>未上传，请抓紧上传</el-tag>
					</el-col>
					<el-col :span="12">操作：
						<el-button type="success" style="margin-left: 10px;width: 150px;" @click="openDialog(2)">点击上传ppt
						</el-button>
						<el-button type="primary" style="margin-left: 20px" @click="look(record.userPpt)">点击查看预览
						</el-button>
						<el-button type="danger" style="margin-left: 20px" @click="download(record.userPpt)">点击下载
						</el-button>
					</el-col>
				</el-row>
				<el-divider></el-divider>
				<el-row style="margin-bottom: 30px;">
					<el-col :span="4">
						<h3 style="margin-left: 20px;">演示视频</h3>
					</el-col>
					<el-col :span="8">当前状态：
						<el-tag type="success" v-if="record.userVideo">已上传，可预览下载</el-tag>
						<el-tag type="danger" v-else>未上传，请抓紧上传</el-tag>
					</el-col>

					<el-col :span="12">操作：<el-button type="success" style="margin-left: 10px;width: 150px;"
							@click="openDialog(3)">点击上传演示视频</el-button>
						<el-button type="primary" style="margin-left: 20px" @click="lookVideo(record.userVideo)">点击查看预览
						</el-button>
						<el-button type="danger" style="margin-left: 20px" @click="download(record.userVideo)">点击下载
						</el-button>
					</el-col>
				</el-row>
				<el-divider></el-divider>
				<el-row style="margin-bottom: 30px;">
					<el-col :span="4">
						<h3 style="margin-left: 20px;">相关材料</h3>
					</el-col>
					<el-col :span="8">当前状态：
						<el-tag type="success" v-if="record.userData">已上传，可下载</el-tag>
						<el-tag type="danger" v-else>未上传，请抓紧上传</el-tag>
					</el-col>

					<el-col :span="12">操作：<el-button type="success" style="margin-left: 10px;width: 150px;"
							@click="openDialog(4)">点击上传相关材料</el-button>
						<el-button type="danger" style="margin-left: 20px" @click="download(record.userData)">点击下载
						</el-button>
					</el-col>
				</el-row>
				<el-divider></el-divider>
				<el-row style="margin-bottom: 30px;">
					<el-col :span="4">
						<h3 style="margin-left: 20px;">答辩记录</h3>
					</el-col>
					<el-col :span="8">当前状态：
						<el-tag type="success" v-if="record.userRecord">已上传，可预览下载</el-tag>
						<el-tag type="danger" v-else>未上传，请抓紧上传</el-tag>
					</el-col>
					<el-col :span="12">操作：<el-button type="success" style="margin-left: 10px;width: 150px;"
							@click="openDialog(5)">点击上传答辩记录</el-button>
						<el-button type="primary" style="margin-left: 20px" @click="look(record.userRecord)">点击查看预览
						</el-button>
						<el-button type="danger" style="margin-left: 20px" @click="download(record.userRecord)">点击下载
						</el-button>
					</el-col>
				</el-row>


				<el-dialog title="提示" v-model="dialogVisible" width="30%" :before-close="handleClose">
					<div style="margin-left: 20px;margin-bottom: 30px;">
						<h2>上传{{title}}</h2>
					</div>
					<div style=" text-align:center;margin-bottom: 50px;">
						<el-upload class="upload-demo" drag ref="upload"
							action="http://localhost:8088/defence/demo/record/upload" :on-preview="handlePreview"
							:on-remove="handleRemove" :auto-upload="false" multiple :on-error="handleUploadError"
							:on-success="handleSuccess" :headers="myHeaders" :data="userUpload"
							:before-upload="beforeAvatarUpload">
							<template #trigger>
								<i class="el-icon-upload"></i>
								<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
							</template>
							<div>

								<el-button style="margin-top: 10px;" size="small" type="success" @click="submitUpload">
									上传到服务器
								</el-button>
							</div>

							<template #tip>
								<div class="el-upload__tip">
									只能上传 {{info}} 文件，且不超过 20mb
								</div>

							</template>
						</el-upload>
					</div>

				</el-dialog>

				<br />
				<!-- <el-upload class="upload-demo" drag ref="upload"
				action="http://10.90.61.250:8088/defence/demo/record/upload" :on-preview="handlePreview"
				:on-remove="handleRemove" :file-list="fileList" :auto-upload="false" multiple
				:on-error="handleUploadError" :before-remove="beforeRemove" :on-success="handleSuccess"
				:headers="myHeaders" :data="userUpload" :before-upload="beforeAvatarUpload">
				<template #trigger>
					<i class="el-icon-upload"></i>
					<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
				</template>
				<div>

					<el-button style="margin-left: 10px;margin-top: 10px;" size="small" type="success"
						@click="submitUpload">上传到服务器
					</el-button>
				</div>

				<template #tip>
					<div class="el-upload__tip">
						只能上传 jpg/png 文件，且不超过 500kb
					</div>
				</template>
			</el-upload> -->
				<!-- <h1>在线预览</h1>
				<a :href="['http://10.90.61.250:8088/defence/demo/record/testPreview?path='+record.userThesis]" target="_blank">跳转</a>
				<iframe :src="url" type="application/x-google-chrome-pdf" width="100%" height="600px" /> -->
			</div>
		</div>

	</div>
</template>

<script>
	import {
		PostUploadTest,
		GetStuGroup,
		GetRecord,
		GetToPdf
	} from "../../api/index";
	export default {
		data() {
			return {
				uploadUrl: "http://localhost:8088/defence/demo/record/upload",
				fileList: [],
				url: '',
				dialogVisible: false,
				myHeaders: {
					token: ''
				},
				userUpload: {
					userId: '',

				},
				type: 1,
				title: '',
				info: '',
				myGroups: [],
				msg: '',
				group: '',
				record: '',
				update: 0,
				userId: ''
			};
		},
		created() {
			this.myHeaders.token = localStorage.getItem("token");
			const userId = localStorage.getItem('userId');
			this.userId = userId;
			this.getMyGroup();

		},
		mounted() {
			this.url = 'http://localhost:8088/defence/demo/record/testPreview';
		},
		methods: {
			selectGroup(group) {
				const _this = this;
				this.group = group;
				console.log(this.group)
				GetRecord({
					userId: this.userId,
					groupId: group.groupId
				}).then(function(res) {
					_this.record = res.data.data
				})
			},
			getMyGroup() {
				const _this = this;
				GetStuGroup().then(function(res) {
					if (res.data.code == 200)
						_this.myGroups = res.data.data;
					else
						_this.msg = res.data.msg;

				})
			},

			openDialog(type) {
				switch (type) {
					case 1:
						this.title = "论文";
						this.type = 1;
						this.info = "doc/docx";
						break;
					case 2:
						this.title = "PPT";
						this.type = 2;
						this.info = "pptx";
						break;
					case 3:
						this.title = "演示视频";
						this.type = 3;
						this.info = "mp4/flv";
						break;
					case 4:
						this.title = "相关材料";
						this.type = 4;
						this.info = "zip/rar";
						break;
					case 5:
						this.title = "答辩记录";
						this.type = 5;
						this.info = "doc/docx";
						break;
					default:
						this.title = "出错";
						this.type = 0;
						break;
				}

				this.dialogVisible = true;
				this.userUpload = {
					userId: 17,
					groupId: this.group.groupId,
					type: type
				}

			},
			look(path) {
				if (!path)
					alert("请先上传后再预览");
				else
					window.open("http://localhost:8088/defence/demo/record/testPreview?path=" + path, "_blank");
			},
			lookVideo(path) {
				if (!path)
					alert("请先上传后再预览");
				else {
					const h = this.$router.resolve({
							name: 'video',
							query: {
								path: this.$Base64.encode(path)
							}});
							window.open(h.href, '_blank')

					}
				},
				download(path) {
						if (!path)
							alert("请先上传后再预览");
						else
							window.open("http://localhost:8088/defence/demo/record/download?fileName=" + path)
					},
					submitUpload() {
						this.$refs.upload.submit();
						console.log(this.$refs.upload)
					},
					handleRemove(file, fileList) {
						console.log(file, fileList);
					},
					handlePreview(file) {
						console.log(file);
					},
					handleUploadError(error, file) {
						console.log("文件上传出错：" + error)
						// this.$notify.error({
						//          title: 'error',
						//          message: '上传出错:' +  error,
						//          type: 'error',
						//          position: 'bottom-right'
						//        })
					},
					handleBeforeUpload(file) {
						this.uploadUrl = 'http://localhost:8088/defence/demo/record/upload'
						console.log("开始上传，上传的文件为：" + file)
						let formData = new FormData();
						formData.append("multipartFiles", file);
						PostUploadTest({
							data: formData,

						}).then(function(res) {
							console.log("文件上传返回：" + res)

						})


						// this.uploadUrl ='http://192.168.43.152:8089/file/upload'
					},
					handleSuccess(response, file, fileList) {
						this.$refs.upload.clearFiles();
						this.update++;

					},
					handleClose(done) {
						this.$confirm('确认关闭？')
							.then(_ => {
								done();
							})
							.catch(_ => {});
					},
					beforeAvatarUpload(file) {
						var filePath = file.name;
						//获取最后一个.的位置
						var index = filePath.lastIndexOf(".");
						//获取后缀
						var ext = filePath.substr(index + 1);
						var isFile = false;
						switch (this.type) {
							case 1 || 5:
								if (ext === "doc" || ext == "docx")
									isFile = true;
								break;
							case 2:
								if (ext === "pptx")
									isFile = true;
								break;
							case 3:
								if (ext === "mp4" || ext == "flv")
									isFile = true;
								break;
							case 4:
								if (ext === "zip" || ext == "rar")
									isFile = true;
								break;
							default:
								break;
						}

						const isLt20M = file.size / 1024 / 1024 < 20;

						if (!isFile) {
							this.$message.error('上传文件与要求格式不符!');
						}
						if (!isLt20M) {
							this.$message.error('上传文件大小不能超过 2MB!');
						}
						return isFile && isLt20M;

					}

			},
			watch: {
				update() {
					this.selectGroup(this.group)
				}
			}
		}
</script>

<style>
</style>
