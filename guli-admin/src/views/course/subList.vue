<template>
  <div class="app-container">
    <el-input
      v-model="filterText"
      placeholder="Filter keyword"
      style="margin-bottom:30px;"
    />
    <el-tree
      class="eTree"
      ref="subjectTree"
      :data="data"
      :props="defaultProps"
      default-expand-all
      :filter-node-method="filterNode"
      highlight-current
    ></el-tree>
  </div>
</template>
<script>
import subject from "@/api/course/subjectApi";
export default {
  data() {
    return {
      filterText: "",
      data: [],
      defaultProps: {
        children: "children",
        label: "title"
      }
    };
  },
  watch: {
    filterText(val) {
      this.$refs.subjectTree.filter(val);
    }
  },
  created() {
    this.getCourseSub();
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    getCourseSub() {
      subject.getSubjectList().then(response => {
        this.data = response.data.list;
      });
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.title.indexOf(value) !== -1;
    }
  }
};
</script>
<style scoped></style>
