//一个修改宽高的函数
function setStatue (ele) {
    //传入的参数为数组形式
    // 获取元素的父元素的宽和高
    var len = ele.length ||0
    for(var i=0;i<len;i++){
        var str = "#" + ele[i]
        // 获取元素的父元素的宽和高 并设置成自己的宽和高
        var width = $(str).parent().width()
        var height = $(str).parent().height()        
        $(str).width(width)
        $(str).height(height)        
    }
}
//保存所有元素参数
var allelement = ['pinKunHuTuopin','ziJingShouRu','fenHongQingKuangOne','fenHongQingKuangTwo','ziJingShouYi','shiShiZhuTiL','shiShiZhuTiR','touRuShouYiL','touRuShouYiR']
setStatue(allelement)
/**
 * 贫困户脱贫情况饼图数据实例化
 */
var pinKunHuTuopinDom = document.getElementById("pinKunHuTuopin")
var pinKunHuTuopinPie = echarts.init(pinKunHuTuopinDom);

/**
 * 资金收入 数据实例化
 */
var ziJingShouRuDom = document.getElementById("ziJingShouRu")
var ziJingShouRuCategory = echarts.init(ziJingShouRuDom)

/**
 * 分红情况 数据实例化
 */
//第一个饼图数据
var fenHongQingKuangOneDom = document.getElementById("fenHongQingKuangOne")
var fenHongQingKuangOnePie = echarts.init(fenHongQingKuangOneDom)

//第二个饼图数据
var fenHongQingKuangTwoDom = document.getElementById("fenHongQingKuangTwo")
var fenHongQingKuangTwoPie = echarts.init(fenHongQingKuangTwoDom)

/**
 * 资金收益 数据实例化
 */
var ziJingShouYiDom = document.getElementById("ziJingShouYi")
var ziJingShouYiCategory = echarts.init(ziJingShouYiDom)

/**
 * 实施主体数量 数据实例化
 */
//柱状图 实例化
var shiShiZhuTiLDom = document.getElementById("shiShiZhuTiL")
var shiShiZhuTiCategory = echarts.init(shiShiZhuTiLDom)

//饼图 实例化
var shiShiZhuTiRDom = document.getElementById("shiShiZhuTiR")
var shiShiZhuTiCake = echarts.init(shiShiZhuTiRDom)

/**
 * 贫困户投入收益 数据实例化
 */
//柱状图 实例化
var touRuShouYiLDom = document.getElementById("touRuShouYiL")
var touRuShouYiCategory = echarts.init(touRuShouYiLDom)

//饼图 实例化
var touRuShouYiRDom = document.getElementById("touRuShouYiR")
var touRuShouYiCake = echarts.init(touRuShouYiRDom)







//保存所有数据的实例化
pinKunHuTuopinPie.setOption(pinKunHuTuopin)
ziJingShouRuCategory.setOption(ziJingShouRu)
fenHongQingKuangOnePie.setOption(fenHongQingKuangOne)
fenHongQingKuangTwoPie.setOption(fenHongQingKuangTwo)
ziJingShouYiCategory.setOption(ziJingShouYi)
shiShiZhuTiCategory.setOption(shiShiZhuTiL)
shiShiZhuTiCake.setOption(shiShiZhuTiR)
touRuShouYiCategory.setOption(touRuShouYiL)
touRuShouYiCake.setOption(touRuShouYiR)

//监听窗口变化
window.onresize=function(){  
    setStatue(allelement)  
    pinKunHuTuopinPie.resize()                                                      
    ziJingShouRuCategory.resize()
    fenHongQingKuangOnePie.resize()
    fenHongQingKuangTwoPie.resize()
    ziJingShouYiCategory.resize()
    shiShiZhuTiCategory.resize()
    shiShiZhuTiCake.resize()
    touRuShouYiCategory.resize()
    touRuShouYiCake.resize()
}


