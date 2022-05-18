<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps
      :active="2"
      process-status="wait"
      align-center
      style="margin-bottom: 40px;"
    >
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="最终发布" />
    </el-steps>

    <el-button @click="openDialog()" type="primary">添加章节</el-button>

    <!-- 章节 -->
    <ul class="chanpterList">
      <li v-for="chapter in chapterVideoList" :key="chapter.id">
        <p>
          {{ chapter.title }}
          <span class="acts">
            <el-button style type="text" @click="openVideo(chapter.id)"
              >添加小节</el-button
            >
            <el-button style type="text" @click="openEditChatper(chapter.id)"
              >编辑</el-button
            >
            <el-button type="text" @click="removeChapter(chapter.id)"
              >删除</el-button
            >
          </span>
        </p>

        <!-- 视频 -->
        <ul class="chanpterList videoList">
          <li v-for="video in chapter.children" :key="video.id">
            <p>
              {{ video.title }}
              <span class="acts">
                <el-button style type="text" @click="editVideo(video.id)"
                  >编辑</el-button
                >
                <el-button type="text" @click="removeVideo(video.id)"
                  >删除</el-button
                >
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>
    <div class="bottomNextOrPrevious">
      <el-button @click="previous">上一步</el-button>
      <el-button type="primary" @click="next">下一步</el-button>
    </div>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapterInfo" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapterInfo.title" />
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number
            v-model="chapterInfo.sort"
            :min="0"
            controls-position="right"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateChapter">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item v-if="false" label="课时id">
          <el-input v-model="video.id" />
        </el-form-item>
        <el-form-item v-if="false" label="章节id">
          <el-input v-model="video.chapterId" />
        </el-form-item>
        <el-form-item label="课时标题">
          <el-input v-model="video.title" />
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number
            v-model="video.sort"
            :min="0"
            controls-position="right"
          />
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.isFree">
            <el-radio :label="1">免费</el-radio>
            <el-radio :label="0">收费</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="上传视频">
          <el-upload
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :action="BASE_API + '/vod-service/uploadVod'"
            :limit="1"
            class="upload-demo"
          >
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">
                最大支持1G，
                <br />支持3GP、ASF、AVI、DAT、DV、FLV、F4V、
                <br />GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、
                <br />MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、
                <br />SWF、TS、VOB、WMV、WEBM 等视频格式上传
              </div>
              <i class="el-icon-question" />
            </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button
          :disabled="saveVideoBtnDisabled"
          type="primary"
          @click="saveOrUpdateVideo()"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>
