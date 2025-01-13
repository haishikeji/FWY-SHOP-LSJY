<!-- 技能认证 -->
<template>
  <div id="skill_certification" class="app-container">

    <div class="filter-container">
      <el-input v-model="listQuery.id" placeholder="输入需要搜索id" style="width: 200px;" class="filter-item" type="text" oninput="value=value.replace(/[^\d]/g,'')" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.nickName" placeholder="输入需要搜索昵称" style="width: 200px;" class="filter-item" type="text" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.phone" placeholder="输入需要搜索手机号" style="width: 200px;" class="filter-item" type="text" maxlength="15" minlength="7" oninput="value=value.replace(/[^\d]/g,'')" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
    </div>

    <el-tabs v-model="activeName" @tab-click="handleClick">

      <el-tab-pane label="待审核" name="first">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="id" width="100" />
          <el-table-column prop="nickName" :formatter="decodeUnicodeN" label="昵称" width="110" />
          <el-table-column label="头像" width="82">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 60px;">
            </template>
          </el-table-column>
          <!-- <el-table-column label="实名照" width="120">
            <template slot-scope="scope">
              <img :src="scope.row.userRealPicture" @click="showPic(scope.row.userRealPicture)" style="width: 80px;">
            </template>
          </el-table-column> -->
          <el-table-column label="性别" width="50">
            <template slot-scope="scope">
              <label v-if="scope.row.gender===0">男</label>
              <label v-else-if="scope.row.gender===1">女</label>
              <label v-else />
            </template>
          </el-table-column>
          <el-table-column prop="certificationGameName" label="认证技能" width="120" />
          <el-table-column label="技能展示图" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.userGameScreenShot" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showPic(scope.row.userGameScreenShot)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="本人展示" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.userPicture" style="width: 100px;">
              <!-- <el-button size="mini" type="primary" style="margin: 2px 10px 0 10px;" @click="showUserImage(scope.row.userId, 1)">
                <label v-if="scope.row.userPicture">审核形象</label>
                <label v-else>查看形象</label>
              </el-button> -->
              <el-button v-if="scope.row.userPicture" size="mini" type="success" style="margin: 2px 10px 0 10px;" @click="showUserImage(scope.row.userId, 1)">
                <label>审核形象</label>
              </el-button>
              <el-button v-else size="mini" type="primary" style="margin: 2px 10px 0 10px;" @click="showUserImage(scope.row.userId, 1)">
                <label>查看形象</label>
              </el-button>
            </template>
          </el-table-column>
          <el-table-column label="语音介绍" >
            <template slot-scope="scope">
              <el-button
                v-show="scope.row.voiceIntroduce"
                size="mini"
                type="text"
                @click="play(scope.$index, scope.row)"
              > 播放<i class="el-icon-video-play" />
                <audio :id="'v'+scope.row.id" :key="scope.row.id"><source :src="scope.row.voiceIntroduce" type="audio/mpeg"></audio>
              </el-button>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="100">
            <template slot-scope="scope">
              <el-button v-if="scope.row.userPicture == null" size="mini" type="text" @click="adopt(scope.$index, scope.row)">
                <label style="color: #61C091">通过</label>
              </el-button>
              <el-button v-if="scope.row.userPicture == null" size="mini" type="text" @click="refuse(scope.$index, scope.row.id, scope.row.userId)">
                <label style="color: #FF4D51">拒绝</label>
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="已通过" name="second">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="id" label="id" width="100" />
          <el-table-column prop="nickName" :formatter="decodeUnicodeN" label="昵称" width="110" />
          <el-table-column label="头像" width="82">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 60px;">
            </template>
          </el-table-column>
          <!-- <el-table-column label="实名照" width="120">
            <template slot-scope="scope">
              <img :src="scope.row.userRealPicture" @click="showPic(scope.row.userRealPicture)" style="width: 80px;">
            </template>
          </el-table-column> -->
          <el-table-column label="性别" width="50">
            <template slot-scope="scope">
              <label v-if="scope.row.gender===0">男</label>
              <label v-else-if="scope.row.gender===1">女</label>
              <label v-else />
            </template>
          </el-table-column>
          <el-table-column prop="certificationGameName" label="认证技能" width="120" />
          <el-table-column label="技能展示图" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.userGameScreenShot" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showPic(scope.row.userGameScreenShot)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="本人展示" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.userPicture" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showUserImage(scope.row.userId, 2)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="语音介绍" >
            <template slot-scope="scope">
              <el-button
                v-show="scope.row.voiceIntroduce"
                size="mini"
                type="text"
                @click="play(scope.$index, scope.row)"
              > 播放<i class="el-icon-video-play" />
                <audio :id="'v'+scope.row.id" :key="scope.row.id"><source :src="scope.row.voiceIntroduce" type="audio/mpeg"></audio>
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="已拒绝" name="third">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="id" label="id" width="100" />
          <el-table-column prop="nickName" :formatter="decodeUnicodeN" label="昵称" width="110" />
          <el-table-column label="头像" width="82">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 60px;">
            </template>
          </el-table-column>
          <!-- <el-table-column label="实名照" width="120">
            <template slot-scope="scope">
              <img :src="scope.row.userRealPicture" @click="showPic(scope.row.userRealPicture)" style="width: 80px;">
            </template>
          </el-table-column> -->
          <el-table-column label="性别" width="50">
            <template slot-scope="scope">
              <label v-if="scope.row.gender===0">男</label>
              <label v-else-if="scope.row.gender===1">女</label>
              <label v-else />
            </template>
          </el-table-column>
          <el-table-column prop="certificationGameName" label="认证技能" width="120" />
          <el-table-column label="技能展示图" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.userGameScreenShot" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showPic(scope.row.userGameScreenShot)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="本人展示" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.userPicture" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showUserImage(scope.row.userId, 1)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column prop="remark" label="拒绝说明" />
        </el-table>
      </el-tab-pane>

    </el-tabs>

    <el-drawer title="查看图片" :visible.sync="showPic_drawer" size="600px" direction="rtl">
      <div style="padding: 0 10px;">
        <img :src="picURL" style="height:auto;max-height:560px;width:auto;max-width:560px;"/>
      </div>
    </el-drawer>

    <el-dialog title="本人展示" :visible.sync="showUserImage_drawer" top="2vh" width="400px" height="660">
      <el-tabs v-model="activeUserShow" @tab-click="UserShowClick">

        <el-tab-pane label="待审核" name="aa">
          <div class="pic_video_list">
            <div v-for="item in UserShowPicList" :key="item.pictureId" class="checkbox">
              <img :src="item.pictureUrl" alt="" style="width:200px;">
              <input type="checkbox" checked="checked" @click="checkboxPicOnclick($event, item.pictureId, item.imageCardId)">
            </div>
            <div v-for="item in UserShowVideoList" :key="item.imageCardId" class="checkbox">
              <video :poster="item.videoCoverUrl" preload="“metadata”" controls="controls" width="200px" height="120px">
                <source :src="item.videoUrl" type="video/mp4; codecs=hevc">
                <source :src="item.videoUrl" type="video/ogg">
                <source :src="item.videoUrl" type="video/WebM">
              </video>
              <input type="checkbox" checked="checked" @click="checkboxImgOnclick($event, item.imageCardId)">
            </div>
          </div>
          <div v-if="UserShowPicList.length != 0 || UserShowVideoList.length != 0" style="text-align:center;margin-top:10px;">
            <el-button type="primary" @click="checkOk">审核</el-button>
          </div>
        </el-tab-pane>

        <el-tab-pane label="已审核" name="bb">
          <div class="pic_video_list">
            <div v-for="item in UserShowPicList" :key="item.pictureId" class="checkbox">
              <img :src="item.pictureUrl" alt="" style="width:200px;">
            </div>
            <div v-for="item in UserShowVideoList" :key="item.imageCardId" class="checkbox">
              <video :poster="item.videoCoverUrl" preload="“metadata”" controls="controls" width="200px" height="120px">
                <source :src="item.videoUrl" type="video/mp4; codecs=hevc">
                <source :src="item.videoUrl" type="video/ogg">
                <source :src="item.videoUrl" type="video/WebM">
              </video>
            </div>
          </div>
        </el-tab-pane>

      </el-tabs>
    </el-dialog>

    <el-dialog :visible.sync="refuseView" title="理由说明" width="700px">
      <el-form label-position="top">
        <div style="margin-bottom: 20px">
          <el-checkbox-group v-model="checkList" @change="clickCheckbox">
            <el-checkbox label="选择的认证段位与上传的认证技能展示图显示的段位不符。" />
            <el-checkbox label="本人展示视频（照片）存在他人的视频（照片），请统一上传自己的资料，请勿使用他人的资料。" />
          </el-checkbox-group>
        </div>
        <el-form-item label="拒绝理由">
          <el-input v-model="verifyQuery.remark" type="textarea" :autosize="{ minRows: 4, maxRows: 8}" maxlength="120" show-word-limit />
        </el-form-item>
      </el-form>
      <div style="text-align:center;">
        <el-button type="primary" @click="sendRefuse()">确定</el-button>
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
import { getSkillCertificationList, skillCertificationStatus, skillCertificationVerify, skillCertificationVerifyPV } from '@/api/gamesAccompany'
import { deepClone, parseTime } from '@/utils'
import { scrollTo } from '@/utils/scrollTo'

