/********************定义获取json数据方法*****************************/
//定义获取饼图json数据函数
function getJson(ul,inde){
    var indexs = inde || -1
    var ssr = []
    $.ajax({
        type:'GET',
        async:false,
        url:'../data/XZ'+ul+'.json',
        dataType:'json',
        success:function(data){
            // 如果inde未传值则是获取饼图数据
            if (indexs === -1){
                for(var i=0;i<data[0].length;i++){
                    ssr.push({
                        name:data[0][i],value:data[1][i]
                    })
                }
            }else{
                //如果传值则获取柱状图数据
                ssr = data[indexs-1]
            }
        }
    })
    return ssr
}

/***********************************************分割线 开始 按行政区域划分数据保存************************************************/

//饼图对象
function pie(data) {
    return {
        color:data.color || ['#c2ef7a','#fc8d77','#88ceef'],
        title: data.title || {},
        series: [
            {
                name:data.name || '',
                type: 'pie',
                radius: '50%',
                center: ['50%','50%'],
                label:data.labelInner || '',
                data: data.data || '',
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0,0,0,0.5)'
                    }
                },
                animation: false,
                markPoint: {
                    symbol: 'pin'
                }
            },{
                name: '',
                type: 'pie',
                radius: '50%',
                center: ['50%','50%'],
                label: {
                    normal: {
                        fontSize: 16,
                        color:'black',
                        formatter: "{b}:{d}%"
                    }
                },
                data:  data.data || '',
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0,0,0,0.5)'
                    }
                },
                animation: false,
                markPoint: {
                    symbol: 'pin'
                },
                labelLine:data.labelLine || {}
            }
        ]
    }
}

//柱状图公共方法
function category(data){
    return {
        color: ['#67b7dc','#4b7386'],
        tooltip: {
            show:false
        },
        grid: {
            left: 40,
            right: 20,
            bottom: '3%',
            containLabel: '3%'
        },
        xAxis: {
            type: "category",
            boundaryGap: [30, 0],
            data:data.xAxis.data,
            axisTick: {
                show: false,
                alignWithLabel: true,
                lineStyle: {
                    type: 'dashed',
                    width:5
                }
            },
            axisLine:{
                lineStyle:{
                    color:'#b7b7b7'
                }
            },
            splitLine:{
                show: true
            },
            axisLabel:{
                color:'#333333',
                fontSize:16
            }
        },
        yAxis: {
            name:data.yAxis.name || "(万元)",
            nameTextStyle:{
                color:'#333333',
                fontSize:16
            },
            type: 'value',
            axisTick:{
                show: false
            },
            axisLabel:{
                color:'#333333',
                fontSize:16
            },
            axisLine:{
                lineStyle:{
                    color:'#b7b7b7'
                }
            }
        },
        series: [
            {
                name: "资金投入",
                type: 'bar',
                barWidth: "50%",
                data: data.series.data,
                label:{
                    normal:{
                        show:true,
                        position:'top',
                        color: 'red',
                        fontSize:16,
                        fontWeight:500
                    }
                },
                itemStyle:{
                    emphasis:{
                        color:"#4b7386"
                    }
                }
            }
        ]
    }
}


/**
 * 贫困户脱贫情况 数据 饼图
 */

var pinKunHuTuopin = pie({
    data: getJson("贫困户脱贫情况"),
    labelInner: {
        normal: {
            fontSize: 18,
            color:'black',
            position: 'inner',
            formatter: "{c}"
        }
    }
})

/**
 * 资金投入 数据 柱状图
 */
var ziJingShouRu = category({
    xAxis:{
        data: getJson("资金投入",1)
    },
    yAxis:{
        name:"(万元)"
    },
    series:{
        data:getJson("资金投入",2)
    }
})

/**
 * 分红情况 数据 两个饼图
 */
// 第一组数据
var fenHongQingKuangOne = pie({
    color: ['#fc8d77','#c2ef7a'],
    name:'贫困村分红统计',
    title: {
        text: "贫困村分红统计",
        textStyle: {
            color: "#313131",
            fontSize: 16,
            fontWeight:'normal'
        },
        bottom: 35,
        left: "center"
    },
    data: getJson("分红情况1"),
    labelInner: {
        normal: {
            fontSize: 18,
            color:'black',
            position: 'inner',
            formatter: "{c}"
        }
    },
    labelLine:{
        normal:{
            length:5,
            length2:0
        }
    }
})
// 第二组数据
var fenHongQingKuangTwo = pie({
    color: ['#fc8d77','#c2ef7a'],
    name:'贫困户分红统计',
    title: {
        text: "贫困户分红统计",
        textStyle: {
            color: "#313131",
            fontSize: 16,
            fontWeight:'normal'
        },
        bottom: 35,
        left: "center"
    },
    data: getJson("分红情况2"),
    labelInner: {
        normal: {
            fontSize: 18,
            color:'black',
            position: 'inner',
            formatter: "{c}"
        }
    },
    labelLine:{
        normal:{
            length:5,
            length2:0
        }
    }
})

