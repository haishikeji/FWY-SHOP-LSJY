<template>
  <div id="ws" class="app-container">
    <div class="filter-container">
      <el-button size="small" type="primary" icon="el-icon-plus" @click="wsSend">发送消息</el-button>
    </div>
  </div>
</template>

<script>
import { sendText } from '@/utils/websocket'
export default {
  data() {
    return {

    }
  },
  methods: {
    wsSend() {
      sendText('info', '这是来自Client的消息。')
    }
  }

}
</script>

<style>
</style>
