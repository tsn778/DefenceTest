<template>
	<div class="sidebar">
		<el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="collapse" background-color="#324157"
			text-color="#bfcbd9" active-text-color="#20a0ff" unique-opened router>
			<template v-for="item in items">
				<template v-if="item.meta.show==1">
					<template v-if="item.children">
						<el-submenu :index="item.name" :key="item.name">

							<template #title>
								<i :class="item.meta.icon"></i>
								<span>{{ item.meta.title }}</span>
							</template>
							<template v-for="subItem in item.children">
								<el-submenu v-if="subItem.children" :index="subItem.path" :key="subItem.path">
									<template #title>{{ subItem.title }}</template>
									<el-menu-item v-for="(threeItem, i) in subItem.subs" :key="i"
										:index="threeItem.index">
										{{ threeItem.title }}
									</el-menu-item>
								</el-submenu>
								<el-menu-item v-else :index="subItem.name" :key="subItem.name">{{ subItem.meta.title }}
								</el-menu-item>
							</template>
						</el-submenu>
					</template>
					<template v-else>
						<el-menu-item :index="item.name" :key="item.name">
							<!-- <el-menu-item :index="item.path" :key="item.path" v-if="item.meta.show"> -->
							<i :class="item.meta.icon"></i>
							<template #title>{{ item.meta.title }}</template>

						</el-menu-item>
					</template>
				</template>
			</template>
		</el-menu>
	</div>
</template>

<script>
	// import bus from "../common/bus";
	export default {
		data() {
			return {
				items: [],
				role: ["look", "read"],
				group: {
					path: "/asda",
					name: "sdsdsa",
					meta: {
						title: '我的组',
						show : 1
					},
					children: []
				},
				myId:'',
				secretaryId:'',
				normal:[]
				
			};
		},
		created() {
			this.myId = localStorage.getItem("userId")
			this.secretaryId=sessionStorage.getItem("secretaryId")
			console.log(this.myId)
			console.log(this.secretaryId)
			this.reloadSide();
			
		},
		methods:{
			reloadSide(){
				this.items = this.$router.options.routes[1].children;
				const user = localStorage.getItem('user');
				
				if (user!=null) {
				
					const users = JSON.parse(user);
				
					// const permissions = users.roles[0].permissions;
					var m = new Array();
					for (const a of this.items) {
				
						if (a.meta.permission)
						for (const myrole of users.roles) {
							const permissions = myrole.permissions;
							for (const b of permissions) {
								if (a.meta.permission == b.permission&&a.meta.show!=0) {
									a.meta.show = 1;
								}
				
				
							}
						}
							
						if (a.meta.show == 1 && a.meta.group == "group") {
							if(a.meta.permission=='review')
								this.normal.push(a);
							
							this.group.children.push(a);
						} else {
							m.push(a);
						}
						
					}
					
					// if (this.group.children.length!=0)
					// 	m.push(this.group);
					console.log(this.normal)
					
					if(sessionStorage.getItem("a")){
						if(this.myId==this.secretaryId)
						this.items=this.group.children
						else
						this.items=this.normal
						
						
					}
					else
					this.items = m;
				}
			}
		},
	
		computed: {
			onRoutes() {
				return this.$route.path.replace("/", "");
			},
			collapse() {
				return this.$store.state.collapse
			}
		}
	};
</script>

<style scoped>
	.sidebar {
		display: block;
		position: absolute;
		left: 0;
		top: 70px;
		bottom: 0;
		overflow-y: scroll;
	}

	.sidebar::-webkit-scrollbar {
		width: 0;
	}

	.sidebar-el-menu:not(.el-menu--collapse) {
		width: 250px;
	}

	.sidebar>ul {
		height: 100%;
	}
</style>