/**
 * 资金收益 数据保存
 */
var ziJingShouYi = category({
    //xAxis以及yAxis参数在调用时必须要写上
    xAxis:{
        data: getJson("资金收益",1)
    },
    yAxis:{
        name:"(万元)"
    },
    series:{
        data:getJson("资金收益",2)
    }
})
/**
 * 实施主体数量 数据
 */
//柱状图数据
var shiShiZhuTiL = category({
    //xAxis以及yAxis参数在调用时必须要写上
    xAxis:{
        data: getJson("实施主体1",1)
    },
    yAxis:{
        name:"(个)"
    },
    series:{
        data:getJson("实施主体1",2)
    }
})

//饼图数据保存
var shiShiZhuTiR = pie({
    color: ["#5cbeec","#c2ef7a","#fc8d77"],
    data: getJson("实施主体2"),
    labelInner: {
        normal: {
            fontSize: 18,
            color:'black',
            position: 'inner',
            formatter: "{c}"
        }
    }
})

/**
 * 贫困户投入收益 数据
 */
//柱状图数据
var touRuShouYiL = {
    color: ['#67b7dc','#fc8d77'],
    tooltip: {
        show:false
    },
    legend:{
        show:true,
        data:["投入金额","收益金额"],
        left:25,
        top:15,
        textStyle:{
            color:"#323232",
            fontSize:16
        }
    },
    grid: {
        left: 40,
        right: 20,
        bottom: '3%',
        containLabel: '3%'
    },
    xAxis: {
        type: "category",
        boundaryGap: [30, 0],
        data:getJson("贫困户贫困村投入收益情况1",1),
        axisTick: {
            show: false,
            alignWithLabel: true,
            lineStyle: {
                type: 'dashed',
                width:5
            }
        },
        axisLine:{
            lineStyle:{
                color:'#b7b7b7'
            }
        },
        splitLine:{
            show: true
        },
        axisLabel:{
            color:'#333333',
            fontSize:16
        }
    },
    yAxis: {
        nameTextStyle:{
            color:'#333333',
            fontSize:16
        },
        type: 'value',
        axisTick:{
            show: false
        },
        axisLabel:{
            color:'#333333',
            fontSize:16
        },
        axisLine:{
            lineStyle:{
                color:'#b7b7b7'
            }
        }
    },
    series: [
        {
            name:'投入金额',
            type: 'bar',
            barWidth: "25%",
            data:getJson("贫困户贫困村投入收益情况1",2),
            label:{
                normal:{
                    show:true,
                    position:'top',
                    color: 'red',
                    fontSize:16,
                    fontWeight:500
                }
            },
            itemStyle:{
                emphasis:{
                    color:"#4b7386"
                }
            },
            barGap:0
        },
        {
            name:'收益金额',
            type: 'bar',
            barWidth: "25%",
            data:getJson("贫困户贫困村投入收益情况1",3),
            label:{
                normal:{
                    show:true,
                    position:'top',
                    color: 'red',
                    fontSize:14,
                    fontWeight:500
                }
            },
            itemStyle:{
                emphasis:{
                    color:"#95594d"
                }
            },
            barGap:0
        }
    ]
}

//饼图数据
var touRuShouYiR = {
    color:["#c2ef7a","#5cbeec","#74e69c","#9ba3f4","#f4a34c","#efe37a","#fc8d77"],
    title: {
        text: "贫困村分布",
        textStyle: {
            color: "#313131",
            fontSize: 16,
            fontWeight:'normal'
        },
        bottom: 25,
        left: "center"
    },
    tooltip:{
        tigger:'item'
    },
    series: [
        {
            name: '',
            type: 'pie',
            radius: '55%',
            center: ['50%','50%'],
            label: {
                normal: {
                    fontSize: 16,
                    color:'black'
                }
            },
            data: getJson("贫困户贫困村投入收益情况2"),
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0,0,0,0.5)'
                }
            },
            animation: false,
            markPoint: {
                symbol: 'pin'
            }
        }
    ]
}

