<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps
      :active="1"
      process-status="wait"
      align-center
      style="margin-bottom: 40px;"
    >
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="最终发布" />
    </el-steps>

    <el-form label-width="120px">
      <el-form-item label="课程标题">
        <el-input
          v-model="courseInfo.title"
          placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"
        />
      </el-form-item>

      <!-- 所属分类 TODO -->
      <el-form-item label="课程分类">
        <el-select
          v-model="courseInfo.subjectParentId"
          placeholder="一级分类"
          @change="subjectLevelOneChanged"
        >
          <el-option
            v-for="subject in subjectOneList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>

        <!-- 二级分类 -->
        <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
          <el-option
            v-for="subject in subjectTwoList"
            clearable
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
      </el-form-item>

      <!-- 课程讲师 TODO -->
      <!-- 课程讲师 -->
      <el-form-item label="课程讲师">
        <el-select v-model="courseInfo.teacherId" placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number
          :min="0"
          v-model="courseInfo.lessonNum"
          controls-position="right"
          placeholder="请填写课程的总课时数"
        />
      </el-form-item>

      <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description"></tinymce>
      </el-form-item>

      <!-- 课程封面 TODO -->
      <!-- 课程封面-->
      <el-form-item label="课程封面">
        <el-upload
          class="avatar-uploader"
          list-type="picture-card"
          accept=".gif, .jpg, .jpeg, .png, .GIF, .JPG, .PNG"
          :action="BASE_API + '/oss-service/uploadFile'"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <img :src="courseInfo.cover" class="avatar" />
        </el-upload>
      </el-form-item>

      <el-form-item label="课程价格">
        <el-input-number
          :min="0"
          v-model="courseInfo.price"
          controls-position="right"
          placeholder="免费课程请设置为0元"
        />元
      </el-form-item>

      <el-form-item class="sumitBtn">
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="saveOrUpdate"
          >保存并下一步</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import course from "@/api/course/courseApi";
import subject from "@/api/course/subjectApi";
import Tinymce from "@/components/Tinymce"; //引入组件
export default {
  components: { Tinymce },
  data() {
    return {
      saveBtnDisabled: false,
      courseInfo: {
        id: "",
        title: "",
        subjectId: "", //二级分类id
        subjectParentId: "", //一级分类id
        teacherId: "",
        lessonNum: 0,
        description: "",
        cover:
          "https://edu-teacher-120.oss-cn-hangzhou.aliyuncs.com/2022/05/13/49f1507d-063b-4fcb-b872-f553f38bd5eefile.png",
        price: 0
      },
      imageUrl: false,
      BASE_API: process.env.BASE_API, // 接口API地址
      teacherList: [], //封装所有的讲师
      subjectOneList: [], //一级分类
      subjectTwoList: [] //二级分类
    };
  },
  created() {
    this.getTeacherList();
    this.getSubjectList();
    if (this.$route.params.id) {
      this.getCourseById(this.$route.params.id);
    }
  },
  methods: {
    // 获取讲师列表
    getTeacherList() {
      course.getTeacherList().then(response => {
        this.teacherList = response.data.items;
      });
    },
    // 获取 课程分类信息，完成二级联动信息
    getSubjectList() {
      subject.getSubjectList().then(response => {
        this.subjectOneList = response.data.list;
      });
    },
    // 封面上传前对图片格式和图片大小进行校验
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    // 封面上传成功后处理
    handleAvatarSuccess(res, file) {
      this.courseInfo.cover = res.data.url;
      this.imageUrl = true;
    },
    // 一级分类变动后，获取二级分类信息
    subjectLevelOneChanged(val) {
      this.subjectOneList.forEach(item => {
        if (item.id == val) {
          this.subjectTwoList = [];
          // 遍历获取到与id相同的值
          this.subjectTwoList = item.children;
          // 下一次变化的时候将二级菜单栏清空
        }
      });
    },
    // 根据课程id获取课程信息
    getCourseById(courseId) {
      var _this = course.getCourseById(courseId).then(response => {
        this.courseInfo = response.data.courseVo;
        subject.getSubjectList().then(response => {
          this.subjectOneList = response.data.list;
          this.subjectOneList.forEach(item => {
            if (item.id == this.courseInfo.subjectParentId) {
              this.subjectTwoList = item.children;
            }
          });
        });
      });
    },
    // 添加课程
    addCourse() {
      course.addCourseInfo(this.courseInfo).then(response => {
        this.$message({
          message: "成功添加课程信息",
          type: "success"
        });
        this.$router.push("/edu/course/chapter/" + response.data.courseId);
      });
    },
    // 更新课程
    updateCourse() {
      course.updateCourse(this.courseInfo).then(response => {
        this.$message({
          message: "成功修改课程信息",
          type: "success"
        });
        this.$router.push("/edu/course/chapter/" + this.courseInfo.id);
      });
    },
    saveOrUpdate() {
      if (!this.courseInfo.id) {
        this.addCourse();
      } else {
        this.updateCourse();
      }
    }
  }
};
</script>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.sumitBtn {
  text-align: center;
}
</style>
