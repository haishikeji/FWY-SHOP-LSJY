<template>
  <div id="testView" class="app-container">

    <div class="filter-container">
      <el-button size="medium" icon="el-icon-plus" type="primary" @click="toAdd">新增酿酒厂</el-button>
    </div>

    <el-table :data="pageInfo" border stripe height="660" style="width: 100%;">
      <el-table-column prop="breweryId" label="酒厂id" width="100" />
      <el-table-column prop="name" label="酒厂名称" width="100" />
      <el-table-column label="酒厂logo" width="120" >
        <template slot-scope="scope">
          <img :src="scope.row.logImg" style="width: 80px;" >
        </template>
      </el-table-column>
      <el-table-column label="封面图" width="120" >
        <template slot-scope="scope">
          <img :src="scope.row.coverImg" style="width: 80px;" >
        </template>
      </el-table-column>
      <el-table-column label="logo图主色" width="100" >
        <template slot-scope="scope">
          <div :style="{background: scope.row.bgColor}" style="width: 40px;height: 40px;"></div>
        </template>
      </el-table-column>
      <el-table-column prop="districtName" label="酒厂地址" />
      <el-table-column label="酒厂详情" >
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="edit(scope.row.breweryId, scope.row.content)">
            <label>编辑详情</label>
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" >
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="modify(scope.$index, scope.row)">
            <label>编辑</label>
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="detail_view" top="10px" width="61%" title="酒厂详情">
      <div style="display: flex;">
        <div style="display: content;margin-left: 10px;">
          <el-upload ref="uploadImg" action="" :auto-upload="true" :limit="1" list-type="picture" :before-upload="addMainImg">
            <el-button slot="trigger" type="primary">上传主图</el-button>
          </el-upload>
        </div>
        <div style="display: content;margin-left: 10px;"><el-button type="primary" @click="addSlogan">添加slogan</el-button></div>
        <div style="display: content;margin-left: 10px;"><el-button type="primary" @click="addIntr">添加介绍</el-button></div>
        <div style="display: content;margin-left: 10px;"><el-button type="primary" @click="addT">添加小标题</el-button></div>
        <div style="display: content;margin-left: 10px;"><el-button type="primary" @click="addP">添加段落</el-button></div>
        <div style="display: content;margin-left: 10px;">
          <el-upload ref="uploadImg" action="" :auto-upload="true" :limit="1" list-type="picture" :before-upload="addImg">
            <el-button slot="trigger" type="primary">上传图片</el-button>
          </el-upload>
        </div>
      </div>
      <div style="display: flex;margin-top: 30px;">
        <div style="margin-right: 30px;width: 300px;">
          <el-input type="textarea" :autosize="{ minRows: 12, maxRows: 20}" v-model="editParams.content" placeholder="详情内容"></el-input>
        </div>
        <div v-html="editParams.content" class="showDetail"></div>
      </div>
      <div style="text-align:center;">
        <el-button type="primary" @click="keep()">保存</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="add_view" top="10px" width="71%" title="添加酿酒厂">
      <el-form label-width="180px" label-position="right" size="medium">
        <el-form-item label="酒厂名" prop="name">
          <el-input v-model="query.name" style="width:200px" />
        </el-form-item>
        <el-form-item label="城市" prop="city">
          <el-select v-model="province_select" placeholder="请选择" @change="cityChangeP">
            <el-option
              v-for="item in cityData.provinceList"
              :key="item.districtId"
              :label="item.districtName"
              :value="item.districtId">
            </el-option>
          </el-select>
          <el-select v-model="city_select" placeholder="请选择" @change="cityChangeC">
            <el-option
              v-for="item in cityList"
              :key="item.districtId"
              :label="item.districtName"
              :value="item.districtId">
            </el-option>
          </el-select>
          <el-select v-model="district_select" placeholder="请选择" @change="cityChangeD">
            <el-option
              v-for="item in districtList"
              :key="item.districtId"
              :label="item.districtName"
              :value="item.districtId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="logo图" prop="file">
          <el-upload
            ref="uploadImg"
            action=""
            :auto-upload="true"
            :limit="1"
            list-type="picture"
            :before-upload="uploadLogo"
          >
            <el-button slot="trigger" size="small" type="primary">选取logo图标</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="" prop="">
          <img v-if="query.logImg" :src="query.logImg" style="width:100px;height: auto;" alt="">
        </el-form-item>
        <el-form-item label="封面图片" prop="file">
          <el-upload
            ref="uploadImg"
            action=""
            :auto-upload="true"
            :limit="1"
            list-type="picture"
            :before-upload="uploadCover"
          >
            <el-button slot="trigger" size="small" type="primary">选取封面图片</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="" prop="">
          <img v-if="query.coverImg" :src="query.coverImg" style="width:100px;height: auto;" alt="">
        </el-form-item>
      </el-form>
      <div style="text-align:center;">
        <el-button type="info" plain @click="resetForm()">取消</el-button>
        <el-button type="primary" @click="save()">确定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { getBreweryList, getCitydata, getColor, imgUpload, breweryAdd, breweryUpdate } from '@/api/zly'
