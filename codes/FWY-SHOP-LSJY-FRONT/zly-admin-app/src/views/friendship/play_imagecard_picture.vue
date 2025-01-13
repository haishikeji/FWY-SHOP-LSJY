<!-- 陪玩照片 -->
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

    <el-drawer title="查看图片" :visible.sync="drawer" size="600px">
      <div style="padding: 0 10px;">
        <img :src="pics" style="height:600px;width:auto;max-width:560px;">
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
                <el-button size="mini" type="primary" style="margin: 2px 10px 0 10px;" @click="showPic(scope.row.userRealPicture)">查看</el-button>
              </div>
              <div v-else>
                无
              </div>
            </template>
          </el-table-column> -->
          <el-table-column label="当前照片" width="102">
            <template slot-scope="scope">
              <div v-if="scope.row.currentImageList.length">
                <div v-for="index in scope.row.currentImageList" :key="index.id">
                  <img :src="index.url" style="width: 80px;height:80px;">
                  <el-button size="mini" type="primary" style="margin: 2px 10px 0 10px;" @click="showPic(index.url)">查看</el-button>
                </div>
              </div>
              <div v-else>
                无
              </div>
            </template>
          </el-table-column>
          <el-table-column label="最新照片">
            <template slot-scope="scope">
              <label v-for="index in scope.row.pictureList" :key="index.id" class="checkbox">
                <input id="index.id" type="checkbox" checked="checked" style="display:none;" @click="checkboxOnclick($event, index.id, scope.row.pictureList)">
                <img for="index.id" :src="index.url" style="width:80px;height:80px;">
                <el-button size="mini" type="primary" @click="showPic(index.url)">查看</el-button>
              </label>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="92">
            <template slot-scope="scope">
              <el-button type="success" @click="verify(scope.$index, scope.row.imageCardId, scope.row.pictureList)">审核</el-button>
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
                <el-button size="mini" type="primary" style="margin: 2px 10px 0 10px;" @click="showPic(scope.row.userRealPicture)">查看</el-button>
              </div>
              <div v-else>
                无
              </div>
            </template>
          </el-table-column> -->
          <el-table-column label="当前照片">
            <template slot-scope="scope">
              <label v-for="index in scope.row.currentImageList" :key="index.id" class="imgBox">
                <img :src="index.url">
                <el-button size="mini" type="primary" style="margin: 0 12px 0;" @click="showPic(index.url)">查看</el-button>
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
                <el-button size="mini" type="primary" style="margin: 2px 10px 0 10px;" @click="showPic(scope.row.userRealPicture)">查看</el-button>
              </div>
              <div v-else>
                无
              </div>
            </template>
          </el-table-column> -->
          <el-table-column label="当前照片">
            <template slot-scope="scope">
              <label v-for="index in scope.row.currentImageList" :key="index.id" class="imgBox">
                <img :src="index.url">
                <el-button size="mini" type="primary" style="margin: 0 12px 0;" @click="showPic(index.url)">查看</el-button>
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
import { play_imagecard_pictureList, play_imagecard_pictureVerify } from '@/api/gamesAccompany'
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
        pageSize: 20,
        status: 1
      },
      pageInfo: {},
      drawer: false,
      pics: null,
      pictureIds: []
    }
  },
  mounted() {
    this.loadDatas()
  },
  methods: {
    showPic(pics) {
      // console.log(pics)
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
    verify(index, imageCardId, pictureList) {
      // console.log(index, imageCardId, pictureList)
      const Arr = pictureList.filter((item) => !this.pictureIds.includes(item.id))
      // console.log(Arr);
      const ArrString = Arr.map((item) => item.id).join()
      // console.log(ArrString);
      play_imagecard_pictureVerify({ imageCardId: imageCardId, pictureIds: ArrString }).then(res => {
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
      play_imagecard_pictureList(this.listQuery).then((res) => {
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

<style scoped>
.el-table__body-wrapper {
    overflow-y: auto;
}
.checkbox{
    width: 84px;
    display: inline-block;
}
.checkbox input[type='checkbox'] {
    width: 20px;
    height: 20px;
}
.checkbox img {
    border:4px solid red;
}
.checkbox input[type='checkbox']:checked + img {
    border-color: #37f1ab;
}
.checkbox button {
    display: flex;
    margin: 2px 10px 0 10px;
}
.imgBox{
    width: 84px;
    display: inline-block;
}
.imgBox img{
    width:80px;
    height:80px;
    margin-right:4px;
}
</style>
