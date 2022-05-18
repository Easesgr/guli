<template>
  <div>
    <div>
      <el-form :inline="true" :model="teacherQuery" class="demo-form-inline">
        <el-form-item label="讲师名">
          <el-input v-model="teacherQuery.name" placeholder="讲师名"></el-input>
        </el-form-item>
        <el-form-item label="讲师级别">
          <el-select v-model="teacherQuery.level" placeholder="讲师级别">
            <el-option label="高级讲师" value="1"></el-option>
            <el-option label="特级讲师" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-date-picker
            type="date"
            placeholder="起始日期"
            v-model="teacherQuery.begin"
            style="width: 100%;"
          ></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-date-picker
            type="date"
            placeholder="结束日期"
            v-model="teacherQuery.end"
            style="width: 100%;"
          ></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getList()" round size="mini"
            >查询</el-button
          >
          <el-button type="default" @click="cancel()" round size="mini"
            >取消</el-button
          >
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" style="width: 100% " border>
      <el-table-column label="主键" type="index">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="日期" sortable>
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.gmtCreate }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="姓名"></el-table-column>

      <el-table-column prop="career" label="讲师资历"></el-table-column>

      <el-table-column
        prop="intro"
        label="讲师简介"
        show-overflow-tooltip
      ></el-table-column>

      <el-table-column label="讲师头衔">
        <template slot-scope="scope">
          {{ scope.row.level === 1 ? "高级讲师" : "特级讲师" }}
        </template>
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <router-link to="/teacher/add">
            <el-button size="mini" type="primary" round>新增</el-button>
          </router-link>
          <router-link :to="'/teacher/edit/' + scope.row.id">
            <el-button type="primary" round size="mini">修改</el-button>
          </router-link>

          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row.id)"
            round
            >删除</el-button
          >
        </template>
      </el-table-column>
      <template></template>
    </el-table>

    <div class="block" style="margin-top:15px;">
      <el-pagination
        background
        class="pages"
        @size-change="getList"
        @current-change="getList"
        :current-page="page"
        :page-size="limit"
        layout="total, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
import teacher from "@/api/teacher/teacherApi";
export default {
  data() {
    return {
      listLoading: true,
      page: 1,
      limit: 10,
      total: 0,
      list: [],
      teacherQuery: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList(page = 1) {
      this.page = page;
      teacher
        .getTeacherList(this.page, this.limit, this.teacherQuery)
        .then(response => {
          this.total = response.data.total;
          this.list = response.data.items;
        });
    },
    handleDelete(id) {
      console.log(id);
      this.$confirm("是否删除该讲师", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "success"
      })
        .then(() => {
          teacher.deleteById(id).then(response => {
            this.$message({
              message: "删除成功",
              type: "info"
            });
          });
          this.getList();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    cancel() {
      this.teacherQuery = {};
      this.getList();
    }
  }
};
</script>
<style scoped>
.demo-form-inline {
  margin-top: 20px;
  margin-left: 40px;
}
.pages {
  text-align: center;
  margin-top: 10px;
}
el-table-column {
  text-align: center;
}
</style>
