<template>
  <div>
    <!-- 阿里云视频播放器样式 -->
    <link
      rel="stylesheet"
      href="https://g.alicdn.com/de/prismplayer/2.8.1/skins/default/aliplayer-min.css"
    />
    <!-- 阿里云视频播放器脚本 -->
    <script
      charset="utf-8"
      type="text/javascript"
      src="https://g.alicdn.com/de/prismplayer/2.8.1/aliplayer-min.js"
    ></script>
    <!-- 定义播放器dom -->
    <div id="J_prismPlayer" class="prism-player" />
  </div>
</template>
<script>
import vod from "@/api/vod";
export default {
  mounted() {
    new Aliplayer(
      {
        id: "J_prismPlayer",
        vid: this.vid, // 视频id
        playauth: this.playAuth, // 播放凭证
        encryptType: "1",
        width: "100%",
        height: "500px"
      },
      function(player) {
        console.log("播放器创建成功");
      }
    );
  },
  asyncData({ params, error }) {
    return vod.getPlayAuth(params.id).then(response => {
      // console.log(response.data.data)
      return {
        vid: params.id,
        playAuth: response.data.data.playAuth
      };
    });
  }
};
</script>
<style scoped></style>
