<!-- 添加视频 -->
<template>
  <div id="addVideo" class="app-container">
    <div class="filter-container">
      <el-button size="medium" type="primary" icon="el-icon-plus" @click="toAdd">新增视频</el-button>
    </div>

    <el-drawer title="查看视频" :visible.sync="drawer" size="60%" direction="rtl">
      <div style="overflow: auto; position: fixed; max-height: 600px; padding-left: 10px;">
        <video id="checkVideo" :src="videoUrl" controls="controls" max-width="80%" height="600px" />
      </div>
    </el-drawer>

    <el-tabs v-model="activeName" @tab-click="handleClick">

      <el-tab-pane label="待审核" name="first">
        <el-table :data="pageInfo.datas" border stripe height="660" style="width: 100%;">
          <el-table-column label="头像" width="62">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 40px;">
            </template>
          </el-table-column>
          <el-table-column prop="nickName" label="昵称" width="120" />
          <el-table-column prop="content" label="分享文案" width="280" />
          <el-table-column label="视频">
            <template slot-scope="scope">
              <label class="checkbox">
                <img :src="scope.row.videoCoverUrl" alt="">
                <el-button size="mini" type="primary" @click="showPic(scope.row.videoUrl)">查看</el-button>
              </label>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="200">
            <template slot-scope="scope">
              <el-button type="success" @click="verify(scope.$index, scope.row.id, 5)">通过</el-button>
              <el-button type="warning" @click="verify(scope.$index, scope.row.id, 4)">下架</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="待推荐" name="second">
        <el-table :data="pageInfo.datas" border stripe height="660" style="width: 100%;">
          <el-table-column label="头像" width="62">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 40px;">
            </template>
          </el-table-column>
          <el-table-column prop="nickName" label="昵称" width="120" />
          <el-table-column prop="content" label="分享文案" width="280" />
          <el-table-column label="视频">
            <template slot-scope="scope">
              <label class="checkbox">
                <img :src="scope.row.videoCoverUrl" alt="">
                <el-button size="mini" type="primary" @click="showPic(scope.row.videoUrl)">查看</el-button>
              </label>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="92">
            <template slot-scope="scope">
              <el-button type="success" @click="verify(scope.$index, scope.row.id, 6)">推荐</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="已推荐" name="third">
        <el-table :data="pageInfo.datas" border stripe height="660" style="width: 100%;">
          <el-table-column label="头像" width="62">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 40px;">
            </template>
          </el-table-column>
          <el-table-column prop="nickName" label="昵称" width="120" />
          <el-table-column prop="content" label="分享文案" width="280" />
          <el-table-column label="视频">
            <template slot-scope="scope">
              <label class="checkbox">
                <img :src="scope.row.videoCoverUrl" alt="">
                <el-button size="mini" type="primary" @click="showPic(scope.row.videoUrl)">查看</el-button>
              </label>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="92">
            <template slot-scope="scope">
              <el-button type="warning" @click="verify(scope.$index, scope.row.id, 4)">下架</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="已下架" name="four">
        <el-table :data="pageInfo.datas" border stripe height="660" style="width: 100%;">
          <el-table-column label="头像" width="62">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 40px;">
            </template>
          </el-table-column>
          <el-table-column prop="nickName" label="昵称" width="120" />
          <el-table-column prop="content" label="分享文案" width="280" />
          <el-table-column label="视频">
            <template slot-scope="scope">
              <label class="checkbox">
                <img :src="scope.row.videoCoverUrl" alt="">
                <el-button size="mini" type="primary" @click="showPic(scope.row.videoUrl)">查看</el-button>
              </label>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="92">
            <template slot-scope="scope">
              <el-button type="primary" @click="verify(scope.$index, scope.row.id, 1)">上架</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

    </el-tabs>

    <el-dialog :visible.sync="dialogVisible" title="添加视频" width="600px">
      <el-form ref="topicForm" :model="rowSound" label-width="180px" label-position="right" size="medium">
        <el-form-item label="分享文案：" prop="content">
          <el-input v-model="rowSound.content" style="width:250px" type="textarea" :autosize="{ minRows: 5, maxRows: 10}" maxlength="255" show-word-limit />
        </el-form-item>
        <el-form-item label="添加视频：" prop="video">
          <el-upload ref="uploadVideo" action="" :auto-upload="false" :limit="1" list-type="picture" :before-upload="videoBefore">
            <el-button slot="trigger" size="small" type="primary">选择视频</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="关联用户：">
          <i class="el-icon-circle-plus-outline" @click="chooseUser" />
        </el-form-item>
        <div style="padding-left:180px;">
          <p v-for="(item,index) in userCheck" :key="index">{{ index+1 }}、{{ item.nickName }}</p>
        </div>
      </el-form>
      <div style="text-align:center;">
        <el-button type="info" plain @click="resetForm('topicForm')">取消</el-button>
        <el-button type="primary" @click="save('topicForm')">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="userVisible" title="选择关联用户" width="400px">
      <div class="music-box">
        <div v-for="item in userList" :key="item.id" class="music_list">
          <div class="music_pic">
            <img :src="item.headImg" alt="" style="width:40px;height:40px;margin: auto;">
          </div>
          <div class="music_name">
            <span>{{ item.nickName }}</span>
            <span>{{ item.age }}/{{ item.height }}/{{ item.constellation }}</span>
          </div>
          <div class="music_make">
            <el-checkbox v-model="item.isSelected" style="margin:auto;" />
          </div>
        </div>
      </div>
      <div style="text-align:center;margin-top:10px;">
        <el-button type="primary" @click="userOk()">确定</el-button>
      </div>
    </el-dialog>

    <div class="pagination">
      <el-pagination
        :current-page.sync="listQuery.pageNum"
        :page-size="listQuery.pageSize"
        :total="pageInfo.total"
        :page-sizes="[10, 20, 50, 80, 100, 200, 300]"
        layout="total, prev, pager, next, sizes, jumper"
        @size-change="pageSizeChange"
        @current-change="pagingChange"
      />
    </div>

  </div>
