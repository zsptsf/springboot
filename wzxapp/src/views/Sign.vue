<template>
  <div class="sign">
    <el-button-group>
      <router-link to="/sign"><el-button type="success" icon="el-icon-date">图表</el-button></router-link>
      <router-link to="/signtable"><el-button type="success" icon="el-icon-tickets">表单</el-button></router-link>
    </el-button-group>
    <router-view/>
  </div>
</template>

<script>
    export default {
        name: "Sign"
    }
</script>

<style scoped>

</style>