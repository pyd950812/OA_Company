/**
 * 玫瑰图图表
 * @param canvas: canvas dom 节点
 * @param data: 数据
 */

;(function () {
    // 构造函数
    var Rose = function (canvas, data) {
        this.canvas = canvas
        this.ctx = canvas.getContext('2d')
        this.width = canvas.offsetWidth
        this.height = canvas.offsetHeight
        this.data = data
        this.radius = Math.min(this.width, this.height) * 0.45
        this.sum = 0
        this.center = {
            x: this.width / 2,
            y: this.height / 2
        }
    
        this._init()
    }
    
    var p = Rose.prototype
    
    // 初始化图表
    p._init = function () {
        var _this = this

        // 对数据进行排序
        this.data.sort(function (a, b){
            return a.value - b.value
        })
        this.maxData = this.data[this.data.length - 1].value
    
        for (var i in this.data) {
            this.sum += this.data[i].value
        }
        
        this._render()
        
        // 添加交互效果
        this.canvas.addEventListener('mousemove', function (e) {
            var x = e.offsetX
            var y = e.offsetY
            _this._render(x, y)
        })
    }
    
    // 主渲染函数
    p._render = function (x, y) {
        var value, reg, alpha, smallRadius, bigRadius, r, isInPath
        var startReg = Math.PI / 2
        var radius = this.radius * 0.65
        var min = 0.05 * radius
        var max = 0.3 * radius
    
        x = x || 0
        y = y || 0
        this.ctx.clearRect(0, 0, this.width, this.height)
        for (var i in this.data) {
            value = this.data[i].value
            reg = (Math.PI * 2 * value) / this.sum
            alpha = value * 0.6 / this.maxData + 0.4
            r = min + value * max / this.maxData
    
            reg1 = Math.PI * 2 - startReg
            reg2 = Math.PI * 2 - reg - startReg
    
            this.ctx.beginPath()
            this.ctx.fillStyle = 'rgba(188, 47, 46, ' + alpha + ')'
            this._createPath(reg1, reg2, r)

            isInPath = this.ctx.isPointInPath(x, y)
            if (isInPath) {
                this._createPath(reg1, reg2, r + 0.1 * radius)
            }
            this.ctx.fill()

            if (isInPath) {
                this._createLabel(reg1, reg2, i)
            }
    
            startReg += reg
        }
    }
    
    // 创建扇形区域的路径
    p._createPath = function (reg1, reg2, r) {
        var radius = this.radius * 0.65
        var smallRadius = radius - r
        var bigRadius = radius + r
        
        this.ctx.moveTo(this.center.x + smallRadius * Math.cos(reg2), this.center.y + smallRadius * Math.sin(reg2))
        this.ctx.arc(this.center.x, this.center.y, bigRadius, reg2, reg1, false)
        this.ctx.lineTo(this.center.x + smallRadius * Math.cos(reg1), this.center.y + smallRadius *  Math.sin(reg1))
        this.ctx.arc(this.center.x, this.center.y, smallRadius, reg1 , reg2, true)
    }

    // 创建标签
    p._createLabel = function (reg1, reg2, i) {
        var reg = (reg1 + reg2) / 2
        var radius = this.radius * 0.65
        var x = this.center.x + radius * Math.cos(reg)
        var y = this.center.y + radius * Math.sin(reg)
        var x2, y2
        var name = this.data[i].name
        var value = this.data[i].value

        this.ctx.beginPath()
        this.ctx.fillStyle = '#ffffff'
        this.ctx.strokeStyle = '#ffffff'
        this.ctx.lineWidth = 1

        // 创建小圆点
        this.ctx.arc(x, y, 3, 0, Math.PI * 2)
        this.ctx.fill()

        // 创建环线
        this.ctx.beginPath()
        this.ctx.arc(x, y, 6, 0, Math.PI * 2)
        this.ctx.stroke()

        // 创建斜线及横线
        if (reg < Math.PI / 2 || reg > Math.PI * 3 / 2) {
            x2 = this.width - 50
            x3 = this.width
        } else {
            x2 = 50
            x3 = 0
        }
        y2 = y + (x2 - x) * Math.tan(reg)
        if (y2 > this.height - 20) {
            y2 = this.height - 20
        }
        if  (y2 < 20) {
            y2 = 20
        }

        this.ctx.beginPath()
        this.ctx.moveTo(x, y)
        this.ctx.lineTo(x2, y2)
        this.ctx.lineTo(x3, y2)
        this.ctx.stroke()

        // 创建文字
        this.ctx.font = '14px 微软雅黑'
        if (x2 < x3) {
            this.ctx.fillText(name, x2 + 4, y2 - 6)
            this.ctx.fillText(value, x2 + 4, y2 + 16)
        } else {
            this.ctx.fillText(name, 2, y2 - 6)
            this.ctx.fillText(value, 2, y2 + 16)
        }
        
    }

    window.Rose = Rose
})();
