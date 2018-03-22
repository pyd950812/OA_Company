//�޸�min-width������
    function WindeoResize(){
        $('.glyphicon').map(function(item){
            var widthParent = $(this).parent().width()
            var widthSpan = $(this).width()
            $(this).siblings('div').width(widthParent - widthSpan-8)
        })
    }
    WindeoResize()
    window.onresize = WindeoResize;