import { deepClone, parseTime } from '@/utils'
import { scrollTo } from '@/utils/scrollTo'

export default {
  data() {
    return {
      pageInfo: [],
      detail_view: false,
      add_view: false,
      cityData: {},
      cityList: [],
      districtList: [],
      province_select: '',
      city_select: '',
      district_select: '',
      query: {
        districtId: null,
        bgColor: '',
        logImg: '',
        coverImg: '',
        name: '',
        content: ''
      },
      editParams: {
        breweryId: null,
        content: '',
      }
    }
  },
  computed: {

  },
  created() {
    this.getCity();
    this.loadDatas();
  },
  mounted() {
     
  },
  methods: {
    modify(index, row) {
      this.query =  {
        districtId: row.districtId,
        breweryId: row.breweryId,
        bgColor: row.bgColor,
        logImg: row.logImg,
        coverImg: row.coverImg,
        name: row.name
      };
      this.edit_status = true;
      this.add_view = true;
    },
    keep() {
      breweryUpdate(this.editParams).then((res) => {
        this.$notify.success({ title: '保存成功。' })
        this.loadDatas();
        this.detail_view = false;
      })
    },
    save(formName) {
      if(this.edit_status) {
        breweryUpdate(this.query).then((res) => {
          this.$notify.success({ title: '提交完成。' })
          this.loadDatas();
          this.resetForm(formName)
        })
      }else{
        breweryAdd(this.query).then((res) => {
          this.$notify.success({ title: '提交完成。' })
          this.loadDatas();
          this.resetForm(formName)
        })
      }

    },
    resetForm(formName) {
      this.add_view = false;
    },
    uploadCover(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJPG) {
        this.$message.error('请上传 jpg/png 格式图片!')
        this.params = null;
        return false
      } else {
        this.params = new FormData();
        this.params.set('file', file);
        this.params.set('subPath', 'admin/cover/');
        imgUpload(this.params).then(res => {
          if (res.data.code === 200) {
              this.$notify.success({ title: '上传成功' });
              this.query.coverImg = res.data.data;
              console.log(res.data.data)
            // return false
          }
        })
      }
    },
    uploadLogo(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJPG) { 
        this.$message.error('请上传 jpg/png 格式图片!')
        this.params = null;
        return false
      } else {
        this.params = new FormData();
        this.params.set('file', file);
        this.params.set('subPath', 'admin/logo/');
        imgUpload(this.params).then(res => {
          if (res.data.code === 200) {
              this.$notify.success({ title: '上传成功' });
              this.query.logImg = res.data.data;
              console.log(res.data.data)
            // return false
          }
        })
        getColor(this.params).then(res => {
          if (res.data.code === 200) {
              this.query.bgColor = res.data.data;
              console.log(res.data.data)
            // return false
          }
        })
      }
    },
    cityChangeP(value) {
      this.city_select = '';
      this.district_select = '';
      this.query.districtId = value;
      this.cityList = this.cityData.cityList.filter(item => item.pid==this.province_select );
    },
    cityChangeC(value) {
      this.query.districtId = value;
      this.district_select = '';
      this.districtList = this.cityData.districtList.filter(item => item.pid==this.city_select );
    },
    cityChangeD(value) {
      this.query.districtId = value;
    },
    toAdd() {
      this.query =  {
        districtId: null,
        bgColor: '',
        logImg: '',
        coverImg: '',
        name: '',
        content: ''
      };
      this.edit_status = false;
      this.add_view = true;
    },
    addMainImg(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJPG) {
        this.$message.error('请上传 jpg/png 格式图片!')
        this.params = null;
        return false
      } else {
        this.params = new FormData();
        this.params.set('file', file);
        this.params.set('subPath', 'admin/content/');
        imgUpload(this.params).then(res => {
          if (res.data.code === 200) {
              this.$notify.success({ title: '上传成功' });
              let imgUrl = res.data.data;
              let imgText = "<img class='mainImg' src='" + imgUrl + "' />\n";
              this.editParams.content = this.editParams.content + imgText;
              console.log(res.data.data)
          }
        })
      }
    },
    addSlogan() {
      let TText = "<div class='sloganCss'></div>\n";
      this.editParams.content = this.editParams.content + TText;
    },
    addIntr() {
      let TText = "<div class='intrCss'></div>\n";
      this.editParams.content = this.editParams.content + TText;
    },
    addT() {
      let TText = "<div class='Subtitle'></div>\n";
      this.editParams.content = this.editParams.content + TText;
    },
    addP() {
      let pText = "<div class='contentText'></div>\n";
      this.editParams.content = this.editParams.content + pText;
    },
    addImg(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJPG) {
        this.$message.error('请上传 jpg/png 格式图片!')
        this.params = null;
        return false
      } else {
        this.params = new FormData();
        this.params.set('file', file);
        this.params.set('subPath', 'admin/content/');
        imgUpload(this.params).then(res => {
          if (res.data.code === 200) {
              this.$notify.success({ title: '上传成功' });
              let imgUrl = res.data.data;
              let imgText = "<img class='contentImg' src='" + imgUrl + "' />\n";
              this.editParams.content = this.editParams.content + imgText;
              console.log(res.data.data)
          }
        })
      }
    },
    edit(id, content) {
      this.editParams.breweryId = id;
      this.editParams.content = content;
      this.detail_view = true;
    },
    // 获取数据
    loadDatas() {
      getBreweryList().then((res) => {
        console.log(res);
        this.pageInfo = res.data.data
      })
    },
    getCity() {
      getCitydata().then((res) => {
        this.cityData = res.data.data;
      })
    }
  }
}
</script>

<style>
.showDetail{
  width: 320px;
  height: 400px;
  border: 1px solid #f2f2f2;
  overflow-x: hidden;
  overflow-y: scroll;
  display: flex;
  flex-direction: column;
}
.Subtitle{
  margin: 20px;
  width: 260px;
  height: 46px;
  background: #9D172F;
  border-radius: 4px;
  font-size: 19px;
  font-weight: 500;
  color: #FFFFFF;
  line-height: 46px;
  text-align: center;
}
.contentText{
  margin: 20px;
  width: 260px;
  height: auto;
  line-height: 28px;
  font-size: 15px;
  white-space: pre-line;
}
.mainImg{
  width: 300px;
  height: auto;
}
.contentImg{
  margin: 20px;
  width: 260px;
  height: auto;
  border-radius: 4px;
}
.sloganCss{
  width: 300px;
  height: 92px;
  background: #9D172F;
  font-size: 19px;
  font-weight: 500;
  color: #FFFFFF;
  line-height: 46px;
  text-align: center;
  white-space: pre-line;
}
.intrCss{
  margin: 20px;
  width: 260px;
  height: auto;
  background: #FFFECE;
  line-height: 28px;
  font-size: 15px;
  padding: 16px;
  white-space: pre-line;
}
</style>
