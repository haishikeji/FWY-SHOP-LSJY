<template>
  <div id="testView" class="app-container">

    <div class="filter-container">
      <el-button size="medium" type="primary" icon="el-icon-plus" @click="toBanner">新增banner</el-button>
    </div>

    <el-tabs v-model="activeName" @tab-click="handleClick">

      <el-tab-pane label="全部" name="first">
        <el-table :data="pageInfo.records" border stripe height="660" style="width: 100%;">
          <el-table-column prop="bannerId" label="bannerId" width="100" />
          <el-table-column label="banner图" width="150">
            <template slot-scope="scope">
              <el-image style="width: 120px;" :src="scope.row.imgUrl" :preview-src-list="[scope.row.imgUrl]">
              </el-image>
            </template>
          </el-table-column>
          <el-table-column :formatter="decodeType" prop="jumpType" label="跳转类型" width="100" />
          <el-table-column prop="jumpUrl" label="跳转地址" />
          <el-table-column label="状态" width="100" >
            <template slot-scope="scope">
              <label v-if="scope.row.status==0">上架</label>
              <label v-else-if="scope.row.status==1">下架</label>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="100">
            <template slot-scope="scope">
              <el-button type="primary" @click="edit(scope.row)">修改</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

    </el-tabs>

    <el-dialog :visible.sync="dialogVisible_addbanner" title="新增banner" width="600px" top="32px">
      <el-form ref="addForm" label-width="160px" label-position="right" size="medium">
        <el-form-item label="banner图:" prop="file">
          <el-upload
            ref="uploadImg"
            action=""
            :auto-upload="true"
            :limit="1"
            list-type="picture"
            :before-upload="uploadImg"
          >
            <el-button slot="trigger" size="small" type="primary">选取banner图</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="" prop="">
          <img v-if="addparmas.img_url" :src="addparmas.img_url" style="width:100px;height: auto;" alt="">
        </el-form-item>
        <el-form-item label="上/下架：" prop="status">
          <el-select v-model="addparmas.status">
            <el-option v-for="item in statusData" :key="item.id" :label="item.v" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="跳转类型：" prop="jumpType">
          <el-select v-model="addparmas.jumpType">
            <el-option v-for="item in jumpTypeData" :key="item.id" :label="item.v" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="h5/微盟跳转地址:" prop="h5Url" v-if="addparmas.jumpType==2 || addparmas.jumpType==5">
          <el-input v-model="addparmas.h5Url" placeholder="请填写h5/微盟跳转地址" />
        </el-form-item>
        <div v-if="addparmas.jumpType==4">
          <el-form-item label="iOSpage:" prop="page" >
            <el-input v-model="addparmas.page" />
          </el-form-item>
          <el-form-item label="安卓targetActivity:" prop="targetActivity" >
            <el-input v-model="addparmas.targetActivity" />
          </el-form-item>
          <el-form-item label="安卓tab:" prop="tab" >
            <el-input v-model="addparmas.tab" />
          </el-form-item>
        </div>
        <div style="text-align:center;">
          <el-button type="primary" @click="addFun('addForm')">确定</el-button>
        </div>
      </el-form>
    </el-dialog>

    <el-dialog :visible.sync="dialogVisible_editbanner" title="编辑banner" width="600px" top="32px">
      <el-form ref="editForm" label-width="160px" label-position="right" size="medium">
        <el-form-item label="banner图:" prop="file">
          <el-upload
            ref="uploadImg"
            action=""
            :auto-upload="true"
            :limit="1"
            list-type="picture"
            :before-upload="uploadImg"
          >
            <el-button slot="trigger" size="small" type="primary">选取banner图</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="" prop="">
          <img v-if="editparmas.img_url" :src="editparmas.img_url" style="width:100px;height: auto;" alt="">
        </el-form-item>
        <el-form-item label="上/下架：" prop="status">
          <el-select v-model="editparmas.status">
            <el-option v-for="item in statusData" :key="item.id" :label="item.v" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="跳转类型：" prop="jumpType">
          <el-select v-model="editparmas.jumpType">
            <el-option v-for="item in jumpTypeData" :key="item.id" :label="item.v" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="h5/微盟跳转地址:" prop="h5Url" v-if="editparmas.jumpType==2 || editparmas.jumpType==5">
          <el-input v-model="editparmas.h5Url" placeholder="请填写h5/微盟跳转地址" />
        </el-form-item>
        <div v-if="editparmas.jumpType==4">
          <el-form-item label="iOSpage:" prop="page" >
            <el-input v-model="editparmas.page" />
          </el-form-item>
          <el-form-item label="安卓targetActivity:" prop="targetActivity" >
            <el-input v-model="editparmas.targetActivity" />
          </el-form-item>
          <el-form-item label="安卓tab:" prop="tab" >
            <el-input v-model="editparmas.tab" />
          </el-form-item>
        </div>
        <div style="text-align:center;">
          <el-button type="primary" @click="keepFun('addForm')">确定</el-button>
        </div>
      </el-form>
    </el-dialog>

    <div class="pagination">
      <el-pagination
        :current-page.sync="listQuery.currentPage"
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
import { bannerList, bannerAdd, bannerUpdate, imgUpload } from '@/api/zly'
import { deepClone, parseTime } from '@/utils'
import { scrollTo } from '@/utils/scrollTo'