<script>
import chapter from "@/api/course/chapterApi";
import video from "@/api/course/videoApi";
export default {
  data() {
    return {
      fileList: [],
      filterText: "",
      dialogChapterFormVisible: false,
      dialogVideoFormVisible: false,
      saveBtnDisabled: false,
      saveVideoBtnDisabled: false,
      chapterVideoList: [],
      BASE_API: process.env.BASE_API,
      video: {
        title: "",
        sort: 0,
        isFree: 1,
        videoSourceId: "",
        videoOriginalName: ""
      },
      chapterInfo: {
        title: "",
        sort: 0,
        courseId: ""
      },
      courseId: ""
    };
  },
  watch: {
    // filterText(val) {
    //   this.$refs.tree.filter(val);
    // }
  },
  created() {
    this.getCharpterAndVideo();
  },
  methods: {
    // ==============================视频操作=======================================
    // 点击确认删除后，执行
    handleVodRemove() {
      video.removeVideo(this.video.videoSourceId).then(res => {
        this.$message({
          message: "成功删除视频",
          type: "success"
        });
        this.fileList = [];
        this.video.videoSourceId = "";
        this.video.videoOriginalName = "";
      });
    },
    // 点击删除后弹出提示框
    beforeVodRemove(file) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    // 上传成功
    handleUploadExceed() {},
    handleVodUploadSuccess(res, file) {
      this.video.videoSourceId = res.data.videoId;
      this.video.videoOriginalName = file.name;
      console.log(this.fileList);
      this.$message({
        message: "成功添加视频",
        type: "success"
      });
    },
    // ==============================小节操作=======================================
    saveOrUpdateVideo() {
      this.video.courseId = this.courseId;
      if (this.video.id) {
        this.updateVideo();
      } else {
        this.addVideo();
      }
    },
    // 添加小节
    addVideo() {
      video.addVideo(this.video).then(res => {
        this.dialogVideoFormVisible = false;
        this.getCharpterAndVideo();
        this.$message({
          message: "成功添加小节信息",
          type: "success"
        });
      });
    },
    // 更新小节
    updateVideo() {
      video.updateVideo(this.video).then(res => {
        this.dialogVideoFormVisible = false;
        this.getCharpterAndVideo();
        this.$message({
          message: "成功修改小节信息",
          type: "success"
        });
      });
    },
    // 删除小节
    removeVideo(id) {
      this.$confirm("此操作将删除该小节, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        video.deleteVideo(id).then(res => {
          this.getCharpterAndVideo();
        });
        this.$message({
          message: "成功删除小节信息",
          type: "success"
        });
      });
    },
    // 打开添加窗口
    openVideo(id) {
      this.dialogVideoFormVisible = true;
      this.video = {};
      this.video.chapterId = id;
    },
    // 打开编辑窗口
    editVideo(videoId) {
      video.getVideoById(videoId).then(res => {
        this.video = res.data.video;
      });
      this.dialogVideoFormVisible = true;
    },

    // ==============================章节操作=======================================
    removeChapter(chapterId) {
      this.$confirm("此操作将删除该章节, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        chapter.deleteChapter(chapterId).then(response => {
          this.getCharpterAndVideo();
          this.$message({
            type: "success",
            message: "删除成功!"
          });
        });
      });
    },
    openEditChatper(chapterId) {
      chapter.getChapterById(chapterId).then(response => {
        this.chapterInfo = response.data.chapter;
        this.dialogChapterFormVisible = true;
      });
    },
    // 打开添加章节弹窗
    openDialog() {
      this.chapterInfo.title = "";
      this.chapterInfo.sort = 0;
      this.dialogChapterFormVisible = true;
    },
    // 添加章节
    saveChapter() {
      chapter.addChapter(this.chapterInfo).then(response => {
        this.dialogChapterFormVisible = false;
        this.getCharpterAndVideo();
        this.$message({
          message: "成功添加章节信息",
          type: "success"
        });
      });
    },
    updateChapter() {
      chapter.updateChapter(this.chapterInfo).then(response => {
        this.dialogChapterFormVisible = false;
        this.getCharpterAndVideo();
        this.$message({
          message: "成功修改章节信息",
          type: "success"
        });
      });
    },

    //添加或者修改章节
    saveOrUpdateChapter() {
      if (this.chapterInfo.id) {
        this.updateChapter();
      } else {
        this.saveChapter();
      }
    },

    previous() {
      this.$router.push("/edu/course/info/" + this.courseId);
    },
    next() {
      this.$router.push("/edu/course/publish/" + this.courseId);
    },
    getCharpterAndVideo() {
      if (this.$route.params && this.$route.params.id) {
        this.courseId = this.$route.params.id;
        chapter.getChapterAndVideo(this.courseId).then(response => {
          this.chapterVideoList = response.data.list;
        });
        this.chapterInfo.courseId = this.$route.params.id;
      }
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    }
  }
};
</script>
<style scoped>
.chanpterList {
  position: relative;
  list-style: none;
  margin: 0;
  padding: 0;
}
.chanpterList li {
  position: relative;
}
.chanpterList p {
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #ddd;
}
.chanpterList .acts {
  float: right;
  font-size: 14px;
}

.videoList {
  padding-left: 50px;
}
.videoList p {
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #ddd;
}
.bottomNextOrPrevious {
  text-align: center;
}
</style>
