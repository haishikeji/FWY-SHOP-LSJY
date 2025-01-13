<template>
  <div id="menuMetaData" class="app-container">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="菜单列表" name="first">
        <el-table :data="menuList" border style="width: 100%">
          <el-table-column prop="icon" label="图标名" />
          <el-table-column prop="desc" label="菜单名" />
          <el-table-column prop="modules" label="菜单的模块中文名称 - 使用值" width="300">
            <template slot-scope="scope">
              <p v-for=" m in scope.row.modules" :key="m.pname">{{ m.desc }} - &nbsp; {{ m.pname }}</p>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="模块列表" name="second">
        <el-table :data="moduleList" border stripe style="width: 100%">
          <el-table-column prop="desc" label="模块名" />
          <el-table-column prop="pname" label="模块值" />
          <el-table-column prop="icon" label="图标名" />
          <el-table-column prop="vue" label="视图路径" />
          <el-table-column prop="display" :formatter="checkDis" label="菜单显示" />
          <el-table-column prop="nodes" label="模块中文名称 - 使用值" width="560">
            <template slot-scope="scope">
              <p v-for=" m in scope.row.nodes" :key="scope.row.pname+m.pname">{{ m.desc }} - &nbsp; {{ m.pname }}</p>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="权限节点列表" name="third">
        <el-table :data="nodeList" height="700" border stripe style="width: 100%">
          <el-table-column prop="index" label="索引" />
          <el-table-column prop="pname" label="节点值" />
          <el-table-column prop="desc" label="节点描述" />
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import { getMenuMetaData } from '@/api/menu'
export default {
  data() {
    return {
      activeName: 'first',
      menuList: [],
      moduleList: [],
      nodeList: []
    }
  },
  created() {
    this.loadMenuMetaData()
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event)
    },
    loadMenuMetaData() {
      getMenuMetaData().then((res) => {
        const ret = res.data.data
        this.menuList = ret.menuList
        this.moduleList = ret.moduleList
        this.nodeList = ret.nodeList
      })
    },
    checkDis(row, colume) {
      if (row.display) {
        return '显示'
      } else {
        return '不显示'
      }
    }
  }
}
</script>

<style scoped="scoped">
  .el-table .warning-row {
    background: oldlace;
  }
</style>