export default {
  data() {
    return {
      activeName: 'first',
      listQuery: {
        currentPage: 1,
        pageSize: 20,
      },
      statusData: [
        {id: 0, v: '上架'},
        {id: 1, v: '下架'}
      ],
      jumpTypeData: [
        {id: 1, v: 'None'},
        {id: 2, v: 'H5'},
        {id: 3, v: 'Miniptogram'},
        {id: 4, v: 'App'},
        {id: 5, v: 'Weimob'}
      ],
      addparmas: {
        img_url: '',
        jumpType: '',
        status: ''
      },
      dialogVisible_addbanner: false,
      dialogVisible_editbanner: false,
      editparmas: {
        bannerId: '',
        img_url: '',
        jumpType: '',
        status: ''
      },
      pageInfo: {}
    }
  },
  mounted() {
    this.loadDatas()
  },
  methods: {
    edit(row) {
      this.editparmas = {
        bannerId: row.bannerId,
        img_url: row.imgUrl,
        jumpType: row.jumpType,
        status: row.status
      },
      this.dialogVisible_editbanner = true;
    },
    keepFun() {
      bannerUpdate(this.editparmas).then(res => {
        if (res.data.code === 200) {
          this.$notify.success({ title: '修改成功' });
          this.loadDatas();
          this.dialogVisible_editbanner = false;
          console.log(res.data.data)
        }
      })
    },
    addFun() {
      bannerAdd(this.addparmas).then(res => {
        if (res.data.code === 200) {
          this.$notify.success({ title: '新增成功' });
          this.loadDatas();
          this.dialogVisible_addbanner = false;
          console.log(res.data.data)
        }
      })
    },
    uploadImg(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJPG) {
        this.$message.error('请上传 jpg/png 格式图片!')
        this.params = null;
        return false
      } else {
        this.params = new FormData();
        this.params.set('file', file);
        this.params.set('subPath', 'admin/banner/');
        imgUpload(this.params).then(res => {
          if (res.data.code === 200) {
              this.$notify.success({ title: '上传成功' });
              this.addparmas.img_url = res.data.data;
              this.editparmas.img_url = res.data.data;
              console.log(res.data.data)
            // return false
          }
        })
      }
    },
    toBanner() {
      this.addparmas = {
        img_url: '',
        jumpType: ''
      },
      this.dialogVisible_addbanner = true;
    },
    handleFilter() {
      this.loadDatas();
    },
    handleClick(tab, event) {
      this.listQuery.pageNum = 1
      if (tab.name === 'first') {
        this.loadDatas();
      }
    },
    decodeType(row) {
      var str = this.jumpTypeData.find((item, index) => {
          return item.id == row.jumpType;
      })
      return str.v
    },
    // 获取数据
    loadDatas() {
      bannerList(this.listQuery).then((res) => {
        console.log(res);
        this.pageInfo = res.data.data;
      })
    },
    pageSizeChange(num) {
      this.listQuery.pageSize = num;
      this.pagingChange(1);
    },
    pagingChange(num) {
      this.listQuery.pageNum = num;
      this.loadDatas();
      scrollTo(0, 600);
    }
  }
}
</script>

<style>
</style>
