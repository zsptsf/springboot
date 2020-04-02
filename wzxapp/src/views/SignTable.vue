<template>
    <div class="signtable">
        <el-table
                :data="Sign"
                style="width: 650px;margin:30px auto;">
            <el-table-column
                    type="index"
                    width="50">
            </el-table-column>
            <el-table-column
                    prop="user.number"
                    label="用户账号"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="user.name"
                    label="用户姓名"
                    width="120">
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
                    label="打卡备注">
            </el-table-column>
        </el-table>
    </div>
</template>

<script>

    import {findSign} from '@/api/sign'
    import {timeFormat} from '@/utils/timeFormat'

    export default {
        name: "SignTable",
        created() {
            this.getfindSign()
        },
        data() {
            return {
                Sign: [

                ],
                Stime:'',
            }
        },
        methods:{
            getfindSign() {
                let that = this;
                let id = this.$store.state.id;
                if (this.$store.state.number.length != 0) {
                    findSign(id).then(data => {
                        that.Sign = data.data;
                        for(let i=0; i<that.Sign.length; i++){
                            that.Sign[i].signtime = timeFormat(that.Sign[i].signtime)
                        }
                    }).catch(error => {
                        if (error !== 'error') {
                            that.$message({type: 'error', message: '您的签到记录加载失败!', showClose: true})
                        }
                    })
                }else {
                    that.$message({message: '您还未登录，请先登录', type: 'error', showClose: true});
                }
            }
        }
    }
</script>

<style scoped>

</style>