
<template>
  <el-table :data="tbDatas" border height="620" style="width: 100%;">
    <el-table-column prop="id" label="id" width="100" />
    <slot name="pic" /> <!-- 查看图片 -->
    <el-table-column label="语音">
      <template slot-scope="scope">
        <el-button
          v-show="scope.row.voiceSrc"
          size="mini"
          type="text"
          @click="play(scope.$index, scope.row)"
        > 播放<i class="el-icon-video-play" />
          <audio :id="'v'+scope.row.id" :key="scope.row.id"><source :src="scope.row.voiceSrc" type="audio/mpeg"></audio>
        </el-button>
      </template>
    </el-table-column>
    <el-table-column prop="authorId" label="作者id" width="100" />
    <el-table-column prop="nickName" :formatter="decodeUnicode" label="昵称" width="110" />
    <!-- <el-table-column prop="likeCount" label="喜欢人数" width="80" />
        <el-table-column prop="missCount" label="错过人数" width="80" /> -->
    <slot name="likeCount" />
    <slot name="missCount" />
    <el-table-column :formatter="createTimeFmt" label="发布时间" width="100" />
    <slot name="opt" /> <!-- 操作 -->
  </el-table>
</template>

<script>
import { parseTime } from '@/utils'
export default {
  name: 'WorksTable',
  props: {
    tbDatas: {
      required: true,
      type: Array
    },
    startRow: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      drawer: false,
      audio: ''
    }
  },
  methods: {
    createTimeFmt(row, column) {
      return parseTime(row.createTime)
    },
    decodeUnicode(row) {
      const str = row.nickName.replace(/\\/g, '%')
      return unescape(str)
    },
    play(index, row) {
      // 将正在播放的暂停
      const id = 'v' + row.id
      if (this.audio && this.audio !== '' && this.audio !== document.getElementById(id)) {
        this.audio.pause()
        // this.audio.controls = false
        this.icon.className = 'iconfont icon-play'
      }
      this.audio = document.getElementById(id)
      this.icon = this.audio.previousElementSibling
      if (this.audio.paused) {
        this.audio.play()
        this.icon.className = 'el-icon-video-pause'
        // this.audio.controls = true
      } else {
        this.audio.pause()
        this.icon.className = 'el-icon-video-play'
        // this.audio.controls = false
      }
    }
  }
}
</script>

<style>
</style>
