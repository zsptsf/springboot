<template>
    <div class="RoleManage">
        <el-button type="success"
                   style="margin:15px 15px 15px 0px;"
                   @click="dialogFormVisible = true">添加</el-button>
        <el-table
                :data="Roles"
                border
                style="width: 400px;">
            <el-table-column
                    prop="id"
                    label="权限编号"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="权限名称"
                    width="120">
            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作">
                <template slot-scope="scope">
                    <el-button @click="edit(scope.row)" type="primary" icon="el-icon-edit" circle></el-button>
                    <el-button @click="deleterole(scope.row)" type="danger" icon="el-icon-delete" circle></el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
                background
                @size-change="size"
                @current-change="contentpage"
                :hide-on-single-page="value"
                :page-size="pageSize"
                layout="total, prev, pager, next, jumper"
                :total="total"
                style="margin-top:20px;">
        </el-pagination>
        <el-dialog title="添加权限" :visible.sync="dialogFormVisible">
            <el-form :model="form">
                <el-form-item label="权限名称">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="addrole()">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="修改权限" :visible.sync="dialogForm">
            <el-form :model="roleform">
                <el-form-item label="权限名称">
                    <el-input v-model="roleform.name" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogForm = false">取 消</el-button>
                <el-button type="primary" @click.native.prevent="editrole('roleform')">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>

    import {rolepage,saveRole,update,deleterole} from '@/api/role'

    export default {
        name: "RoleManage",
        created() {
            if (this.$store.state.number.length != 0) {
                if (this.$store.state.role.name === 'admin') {
                }else {
                    this.$router.push({path: '/'})
                }
            }else {
                this.$router.push({path: '/'})
            };
            this.getRole()
        },
        data() {
            return {
                value: true,
                pageSize:0,
                total:0,
                page:0,
                size:5,
                Roles: [],
                dialogFormVisible: false,
                dialogForm: false,
                form: {
                    name: ''
                },
                roleform:{
                    id:'',
                    name:''
                }
            }
        },
        methods: {
            getRole() {
                let that = this
                rolepage(this.page,this.size).then(data => {
                    that.Roles = data.data.content
                    that.pageSize = data.data.size
                    that.total = data.data.totalElements
                }).catch(error => {
                    if (error !== 'error') {
                        that.$message({type: 'error', message: '权限信息加载失败!', showClose: true})
                    }
                })
            },
            contentpage(currentPage){
                let that=this;
                rolepage(currentPage-1,this.size).then(data => {
                    that.Roles = data.data.content
                    that.pageSize = data.data.size
                    that.total = data.data.totalElements
                }).catch(error => {
                    if (error !== 'error') {
                        that.$message({type: 'error', message: '权限信息加载失败!', showClose: true})
                    }
                })
            },
            addrole(){
                let that = this
                saveRole(this.form.name).then(data => {
                    that.$message({type: 'success', message: '添加权限成功!', showClose: true})
                    window.location.reload()
                }).catch(error => {
                    if (error !== 'error') {
                        that.$message({type: 'error', message: '添加权限失败!', showClose: true})
                    }
                })
                this.dialogFormVisible = false;
            },
            edit(row){
                this.dialogForm = true,
                    this.roleform.id = row.id,
                    this.roleform.name = row.name
            },
            editrole(){
                let that = this
                update(this.roleform.id,this.roleform.name).then(data => {
                    that.$message({type: 'success', message: '修改权限成功!', showClose: true})
                    window.location.reload()
                }).catch(error => {
                    if (error !== 'error') {
                        that.$message({type: 'error', message: '修改权限失败!', showClose: true})
                    }
                })
            },
            deleterole(row){
                let that = this
                this.$confirm('此操作将永久删除该权限, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleterole(row.id).then(data => {
                        that.$message({type: 'success', message: '权限删除成功!', showClose: true})
                        window.location.reload()
                    }).catch(error => {
                        if (error !== 'error') {
                            that.$message({type: 'error', message: '权限删除失败!', showClose: true})
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            }
        }
    }
</script>

<style scoped>

</style>