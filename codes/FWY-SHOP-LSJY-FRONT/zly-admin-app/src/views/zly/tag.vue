<template>
  <div id="testView" class="app-container">

    <div class="filter-container">
      <el-button size="medium" type="primary" icon="el-icon-plus" @click="toAddName">新增标签</el-button>
      <el-button size="medium" type="primary" icon="el-icon-plus" @click="toAddValue">新增标签值</el-button>
    </div>

    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="全部" name="first">
        <el-table :data="pageInfo.records" border stripe height="660" style="width: 100%;">
          <el-table-column prop="tagId" label="标签id" width="200" />
          <el-table-column prop="tagName" label="标签名称" width="200"/>
          <el-table-column prop="tagValueId" label="标签值id" width="200" />
          <el-table-column prop="tagValue" label="标签值" width="200"/>
        </el-table>
      </el-tab-pane>
    </el-tabs>

    <el-dialog :visible.sync="dialogVisible_addName" title="新增标签" width="600px" top="32px">
      <el-form ref="addForm" label-width="80px" label-position="right" size="medium">
        <el-form-item label="标签名:" prop="title">
          <el-input v-model="tagName" placeholder="请填写标签名" />
        </el-form-item>
        <div style="text-align:center;">
          <el-button type="info" plain @click="resetForm('addForm')">取消</el-button>
          <el-button type="primary" @click="save('addForm')">确定</el-button>
        </div>
      </el-form>
    </el-dialog>

    <el-dialog :visible.sync="dialogVisible_addValue" title="新增标签值" width="600px" top="32px">
      <el-form ref="addForm" label-width="160px" label-position="right" size="medium">
        <el-form-item label="选择标签名：" prop="tagId">
          <el-select v-model="tagId">
            <el-option v-for="item in tagNameData" :key="item.tagId" :label="item.tagName" :value="item.tagId" />
          </el-select>
        </el-form-item>
        <el-form-item label="标签值：" prop="tagValues"  v-for="(item, index) in tagValues_datalist" :key="index">
          <el-input v-model="item.value" placeholder="请填写标签值" style="width:200px;"/>
          <el-button @click.prevent="removetagvalue(item)">删除</el-button>
        </el-form-item>
        <div style="text-align:center;">
          <el-button @click="addTag">新增标签值</el-button>
          <el-button type="primary" @click="keep('addForm')">确定</el-button>
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
import { tagList, tagListName, tagAdd, tagAddValue } from '@/api/zly'
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
      pageInfo: {},
      tagNameData: [],
      dialogVisible_addName: false,
      dialogVisible_addValue: false,
      tagName: '',
      tagId: null,
      tagValues: '',
      tagValues_datalist: [
        {
          value: ''
        }
      ]
    }
  },
  mounted() {
    this.loadDatas_tagList();
    this.loadDatas_tagName();
  },
  methods: {
    keep() {
      let tagValues_array= this.tagValues_datalist.map(item=>{
        return  item.value
      })
      this.tagValues = tagValues_array.join(';');
      console.log(this.tagId, this.tagValues);
      tagAddValue({tagId: this.tagId, tagValues: this.tagValues}).then((res) => {
        console.log(res);
        this.$notify.success({ title: '新增成功' })
        this.loadDatas_tagList();
        this.dialogVisible_addValue = false;
      })
    },
    removetagvalue(item) {
      let index = this.tagValues_datalist.indexOf(item)
      if (index !== -1) {
        this.tagValues_datalist.splice(index, 1)
      }
    },
    addTag() {
      this.tagValues_datalist.push({
        value: ''
      });
    },
    save() {
      tagAdd({tagName: this.tagName}).then((res) => {
        console.log(res);
        this.$notify.success({ title: '新增成功' })
        this.loadDatas_tagList();
        this.resetForm();
      })
    },
    resetForm() {
      this.tagName = '';
      this.dialogVisible_addName = false;
    },
    toAddName() {
      this.tagName = '';
      this.dialogVisible_addName = true;
    },
    toAddValue() {
      this.tagValues_datalist = [
        {
          value: ''
        }
      ]
      this.dialogVisible_addValue = true;
    },
    // 获取数据
    loadDatas_tagList() {
      tagList(this.listQuery).then((res) => {
        console.log(res);
        this.pageInfo = res.data.data;
      })
    },
    loadDatas_tagName() {
      tagListName({pageSize: 50, currentPage: 1}).then((res) => {
        console.log(res);
        this.tagNameData = res.data.data.records;
      })
    },
    handleFilter() {
      this.loadDatas_tagList();
    },
    handleClick(tab, event) {
      this.listQuery.currentPage = 1
      if (tab.name === 'first') {
        this.loadDatas_tagList();
      }
    },
    pageSizeChange(num) {
      this.listQuery.pageSize = num;
      this.pagingChange(1);
    },
    pagingChange(num) {
      this.listQuery.currentPage = num;
      this.loadDatas_tagList();
      scrollTo(0, 600);
    }
  }
}
</script>

<style>
</style>
