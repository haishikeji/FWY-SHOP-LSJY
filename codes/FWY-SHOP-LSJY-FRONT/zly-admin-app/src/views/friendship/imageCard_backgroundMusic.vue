<!-- 首页音乐 -->
<template>
  <div id="bgsoundList" class="app-container">

    <div class="filter-container">
      <el-button size="medium" type="primary" icon="el-icon-plus" @click="toAdd">新增首页音乐</el-button>
      <el-input v-model="listQuery.title" placeholder="名称" style="width: 200px; margin-left: 50px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.authorName" placeholder="作者" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.type" style="width: 140px" class="filter-item" placeholder="不区分类型" clearable @change="handleFilter">
        <el-option v-for="item in dictionaryList" :key="item.id" :label="item.name" :value="item.id" />
      </el-select>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
    </div>

    <el-table :data="bgSoundList" border height="660" style="width: 100%;">
      <el-table-column label="#" width="50">
        <template slot-scope="scope">
          {{ scope.$index + pageInfo.startRow + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column label="封面">
        <template slot-scope="scope">
          <img :src="scope.row.coverImg" style="width: 80px;">
        </template>
      </el-table-column>
      <el-table-column label="名称(播放)" width="380">
        <template slot-scope="scope">
          <el-button
            v-show="scope.row.musicUrl"
            size="mini"
            type="text"
            @click="play(scope.$index, scope.row)"
          > {{ scope.row.title }}<i class="el-icon-video-play" />
            <audio :id="'music'+scope.row.id" :key="scope.row.id"><source :src="scope.row.musicUrl" type="audio/mpeg"></audio>
          </el-button>
        </template>
      </el-table-column>
      <el-table-column prop="musicDuration" label="时长" />
      <el-table-column prop="authorName" label="作者" />
      <el-table-column label="类型" prop="type" :formatter="typeColFmt" />
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-popover placement="left" width="350">
            <el-alert title="确认" description="上下架会影响当前数据在手机APP上的使用." type="warning" show-icon />
            <div style="text-align: right; margin-top: 10px">
              <el-button v-if="scope.row.status===0" type="warning" @click="switchStatus(scope.row.id, 1)">确定下架</el-button>
              <el-button v-else type="success" @click="switchStatus(scope.row.id, 0)">确定上架</el-button>
            </div>
            <el-button slot="reference" size="mini" type="text">
              <label v-if="scope.row.status===0" style="color: #61C091">下架</label>
              <label v-else style="color: #FF4D51">上架</label>
            </el-button>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>

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

    <el-dialog :visible.sync="dialogVisible" title="新增">
      <el-form ref="bgForm" :rules="bgRules" :model="rowSound" label-width="80px" label-position="right" size="medium">
        <el-form-item label="名称" prop="title">
          <el-input v-model="rowSound.title" placeholder="请填写名称" />
        </el-form-item>
        <el-form-item label="作者" prop="authorName">
          <el-input v-model="rowSound.authorName" placeholder="请填写作者" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="rowSound.type" style="width:100%">
            <el-option v-for="item in dictionaryList" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="封面图片" prop="coverImg">
          <el-upload
            ref="uploadCoverImg"
            action=""
            :auto-upload="false"
            :limit="1"
            list-type="picture"
            :before-upload="imgBefore"
          >
            <el-button slot="trigger" size="small" type="primary">选取封面图片</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="音乐文件" prop="music">
          <el-upload
            ref="uploadMusic"
            action=""
            :auto-upload="false"
            :limit="1"
            :before-upload="musicBefore"
          >
            <el-button slot="trigger" size="small" type="primary">选取音乐文件</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="上下架" prop="status">
          <el-radio-group v-model="rowSound.status" size="medium">
            <el-radio border label="0">上架</el-radio>
            <el-radio border label="1">下架</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div style="text-align:right;">
        <el-button type="danger" @click="resetForm('bgForm')"> 取 消 </el-button>
        <el-button type="primary" @click="save('bgForm')">提交保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { deepClone } from '@/utils'
import { getHomeSoundList, addImageCard_backgroundMusic, setSoundStatus } from '@/api/bgsound'
import { scrollTo } from '@/utils/scrollTo'
const defaultBgSound = {
  id: 0,
  title: '',
  authorName: '',
  type: '',
  coverImg: 'coverImg',
  music: 'music',
  status: '0'
}
const bgRule = {
  title: [
    { required: true, message: '请填写名称', trigger: 'blur' },
    { min: 1, max: 25, message: '长度在 1 到 25 个字符', trigger: 'blur' }
  ],
  type: [
    { required: true, message: '请选择类别', trigger: 'blur' }
  ],
  authorName: [
    { required: true, message: '需要填写作者', trigger: 'blur' }
  ]
}
export default {
  data() {
    return {
      pageInfo: {},
      bgSoundList: [],
      dictionaryList: [],
      listQuery: {
        title: '',
        authorName: '',
        type: '',
        pageNum: 1,
        pageSize: 20
      },
      dialogVisible: false,
      rowSound: {
        status: '0'
      },
      bgRules: Object.assign({}, bgRule),
      params: undefined, // 新增的数据对象
      audio: ''
    }
  },
  created() {
    this.loadBgSoundList()
  },
  methods: {
    loadBgSoundList() {
      getHomeSoundList(this.listQuery).then((res) => {
        const ret = res.data.data
        this.dictionaryList = ret.dictionaryList
        this.bgSoundList = ret.pageInfo.datas
        this.pageInfo = ret.pageInfo
      })
    },
    typeColFmt(row, column) {
      let typeName = '---'
      for (const item of this.dictionaryList) {
        if (item.id === row.type) {
          typeName = item.name
          break
        }
      }
      return typeName
    },
    handleFilter() {
      if (this.audio) {
        this.audio.pause()
      }
      this.listQuery.pageNum = 1
      this.loadBgSoundList()
    },
    pageSizeChange(num) {
      this.listQuery.pageSize = num
      this.pagingChange(1)
    },
    pagingChange(num) {
      this.listQuery.pageNum = num
      this.loadBgSoundList()
      scrollTo(0, 600)
    },
    imgBefore(file) {
      // this.params.append(this.rowSound.coverImg, file, file.name)
      this.params.append('coverImg', file)
      return false
    },
    musicBefore(file) {
      // this.params.append(this.rowSound.music, file, file.name)
      this.params.append('music', file)
      return false
    },
    toAdd() {
      this.dialogVisible = true
      this.rowSound.status = '0'
    },
    toEdit(index, row) {
      this.rowSound = deepClone(row)
      this.dialogVisible = true
    },
    resetForm(formName) {
      this.rowSound = {}
      this.dialogVisible = false
      this.params = undefined
      this.$refs[formName].resetFields()
      this.$refs[formName].clearValidate()
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$notify.success({ title: '提交中……' })
          this.params = new FormData()
          this.params.append('title', this.rowSound.title)
          this.params.append('authorName', this.rowSound.authorName)
          this.params.append('type', this.rowSound.type)
          this.params.append('status', this.rowSound.status)
          this.$refs.uploadCoverImg.submit()
          this.$refs.uploadMusic.submit()
          // console.log("row=", this.rowSound);
          addImageCard_backgroundMusic(this.params).then((res) => {
            this.$notify.success({ title: '提交完成。' })
            const ret = res.data
            this.resetForm(formName)
            this.loadBgSoundList()
          })
        }
      })
    },
    switchStatus(id, status) {
      setSoundStatus({ id: id, status: status }).then((res) => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '上/下架操作成功' })
          this.loadBgSoundList()
        }
      })
    },
    play(index, row) {
      // 将正在播放的暂停
      const id = 'music' + row.id
      if (this.audio && this.audio !== '' && this.audio !== document.getElementById(id)) {
        this.audio.pause()
        this.audio.controls = false
        this.icon.className = 'iconfont icon-play'
      }
      this.audio = document.getElementById(id)
      this.icon = this.audio.previousElementSibling
      if (this.audio.paused) {
        this.audio.play()
        this.icon.className = 'el-icon-video-pause'
        this.audio.controls = true
      } else {
        this.audio.pause()
        this.icon.className = 'el-icon-video-play'
        this.audio.controls = false
      }
    }
  }
}
</script>

<style scoped="scoped">
</style>
