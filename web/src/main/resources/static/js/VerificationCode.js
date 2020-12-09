(function($) {
    /**生成一个随机数**/
    function randomNum(min, max) {
        return Math.floor(Math.random() * (max - min) + min);
    }
    /**生成一个随机色**/
    function randomColor(min, max) {
        var r = randomNum(min, max);
        var g = randomNum(min, max);
        var b= randomNum(min, max);
        return "rgb(" + r + "," + g + "," + b + ")";
    }
    /**绘制验证码图片**/
    function drawPic() {
        var canvas = document.getElementById("canvas");
        var width = canvas.width;
        var height = canvas.height;
        //获取该canvas的2D绘图环境
        var ctx = canvas.getContext('2d');
        ctx.textBaseline ='bottom';
        /**绘制背景色**/
        ctx.fillStyle = "#efefef";
        //颜色若太深可能导致看不清
        ctx.fillRect(0,0,width,height);
        /**绘制文字**/
        var str ='ABCEFGHJKLMNPQRSTWXY123456789';
        var code="";
        //生成四个验证码
        for(var i=1;i<=4;i++) {
            var txt = str[randomNum(0,str.length)];
            code=code+txt;
        }

        ctx.fillStyle = "#000000";
        //随机生成字体颜色
        ctx.font = '26px 微软雅黑';
        //ctx.translate(10, 10);
        ctx.fillText(code,0,30);

        // 绘制干扰线**
        for(var i=0;i<3;i++) {
            ctx.strokeStyle = randomColor(40, 180);
            ctx.beginPath();
            ctx.moveTo(randomNum(0,width/2), randomNum(0,height/2));
            ctx.lineTo(randomNum(0,width/2), randomNum(0,height));
            ctx.stroke();
        }
        //绘制干扰点
        for(var i=0;i <50;i++) {
            ctx.fillStyle = randomColor(255);
            ctx.beginPath();
            ctx.arc(randomNum(0, width), randomNum(0, height),1,0,2* Math.PI);
            ctx.fill();
        }
        return code;
    }

    $.fn.makeCode = function (options) {
        let code = drawPic();
        $(this).val(code);
    }
})(jQuery);