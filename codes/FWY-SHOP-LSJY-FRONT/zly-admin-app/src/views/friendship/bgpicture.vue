<!-- 背景图库 -->
<template>
  <div id="testView" class="app-container">

    <div class="filter-container">
      <el-upload ref="uploadBgImg" action="" :auto-upload="true" :limit="1" list-type="picture" :before-upload="imgBefore">
        <el-button slot="trigger" size="medium" icon="el-icon-plus" type="primary">添加新背景图</el-button>
      </el-upload>
    </div>

    <el-tabs v-model="activeName" @tab-click="handleClick">

      <el-tab-pane label="已上架" name="first">
        <el-table :data="pageInfo.datas" border stripe height="660" style="width: 100%;">
          <el-table-column label="#" width="50">
            <template slot-scope="scope">
              {{ scope.$index + pageInfo.startRow + 1 }}
            </template>
          </el-table-column>
          <el-table-column label="预览图" width="300">
            <template slot-scope="scope">
              <img :src="scope.row.pictureUrl" style="width: 240px;">
            </template>
          </el-table-column>
          <el-table-column label="操作" width="100">
            <template slot-scope="scope">
              <el-button type="primary" @click="verify(scope.$index, scope.row.id, 1)">
                <label>下架</label>
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="已下架" name="second">
        <el-table :data="pageInfo.datas" border stripe height="660" style="width: 100%;">
          <el-table-column label="#" width="50">
            <template slot-scope="scope">
              {{ scope.$index + pageInfo.startRow + 1 }}
            </template>
          </el-table-column>
          <el-table-column label="预览图" width="300">
            <template slot-scope="scope">
              <img :src="scope.row.pictureUrl" style="width: 240px;">
            </template>
          </el-table-column>
          <el-table-column label="操作" width="100">
            <template slot-scope="scope">
              <el-button type="success" @click="verify(scope.$index, scope.row.id, 0)">
                <label>上架</label>
              </el-button>
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
import { bgpictureAdd, bgpictureList, bgpictureStatus } from '@/api/bgsound'
import { deepClone, parseTime } from '@/utils'
import { scrollTo } from '@/utils/scrollTo'

export default {
  data() {
    return {
      activeName: 'first',
      listQuery: {
        pageNum: 1,
        pageSize: 20,
        status: 0
      },
      pageInfo: {},
      params: null
    }
  },
  mounted() {
    this.loadDatas()
  },
  methods: {
    imgBefore(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJPG) {
        this.$message.error('请上传 jpg/png 格式图片!')
        this.params = null
        return false
      } else {
        // this.pictureFile = file;
        // console.log(this.pictureFile);
        this.params = new FormData()
        this.params.set('pictureFile', file)
        bgpictureAdd(this.params).then(res => {
          const ret = res.data
          if (ret.code === 200) {
            this.$notify.success({ title: '上传成功' })
            this.loadDatas()
          }
        })
        return false
      }
    },
    handleFilter() {
      this.loadDatas()
    },
    handleClick(tab, event) {
      this.listQuery.pageNum = 1
      if (tab.name === 'first') {
        this.listQuery.status = 0
        this.loadDatas()
      } else if (tab.name === 'second') {
        this.listQuery.status = 1
        this.loadDatas()
      }
    },
    verify(index, id, status) {
      bgpictureStatus({ id: id, status: status }).then(res => {
        const ret = res.data
        if (ret.code === 200) {
          this.$notify.success({ title: '操作成功' })
          this.pageInfo.datas.splice(index, 1)
        }
      })
    },
    // 获取数据
    loadDatas() {
      bgpictureList(this.listQuery).then((res) => {
        // console.log(res.data)
        this.pageInfo = res.data.data.pageInfo
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
