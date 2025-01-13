<!-- 添加技能 -->
<template>
  <div id="play_game_config" class="app-container">

    <div class="filter-container">
      <el-button size="medium" icon="el-icon-plus" type="primary" @click="toAdd">添加技能</el-button>
    </div>

    <el-tabs v-model="activeName" @tab-click="handleClick">

      <el-tab-pane label="全部" name="first">
        <el-table :data="pageInfo.list" border stripe height="660" style="width: 100%;">
          <el-table-column prop="gameName" label="技能名称" align="center" width="100" />
          <el-table-column label="技能封面" align="center" width="220">
            <template slot-scope="scope">
              <img :src="scope.row.gameCover" style="width: 180px;">
            </template>
          </el-table-column>
          <el-table-column label="示例图" align="center" width="220">
            <template slot-scope="scope">
              <img :src="scope.row.certificationPictureTemplate" style="width: 180px;">
            </template>
          </el-table-column>
          <el-table-column label="计费方式" align="center">
            <template slot-scope="scope">
              <div v-for="item in billingWay" :key="item.num">
                <span v-if="item.num == scope.row.billingWay">{{ item.value }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="游戏区服" >
            <template slot-scope="scope">
              <div v-if="scope.row.gameZone">
                <div v-for="item in scope.row.gameZone" :key="item">
                  <label>{{ item }}</label>
                </div>
              </div>
              <div v-else>
                <label>—</label>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="擅长位置" >
            <template slot-scope="scope">
              <div v-if="scope.row.goodAtLocation">
                <div v-for="item in scope.row.goodAtLocation" :key="item">
                  <label>{{ item }}</label>
                </div>
              </div>
              <div v-else>
                <label>—</label>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="sortScore" label="排序" align="center" />
          <el-table-column label="操作" align="center" width="100">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="edit(scope.row)">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

    </el-tabs>

    <el-dialog :visible.sync="dialogVisible" top="10px" title="添加/编辑技能">
      <el-form ref="form1" :model="rowData" label-width="140px" label-position="right" size="medium">
        <el-form-item label="技能名称">
          <el-input v-model="rowData.skillName" placeholder="请填写名称" style="width:240px" />
        </el-form-item>
        <el-form-item label="语音介绍示例">
          <el-input v-model="rowData.voiceIntroduceTemp" type="textarea" :autosize="{ minRows: 3, maxRows: 6}" maxlength="140" show-word-limit style="width:440px" />
        </el-form-item>
        <el-form-item label="技能封面">
          <el-upload
            ref="uploadSkillCover"
            action=""
            :auto-upload="false"
            :limit="1"
            :file-list="skillCover_list"
            list-type="picture"
            :before-upload="SkillCoverBefore"
            :on-remove="removeSkillCover"
          >
            <el-button slot="trigger" size="small" type="primary">选取封面图片</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="示例图">
          <el-upload
            ref="uploadCertificationPicTemp"
            action=""
            :auto-upload="false"
            :limit="1"
            :file-list="certificationPicTemp_list"
            list-type="picture"
            :before-upload="CertificationPicTempBefore"
            :on-remove="removeCertificationPicTemp"
          >
            <el-button slot="trigger" size="small" type="primary">选择示例图</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="认证段位">
          <el-select v-model="rowData.dictionaryId" style="width: 140px">
            <el-option v-for="item in dictionaryList" :key="item.dictionaryId" :label="item.name" :value="item.dictionaryId" />
          </el-select>
        </el-form-item>
        <el-form-item label="计费方式">
          <el-select v-model="rowData.billingWay" style="width: 140px">
            <el-option v-for="item in billingWay" :key="item.billingWay" :label="item.value" :value="item.num" />
          </el-select>
        </el-form-item>
        <el-form-item label="游戏区服">
          <el-input v-model="rowData.gameZone" :placeholder="gameZoneChecked?'例:微信区/QQ区':''" :disabled="!gameZoneChecked" style="width:240px" />
          <el-checkbox v-model="gameZoneChecked"></el-checkbox>
        </el-form-item>
        <el-form-item label="擅长位置">
          <el-input v-model="rowData.goodAtLocation" :placeholder="goodAtLocationChecked?'例:打野/中路/下路':''" :disabled="!goodAtLocationChecked" style="width:240px" />
          <el-checkbox v-model="goodAtLocationChecked"></el-checkbox>
        </el-form-item>
        <el-form-item label="是否修改段位">
          <el-radio v-model="rowData.isEditRank" label='1'>是</el-radio>
          <el-radio v-model="rowData.isEditRank" label='0'>否</el-radio>
        </el-form-item>
        <el-form-item label="排序">
          <el-input v-model="rowData.sortScore" placeholder="大到小排列" style="width:240px" />
        </el-form-item>
        <el-form-item label="(安卓)最小支持版本">
          <el-input v-model="rowData.minAndroidVersion" style="width:240px" />
        </el-form-item>
        <el-form-item label="(iOS)最小支持版本">
          <el-input v-model="rowData.minIosVersion" style="width:240px" />
        </el-form-item>
      </el-form>
      <div style="text-align:center;">
        <el-button type="danger" @click="resetForm('form1')"> 取 消 </el-button>
        <el-button type="primary" @click="save('form1')">提交保存</el-button>
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
import { play_game_configList, play_game_configAdd, play_game_configUpdate, play_game_configListRank, play_game_configListBillingWay, play_game_configUpload } from '@/api/gamesAccompany'
import { deepClone, parseTime } from '@/utils'
import { scrollTo } from '@/utils/scrollTo'

export default {
  data() {
    return {
      activeName: 'first',
      dialogVisible: false,
      listQuery: {
        pageNum: 1,
        pageSize: 20
      },
      dictionaryList: [],
      billingWay: [],
      pageInfo: {},
      rowData: {
        skillName: null,
        voiceIntroduceTemp: null,
        dictionaryId: null,
        billingWay: null,
        skillCover: null,
        certificationPicTemp: null,
        gameZone: null,
        goodAtLocation: null,
        isEditRank: '1',
        sortScore: null,
        minAndroidVersion: null,
        minIosVersion: null
      },
      isEdit: false,
      skillCover_list:[],
      certificationPicTemp_list:[],
      gameZoneChecked: false,
      goodAtLocationChecked: false,
    }
  },
  mounted() {
    this.loadDatas()
  },
  methods: {
    removeSkillCover(file, fileList){
      this.skillCover_list = fileList
      //console.log(file, fileList);
    },
    removeCertificationPicTemp(file, fileList){
      this.certificationPicTemp_list = fileList
      //console.log(file, fileList);
    },
    // 取消
    resetForm(topicForm) {
      this.dialogVisible = false
      this.rowData = {
        skillName: null,
        voiceIntroduceTemp: null,
        dictionaryId: null,
        billingWay: null,
        skillCover: null,
        certificationPicTemp: null,
        isEditRank: '1',
        sortScore: null,
        minAndroidVersion: null,
        minIosVersion: null
      }
    },
    // 新增
    toAdd() {
      this.skillCover_list = [];
      this.certificationPicTemp_list = [];
      this.rowData = {
        skillName: null,
        voiceIntroduceTemp: null,
        dictionaryId: null,
        billingWay: null,
        skillCover: null,
        certificationPicTemp: null,
        isEditRank: '1',
        sortScore: null,
        minAndroidVersion: null,
        minIosVersion: null
      }
      this.isEdit = false
      this.dialogVisible = true
    },
    // 确定保存
    save() {

      if(this.isEdit == false){

        this.$refs.uploadSkillCover.submit()
        
      }else if(this.isEdit == true){

        if(this.skillCover_list.length == 0){
          this.$refs.uploadSkillCover.submit()
        }else if(this.certificationPicTemp_list.length == 0){
          this.$refs.uploadCertificationPicTemp.submit()
        }else{
          this.addGameSkill()
        }
      }
    },
    // 上传封面图片前
    SkillCoverBefore(file) {
      const params = new FormData()
      params.append('file', file)
      params.append('isCoverImg', true)
      play_game_configUpload(params).then(res => {
        // console.log(res.data.data);
        this.rowData.skillCover = res.data.data
        if(this.isEdit == false){
          this.$refs.uploadCertificationPicTemp.submit()
        }else{
          if(this.certificationPicTemp_list.length == 0){
            this.$refs.uploadCertificationPicTemp.submit()
          }else{
            this.addGameSkill()
          }
        }
      })
      return false
    },
    // 上传示例图前
    CertificationPicTempBefore(file) {
      const params = new FormData()
      params.append('file', file)
      params.append('isCoverImg', false)
      play_game_configUpload(params).then(res => {
        // console.log(res.data.data);
        this.rowData.certificationPicTemp = res.data.data
        this.addGameSkill()
      })
      return false
    },
    addGameSkill() {
      if (this.rowData.skillName == null || this.rowData.skillName == '') {
        this.$notify.error({ title: '请填写游戏名称' })
      } else if (this.rowData.voiceIntroduceTemp == null || this.rowData.voiceIntroduceTemp == '') {
        this.$notify.error({ title: '请填写语介绍' })
      } else if (this.rowData.dictionaryId == null) {
        this.$notify.error({ title: '请选择游戏段位' })
      } else if (this.rowData.billingWay == null) {
        this.$notify.error({ title: '请选择计费方式' })
      } else if (this.isEdit == false) {
        play_game_configAdd(this.rowData).then(res => {
          this.$notify.success({ title: '添加成功' })
          this.loadDatas()
          this.dialogVisible = false
        })
      } else if (this.isEdit == true) {
        play_game_configUpdate(this.rowData).then(res => {
          this.$notify.success({ title: '编辑成功' })
          this.loadDatas()
          this.dialogVisible = false
        })
      }
    },
    //编辑
    edit (row) {
      this.skillCover_list = [{name:row.gameName, url:row.gameCover}];
      this.certificationPicTemp_list = [{name:row.gameName, url:row.certificationPictureTemplate}],
      this.rowData = {
        skillName: row.gameName,
        voiceIntroduceTemp: row.voiceIntroduceTemp,
        dictionaryId: String(row.dictionaryId),
        billingWay: row.billingWay,
        skillCover: row.gameCover,
        certificationPicTemp: row.certificationPictureTemplate,
        id: row.id,
        gameZone : row.gameZone ? row.gameZone.join("/") : null,
        goodAtLocation : row.goodAtLocation ? row.goodAtLocation.join("/") : null,
        isEditRank: String(row.isEditRank),
        sortScore: row.sortScore,
        minAndroidVersion: row.minAndroidVersion,
        minIosVersion: row.minIosVersion
      }
      if(row.gameZone != null){
        this.gameZoneChecked = true
      }else{
        this.gameZoneChecked = false
      }
      if(row.goodAtLocation != null){
        this.goodAtLocationChecked = true
      }else{
        this.goodAtLocationChecked = false
      }
      this.dialogVisible = true
      this.isEdit = true
    },
    handleFilter() {
      this.loadDatas()
    },
    handleClick(tab, event) {
      this.listQuery.pageNum = 1
      if (tab.name === 'first') {
        this.loadDatas()
      }
    },
    // 获取数据
    loadDatas() {
      play_game_configList(this.listQuery).then((res) => {
        // console.log(res);
        this.pageInfo = res.data.data
      })
      play_game_configListRank().then((res) => {
        // console.log(res);
        this.dictionaryList = res.data.data
      })
      play_game_configListBillingWay().then((res) => {
        // console.log(res);
        this.billingWay = res.data.data
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
