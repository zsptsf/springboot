<template>
    <div class="UserManage">
        <download-excel
                class = "export-excel-wrapper"
                :data = "User"
                :fields = "json_fields"
                name = "用户详情.xls"
                style="width:100px;height:40px;margin:15px 15px 15px 0px;float: left;">
            <!-- 上面可以自定义自己的样式，还可以引用其他组件button -->
            <el-button
                    type="primary"
                    size="small"
                    style="width:100px;height:40px;">导出EXCEL</el-button>
        </download-excel>
        <el-button type="success"
                   style="margin:15px 15px 15px 0px;"
                   @click="dialogFormVisible = true">添加</el-button>
        <el-input
                placeholder="输入关键词搜索用户..."
                v-model="input"
                style="width:300px;margin:15px 15px 15px 0px;">
            <el-button slot="append" icon="el-icon-search" @click="findlink()"></el-button>
        </el-input>
        <el-table
                :data="User.slice((curPage - 1)*pageSize,curPage*pageSize)"
                border
                style="width: 850px">
            <el-table-column
                    prop="id"
                    label="用户编号"
                    width="100">
            </el-table-column>
            <el-table-column label="头像">
                <template slot-scope="scope">
                    <img :src="scope.row.avatar" width="80" height="80"/>
                </template>
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="姓名"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="number"
                    label="账号"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="clbum.name"
                    label="所属班级"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="role.name"
                    label="权限"
                    width="120">
            </el-table-column>
            <!--<el-table-column-->
                    <!--fixed="right"-->
                    <!--label="操作">-->
                <!--<template slot-scope="scope">-->
                    <!--<el-button @click="edit(scope.row)" type="primary" icon="el-icon-edit" circle></el-button>-->
                    <!--<el-button @click="deleteuser(scope.row)" type="danger" icon="el-icon-delete" circle></el-button>-->
                <!--</template>-->
            <!--</el-table-column>-->
        </el-table>
        <el-pagination
                background
                :current-page.sync="curPage"
                :hide-on-single-page="value"
                :page-size="pageSize"
                layout="total, prev, pager, next, jumper"
                :total="User.length"
                style="margin-top:20px;">
        </el-pagination>
        <!--<el-dialog title="添加用户" :visible.sync="dialogFormVisible">-->
            <!--<el-form :model="form">-->
                <!--<el-form-item label="头像">-->
                    <!--<el-upload-->
                            <!--class="avatar-uploader"-->
                            <!--action="https://jsonplaceholder.typicode.com/posts/"-->
                            <!--:show-file-list="false"-->
                            <!--:on-success="handleAvatarSuccess"-->
                            <!--:before-upload="beforeAvatarUpload">-->
                        <!--<img v-if="form.avatar" :src="form.avatar" class="avatar">-->
                        <!--<i v-else class="el-icon-plus avatar-uploader-icon"></i>-->
                    <!--</el-upload>-->
                <!--</el-form-item>-->
                <!--<el-form-item label="用户姓名">-->
                    <!--<el-input v-model="form.name"></el-input>-->
                <!--</el-form-item>-->
                <!--<el-form-item label="用户账号">-->
                    <!--<el-input v-model="form.number"></el-input>-->
                <!--</el-form-item>-->
                <!--<el-form-item label="用户密码">-->
                    <!--<el-input type="password" v-model="form.password" autocomplete="off"></el-input>-->
                <!--</el-form-item>-->
                <!--<el-form-item label="所属班级">-->
                    <!--<el-select v-model="form.clbum" placeholder="请选择用户所属班级">-->
                        <!--<el-option label="班级一" value="shanghai"></el-option>-->
                        <!--<el-option label="班级二" value="beijing"></el-option>-->
                    <!--</el-select>-->
                <!--</el-form-item>-->
                <!--<el-form-item label="选择权限">-->
                    <!--<el-select v-model="form.role" placeholder="请选择用户权限">-->
                        <!--<el-option label="用户" value="shanghai"></el-option>-->
                        <!--<el-option label="管理员" value="beijing"></el-option>-->
                    <!--</el-select>-->
                <!--</el-form-item>-->
            <!--</el-form>-->
            <!--<div slot="footer" class="dialog-footer">-->
                <!--<el-button @click="dialogFormVisible = false">取 消</el-button>-->
                <!--<el-button type="primary" @click="adduser()">确 定</el-button>-->
            <!--</div>-->
        <!--</el-dialog>-->
        <!--<el-dialog title="修改用户" :visible.sync="dialogForm">-->
            <!--<el-form :model="userform">-->
                <!--<el-form-item label="用户名称">-->
                    <!--<el-input v-model="userform.name" autocomplete="off"></el-input>-->
                <!--</el-form-item>-->
            <!--</el-form>-->
            <!--<div slot="footer" class="dialog-footer">-->
                <!--<el-button @click="dialogForm = false">取 消</el-button>-->
                <!--<el-button type="primary" @click.native.prevent="edituser('userform')">确 定</el-button>-->
            <!--</div>-->
        <!--</el-dialog>-->
    </div>
</template>

<script>
    import {getAllUser,findByUserNameLike,saveUser,update,deleteuser} from '@/api/user'

    export default {
        name: "UserManage",
        created() {
            if (this.$store.state.number.length != 0) {
                if (this.$store.state.role.name === 'admin') {
                }else {
                    this.$router.push({path: '/'})
                }
            }else {
                this.$router.push({path: '/'})
            };
            this.getUser();
        },
        data() {
            return {
                input: '',
                value: true,//当只有一页时隐藏分页
                pageSize:5,//每页多少条
                curPage:1,//第几页
                page:0,
                size:5,
                User: [],
                json_fields: {
                    "ID": "id",
                    "姓名": "name",
                    "账号": "number",
                    "班级": "clbum.name"
                },
                dialogFormVisible: false,
                dialogForm: false,
                form: {
                    name: '',
                    number:'',
                    avatar:'',
                    clbum:'',
                    password: '',
                    role:''
                },
                userform:{
                    id:'',
                    name:'',
                    number:'',
                    avatar:'',
                    clbum:'',
                    password: '',
                    role:''
                }
            }
        },
        methods: {

            getUser() {
                let that = this
                getAllUser().then(data => {
                    that.User = data.data
                }).catch(error => {
                    if (error !== 'error') {
                        that.$message({type: 'error', message: '用户信息加载失败!', showClose: true})
                    }
                })
            },
        }
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