<template>
	<div>
		<div class="crumbs">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item>
					<i class="el-icon-lx-sort"></i> 分组
				</el-breadcrumb-item>
				<el-breadcrumb-item>分组</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="container">
			<h2 style="margin-bottom: 20px;">添加组</h2>
			<div class="form-box">
				<el-form ref="form" :model="group" :rules="rules" label-width="80px">

					<el-form-item label="毕业年级">
						<el-col :span="10">
							<el-date-picker type="year" placeholder="选择年级" v-model="group.gradeYear"
								style="width: 100%;">
							</el-date-picker>
						</el-col>
					</el-form-item>
					<el-form-item label="组长">
						<el-select v-model="value1" placeholder="请选择组长" filterable>
							<!-- 					<el-option v-for="item in options" :key="item.userId"
								:label="'账号：'+item.account+'&nbsp(姓名：'+item.userName+')'" :value="item.userId" /> -->
							<el-option v-for="item in leaders" :key="item.userId" :label="item.account+' '+item.userName"
								:value="item.userId">
								<span style="float: left">{{ item.account }}</span>
								<span style="float: right; color: #8492a6; font-size: 13px">{{ item.userName }}</span>
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="秘书">
						<el-select v-model="value2" placeholder="请选择秘书" filterable>
							<!-- 					<el-option v-for="item in options" :key="item.userId"
								:label="'账号：'+item.account+'&nbsp(姓名：'+item.userName+')'" :value="item.userId" /> -->
							<el-option v-for="item in secretarys" :key="item.userId" :label="item.account+' '+item.userName"
								:value="item.userId">
								<span style="float: left">{{ item.account }}</span>
								<span style="float: right; color: #8492a6; font-size: 13px">{{ item.userName }}</span>
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="答辩日期">
						<el-col :span="11">
							<el-date-picker type="date" placeholder="选择日期,预估时间" format="YYYY 年 MM 月 DD 日"
								v-model="group.groupDefenceTime" style="width: 100%;">
							</el-date-picker>
						</el-col>
						<!-- <el-col class="line" :span="2">-</el-col>
						<el-col :span="11">
							<el-time-picker placeholder="选择时间" v-model="form.date2" style="width: 100%;">
							</el-time-picker>
						</el-col> -->
					</el-form-item>
					<el-form-item label="答辩地点">
						<el-input v-model="group.groupDefencePlace" placeholder="预测答辩地点,如:8号楼312"></el-input>
					</el-form-item>
					<!-- <el-form-item label="选择器">
						<el-select v-model="form.region" placeholder="请选择">
							<el-option key="bbk" label="步步高" value="bbk"></el-option>
							<el-option key="xtc" label="小天才" value="xtc"></el-option>
							<el-option key="imoo" label="imoo" value="imoo"></el-option>
						</el-select>
					</el-form-item>
					
					<el-form-item label="城市级联">
						<el-cascader :options="options" v-model="form.options"></el-cascader>
					</el-form-item>
					<el-form-item label="选择开关">
						<el-switch v-model="form.delivery"></el-switch>
					</el-form-item>
					<el-form-item label="多选框">
						<el-checkbox-group v-model="form.type">
							<el-checkbox label="步步高" name="type"></el-checkbox>
							<el-checkbox label="小天才" name="type"></el-checkbox>
							<el-checkbox label="imoo" name="type"></el-checkbox>
						</el-checkbox-group>
					</el-form-item>
					<el-form-item label="单选框">
						<el-radio-group v-model="form.resource">
							<el-radio label="步步高"></el-radio>
							<el-radio label="小天才"></el-radio>
							<el-radio label="imoo"></el-radio>
						</el-radio-group>
					</el-form-item> -->

					<el-form-item>
						<el-button type="primary" @click="onSubmit">表单提交</el-button>
						<el-button>取消</el-button>
					</el-form-item>
				</el-form>
			</div>
		</div>
	</div>
</template>

<script>
	import {
		GetTeacherList,
		PostSomeoneList,
		PostManageGroup,
		GetTchList
	} from "../../api/index.js"
	export default {
		name: 'group',
		data() {
			return {
				optionsCopy: [],
				leaders: [],
				secretarys: [],
				group: {
					groupName: "组1"
				},
				form: {
					name: '',
					region: '',
					date1: '',
					date2: '',
					delivery: true,
					type: ['步步高'],
					resource: '小天才',
					desc: '',
					options: []
				},
				info: {
					type: 1,
					info: ""
				},
				value1: '',
				value2: '',
			};
		},
		created() {
			const _this = this;
			
			GetTchList(1, 1000).then(function(res) {
				_this.leaders = res.data.records;
				_this.secretarys = res.data.records;
				_this.optionsCopy = res.data.records;

			})

		},
		methods: {
			onSubmit() {
				const _this = this;
				
				this.group.groupLeaderId = this.value1;
				this.group.groupSecretaryId = this.value2;
				console.log(this.group)
				PostManageGroup(this.group).then(function(res) {
					if (res.data.code == 200)
						_this.$message.success('操作成功! 组名为' + res.data.data)
					else {
						_this.$message.error(res.data.msg)
					}

				})
				// this.$message.success('提交成功！');
			},
			dataFilter(val) {
				const _this = this;

				this.value1 = val;


				if (val) { //val存在

					PostSomeoneList({
						info: val
					}).then(function(res) {
						_this.leaders = res.data.data;

						console.log(res);
					})
					return true;


					// this.options = this.optionsCopy.filter((item) => {

					// 	if (item.userName.indexOf(val)) {
					// 		return true 
					// 	}
					// })
				} else { //val为空时，还原数组
					this.leaders = this.optionsCopy;
				}

			},
			dataFilter1(val) {
				const _this = this;

				this.value2 = val;


				if (val) { //val存在

					PostSomeoneList({
						info: val
					}).then(function(res) {
						_this.secretarys = res.data.data;

						console.log(res);
					})
					return true;


					// this.options = this.optionsCopy.filter((item) => {

					// 	if (item.userName.indexOf(val)) {
					// 		return true 
					// 	}
					// })
				} else { //val为空时，还原数组
					this.secretarys = this.optionsCopy;
				}

			},
		}
	};
</script>