/*******************************************分割线 开始实施主体统计数据********************************************************/
/**
 * 资金投入情况
 */
var ZTziJinTouRu = {
    series: [
        {
            name: '',
            type: 'pie',
            radius: '55%',
            center: ['50%','50%'],
            data: [
                {value:0.3,name:'集体资产投入金额/2%'},
                {value:2,name:'自有资产/43%'},
                {value:2.7,name:'财政资金投入金额/54%'}
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0,0,0,0.5)'
                }
            },
            animation: false,
            markPoint: {
                symbol: 'pin'
            }
        }
    ]
}
/**
 * 资金收益情况
 */
var ZTziJinShouYi = {
    series: [
        {
            name: '',
            type: 'pie',
            radius: '55%',
            center: ['50%','50%'],
            data: [
                {value:150,name:'财政资金收益金额/2%'},
                {value:7100,name:'自有资产/75%'}
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0,0,0,0.5)'
                }
            },
            animation: false,
            markPoint: {
                symbol: 'pin'
            }
        }
    ]
}
/**
 * 年收入情况
 */
var ZTnianShouRu = {
    series: [
        {
            name: '',
            type: 'pie',
            radius: '55%',
            center: ['50%','50%'],
            data: [
                {value:150,name:'土地扭转收益金额/2%'},
                {value:5000,name:'资产扶贫收益金额/2%'},
                {value:7100,name:'劳工收入/75%'}
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0,0,0,0.5)'
                }
            },
            animation: false,
            markPoint: {
                symbol: 'pin'
            }
        }
    ]
}

/**
 * 参加资产收益扶贫情况数据
 */
var ZTcanJiaFupin = [
    {"id":1,"name":'壶关县旱地蔬菜种植协会',"fenpei":'已脱贫每人一百未脱贫每人两百',"zijin":'300',"zichan":'-','daikuan':'0','touzi':'0','shouru':'3000'},
    {"id":2,"name":'壶关县圣华种养专业合作社',"fenpei":'已脱贫每人一百未脱贫每人两百',"zijin":'126',"zichan":'10','daikuan':'10','touzi':'0','shouru':'5000'},
    {"id":3,"name":'长治市紫团饮业有限公司',"fenpei":'已脱贫每人一百未脱贫每人两百',"zijin":'248',"zichan":'-','daikuan':'15','touzi':'0','shouru':'3500'},
    {"id":4,"name":'壶关县地鑫圣水种养专业合作社',"fenpei":'已脱贫每人一百未脱贫每人两百',"zijin":'300',"zichan":'-','daikuan':'0','touzi':'0','shouru':'1589'},
    {"id":5,"name":'壶关县圣华种养专业合作社',"fenpei":'已脱贫每人一百未脱贫每人两百',"zijin":'126',"zichan":'0','daikuan':'10','touzi':'5','shouru':'12356'},
    {"id":6,"name":'长治市紫团饮业有限公司',"fenpei":'已脱贫每人一百未脱贫每人两百',"zijin":'248',"zichan":'0','daikuan':'5','touzi':'10','shouru':'3014'}
]



/***************************************分割线 合作社带动贫困户情况表 数据********************************************************/

var DDdaiDongQingkuang = [
    {"allcount":'30','fpcount':'15','cycount':'25','hucount':'120','cuncount':'6','tphcount':'30','tprcount':'68','ddcount':'100'},
    {"allcount":'20','fpcount':'17','cycount':'15','hucount':'100','cuncount':'6','tphcount':'20','tprcount':'46','ddcount':'90'},
    {"allcount":'26','fpcount':'20','cycount':'17','hucount':'80','cuncount':'5','tphcount':'26','tprcount':'50','ddcount':'76'},
    {"allcount":'30','fpcount':'28','cycount':'20','hucount':'70','cuncount':'4','tphcount':'30','tprcount':'90','ddcount':'70'},
    {"allcount":'30','fpcount':'30','cycount':'27','hucount':'86','cuncount':'5','tphcount':'35','tprcount':'60','ddcount':'86'},
    {"allcount":'46','fpcount':'35','cycount':'30','hucount':'68','cuncount':'5','tphcount':'40','tprcount':'50','ddcount':'68'}
]

/*****************************************分割线 XN 吸纳贫困户情况 数据*************************************************************/

