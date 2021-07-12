<template>
	<section class="video-box" style="margin:0 auto;text-align: center;">
		<h1 style="margin-bottom: 20px;">视频播放</h1>
		<video-player class="video-player vjs-custom-skin" ref="videoPlayer" :playsinline="true"
			:options="playerOptions">
		</video-player>
	</section>
</template>
<script>
	import 'video.js/dist/video-js.css'
	import {
		videoPlayer
	} from 'vue-video-player/src'

	export default {
		components: {
			videoPlayer
		},
		
		data() {
			return {
				url:'',
				playerOptions: {
					//播放速度
					playbackRates: [0.5, 1.0, 1.5, 2.0],
					//如果true,浏览器准备好时开始回放。
					autoplay: false,
					// 默认情况下将会消除任何音频。
					muted: false,
					// 导致视频一结束就重新开始。
					loop: false,
					// 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
					preload: 'auto',
					language: 'zh-CN',
					// 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
					aspectRatio: '16:9',
					// 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
					fluid: true,
					sources: [{
						//类型
						type: "video/mp4",
						//url地址
						src: require('../../upload/184173117.mp4')
					}],
					//你的封面地址
					//允许覆盖Video.js无法播放媒体源时显示的默认信息。
					notSupportedMessage: '此视频暂无法播放，请稍后再试',
					controlBar: {
						timeDivider: true,
						durationDisplay: true,
						remainingTimeDisplay: false,
						//全屏按钮
						fullscreenToggle: true
					}
				}


			}
		},
		created() {
			if(!this.$route.query.path){
			this.$router.push({
				name:'404'
			})
			}
			
		},
		mounted() {
			
			
			if(!this.$route.query.path){
				let path = JSON.parse(this.$Base64.decode(this.$route.query.path));
			this.url="http://10.90.61.250:8088/defence/upload/"+path;
			
			var index = path.lastIndexOf(".");
			//获取后缀
			var ext = path.substr(index + 1);
			if(ext=='mp4'){
				var mp4={
					type: "video/mp4",
					//url地址
					src: require(''+this.url)
				}
				this.playerOptions.sources.push(mp4)
			}
			else if(ext=='flv')
			{
				var flv={
					type: "video/flv",
					//url地址
					src: require(''+this.url)
				}
				this.playerOptions.sources.push(flv)
			}
			}
			
		}
	}
</script>
<style scoped>
	.video-box {
		width: 1000px;
		padding: 20px;
	}
</style>
