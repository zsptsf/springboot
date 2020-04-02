<template>
    <div class="signchart">
        <el-calendar v-model="value"
                     style="width: 650px;margin:30px auto;">
            <!-- 这里使用的是 2.5 slot 语法，对于新项目请使用 2.6 slot 语法-->
            <template
                    slot="dateCell"
                    slot-scope="{date, data}">
                <p>
                    {{ data.day.split('-').slice(2).join('-') }}<br /> {{dealMyDate(data.day)}}
                </p>
            </template>
        </el-calendar>
    </div>
</template>

<script>
    import {findSign} from '@/api/sign'
    import {formatDate} from '@/utils/formatDate'

    export default {
        created() {
            this.getfindSign()
        },
        data() {
            return {
                value: new Date(),
                resDate: [

                ]
            }
        },
        methods:{
            getfindSign() {
                let that = this
                let id = this.$store.state.id
                if (this.$store.state.number.length != 0) {
                    findSign(id).then(data => {
                        that.resDate = data.data
                    }).catch(error => {
                        if (error !== 'error') {
                            that.$message({type: 'error', message: '您的签到记录加载失败!', showClose: true})
                        }
                    })
                }else {
                    that.$message({message: '您还未登录，请先登录', type: 'error', showClose: true});
                }
            },
            dealMyDate(v) {
                let len = this.resDate.length
                let res = ""
                for(let i=0; i<len; i++){
                    let date = formatDate(this.resDate[i].signtime)
                    if(date == v) {
                        res = "✔️"
                        break
                    }
                }
                return res
            }
        }
    }
</script>

<style scoped>
    .is-selected {
        color: #1989FA;
    }
</style>