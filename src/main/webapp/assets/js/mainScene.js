/**
 * 主3d场景
 */

;(function () {
    var MainScene = function (canvas) {
        this.canvas = canvas
        this.width = this.canvas.width = canvas.offsetWidth
        this.height = this.canvas.height = canvas.offsetHeight

        this.scene = new THREE.Scene()
        this.camera = new THREE.PerspectiveCamera(75, this.canvas.width / this.canvas.height, 0.1, 10000)
        this.renderer = new THREE.WebGLRenderer({
            canvas: canvas,
            alpha: true
        })
        this.controls = new THREE.TrackballControls(this.camera)
        this.camera.position.x = 0
        this.camera.position.y = 0
        this.camera.position.z = 150

        // this._initStats()
        this._initLight()
        this._render()
        this._initEarth()
    }

    var p = MainScene.prototype

    // 加载数据并添加到场景中
    p.addGeojson = function (geojson) {
        var features = geojson.features || geojson
        var feature, shapes = [], vector, shape, mesh, geometry, path, lineGeometry, lineMaterial, lineMesh
        var material
        var lineMaterial = new THREE.LineBasicMaterial({
            color: 0xaaaaaa,
            opacity: 0.9,
            transparent: true
        })
        for (var i in features) {
            var vectors = []
            if (features[i].geometry.type === 'Polygon') {
                var a
                for (var k in features[i].geometry.coordinates[0]) {
                    vector = this._coordToVector(features[i].geometry.coordinates[0][k])
                    vectors.push(vector)
                }

                // 创建形状
                shape = new THREE.Shape(vectors)
                geometry = new THREE.ExtrudeGeometry(shape, {
                    amount: 2,
                    bevelEnabled: false
                })
                material = new THREE.MeshPhongMaterial({
                    color: 0x666666,
                    opacity: 0.8,
                    transparent: true
                })

                mesh = new THREE.Mesh(geometry, material)
                mesh.position.z = -2
                mesh.dataType = 'polygon'
                mesh.dataName = features[i].properties.name

                // 创建边线路径
                path = new THREE.Path(vectors)
                lineGeometry = path.createPointsGeometry(12)
                lineMesh = new THREE.Line(lineGeometry, lineMaterial)
                lineMesh.position.z = 0

                this.scene.add(mesh)
                this.scene.add(lineMesh)
            }
        }
    }

    // 添加主要数据
    p.addData = function (data, shiGeojson, show) {
        this.keys = data[0]
        this.data = data
        if (show === undefined) show = true
        show = !!show

        var investGroup = new THREE.Group()
        var incomeGroup = new THREE.Group()
        // var poorFamilyGroup = new THREE.Group() // 3

        var position, vector, geometry, material, mesh, investHeight, incomeHeight, poorFamilyHeight
        var vectorsData = []

        for (var i in this.data) {
            if (i == 0) continue
            position = this.data[i][27]
            poorFamilyHeight = this.data[i][3]
            investHeight = this.data[i][5]
            incomeHeight = this.data[i][15]

            if (position) {
                position = position.split(',')
                position = this._randPosition(position)
                vector = this._coordToVector(position)

                // 添加投资数据
                investHeight /= 30
                geometry = new THREE.BoxGeometry(0.5, 0.5, investHeight || 0.04)
                material = new THREE.MeshPhongMaterial({  // 投入材质 蓝
                    color: 0x00aaff,
                    opacity: 0.8,
                    transparent: true
                })
                mesh = new THREE.Mesh(geometry, material)
                mesh.position.x = vector.x
                mesh.position.y = vector.y
                mesh.position.z = investHeight / 2 || 0.02
                mesh.dataIndex = i
                investGroup.add(mesh)

                // 添加收益数据
                incomeHeight /= 30
                geometry = new THREE.BoxGeometry(0.6, 0.6, incomeHeight || 0.02)
                material = new THREE.MeshPhongMaterial({  // 收入材质 红
                    color: 0xff0000,
                    opacity: 0.8,
                    transparent: true
                })
                mesh = new THREE.Mesh(geometry, material)
                mesh.position.x = vector.x
                mesh.position.y = vector.y
                mesh.position.z = incomeHeight / 2 || 0.01
                mesh.dataIndex = i
                incomeGroup.add(mesh)

                // 添加吸纳贫困户数数据
                if (poorFamilyHeight > 0) {
                    vectorsData.push({
                        vector: vector,
                        value: poorFamilyHeight

                    })
                    // poorFamilyHeight /= 50
                    // geometry = new THREE.BoxGeometry(0.4, 0.4, poorFamilyHeight)
                    // material = new THREE.MeshPhongMaterial({  // 贫困户材质 黄
                    //     color: 0xffff00,
                    //     opacity: 0.8,
                    //     transparent: true
                    // })
                    // mesh = new THREE.Mesh(geometry, material)
                    // mesh.position.x = vector.x
                    // mesh.position.y = vector.y
                    // mesh.position.z = poorFamilyHeight / 2
                    // mesh.dataIndex = i
                    // poorFamilyGroup.add(mesh)
                }
            }
        }

        this._createTerrain(vectorsData, shiGeojson)

        investGroup.visible = show
        incomeGroup.visible = show
        // poorFamilyGroup.visible = show

        this.scene.add(investGroup, incomeGroup)

        this.investGroup = investGroup
        this.incomeGroup = incomeGroup
        // this.poorFamilyGroup = poorFamilyGroup
    }

    // 设置可见性
    p.showInvest = function (bool) {
        if (bool === undefined) return
        this.investGroup.visible = !!bool
    }
    p.showIncome = function (bool) {
        if (bool === undefined) return
        this.incomeGroup.visible = !!bool
    }
    p.showPoorFamily = function (bool) {
        if (bool === undefined) return
        // this.poorFamilyGroup.visible = !!bool
        this.plane.visible = !!bool

        if (this.plane.visible) {
            this._animateTerrain()
        }
    }

    // 根据点击事件的x y坐标位置计算被选中的盒子的对象
    p.pickData = function (x, y) {
        var data, values
        var object = this._pickObject(x, y)

        if (object && object.dataIndex) {
            data = []
            values = this.data[object.dataIndex]
            for (var i in this.keys) {
                data.push({
                    name: this.keys[i],
                    value: values[i]
                })
            }
        } else if (object && object) {
            data = object.dataName
        }

        return data
    }

    // 当鼠标移到3d对象时变色相应
    p.hover = function (x, y) {
        var object = this._pickObject(x, y)
        if (this._lastObject !== object) {
            if (this._lastObject) {
                this._lastObject.material.color.set(this._lastColor)
            }
            if (object && (object.dataIndex || object.dataType)) {
                this._lastObject = object
                this._lastColor = object.material.color.getHex()
                if (object.dataType === 'polygon') {
                    object.material.color.set(0x6f6f6f)
                } else {
                    object.material.color.set(0x00ff00)
                }
                return true
            } else {
                this._lastObject = undefined
            }
        }
    }

    // 当窗口发生变化时重新调整
    p.resize = function () {
        this.width = this.canvas.width = this.canvas.offsetWidth
        this.height = this.canvas.height = this.canvas.offsetHeight
        this.camera.aspect = this.width / this.height
        this.camera.updateProjectionMatrix()

        this.renderer.setSize(this.width, this.height)
    }

    // 初始化灯光
    p._initLight = function () {
        var light = new THREE.AmbientLight(0x888888)   // 添加一个全局环境光
        var light2 = new THREE.PointLight(0xffddaa)
        light2.position.x = 50
        light2.position.y = -50
        light2.position.z = 80
        this.scene.add(light, light2)
    }

    // 根据贫困户吸收数量计算地形走势
    p._createTerrain = function (vectorsData, geojson) {
        var geometry = new THREE.PlaneGeometry(200, 200, 127, 127)
        var material = new THREE.MeshBasicMaterial({
            transparent: true,
            wireframe: true,
            side: THREE.DoubleSide,
            alphaMap: this._createAlphaMap(vectorsData, geojson)
        })
        var plane = new THREE.Mesh(geometry, material);
        var vertices = geometry.vertices
        var distance, k, value
        var _this = this

        this.plane = plane
        this.plane.visible = false
        this.verticesHeights = []
        plane.position.z = 0
        for (var i in vectorsData) {
            value = vectorsData[i].value
            for (k in vertices) {
                distance = vectorsData[i].vector.distanceTo(vertices[k])
                distance = Math.max(distance, 1)
                vertices[k].z += value / (Math.pow(distance + 1, 2) * 4)
                if (i == vectorsData.length - 1) {
                    _this.verticesHeights.push(vertices[k].z)
                }
            }
        }
        material.map = this._createMap(vectorsData)
        this.scene.add(plane)
    }

    // 生成alpha贴图，只显示长治市区域的
    p._createAlphaMap = function (vectorsData, geojson) {
        var canvas = document.createElement('canvas')
        var ctx = canvas.getContext('2d')
        var features = geojson.features
        var vectors = [], vector, x, y

        canvas.width = 512
        canvas.height = 512
        canvas.className = 'hahahaah'
        ctx.fillStyle = '#000000'
        ctx.fillRect(0, 0, 512, 512)

        ctx.beginPath()
        ctx.fillStyle = '#ffffff'

        for (var i in features[0].geometry.coordinates[0]) {
            vector = this._coordToVector(features[0].geometry.coordinates[0][i])
            x = (100 + vector.x) * 2.56
            y = (100 - vector.y) * 2.56
            if (i == 0) {
                ctx.moveTo(x, y)
            } else {
                ctx.lineTo(x, y)
            }
        }
        ctx.fill()
        // document.body.appendChild(canvas)
        return new THREE.CanvasTexture(canvas)
    }

    // 生成普通颜色贴图
    p._createMap = function (vectorsData) {
        var canvas = document.createElement('canvas')
        var ctx = canvas.getContext('2d')
        var x, y, imageData, data, value
        var vertices = this.plane.geometry.vertices

        canvas.width = 128
        canvas.height = 128
        canvas.className = 'hahahaah'

        ctx.fillStyle = 'rgba(0, 0, 255, 0.8)'
        ctx.fillRect(0, 0, 128, 128)

        imageData = ctx.getImageData(0, 0, 128, 128)
        data = imageData.data

        for (var i in vertices) {
            value = vertices[i].z
            if (value < 5) {
                data[i * 4 + 1] = vertices[i].z * 51
            } else if (value < 20) {
                data[i * 4] = (vertices[i].z - 5) * 17
                data[i * 4 + 1] = 255
                data[i * 4 + 2] = 255 - data[i * 4]
            } else {
                data[i * 4] = 255
                data[i * 4 + 1] = 255 - (vertices[i].z - 20)* 12.75
                data[i * 4 + 2] = 0
            }
        }

        ctx.putImageData(imageData, 0, 0)
        
        // document.body.appendChild(canvas)
        return new THREE.CanvasTexture(canvas)
    }

    // 地形长高动画
    p._animateTerrain = function () {
        var vertices = this.plane.geometry.vertices
        var geometry = this.plane.geometry
        var verticesHeights = this.verticesHeights
        var _this = this

        // 动画开始之前先把高度归零
        for (var i in vertices) {
            vertices[i].z = 0
        }
        geometry.verticesNeedUpdate = true
        var k = 0
        function animate() {
            for (var i in vertices) {
                vertices[i].z += ((verticesHeights[i] - vertices[i].z) / 10)
            }
            geometry.verticesNeedUpdate = true

            if (k < 60) {
                requestAnimationFrame(animate)
            }
            k++
        }
        animate()
    }

    // 初始化stats
    p._initStats = function () {
        this.stats = new Stats()
        this.stats.domElement.style.left = 'auto'
        this.stats.domElement.style.right = '0px'
        this.stats.domElement.style.top = '0px'
        document.body.appendChild(this.stats.domElement)
    }

    // 初始化右上角旋转的小球球
    p._initEarth = function () {
        var canvas = document.getElementById('earth-canvas')
        var scene = new THREE.Scene()
        var camera = new THREE.PerspectiveCamera(75, 1, 0.1, 10000)
        var renderer = new THREE.WebGLRenderer({
            canvas: canvas,
            alpha: true
        })

        var sphereGeometry = new THREE.SphereGeometry(1.9, 32, 32)
        var sphereMaterial = new THREE.MeshPhongMaterial({
            transparent: true
        })
        var sphere = new THREE.Mesh(sphereGeometry, sphereMaterial)

        var planeGeometry = new THREE.PlaneGeometry(7, 7, 1, 1)
        var planeMaterial = new THREE.MeshPhongMaterial({
            transparent: true
        })
        var plane = new THREE.Mesh(planeGeometry, planeMaterial)

        var light = new THREE.AmbientLight(0x888888)
        var pointLight = new THREE.PointLight(0xffffff)
        var loader = new THREE.TextureLoader()

        camera.position.z = 5
        pointLight.position.x = 10
        pointLight.position.z = 10

        scene.add(light)
        scene.add(pointLight)
        
        loader.load('assets/image/earth.png', function (texture) {
            sphereMaterial.map = texture
            scene.add(sphere)
        })

        loader.load('assets/image/earth-bg.png', function (texture) {
            planeMaterial.map = texture
            scene.add(plane)
        })

        function render () {
            sphere.rotateY(0.03)
            plane.rotateZ(-0.005)
            renderer.render(scene, camera)
            requestAnimationFrame(render)
        }
        render()
    }

    // 拾取对象, x: 事件x坐标，y: 事件y坐标
    p._pickObject = function (x, y, bool) {
        var mouse = new THREE.Vector2()
        var raycaster = new THREE.Raycaster()

        mouse.x = (x / this.width) * 2 - 1
        mouse.y = - (y / this.height) * 2 + 1

        raycaster.setFromCamera(mouse, this.camera)

        if (raycaster.intersectObjects(this.scene.children, true).length > 0) {
            return raycaster.intersectObjects(this.scene.children, true)[0].object
        }
    }

    // 经纬度转换为三维世界坐标, 借用一下leaflet的坐标转换方法
    p._coordToVector = function (coord) {
        var center = [12567210.556639412, 4368152.1620292235]
        var max = 85.0511287798
        var lng = coord[0]
        var lat = Math.max(Math.min(max, coord[1]), -max)
        var R = 6378137
        var d = Math.PI / 180
        var sin = Math.sin(lat * d)

        var x = R * lng * d - center[0]
        var y = R * Math.log((1 + sin) / (1 - sin)) / 2 - center[1]

        return new THREE.Vector3(x / 1000, y / 1000, 0)
    }

    // 对位置进行随机偏移
    p._randPosition = function (coord) {
        coord[0] = parseFloat(coord[0]) + (Math.random() - 0.5) * 0.05
        coord[1] = parseFloat(coord[1]) + (Math.random() - 0.5) * 0.05
        return coord
    }

    p._render = function () {
        var _this = this

        cancelAnimationFrame(this.timer)
        function render() {
            // _this.stats.update()
            _this.controls.update()
            _this.renderer.render(_this.scene, _this.camera)
            _this.timer = requestAnimationFrame(render)
        }
        render()
    }

    window.MainScene = MainScene
})()