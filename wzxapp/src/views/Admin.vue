<template>
    <div class="admin">
        <el-container>
            <base-header></base-header>
            <el-container>
                <el-aside style="width: 250px;padding-top: 60px;">
                    <el-menu
                            :router=true
                            :default-active="activeIndex"
                            class="el-menu-vertical-demo">
                        <el-submenu index="1">
                            <template slot="title">
                                <i class="el-icon-user-solid"></i>
                                <span>用户管理</span>
                            </template>
                            <el-menu-item index="/usermanage">查看用户信息</el-menu-item>
                        </el-submenu>
                        <el-submenu index="2">
                            <template slot="title">
                                <i class="el-icon-s-home"></i>
                                <span>班级管理</span>
                            </template>
                            <el-menu-item index="/clbummanage">查看班级信息</el-menu-item>
                            <el-menu-item index="/">查看班级成员</el-menu-item>
                        </el-submenu>
                        <el-submenu index="3">
                            <template slot="title">
                                <i class="el-icon-s-claim"></i>
                                <span>签到管理</span>
                            </template>
                            <el-menu-item index="/signmanage">查看签到信息</el-menu-item>
                        </el-submenu>
                        <el-submenu index="4">
                            <template slot="title">
                                <i class="el-icon-s-help"></i>
                                <span>权限管理</span>
                            </template>
                            <el-menu-item index="/rolemanage">查看权限信息</el-menu-item>
                        </el-submenu>
                    </el-menu>
                </el-aside>
                <el-main style="padding-top: 60px;"><router-view/></el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
    import BaseHeader from '@/views/BaseHeader'
    export default {
        name: "Admin",
        created() {
            if (this.$store.state.number.length != 0) {
                if (this.$store.state.role.name === 'admin') {
                    //this.$message({message: '欢迎您，管理员', type: 'success', showClose: true});
                }else {
                    //this.$message({message: '您不是管理员，无权访问', type: 'error', showClose: true});
                    this.$router.push({path: '/'})
                }
            }else {
                //this.$message({message: '您还未登录，请先登录', type: 'error', showClose: true});
                this.$router.push({path: '/'})
            }
        },
        props: {
            activeIndex: String,
        },
        components: {
            'base-header': BaseHeader
        },
        data() {
            return {

            };
        },
        methods: {

        }
    }
</script>

<style scoped>
    .admin{
        background-color: #ffffff;
    }
</style>