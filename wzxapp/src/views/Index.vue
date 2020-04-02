<template>
    <div class="index">
        <el-button type="success"
                   style="margin:auto;"
                   @click="open">签到</el-button>
        <el-calendar v-model="today"
                     style="width: 650px;margin:30px auto;">
        </el-calendar>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                today: new Date(),
                sign:{
                    usernumber:this.$store.state.number,
                    remark: ''
                }
            }
        },
        methods: {
            open() {
                let that=this
                if (this.$store.state.number.length != 0) {
                    this.$prompt('请输入打卡备注', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消'
                    }).then(({ value }) => {
                        // that.$message({type: 'success',message: '你的打卡备注是: ' + value});
                        that.sign.remark = value;
                        that.$store.dispatch('saveSign',that.sign).then(() => {
                            that.$message({message: '签到成功', type: 'success', showClose: true});
                        }).catch((error) => {
                            if (error !== 'error') {
                                console.log(that.sign.usernumber);
                                console.log(that.sign.remark);
                                that.$message({message: error, type: 'error', showClose: true});
                            }
                        })
                    }).catch(() => {
                        that.$message({type: 'info', message: '取消输入'});
                    });
                }else {
                    that.$message({message: '您还未登录，请先登录', type: 'error', showClose: true});
                }
            }
        }
    }
</script>

<style scoped>
    .is-selected {
        color: #1989FA;
    }
</style>