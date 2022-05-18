<template>
  <div>
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <!-- 所属分类：级联下拉列表 -->
      <!-- 一级分类 -->
      <el-form-item label="课程类别">
        <el-select
          v-model="searchObj.subjectParentId"
          placeholder="请选择"
          @change="subjectLevelOneChanged"
        >
          <el-option
            v-for="subject in oneSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
        <!-- 二级分类 -->
        <el-select v-model="searchObj.subjectId" placeholder="请选择">
          <el-option
            v-for="subject in twoSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
      </el-form-item>
      <!-- 标题 -->
      <el-form-item label="课程标题">
        <el-input v-model="searchObj.title" placeholder="课程标题" />
      </el-form-item>
      <!-- 讲师 -->
      <el-form-item label="讲师">
        <el-select v-model="searchObj.teacherId" placeholder="请选择讲师">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"
          />
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getPage()"
        >查 询</el-button
      >
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
    <!-- 表格 -->
    <el-table
      class="tableFrom"
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row
      row-class-name="myClassList"
    >
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="课程信息" width="470" align="center">
        <template slot-scope="scope">
          <div class="info">
            <div class="pic">
              <img :src="scope.row.cover" alt="scope.row.title" width="150px" />
            </div>
            <div class="title">
              <a href="">{{ scope.row.title }}</a>
              <p>讲师：{{ scope.row.teacherId }}</p>
              <p>课程时长：{{ scope.row.lessonNum }}</p>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="浏览人数" align="center">
        <template slot-scope="scope">
          {{ scope.row.viewCount }}
        </template>
      </el-table-column>
      <el-table-column label="发布时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.gmtModified.substr(0, 10) }}
        </template>
      </el-table-column>
      <el-table-column label="价格" width="100" align="center">
        <template slot-scope="scope">
          {{
            Number(scope.row.price) === 0
              ? "免费"
              : "¥" + scope.row.price.toFixed(2)
          }}
        </template>
      </el-table-column>
      <el-table-column
        prop="buyCount"
        label="付费学员"
        width="100"
        align="center"
      >
        <template slot-scope="scope">
          {{ scope.row.buyCount }}人
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <router-link :to="'/edu/course/info/' + scope.row.id">
            <el-button
              type="text"
              size="mini"
              icon="el-icon-edit"
              class="operaBtn"
              >编辑信息</el-button
            >
          </router-link>
          <router-link :to="'/edu/course/chapter/' + scope.row.id">
            <el-button
              type="text"
              size="mini"
              icon="el-icon-edit"
              class="operaBtn"
              >编辑大纲</el-button
            >
          </router-link>
          <el-button
            type="text"
            size="mini"
            icon="el-icon-delete"
            class="operaBtn"
            @click="deleteCourse(scope.row.id)"
            >删除课程</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @size-change="getPage"
      @current-change="getPage"
    />
  </div>
</template>

<script>
import course from "@/api/course/courseApi";
import subject from "@/api/course/subjectApi";
export default {
  data() {
    return {
      page: 1,
      total: 0,
      limit: 5,
      list: [],
      listLoading: true,
      searchObj: {
        // 一级分类
        subjectParentId: "",
        // 二级分类
        subjectId: "",
        title: "",
        teacherId: ""
      },
      oneSubjectList: [],
      twoSubjectList: [],
      teacherList: []
    };
  },
  created() {
    this.getTeacherList();
    this.getPage();
    this.getSubjectList();
  },
  methods: {
    // 分页查询
    getPage(page = 1) {
      this.page = page;
      this.listLoading = true;
      course.getList(this.page, this.limit, this.searchObj).then(res => {
        this.list = res.data.courses;
        this.total = res.data.total;
        this.listLoading = false;
        for (let i = 0; i < this.list.length; i++) {
          this.teacherList.forEach(teacher => {
            if (teacher.id == this.list[i].teacherId) {
              this.list[i].teacherId = teacher.name;
            }
          });
        }
      });
    },
    subjectLevelOneChanged(val) {
      this.oneSubjectList.forEach(item => {
        if (item.id === val) {
          this.twoSubjectList = item.children;
        }
      });
    },
    resetData() {
      this.searchObj = {};
      this.getPage();
    },
    // 删除课程
    deleteCourse(id) {
      this.$confirm("此操作将删除该课程, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        course.deleteCourse(id).then(res => {
          this.$message({
            message: "成功删除课程",
            type: "success"
          });
          this.getPage();
        });
      });
    },
    // 获取讲师列表
    getTeacherList() {
      course.getTeacherList().then(res => {
        this.teacherList = res.data.items;
      });
    },
    // 获取课程分类列表
    getSubjectList() {
      subject.getSubjectList().then(res => {
        this.oneSubjectList = res.data.list;
      });
    }
  }
};
</script>
<style scoped>
.myClassList .info {
  width: 450px;
  overflow: hidden;
}
.myClassList .info .pic {
  width: 150px;
  height: 90px;
  overflow: hidden;
  float: left;
}
.myClassList .info .pic a {
  display: block;
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
}
.myClassList .info .pic img {
  display: block;
  width: 100%;
}
.myClassList td .info .title {
  width: 280px;
  float: right;
}
.myClassList td .info .title a {
  display: block;
  height: 48px;
  line-height: 24px;
  overflow: hidden;
  color: #00baf2;
  margin-bottom: 12px;
}
.myClassList td .info .title p {
  line-height: 20px;
  margin-top: 5px;
  color: #818181;
}
.demo-form-inline {
  margin-top: 30px;
  margin-left: 80px;
}
.tableFrom {
  padding: 20px;
  margin-right: 20px;
}
.operaBtn {
  margin-right: 30px;
}
</style>
