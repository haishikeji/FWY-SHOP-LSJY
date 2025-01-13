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
          <el-table-column prop="userId" label="用户id" width="100" />
          <el-table-column prop="nickName" :formatter="decodeUnicodeN" label="昵称" width="110" />
          <el-table-column label="头像" width="82">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 60px;">
            </template>
          </el-table-column>
          <el-table-column prop="gameLevel" label="当前段位" />
          <el-table-column label="当前认证图" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.levelScreenshots" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showPic(scope.row.levelScreenshots)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column prop="newLevel" label="最新段位" />
          <el-table-column label="最新认证图" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.newLevelScreenshots" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showPic(scope.row.newLevelScreenshots)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="本人展示" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.userPicture" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showUserImage(scope.row.userId)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="100">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="adopt(scope.$index, scope.row)">
                <label style="color: #61C091">通过</label>
              </el-button>
              <el-button size="mini" type="text" @click="refuse(scope.$index, scope.row)">
                <label style="color: #FF4D51">拒绝</label>
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="已通过" name="second">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="用户id" width="100" />
          <el-table-column prop="nickName" :formatter="decodeUnicodeN" label="昵称" width="110" />
          <el-table-column label="头像" width="82">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 60px;">
            </template>
          </el-table-column>
          <el-table-column prop="gameLevel" label="当前段位" />
          <el-table-column label="当前认证图" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.levelScreenshots" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showPic(scope.row.levelScreenshots)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column prop="newLevel" label="最新段位" />
          <el-table-column label="最新认证图" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.newLevelScreenshots" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showPic(scope.row.newLevelScreenshots)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="本人展示" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.userPicture" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showUserImage(scope.row.userId)">查看</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="已拒绝" name="third">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="userId" label="用户id" width="100" />
          <el-table-column prop="nickName" :formatter="decodeUnicodeN" label="昵称" width="110" />
          <el-table-column label="头像" width="82">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 60px;">
            </template>
          </el-table-column>
          <el-table-column prop="gameLevel" label="当前段位" />
          <el-table-column label="当前认证图" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.levelScreenshots" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showPic(scope.row.levelScreenshots)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column prop="newLevel" label="最新段位" />
          <el-table-column label="最新认证图" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.newLevelScreenshots" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showPic(scope.row.newLevelScreenshots)">查看</el-button>
            </template>
          </el-table-column>
          <el-table-column label="本人展示" width="160">
            <template slot-scope="scope">
              <img :src="scope.row.userPicture" style="width: 100px;">
              <el-button size="mini" type="primary" style="margin: 2px 20px 0 20px;" @click="showUserImage(scope.row.userId)">查看</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

    </el-tabs>

    <el-drawer title="查看图片" :visible.sync="showPic_drawer" size="600px" direction="rtl">
      <div style="padding: 0 10px;">
        <el-image :src="picURL" style="height:auto;max-height:560px;width:auto;max-width:560px;"/>
      </div>
    </el-drawer>

    <el-dialog title="本人展示" :visible.sync="showUserImage_drawer" top="10px" width="400px" height="660">
      <div>
        <div v-for="item in UserShowPicList" :key="item.pictureId">
          <img :src="item.pictureUrl" alt="" style="width:200px;">
        </div>
        <div v-for="item in UserShowVideoList" :key="item.imageCardId">
          <video :poster="item.videoCoverUrl" preload="“metadata”" controls="controls" width="200px" height="120px">
            <source :src="item.videoUrl" type="video/mp4; codecs=hevc">
            <source :src="item.videoUrl" type="video/ogg">
            <source :src="item.videoUrl" type="video/WebM">
          </video>
        </div>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="refuseView" title="理由说明" width="700px">
      <el-form label-position="top">
        <div style="margin-bottom: 20px">
          <el-checkbox-group v-model="refuseList" @change="clickCheckbox">
            <el-checkbox label="技能认证图和段位信息更新不符合，请重新更新资料" />
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
import { player_levelList, player_levelSelf_show, player_levelVerify } from '@/api/gamesAccompany'
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
      pageInfo: {}, // 页面数据
      showPic_drawer: false,//查看技能展示图窗口
      picURL: null, // 技能展示图地址
      showUserImage_drawer: false,//本人形象窗口
      UserShowPicList: [], // 本人照片展示
      UserShowVideoList: [], // 本人视频展示
      refuseView: false,//拒绝窗口
      verifyQuery: {// 审核参数
        gameId: null,
        userId: null,
        status: null,
        remark: null
      },
      refuseList: []//拒绝说明
    }
  },
  mounted() {
    this.loadDatas()
  },
  methods: {
    adopt (index, row) {
      this.verifyQuery = {
        userId: row.userId,
        gameId: row.gameId,
        status: 2
      }
      player_levelVerify(this.verifyQuery).then(res => {
        if (res.data.code === 200) {
          this.$notify.success({ title: '审核通过' })
          this.pageInfo.list.splice(index, 1)
        }
      })
    },
    // 查看本人形象展示
    showUserImage (userId) {
      this.getUserPicList(userId)
      this.showUserImage_drawer = true
    },
    getUserPicList (id) {
      player_levelSelf_show({ userId: id }).then(res => {
        this.UserShowPicList = res.data.data.filter((item) => item.type == 0)
        this.UserShowVideoList = res.data.data.filter((item) => item.type == 1)
      })
    },
    // 拒绝
    refuse (index, row) {
      this.refuseList = []
      this.verifyQuery = {
        userId: row.userId,
        gameId: row.gameId,
        status: 3,
        remark: null,
      }
      this.refuseView = true
    },
    //选择拒绝理由
    clickCheckbox (e) {
      if (e.length == 1) {
        this.verifyQuery.remark = this.refuseList[0]
      } else if (e.length == 2) {
        this.verifyQuery.remark = this.refuseList[0] + this.refuseList[1]
      } else {
        this.verifyQuery.remark = null
      }
    },
    //确定拒绝
    sendRefuse() {
      if (this.verifyQuery.remark == null) {
        this.$notify.error({ title: '请说明理由' })
      } else {
        player_levelVerify(this.verifyQuery).then(res => {
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
      player_levelList(this.listQuery).then((res) => {
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
</style>
