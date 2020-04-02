export function formatDate(time){
    let d = new Date(time.substr(0, 19));//加入substr(0, 19)处理兼容ios报错NAN
    let year = d.getFullYear();       //年
    let month = d.getMonth() + 1;     //月
    let day = d.getDate();            //日

    // var hh = d.getHours();            //时
    // var mm = d.getMinutes();          //分
    // var ss = d.getSeconds();           //秒

    let clock = year + "-";

    if (month < 10)
        clock += "0";

    clock += month + "-";

    if (day < 10)
        clock += "0";

    clock += day;
    // clock += day + " ";

    // if (hh < 10)
    //     clock += "0";
    //
    // clock += hh + ":";
    // if (mm < 10) clock += '0';
    // clock += mm + ":";
    //
    // if (ss < 10) clock += '0';
    // clock += ss;
    return (clock);
}