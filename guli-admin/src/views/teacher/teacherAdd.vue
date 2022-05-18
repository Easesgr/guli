<template>
  <div>
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      class="demo-ruleForm"
    >
      <el-form-item label="讲师主鍵" prop="id" v-if="false">
        <el-input v-model="ruleForm.id"></el-input>
      </el-form-item>
      <el-form-item label="讲师姓名" prop="name">
        <el-input v-model="ruleForm.name"></el-input>
      </el-form-item>
      <el-form-item label="讲师资历" prop="career">
        <el-input v-model="ruleForm.career"></el-input>
      </el-form-item>

      <el-form-item label="讲师头衔" prop="level">
        <el-radio-group v-model="ruleForm.level">
          <el-radio :label="1">高级讲师</el-radio>
          <el-radio :label="2">首席讲师</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="讲师简介" prop="intro">
        <el-input type="textarea" v-model="ruleForm.intro"></el-input>
      </el-form-item>
      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="ruleForm.avatar" />
        <!-- 文件上传按钮 -->
        <el-button
          type="primary"
          icon="el-icon-upload"
          @click="imagecropperShow = true"
          >更换头像
        </el-button>
        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="BASE_API + '/oss-service/uploadFile'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')"
          >立即创建</el-button
        >
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import teacher from "@/api/teacher/teacherApi";
import PanThumb from "@/components/PanThumb";
import ImageCropper from "@/components/ImageCropper";
export default {
  components: { ImageCropper, PanThumb },
  data() {
    return {
      ruleForm: {
        name: "",
        career: "",
        level: "",
        intro: "",
        avatar: ""
      },
      BASE_API: process.env.BASE_API, // 接口API地址
      imagecropperShow: false, // 是否显示上传组件
      imagecropperKey: 0, // 上传组件id
      rules: {
        name: [
          { required: true, message: "请输入讲师姓名", trigger: "blur" },
          { min: 2, max: 6, message: "长度在 3 到 5 个字符", trigger: "blur" }
        ],
        career: [
          { required: true, message: "请输入讲师资历", trigger: "change" }
        ],
        level: [
          { required: true, message: "请选择讲师头衔", trigger: "change" }
        ],
        intro: [{ required: true, message: "请填写讲师简介", trigger: "blur" }]
      }
    };
  },
  created() {
    this.init();
  },
  watch: {
    //路由變化方法
    $route(to, from) {
      this.init();
    }
  },
  methods: {
    submitForm() {
      if (!this.ruleForm.id) {
        this.addTeacher();
      } else {
        this.updateTeacher();
      }
    },
    addTeacher() {
      teacher.addTeacher(this.ruleForm).then(response => {
        this.$router.push("/teacher/list");
      });
    },
    updateTeacher() {
      teacher.updateTeacherById(this.ruleForm).then(response => {
        this.$router.push("/teacher/list");
      });
    },
    init() {
      if (this.$route.params.id) {
        teacher.getTeacherById(this.$route.params.id).then(response => {
          this.ruleForm = response.data.data;
        });
      } else {
        this.ruleForm = {};
      }
    },
    cropSuccess(data) {
      this.imagecropperShow = false;
      this.ruleForm.avatar = data.url;
      this.imagecropperKey = this.imagecropperKey + 1;
    },
    close() {
      this.imagecropperShow = false;
      this.imagecropperKey = this.imagecropperKey + 1;
    }
  }
};
</script>
<style scoped>
.demo-ruleForm {
  margin-top: 30px;
  padding: 0 10px;
}
</style>
