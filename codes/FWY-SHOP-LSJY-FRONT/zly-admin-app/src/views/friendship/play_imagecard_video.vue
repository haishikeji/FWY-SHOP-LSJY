<!-- 陪玩视频 -->
<template>
  <div id="user" class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.id" placeholder="输入需要搜索id" style="width: 200px;" class="filter-item" type="text" maxlength="15" minlength="7" oninput="value=value.replace(/[^\d]/g,'')" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.nickName" placeholder="输入需要搜索昵称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.phone" placeholder="输入需要搜索手机号" style="width: 200px;" class="filter-item" type="text" maxlength="15" minlength="7" oninput="value=value.replace(/[^\d]/g,'')" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
    </div>

    <el-drawer title="查看视频" :visible.sync="drawer" size="60%" direction="rtl">
      <div style="overflow: auto; position: fixed; max-height: 600px; padding-left: 10px;">
        <video id="checkVideo" :src="pics" controls="controls" max-width="80%" height="600px" />
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
          <el-table-column prop="nickName" label="昵称" width="80" />
          <el-table-column label="性别" width="60">
            <template slot-scope="scope">
              <span v-if="scope.row.gender == 0">男</span>
              <span v-else>女</span>
            </template>
          </el-table-column>
          <!-- <el-table-column label="实名照" width="102">
            <template slot-scope="scope">
              <div v-if="scope.row.userRealPicture">
                <img :src="scope.row.userRealPicture" style="width: 80px;height:80px;">
              </div>
              <div v-else>
                无
              </div>
            </template>
          </el-table-column> -->
          <el-table-column label="当前视频" width="222">
            <template slot-scope="scope">
              <div v-if="scope.row.currentVideoList.length">
                <div v-for="index in scope.row.currentVideoList" :key="index.imageCardId">
                  <video :poster="index.videoCoverUrl" preload="“metadata”" controls="controls" width="200px" height="120px">
                    <source :src="index.videoUrl" type="video/mp4; codecs=hevc">
                    <source :src="index.videoUrl" type="video/ogg">
                    <source :src="index.videoUrl" type="video/WebM">
                  </video>
                  <el-button size="mini" type="primary" style="margin: 2px 10px 0 10px;" @click="showPic(index.videoUrl)">查看</el-button>
                </div>
              </div>
              <div v-else>
                无
              </div>
            </template>
          </el-table-column>
          <el-table-column label="最新视频">
            <template slot-scope="scope">
              <label v-for="index in scope.row.lastVideolist" :key="index.imageCardId" class="checkbox">
                <input id="index.imageCardId" type="checkbox" checked="checked" @click="checkboxOnclick($event, index.imageCardId, scope.row.lastVideolist)">
                <video for="index.imageCardId" :src="index.videoUrl" :poster="index.videoCoverUrl" preload="“metadata”" controls="controls" width="200px" height="120px" />
                <el-button size="mini" type="primary" @click="showPic(index.videoUrl)">查看</el-button>
              </label>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="92">
            <template slot-scope="scope">
              <el-button type="success" @click="verify(scope.$index, scope.row.userId, scope.row.lastVideolist)">审核</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="已通过" name="second">
        <el-table :data="pageInfo.datas" border stripe height="660" style="width: 100%;">
          <el-table-column label="头像" width="62">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 40px;">
            </template>
          </el-table-column>
          <el-table-column prop="nickName" label="昵称" width="80" />
          <!-- <el-table-column label="实名照" width="102">
            <template slot-scope="scope">
              <div v-if="scope.row.userRealPicture">
                <img :src="scope.row.userRealPicture" style="width: 80px;height:80px;">
              </div>
              <div v-else>
                无
              </div>
            </template>
          </el-table-column> -->
          <el-table-column label="当前视频">
            <template slot-scope="scope">
              <label v-for="index in scope.row.currentVideoList" :key="index.imageCardId" class="imgBox">
                <img :src="index.videoCoverUrl" alt="">
                <el-button size="mini" type="primary" style="margin: 0 32px;" @click="showPic(index.videoUrl)">查看</el-button>
              </label>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="不通过" name="third">
        <el-table :data="pageInfo.datas" border stripe height="660" style="width: 100%;">
          <el-table-column label="头像" width="62">
            <template slot-scope="scope">
              <img :src="scope.row.headImg" style="width: 40px;">
            </template>
          </el-table-column>
          <el-table-column prop="nickName" label="昵称" width="80" />
          <!-- <el-table-column label="实名照" width="102">
            <template slot-scope="scope">
              <div v-if="scope.row.userRealPicture">
                <img :src="scope.row.userRealPicture" style="width: 80px;height:80px;">
              </div>
              <div v-else>
                无
              </div>
            </template>
          </el-table-column> -->
          <el-table-column label="当前视频">
            <template slot-scope="scope">
              <label v-for="index in scope.row.currentVideoList" :key="index.imageCardId" class="imgBox">
                <img :src="index.videoCoverUrl" alt="">
                <el-button size="mini" type="primary" style="margin: 0 32px;" @click="showPic(index.videoUrl)">查看</el-button>
              </label>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

    </el-tabs>

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
import { play_imagecard_videoList, play_imagecard_videoVerify } from '@/api/gamesAccompany'
import { scrollTo } from '@/utils/scrollTo'

export default {
  data() {
    return {
      activeName: 'first',
      listQuery: {
        id: null,
        nickName: null,
        phone: null,
        pageNum: 1,
        pageSize: 10,
        status: 1
      },
      pageInfo: {},
      drawer: false,
      pics: null,
      pictureIds: []
    }
  },
  watch: {
  },
  mounted() {
    this.loadDatas()
  },
  methods: {
    showPic(pics) {
      this.pics = pics
      this.drawer = true
    },
    checkboxOnclick(e, id, data) {
      // console.log(e.target.checked,id,data)
      if (e.target.checked === true) {
        const index = this.pictureIds.indexOf(id)
        if (index > -1) {
          this.pictureIds.splice(index, 1)
        }
        // console.log(this.pictureIds);
      } else {
        this.pictureIds.push(id)
        // console.log(this.pictureIds);
      }
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
    verify(index, userId, lastVideolist) {
      const Arr = lastVideolist.filter((item) => !this.pictureIds.includes(item.imageCardId))
      // console.log(Arr);
      const ArrString = Arr.map((item) => item.imageCardId).join()
      // console.log(ArrString);
      play_imagecard_videoVerify({ userId: userId, imageCardIds: ArrString }).then(res => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '操作成功' })
          this.pictureIds = []
          this.pageInfo.datas.splice(index, 1)
        }
      })
    },
    // 获取数据
    loadDatas() {
      play_imagecard_videoList(this.listQuery).then((res) => {
        // console.log(res);
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
    width: 222px;
    display: inline-table;
    position: relative;
}
.checkbox input[type='checkbox'] {
    width: 20px;
    height: 20px;
    position: absolute;
    top: 132px;
    left: 60px;
}
.checkbox video {
    width: 200px;
    height: 120px;
    border: 4px solid red;
}
.checkbox input[type='checkbox']:checked + video {
    border-color: #37f1ab;
}
.checkbox button {
    display: flex;
    margin: 0px auto 10px 90px;
}
.imgBox{
    width: 120px;
    display: inline-table;
    position: relative;
}
.imgBox img{
    width: 120px;
    height: 80px;
    margin-right:4px;
}
</style>
