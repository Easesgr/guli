<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps
      :active="3"
      process-status="wait"
      align-center
      style="margin-bottom:
40px;"
    >
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="发布课程" />
    </el-steps>
    <div class="ccInfo">
      <img :src="coursePublish.cover" />
      <div class="main">
        <h2>{{ coursePublish.title }}</h2>
        <p class="gray">
          <span>共{{ coursePublish.lessonNum }}课时</span>
        </p>
        <p>
          <span
            >所属分类：{{ coursePublish.subjectLevelOne }} —
            {{ coursePublish.subjectLevelTwo }}</span
          >
        </p>
        <p>课程讲师：{{ coursePublish.teacherName }}</p>
        <h3 class="red">￥{{ coursePublish.price }}</h3>
      </div>
    </div>
    <div class="publishBtn">
      <el-button @click="previous">返回修改</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="publish"
        >发布课 程</el-button
      >
    </div>
  </div>
</template>
<script>
import course from "@/api/course/courseApi";
export default {
  data() {
    return {
      saveBtnDisabled: false,
      coursePublish: {},
      courseId: ""
    };
  },
  created() {
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id;
      this.getPublishCourseInfo();
    }
  },
  methods: {
    getPublishCourseInfo() {
      course.getPublishCourse(this.courseId).then(res => {
        this.coursePublish = res.data.publishCourse;
      });
    },
    previous() {
      this.$router.push("/edu/course/chapter/" + this.courseId);
    },
    publish() {
      course.publishCourse(this.courseId).then(res => {
        this.$message({
          message: "成功发布课程",
          type: "success"
        });
        this.$router.push("/edu/course/list");
      });
    }
  }
};
</script>

<style scoped>
.ccInfo {
  background: #f5f5f5;
  padding: 20px;
  overflow: hidden;
  border: 1px dashed #ddd;
  margin-bottom: 40px;
  position: relative;
}
.ccInfo img {
  background: #d6d6d6;
  width: 500px;
  height: 278px;
  display: block;
  float: left;
  border: none;
}
.ccInfo .main {
  margin-left: 520px;
}
.ccInfo .main h2 {
  font-size: 28px;
  margin-bottom: 30px;
  line-height: 1;
  font-weight: normal;
}
.ccInfo .main p {
  margin-bottom: 10px;
  word-wrap: break-word;
  line-height: 24px;
  max-height: 48px;
  overflow: hidden;
}
.ccInfo .main p {
  margin-bottom: 10px;
  word-wrap: break-word;
  line-height: 24px;
  max-height: 48px;
  overflow: hidden;
}
.ccInfo .main h3 {
  left: 540px;
  bottom: 20px;
  line-height: 1;
  font-size: 28px;
  color: #d32f24;
  font-weight: normal;
  position: absolute;
}
.publishBtn {
  text-align: center;
}
</style>
