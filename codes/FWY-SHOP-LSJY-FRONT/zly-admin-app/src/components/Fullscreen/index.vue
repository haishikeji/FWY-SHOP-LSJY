<template>
    <el-tooltip effect="dark" :content="isFullscreen ? '退出全屏' : '全屏'" placement="bottom">
      <img v-if="isFullscreen == false" src="@/assets/全屏.png" style="width:24px;height:24px;margin:13px;" @click="fullScreen">
      <img v-else src="@/assets/退出全屏.png" style="width:24px;height:24px;margin:13px;" @click="noFullScreen">
    </el-tooltip>
</template>

<script>
export default {
  name: 'Fullscreen',
  computed: {},
  data() {
    return {
      isFullscreen: false
    }
  },
  methods: {
    fullScreen() {
      this.isFullscreen = true
      // console.log(document.documentElement.RequestFullScreen)
      if (document.documentElement.RequestFullScreen) {
        document.documentElement.RequestFullScreen()
      }
      // 兼容火狐
      // console.log(document.documentElement.mozRequestFullScreen)
      if (document.documentElement.mozRequestFullScreen) {
        document.documentElement.mozRequestFullScreen()
      }
      // 兼容谷歌等可以webkitRequestFullScreen也可以webkitRequestFullscreen
      // console.log(document.documentElement.webkitRequestFullScreen)
      if (document.documentElement.webkitRequestFullScreen) {
        document.documentElement.webkitRequestFullScreen()
      }
      // 兼容IE,只能写msRequestFullscreen
      if (document.documentElement.msRequestFullscreen) {
        document.documentElement.msRequestFullscreen()
      }
    },
    noFullScreen() {
      this.isFullscreen = false
      if (document.exitFullScreen) {
        document.exitFullscreen()
      }
      // 兼容火狐
      // console.log(document.mozExitFullScreen)
      if (document.mozCancelFullScreen) {
        document.mozCancelFullScreen()
      }
      // 兼容谷歌等
      if (document.webkitExitFullscreen) {
        document.webkitExitFullscreen()
      }
      // 兼容IE
      if (document.msExitFullscreen) {
        document.msExitFullscreen()
      }
    },
  },
  mounted () {
    let that = this
    document.addEventListener('webkitfullscreenchange', function(e) {
      if (document.fullscreenElement) {
        // console.log('进入全屏')
        that.isFullscreen = true
      } else {
        // console.log('退出全屏')
        that.isFullscreen = false
      }
    })
    document.addEventListener('fullscreenchange', function() {
      if (document.fullscreenElement) {
        that.isFullscreen = true
      } else {
        that.isFullscreen = false
      }
    })
    document.addEventListener('mozfullscreenchange', function() {
      if (document.fullscreenElement) {
        that.isFullscreen = true
      } else {
        that.isFullscreen = false
      }
    })
    document.addEventListener('MSFullscreenChange', function() {
      if (document.fullscreenElement) {
        that.isFullscreen = true
      } else {
        that.isFullscreen = false
      }
    })
  }
}
</script>

<style scoped>

</style>