</template>

<script>
import { videoList, videoVerify, videoAdd, videoListUser } from '@/api/user'
import { deepClone, parseTime } from '@/utils'
import { scrollTo } from '@/utils/scrollTo'

export default {
  data() {
    return {
      activeName: 'first',
      listQuery: {
        pageNum: 1,
        pageSize: 10,
        status: 1
      },
      pageInfo: {},
      drawer: false,
      videoUrl: null,
      dialogVisible: false,
      userVisible: false,
      userList: [], // 所有的用户列表
      userCheck: [], // 选中的用户列表
      rowSound: {
        content: '',
        video: null,
        userId: ''
      },
      params: undefined // 新增的数据对象
    }
  },
  watch: {
  },
  mounted() {
    this.loadDatas()
  },
  methods: {
    toAdd() {
      this.dialogVisible = true
    },
    // 取消
    resetForm(formName) {
      this.dialogVisible = false
      this.$refs[formName].resetFields()
      this.userCheck = []
    },
    // 保存视频
    save(formName) {
      this.params = new FormData()
      this.$refs.uploadVideo.submit()
      this.params.append('content', this.rowSound.content)
      this.params.append('userId', this.rowSound.userId)
      if (this.params.get('video')) {
        videoAdd(this.params).then((res) => {
          this.$notify.success({ title: '提交完成。' })
          this.resetForm(formName)
          this.loadDatas()
        })
      } else {
        this.$notify.error({ title: '视频不能为空' })
      }
    },
    // 上传视频前
    videoBefore(file) {
      this.params.append('video', file)
      return false
    },
    // 选择关联用户
    chooseUser() {
      this.userVisible = true
      videoListUser().then((res) => {
        if (res.data.code === 200) {
          const list = res.data.data
          list.forEach((item, index) => {
            this.userList.push(Object.assign({}, item, { 'isSelected': false }))
          })
        }
      })
    },
    // 确定用户
    userOk() {
      let list = this.userList.filter((item) => item.isSelected === true)
      if (list.length === 1) {
        this.userCheck = list
        this.rowSound.userId = this.userCheck[0].userId
        this.userVisible = false
        list = null
      } else {
        this.$notify.error({ title: '请选择一位用户' })
      }
    },
    showPic(videoUrl) {
      this.videoUrl = videoUrl
      this.drawer = true
    },
    handleFilter() {
      this.loadDatas()
    },
    handleClick(tab, event) {
      this.listQuery.pageNum = 1
      if (tab.name === 'first') {
        this.listQuery.status = 1
        this.loadDatas()
      } else if (tab.name === 'second') {
        this.listQuery.status = 5
        this.loadDatas()
      } else if (tab.name === 'third') {
        this.listQuery.status = 6
        this.loadDatas()
      } else if (tab.name === 'four') {
        this.listQuery.status = 4
        this.loadDatas()
      }
    },
    verify(index, id, status) {
      videoVerify({ id: id, status: status }).then(res => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '操作成功' })
          this.pageInfo.datas.splice(index, 1)
        }
      })
    },
    // 获取数据
    loadDatas() {
      videoList(this.listQuery).then((res) => {
        if (res.data.code === 200) {
          this.pageInfo = res.data.data
        }
      })
    },
    pageSizeChange(num) {
      this.listQuery.pageSize = num
      this.pagingChange(1)
    },
    pagingChange(num) {
      this.listQuery.pageNum = num
      this.loadDatas()
      scrollTo(0, 600)
    }
  }
}
</script>

<style scoped>
.checkbox{
    width: 200px;
    display: flex;
    flex-direction: column;
}
.checkbox video {
    width: 200px;
    height: 120px;
}
.checkbox button {
    display: flex;
    margin: auto;
}
.checkbox img{
    width: auto;
    height: 120px;
    margin: auto;
    margin-bottom:10px;
}
.music-box{
    width: 100%;
    max-height: 400px;
    overflow: auto;
}
.music_list{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    border: 1px solid #dddddd;
    border-bottom:0;
}
.music_list:last-child{
    border: 1px solid #dddddd;
}
.music_pic{
    width: 60px;
    height: 60px;
    display: flex;
}
.music_name{
    display: flex;
    flex-direction: column;
    width: 200px;
    height: 50px;
    margin: 5px 0;
    justify-content: space-around;
}
.music_name>span:nth-child(1){
    font-weight: 700;
    color: #000;
}
.music_make{
    display: flex;
    width: 60px;
}
</style>
