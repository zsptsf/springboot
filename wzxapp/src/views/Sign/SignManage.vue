<template>
    <div class="SignManage">
        <download-excel
                class = "export-excel-wrapper"
                :data = "Sign"
                :fields = "json_fields"
                name = "签到详情.xls"
                style="width:100px;height:40px;margin:15px 15px 15px 0px;">
            <el-button
                    type="primary"
                    size="small"
                    style="width:100px;height:40px;">导出EXCEL</el-button>
        </download-excel>
        <el-table
                :data="Sign.slice((curPage - 1)*pageSize,curPage*pageSize)"
                border
                style="width: 800px">
            <el-table-column
                    prop="id"
                    label="签到序号"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="user.number"
                    label="用户账号"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="user.name"
                    label="用户姓名"
                    width="80">
            </el-table-column>
            <el-table-column
                    prop="user.clbum.name"
                    label="所属班级"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="signtime"
                    label="签到时间"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="remark"
                    label="打卡备注"
                    width="120">
            </el-table-column>
            <!--<el-table-column-->
                    <!--fixed="right"-->
                    <!--label="操作">-->
                <!--<template slot-scope="scope">-->
                    <!--<el-button @click="edit(scope.row)" type="primary" icon="el-icon-edit" circle></el-button>-->
                    <!--<el-button @click="delete(scope.row)" type="danger" icon="el-icon-delete" circle></el-button>-->
                <!--</template>-->
            <!--</el-table-column>-->
        </el-table>
        <el-pagination
                background
                :current-page.sync="curPage"
                :hide-on-single-page="value"
                :page-size="pageSize"
                layout="total, prev, pager, next, jumper"
                :total="Sign.length"
                style="margin-top:20px;">
        </el-pagination>
    </div>
</template>

<script>
    import {getAllSign} from '@/api/sign'
    import {timeFormat} from '@/utils/timeFormat'
    export default {
        name: "SignManage",
        created() {
            if (this.$store.state.number.length != 0) {
                if (this.$store.state.role.name === 'admin') {
                }else {
                    this.$router.push({path: '/'})
                }
            }else {
                this.$router.push({path: '/'})
            };
            this.getSign()
        },
        data() {
            return {
                value: true,
                pageSize:5,
                curPage:1,
                page:0,
                size:5,
                Sign: [],
                json_fields: {
                    "ID": "id",
                    "姓名": "user.name",
                    "账号": "user.number",
                    "班级": "user.clbum.name",
                    "签到时间": "signtime",
                    "打卡备注": "remark"
                },
            }
        },
        methods: {
            getSign() {
                let that = this
                getAllSign().then(data => {
                    that.Sign = data.data
                    for(let i=0; i<that.Sign.length; i++){
                        that.Sign[i].signtime = timeFormat(that.Sign[i].signtime)
                    }
                }).catch(error => {
                    if (error !== 'error') {
                        that.$message({type: 'error', message: '签到信息加载失败!', showClose: true})
                    }
                })
            },
        }
    }
</script>

<style scoped>

</style>