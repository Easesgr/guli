<template>
  <div class="app-container">
    <el-form>
      <el-form-item label="信息描述">
        <el-tag type="info">excel模版说明</el-tag>
        <el-tag>
          <i class="el-icon-download"></i>
          <a
            href="https://edu-teacher-120.oss-cn-hangzhou.aliyuncs.com/2022/05/13/subject.xlsx"
            >点击下载模版</a
          >
        </el-tag>
      </el-form-item>
      <el-form-item label="选择Excel">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="fileUploadSuccess"
          :on-error="fileUploadError"
          :disabled="importBtnDisabled"
          :limit="1"
          :action="BASE_API + '/edu/subject/excel'"
          name="file"
          accept="application/vnd.ms-excel.xlsx"
        >
          <el-button slot="trigger" size="small" type="primary"
            >选取文件</el-button
          >
          <el-button
            :loading="loading"
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="submitUpload"
            >{{ fileUploadBtnText }}</el-button
          >
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      BASE_API: process.env.BASE_API, // 接口API地址
      OSS_PATH: process.env.OSS_PATH, // 阿里云OSS地址
      fileUploadBtnText: "上传到服务器", // 按钮文字
      importBtnDisabled: false, // 按钮是否禁用,
      loading: false
    };
  },
  methods: {
    submitUpload() {
      this.$refs.upload.submit();
    }
  }
};
</script>
<style scoped></style>
