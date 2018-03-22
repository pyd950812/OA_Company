/**
 * 块型图表
 */

;(function () {
    var Block = function (canvas, data) {
        this.canvas = canvas
        this.data = data
        this.ctx = canvas.getContext('2d')
        this.width = canvas.offsetWidth
        this.height = canvas.offsetHeight

        this._init()
    }

    var p = Block.prototype

    // 初始化图表
    p._init = function () {
        this._initData()
        this._drawBlock()
    }

    // 初始化数据
    p._initData = function () {
        var max = 0
        for (var i in this.data) {
            max = Math.max(max, this.data[i].invest, this.data[i].income)
        }
        this.max = max
        this.unit = max / 12
    }

    // 绘制块
    p._drawBlock = function () {
        var size = Math.floor((this.width - 70) / 12)
        var size2 = Math.floor((this.height - 20) / (this.data.length * 2.5))
        var investNum, incomeNum, y

        size = Math.min(size, size2)
        for (var i in this.data) {
            investNum = Math.ceil(this.data[i].invest / this.unit)
            incomeNum = Math.ceil(this.data[i].income / this.unit)
            y = Math.floor(size * i * 2.5 + 20)

            this.ctx.beginPath()
            this.ctx.font = '14px 微软雅黑'
            this.ctx.fillStyle = '#ffffff'
            this.ctx.fillText(this.data[i].name, 5, y + 12)

            this.ctx.beginPath()
            this.ctx.font = '10px 微软雅黑'
            this.ctx.fillText(this.data[i].invest, size * investNum + 65, y + 11)
            this.ctx.fillText(this.data[i].income, size * incomeNum + 65, y + 30)

            this.ctx.beginPath()
            this.ctx.fillStyle = '#0864d7'
            for (var k = 0; k < investNum; k++) {
                this.ctx.fillRect(k * size + 60, y, size - 3, size - 3)
            }
            if (investNum === 0) {
                this.ctx.fillRect(k * size + 60, y, 1, size - 3)
            }

            this.ctx.beginPath()
            this.ctx.fillStyle = '#bc2f2e'
            for (var k = 0; k < incomeNum; k++) {
                this.ctx.fillRect(k * size + 60, y + size, size - 3, size - 3)
            }
            if (incomeNum === 0) {
                this.ctx.fillRect(k * size + 60, y + size, 1, size - 3)
            }
        }
    }

    window.Block = Block
})()