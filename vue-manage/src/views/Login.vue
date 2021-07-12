<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">后台管理系统</div>
            <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
                <el-form-item prop="account">
                    <el-input v-model="param.account" placeholder="account">
                        <template #prepend>
                            <el-button icon="el-icon-user"></el-button>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input
                        type="password"
                        placeholder="password"
                        v-model="param.password"
                        @keyup.enter="submitForm()"
                    >
                        <template #prepend>
                            <el-button icon="el-icon-lock"></el-button>
                        </template>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm('login')">登录</el-button>
                </div>
                <p class="login-tips">Tips : 用户名和密码随便填。</p>
            </el-form>
				<el-col :span="3">
							<div class="grid-content bg-purple-light">
								<el-button type="info" @click="isSvip('6')">svip角色</el-button>
							</div>
						</el-col>
        </div>
    </div>
</template>

<script>
import{PostLoginAPI,PostIsSvipUser}from "../api/index.js"

export default {
    data() {
        return {
            param:{
			
			},
            rules: {
                account: [
                    { required: true, message: "请输入用户名", trigger: "blur" }
                ],
                password: [
                    { required: true, message: "请输入密码", trigger: "blur" }
                ]
            }
        };
    },
    created() {
        this.$store.commit("clearTags");
    },
    methods: {
        submitForm(login) {
			const router=this.$router
			const _this=this;
			console.log(this.param)
            this.$refs.login.validate(valid => {
                if (valid) {
                    // this.$message.success("登录成功");
                    // localStorage.setItem("ms_username", this.param.username);
                    // this.$router.push("/");
					
					PostLoginAPI(this.param).then(function(res) {
												console.log(res)
												if (res.data.code === 200) {
												
													const token = res.data.data.token;
													const user=res.data.data.user;
													
													localStorage.setItem('token', token);
												
													// console.log(user.roles.permissions);
													localStorage.setItem("user",JSON.stringify(user));
													console.log(JSON.parse(JSON.stringify(user)))
													const myInfo = JSON.parse(JSON.stringify(user));
													localStorage.setItem("userId",myInfo.userId);
													localStorage.setItem("userName",myInfo.userName);
													
													//记录用户数据
										
												}
												router.push({ path:'/'});
											})
											
											
                } else {
                    this.$message.error("请输入账号和密码");
                    return false;
                }
            });
        },
		isSvip(e){
			console.log("isSvip" + e);
							PostIsSvipUser().then(function(res) {
								console.log(res)
								if (res.data.code === 500) {
									alert(res.data.msg)
								} else if (res.data.code === 200) {
									alert(res.data.msg)
								}
							})
		}
    }
};
</script>

<style scoped>
.login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
    background-image: url(../assets/img/login-bg.jpg);
    background-size: 100%;
}
.ms-title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: #fff;
    border-bottom: 1px solid #ddd;
}
.ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 350px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    background: rgba(255, 255, 255, 0.3);
    overflow: hidden;
}
.ms-content {
    padding: 30px 30px;
}
.login-btn {
    text-align: center;
}
.login-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
}
.login-tips {
    font-size: 12px;
    line-height: 30px;
    color: #fff;
}
</style>