export default {
  data() {
    return {
      activeName: 'first',
      listQuery: {// 页面参数
        id: null,
        nickName: null,
        phone: null,
        pageNum: 1,
        pageSize: 20,
        status: 1
      },
      audio: '',
      pageInfo: {}, // 页面数据
      showPic_drawer: false,//查看技能展示图窗口
      picURL: null, // 技能展示图地址
      showUserImage_drawer: false,//本人形象窗口
      activeUserShow: 'aa',//本人形象状态
      userShowQuery: {//查询本人形象参数
        status: 1,
        userId: null
      },
      UserShowPicList: [], //本人形象照片数据
      UserShowVideoList: [], //本人形象视频数据
      checkPictureIds: [],//本人形象照片id数组
      checkImageCardIds: [],//本人形象视频id数组
      denailPictureIds: [],//
      denailImageCardIds: [],//
      refuseView: false,//拒绝窗口
      pictureAndVideoQuery:{//图片、视频审核参数
        userId: null,
        passImageCardIds: null,
        denailImageCardIds: null,
        passPictureIds: null,
        denailPictureIds: null,
      },
      verifyQuery: {// 审核参数
        id: null,
        userId: null,
        status: null,
        remark: null
      },
      checkList: []//拒绝说明
    }
  },
  mounted() {
    this.loadDatas()
  },
  methods: {
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
    },
    // 查看本人形象展示(待审核)
    showUserImage(userId, status) {
      this.userShowQuery.userId = userId
      this.userShowQuery.status = status
      this.pictureAndVideoQuery.userId = userId
      this.getUserPicList()
      if (status == 1) {
        this.activeUserShow = 'aa'
      } else if (status == 2) {
        this.activeUserShow = 'bb'
      }
      this.showUserImage_drawer = true
    },
    // 本人形象列表状态
    UserShowClick(tab, event) {
      if (tab.name === 'aa') {
        this.userShowQuery.status = 1
        this.getUserPicList()
      } else if (tab.name === 'bb') {
        this.userShowQuery.status = 2
        this.getUserPicList()
      }
    },
    // 获取本人形象展示
    getUserPicList() {
      skillCertificationStatus(this.userShowQuery).then(res => {
        // console.log(res.data.data)
        this.UserShowPicList = res.data.data.filter((item) => item.type == 0)
        this.UserShowVideoList = res.data.data.filter((item) => item.type == 1)
        this.checkPictureIds = this.UserShowPicList.map((item) => item.pictureId)
        this.checkImageCardIds = this.UserShowVideoList.map((item) => item.imageCardId)

        for(let i=0; i<this.UserShowPicList.length; i++){
          this.checkImageCardIds.push(this.UserShowPicList[i].imageCardId)
        }
        // console.log(this.checkPictureIds,this.checkImageCardIds)
      })
    },
    // 选择图片形象
    checkboxPicOnclick(e, pictureId, imageCardId) {
      let index1 = this.checkPictureIds.indexOf(pictureId)
      let index2 = this.denailPictureIds.indexOf(pictureId)
      let index3 = this.checkImageCardIds.indexOf(imageCardId)
      let index4 = this.denailImageCardIds.indexOf(imageCardId)
      if (e.target.checked === true) {
        this.checkPictureIds.push(pictureId)
        this.denailPictureIds.splice(index2, 1)
        this.checkImageCardIds.push(imageCardId)
        if(this.checkPictureIds.length > 0){
          this.denailImageCardIds.splice(index4, 1)
        }
      } else {
        this.checkPictureIds.splice(index1, 1)
        this.denailPictureIds.push(pictureId)
        this.checkImageCardIds.splice(index3, 1)
        if(this.checkPictureIds.length == 0){
          this.denailImageCardIds.push(imageCardId)
        }
      }

    },
    // 选择视频形象
    checkboxImgOnclick(e, imageCardId) {
      let index1 = this.checkImageCardIds.indexOf(imageCardId)
      let index2 = this.denailImageCardIds.indexOf(imageCardId)
      if (e.target.checked === true) {
        this.checkImageCardIds.push(imageCardId)
        this.denailImageCardIds.splice(index2, 1)
      } else {
        this.checkImageCardIds.splice(index1, 1)
        this.denailImageCardIds.push(imageCardId)
      }
    },
    // 审核陪玩形象
    checkOk() {
      let set = new Set(this.checkImageCardIds)
      this.checkImageCardIds = Array.from(set)
      
      this.pictureAndVideoQuery.passPictureIds = this.checkPictureIds.join()
      this.pictureAndVideoQuery.passImageCardIds = this.checkImageCardIds.join()
      this.pictureAndVideoQuery.denailPictureIds = this.denailPictureIds.join()
      this.pictureAndVideoQuery.denailImageCardIds = this.denailImageCardIds.join()
      //this.showUserImage_drawer = false
      //console.log(this.pictureAndVideoQuery)
      skillCertificationVerifyPV(this.pictureAndVideoQuery).then(res => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '成功设置审核' })
          this.checkPictureIds = []
          this.checkImageCardIds = []
          this.denailPictureIds = []
          this.denailImageCardIds = []
          this.pictureAndVideoQuery = {
            userId: null,
            passImageCardIds: null,
            denailImageCardIds: null,
            passPictureIds: null,
            denailPictureIds: null,
          }
          this.loadDatas()
          this.showUserImage_drawer = false
        }
      })
    },
    // 通过
    adopt(index, row) {
      this.verifyQuery.id = row.id
      this.verifyQuery.userId = row.userId
      this.verifyQuery.status = 2
      // console.log(this.verifyQuery)
      skillCertificationVerify(this.verifyQuery).then(res => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '成功设置审核' })
          this.pageInfo.list.splice(index, 1)
        }
      })
    },
    // 拒绝
    refuse(index, id, userId) {
      this.checkList = []
      this.verifyQuery = {
        id: id,
        userId: userId,
        status: 3,
        remark: null
      }
      this.refuseView = true
    },
    //选择拒绝理由
    clickCheckbox(e) {
      if (e.length == 1) {
        this.verifyQuery.remark = this.checkList[0]
      } else if (e.length == 2) {
        this.verifyQuery.remark = this.checkList[0] + this.checkList[1]
      } else {
        this.verifyQuery.remark = null
      }
    },    
    //确定拒绝
    sendRefuse() {
      // console.log(this.verifyQuery)
      if (this.verifyQuery.remark == null) {
        this.$notify.error({ title: '请说明理由' })
      } else {
        skillCertificationVerify(this.verifyQuery).then(res => {
          const ret = res.data
          if (ret.code === 200) {
            this.loadDatas()
            this.$notify.success({ title: '成功设置审核' })
            this.refuseView = false
          }
        })
      }
    },
    // 查看技能展示图
    showPic(picURL) {
      this.picURL = picURL
      this.showPic_drawer = true
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
        this.listQuery.status = 2
        this.loadDatas()
      } else if (tab.name === 'third') {
        this.listQuery.status = 3
        this.loadDatas()
      }
    },
    decodeUnicodeN(row) {
      if (row.nickName != null) {
        const str = row.nickName.replace(/\\/g, '%')
        return unescape(str)
      }
    },    
    // 获取列表数据
    loadDatas() {
      getSkillCertificationList(this.listQuery).then((res) => {
        // console.log(res);
        this.pageInfo = res.data.data
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

<style>
.el-table__body-wrapper {
    overflow-y: auto;
}
.pic_video_list{
  width: 100%;
  max-height: 400px;
  overflow: auto;
}
.checkbox{
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  border: 1px solid #dddddd;
  border-bottom:0;
}
input[type='checkbox'] {
  width: 30px;
  height: 30px;
  margin: auto;
  margin-left: 30px;
}
</style>