var XNxiNapinkun = [
    {'name':'黎城县上善生态养殖专业合作社','fr':'能人','ry':'-','ccount':'10','hcount':'120','rcount':'300','pkccount':'5','pkhcount':'46','pkrcount':'120'},
    {'name':'沁县鹏胜养殖有限公司','fr':'能人','ry':'-','ccount':'5','hcount':'56','rcount':'120','pkccount':'2','pkhcount':'30','pkrcount':'80'},
    {'name':'恒信蔬菜种植合作社','fr':'村长','ry':'-','ccount':'9','hcount':'100','rcount':'240','pkccount':'5','pkhcount':'40','pkrcount':'54'},
    {'name':'沁县晋农信小米专业合作社','fr':'能人','ry':'-','ccount':'12','hcount':'154','rcount':'360','pkccount':'6','pkhcount':'26','pkrcount':'76'},
    {'name':'武乡县大山禽业有限公司','fr':'返乡青年','ry':'-','ccount':'8','hcount':'94','rcount':'210','pkccount':'4','pkhcount':'30','pkrcount':'66'},
    {'name':'小南清秀军种养合作社','fr':'能人','ry':'-','ccount':'7','hcount':'66','rcount':'156','pkccount':'7','pkhcount':'30','pkrcount':'86'}
]

/*****************************************分割线 收益情况统计 SY 数据**********************************************************************/

var SYfuPinshouyi = [
     {'name':'黎城县上善生态养殖专业合作社','nsy':'能人','zsy':'5','snsy':'-','qsy':'2','dksy':'2','xj':'5','zxh':'30','zxj':'2','snh':'-','snj':'-','qth':'30','qtj':'2','dkh':'30','dkj':'2','pkh':'30','pkj':'2'},
     {'name':'沁县鹏胜养殖有限公司','nsy':'村长','zsy':'5','snsy':'-','qsy':'5','dksy':'5','xj':'9','zxh':'20','zxj':'5','snh':'-','snj':'-','qth':'30','qtj':'2','dkh':'30','dkj':'2','pkh':'30','pkj':'2'},
     {'name':'恒信蔬菜种植合作社','nsy':'能人','zsy':'5','snsy':'-','qsy':'6','dksy':'6','xj':'12','zxh':'26','zxj':'6','snh':'-','snj':'-','qth':'30','qtj':'2','dkh':'30','dkj':'2','pkh':'30','pkj':'2'},
     {'name':'沁县晋农信小米专业合作社','nsy':'返乡青年','zsy':'5','snsy':'-','qsy':'4','dksy':'8','xj':'8','zxh':'30','zxj':'4','snh':'-','snj':'-','qth':'30','qtj':'2','dkh':'30','dkj':'2','pkh':'30','pkj':'2'},
     {'name':'武乡县大山禽业有限公司','nsy':'能人','zsy':'5','snsy':'-','qsy':'7','dksy':'7','xj':'7','zxh':'30','zxj':'7','snh':'-','snj':'-','qth':'30','qtj':'2','dkh':'30','dkj':'2','pkh':'30','pkj':'2'},
     {'name':'小南清秀军种养合作社','nsy':'能人','zsy':'5','snsy':'-','qsy':'5','dksy':'5','xj':'10','zxh':'46','zxj':'5','snh':'-','snj':'-','qth':'30','qtj':'2','dkh':'30','dkj':'2','pkh':'30','pkj':'2'}    
]

/*****************************************分割线 扶贫资金渠道 FP 数据*********************************************************************/

var FPziJinQuDao = [
    {"name":'黎城县上善生态养殖专业合作社','xz':'合作社','sshy':'养殖','zxtr':'0.32','sntr':'-','qttr':'-','dktr':'-','cjttr':'-','pkhtr':'-'},
    {"name":'沁县鹏胜养殖有限公司','xz':'龙头企业','sshy':'养殖','zxtr':'6','sntr':'-','qttr':'-','dktr':'2','cjttr':'6','pkhtr':'2'},
    {"name":'恒信蔬菜种植合作社','xz':'合作社','sshy':'养殖','zxtr':'0.32','sntr':'-','qttr':'-','dktr':'-','cjttr':'-','pkhtr':'-'},
    {"name":'沁县晋农信小米专业合作社','xz':'龙头企业','sshy':'养殖','zxtr':'6','sntr':'-','qttr':'-','dktr':'2','cjttr':'6','pkhtr':'2'},
    {"name":'武乡县大山禽业有限公司','xz':'合作社','sshy':'养殖','zxtr':'0.32','sntr':'-','qttr':'-','dktr':'-','cjttr':'-','pkhtr':'-'},
    {"name":'小南清秀军种养合作社','xz':'龙头企业','sshy':'养殖','zxtr':'6','sntr':'-','qttr':'-','dktr':'2','cjttr':'6','pkhtr':'2'}
]











