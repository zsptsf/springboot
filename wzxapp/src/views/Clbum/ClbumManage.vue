<template>
    <div class="ClbumManage">
        <download-excel
                class = "export-excel-wrapper"
                :data = "Clbum"
                :fields = "json_fields"
                name = "班级详情.xls"
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
                placeholder="输入关键词搜索班级..."
                v-model="input"
                style="width:300px;margin:15px 15px 15px 0px;">
            <el-button slot="append" icon="el-icon-search" @click="findlink()"></el-button>
        </el-input>
        <el-table
                :data="Clbum.slice((curPage - 1)*pageSize,curPage*pageSize)"
                border
                style="width: 400px">
            <el-table-column
                    prop="id"
                    label="班级编号"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="班级名称"
                    width="120">
            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作">
                <template slot-scope="scope">
                    <el-button @click="edit(scope.row)" type="primary" icon="el-icon-edit" circle></el-button>
                    <el-button @click="deleteclbum(scope.row)" type="danger" icon="el-icon-delete" circle></el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
                background
                :current-page.sync="curPage"
                :page-size="pageSize"
                :hide-on-single-page="value"
                layout="total, prev, pager, next, jumper"
                :total="Clbum.length"
                style="margin-top:20px;">
        </el-pagination>
        <el-dialog title="添加班级" :visible.sync="dialogFormVisible">
            <el-form :model="form">
                <el-form-item label="班级名称">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="addclbum()">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="修改班级" :visible.sync="dialogForm">
            <el-form :model="clbumform">
                <el-form-item label="班级名称">
                    <el-input v-model="clbumform.name" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogForm = false">取 消</el-button>
                <el-button type="primary" @click.native.prevent="editclbum('clbumform')">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>

    import {getAllClbum,findByClbumNameLike,saveClbum,update,deleteclbum} from '@/api/clbum'

    export default {
        name: "ClbumManage",
        //created ：处于loading结束后，还做一些初始化，实现函数自执行（data数据已经初始化，但是DOM结构渲染完成，组件没有加载）
        created() {
            if (this.$store.state.number.length != 0) {
                if (this.$store.state.role.name === 'admin') {
                }else {
                    this.$router.push({path: '/'})
                }
            }else {
                this.$router.push({path: '/'})
            };
            this.getClbum()
        },
        data() {
            return {
                input: '',
                value: true,
                curPage:1,
                pageSize:5,
                page:0,
                Clbum: [],
                json_meta: [
                    [
                        {
                            " key ": " charset ",
                            " value ": " utf- 8 "
                        }
                    ]
                ],
                json_fields: {
                    // "头部-诊断名称": "name",    //常规字段
                    // "头部-联系电话": "phone.mobile", //支持嵌套属性
                    // "头部-损坏区域代码": {
                    //     field: "phone.landline",
                    //     //自定义回调函数
                    //     callback: value => {
                    //         return `损坏区域代码 - ${value}`;
                    //     }
                    // }
                    "ID": "id",
                    "班级名称": "name",
                },
                dialogFormVisible: false,
                dialogForm: false,
                form: {
                    name: ''
                },
                clbumform:{
                    id:'',
                    name:''
                }
            }
        },
        methods: {
            getClbum() {
                //let that = this
                // clbumpage(this.page,this.size).then(data => {
                //     that.Clbum = data.data.content
                //     that.pageSize = data.data.size
                //     that.total = data.data.totalElements
                // }).catch(error => {
                //     if (error !== 'error') {
                //         that.$message({type: 'error', message: '班级信息加载失败!', showClose: true})
                //     }
                // })
                let that = this
                getAllClbum().then(data => {
                    that.Clbum = data.data
                }).catch(error => {
                    if (error !== 'error') {
                        that.$message({type: 'error', message: '班级信息加载失败!', showClose: true})
                    }
                })
            },
            // contentpage(currentPage){
            //     let that=this;
            //     clbumpage(currentPage-1,this.size).then(data => {
            //         that.Clbum = data.data.content
            //         that.pageSize = data.data.size
            //         that.total = data.data.totalElements
            //     }).catch(error => {
            //         if (error !== 'error') {
            //             that.$message({type: 'error', message: '班级信息加载失败!', showClose: true})
            //         }
            //     })
            // },
            findlink(){
                let that = this
                findByClbumNameLike(this.input).then(data => {
                    that.Clbum = data.data
                }).catch(error => {
                    if (error !== 'error') {
                        that.$message({type: 'error', message: '特定班级信息查询失败!', showClose: true})
                    }
                })
            },
            addclbum(){
                let that = this
                saveClbum(this.form.name).then(data => {
                    that.$message({type: 'success', message: '添加班级成功!', showClose: true})
                    window.location.reload()
                }).catch(error => {
                    if (error !== 'error') {
                        that.$message({type: 'error', message: '添加班级失败!', showClose: true})
                    }
                })
                this.dialogFormVisible = false;
            },
            edit(row){
                this.dialogForm = true,
                this.clbumform.id = row.id,
                this.clbumform.name = row.name
            },
            editclbum(){
                let that = this
                update(this.clbumform.id,this.clbumform.name).then(data => {
                    that.$message({type: 'success', message: '修改班级成功!', showClose: true})
                    window.location.reload()
                }).catch(error => {
                    if (error !== 'error') {
                        that.$message({type: 'error', message: '修改班级失败!', showClose: true})
                    }
                })
            },
            deleteclbum(row){
                let that = this
                this.$confirm('此操作将永久删除该班级, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteclbum(row.id).then(data => {
                        that.$message({type: 'success', message: '班级删除成功!', showClose: true})
                        window.location.reload()
                    }).catch(error => {
                        if (error !== 'error') {
                            that.$message({type: 'error', message: '班级删除失败!', showClose: true})
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