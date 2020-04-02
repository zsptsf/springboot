<template>
    <div id="userupdata">
        <base-header></base-header>
        <el-form
                :model="ruleForm"
                :rules="rules"
                ref="ruleForm"
                label-width="100px"
                class="demo-ruleForm"
                style="width: 550px;margin:auto;padding-top: 100px;">

            <el-form-item label="用户编号" hidden>
                <el-input v-model="ruleForm.id" readonly></el-input>
            </el-form-item>

            <el-form-item label="班级" hidden>
                <el-input v-model="ruleForm.clbum.name"></el-input>
            </el-form-item>

            <el-form-item label="账号">
                <el-input v-model="ruleForm.number" readonly></el-input>
            </el-form-item>

            <el-form-item label="头像">
                <el-upload
                        class="avatar-uploader"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload">
                    <img v-if="ruleForm.avatar" :src="ruleForm.avatar" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
            </el-form-item>

            <el-form-item label="姓名" prop="name">
                <el-input v-model="ruleForm.name"></el-input>
            </el-form-item>

            <el-form-item label="旧密码" prop="oldpassword">
                <el-input type="password" v-model="ruleForm.oldpassword" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="新密码" prop="password">
                <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
            </el-form-item>


            <!--<el-form-item label="权限" prop="role">-->
                <!--<el-select v-model="ruleForm.role" placeholder="请选择新用户权限">-->
                    <!--<el-option label="管理员" value="admin"></el-option>-->
                    <!--<el-option label="普通用户" value="guest"></el-option>-->
                <!--</el-select>-->
            <!--</el-form-item>-->

            <el-form-item>
                <el-button type="primary" @click.native.prevent="submitForm('ruleForm')">修改</el-button>
                <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import BaseHeader from '@/views/BaseHeader'

    export default {
        data() {
            return {
                ruleForm: {
                    number:this.$store.state.number,
                    avatar:this.$store.state.avatar,
                    name: this.$store.state.name,
                    clbum:this.$store.state.clbum,
                    oldpassword:'',
                    password: '',
                    role:this.$store.state.role,
                    id:this.$store.state.id
                },
                rules: {
                    name: [
                        { required: true, message: '请输入名字', trigger: 'blur' },
                        { min: 2, max: 11, message: '长度在 2 到 11 个字符', trigger: 'blur' }
                    ],
                    oldpassword: [
                        { required: true, message: '请输入旧密码', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入新密码', trigger: 'blur' }
                    ],
                    // role: [
                    //     { required: true, message: '请选择新用户权限', trigger: 'change' }
                    // ]
                }
            };
        },
        // computed: {
        //     ruleForm() {
        //         let avatar = this.$store.state.avatar
        //         let password = this.$store.state.password
        //         let name = this.$store.state.name
        //         let number = this.$store.state.number
        //         let role = this.$store.state.role
        //         let id = this.$store.state.id
        //         return {
        //             avatar,role,password,name,number,id
        //         }
        //     }
        // },
        methods: {
            handleAvatarSuccess(res, file) {
                this.ruleForm.avatar = URL.createObjectURL(file.raw);
            },
            beforeAvatarUpload(file) {
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isLt2M;
            },

            submitForm(formName) {
                let that=this
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        // axios.put('http://localhost:8181/user/update',this.ruleForm).then(function (resp) {
                        //     if(resp.data == 'success'){
                        //         _this.$alert('修改成功!', '修改用户', {
                        //             confirmButtonText: '确定',
                        //             callback: action => {
                        //                 _this.$router.push('/UserManage')
                        //             }
                        //         });
                        //     }
                        // })
                        if (that.ruleForm.oldpassword === this.$store.state.password) {
                            //dispatch：含有异步操作，例如向后台提交数据，写法： this.$store.dispatch('mutations方法名',值)
                            //commit：同步操作，写法：this.$store.commit('mutations方法名',值)
                            that.$store.dispatch('userupdate', that.ruleForm).then(() => {
                                that.$message({message: '信息修改成功,请重新登录', type: 'success', showClose: true});
                                that.$store.dispatch('logout').then(() => {
                                    this.$router.push({path: '/'})
                                }).catch((error) => {
                                    if (error !== 'error') {
                                        that.$message({message: error, type: 'error', showClose: true});
                                    }
                                })
                                //页面刷新
                                //window.location.reload()
                            }).catch((error) => {
                                if (error !== 'error') {
                                    that.$message({message: error, type: 'error', showClose: true});
                                }
                            })
                        }else {
                            that.$message({message: '旧密码输入错误', type: 'error', showClose: true});
                        }
                    } else {
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        },
        components: {
            'base-header': BaseHeader
        },
    }
</script>

<style scoped>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
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
</style>