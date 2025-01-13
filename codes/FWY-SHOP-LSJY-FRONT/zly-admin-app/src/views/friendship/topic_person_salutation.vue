<!-- 问候语录 -->
<template>
  <div id="salutation" class="app-container">

    <el-tabs v-model="activeName" @tab-click="handleClick">

      <el-tab-pane label="全部" name="first">
        <el-table :data="pageInfo" border height="auto" style="width: 100%;">
          <el-table-column prop="salutation" label="问候内容" />
          <el-table-column label="操作" fixed="right" width="200">
            <template slot-scope="scope">
              <el-button type="text" @click="verify(scope.row.id, scope.row.salutation)">
                <label style="color: #FF4D51">编辑</label>
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

    </el-tabs>

    <el-dialog :visible.sync="dialogVisible" title="编辑问候语录" width="600px">
      <el-form ref="topicForm" :model="rowSound" label-width="100px" label-position="right" size="medium">
        <el-form-item label="内容：" prop="salutation">
          <el-input v-model="rowSound.salutation" style="width:400px" />
        </el-form-item>
      </el-form>
      <div style="text-align:center;">
        <el-button type="info" plain @click="resetForm('topicForm')">取消</el-button>
        <el-button type="primary" @click="save('topicForm')">保存</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { salutationList, salutationModify } from '@/api/topics'

export default {
  data() {
    return {
      dialogVisible: false,
      activeName: 'first',
      pageInfo: [],
      rowSound: {
        id: null,
        salutation: null
      }
    }
  },
  mounted() {
    this.loadDatas()
  },
  methods: {
    handleClick(tab, event) {
      if (tab.name === 'first') {
        this.loadDatas()
      }
    },
    // 取消
    resetForm(formName) {
      this.dialogVisible = false
    },
    save() {
      if (this.rowSound.salutation === null || this.rowSound.salutation.match(/^\s*$/)) {
        this.$notify.error({ title: '内容不能为空!' })
      } else {
        salutationModify({ id: this.rowSound.id, salutation: this.rowSound.salutation }).then(res => {
          const ret = res.data
          if (ret.code === 200) {
            this.$notify.success({ title: '修改成功' })
            this.loadDatas()
            this.dialogVisible = false
          }
        })
      }
    },
    verify(id, salutation) {
      this.rowSound.id = id
      this.rowSound.salutation = salutation
      this.dialogVisible = true
    },
    // 获取数据
    loadDatas() {
      salutationList().then((res) => {
        this.pageInfo = res.data.data
      })
    }
  }
}
</script>

<style>
</style>
