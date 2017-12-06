<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<script>
    $(function () {
        //通过库存量，校验数量input处的数字
        var quantity = ${p.stock};
        $("input.productNumberSetting").keyup(function () {
            var num = $(this).val();
            num = parseInt(num);
            if(isNaN(num)){
                num = 1;
            }
            if(num <= 0){
                num = 1;
            }
            if(num >quantity){
                num = quantity;
            }
            $("input.productNumberSetting").val(num);
        });
        //increaseNumber
        $("a.increaseNumber").click(function () {
            var num = $("input.productNumberSetting").val();
            num++;
            if(num>quantity)
                num = quantity;
            $("input.productNumberSetting").val(num);
        });
        //decreaseNumber
        $("a.decreaseNumber").click(function () {
            var num = $("input.productNumberSetting").val();
            num--;
            if(num<=0)
                num = 1;
            $("input.productNumberSetting").val(num);
        });
        //addCartLink
        $(".addCartLink").click(function () {
            var page = "forecheckLogin";
            $.get(
                page,
                function (result) {
                    if("success"==result){
                        var pid = ${p.id};
                        var num = $("input.productNumberSetting").val();
                        var addCartPage = "foreaddCart";
                        //提交到购物车页forecart
                        $.get(
                            addCartPage,
                            {"pid":pid,"num":num},
                            function (result) {
                                if("success"==result){
                                    $(".addCartButton").html("已加入购物车");
                                    $(".addCartButton").attr("disabled","disabled");
                                    $(".addCartButton").css("background-color","lightgray");
                                    $(".addCartButton").css("border-color","lightgray");
                                    $(".addCartButton").css("color","black");
                                }else{
                                    alert("fail");
                                }
                            }
                        );
                    }else{
                        $("#loginModal").modal("show");
                    }
                }
            );
            //不进行默认行为，不跳转到#nowhere
            return false;
        });

        //Ajax检查是否已登录
        $(".buyLink").click(function () {
            var page = "forecheckLogin";
            $.get(
                page,
                function (result) {
                    if("success"==result){
                        //页面跳转，进入forebuy，即将提交订单
                        var num = $(".productNumberSetting").val();
                        location.href = $(".buyLink").attr("href")+"&num="+num;
                    }else{
                        $("#loginModal").modal("show");
                    }
                }
            );
            //取消buyLink的默认行为
            return false;
        });

        //loginModal  loginSubmitButton
        $("button.loginSubmitButton").click(function () {
            var name = $("input#name").val();
            var password = $("input#password").val();
            if(0==name.length || 0==password.length){
                $("span.errorMessage").html("请输入账号密码");
                $("div.loginErrorMessageDiv").show();
                //不提交表单
                return false;
            }
            var page = "foreloginAjax";
            $.get(
                page,
                {"name":name,"password":password},
                function (result) {
                    if("success"==result){
                        location.reload();
                    }else {
                        $("span.errorMessage").html("账号密码错误");
                        $("div.loginErrorMessageDiv").show();
                    }
                }
            );
            //下面这句，删除也不影响功能
            return true;
        });

        $("img.smallImage").mouseenter(function () {
            var imgUrl = $(this).attr("bigImageURL");
            $("img.bigImg").attr("src",imgUrl);
        });

        $("img.bigImg").load(
            function(){
                $("img.smallImage").each(function(){
                    var bigImageURL = $(this).attr("bigImageURL");
                    img = new Image();
                    img.src = bigImageURL;

                    img.onload = function(){
                        console.log(bigImageURL);
                        $("div.img4load").append($(img));
                    };
                });
            }
        );
        

        $(".addCartLink").click(function () {
            var page = "forecheckLogin";
            $.get(
                page,
                function (result) {
                    if("success"==result){

                    }else{
                        $("#loginModal").modal("show");
                    }
                }
            );
            return false;
        });
    });
</script>
<div class="imgAndInfo">
    <div class="imgInimgAndInfo">
        <img src="img/productSingle/${p.productImage.id}.jpg" class="bigImg"/>
        <div class="smallImageDiv">
            <c:forEach var="pi" items="${p.productSingleImage}">
                <img src="img/productSingle_small/${pi.id}.jpg" class="smallImage" bigImageURL="img/productSingle/${pi.id}.jpg">
            </c:forEach>
        </div>
        <div class="img4load hidden"></div>
    </div>

    <div class="infoInimgAndInfo">
        <div class="productTitle">${p.name}</div>
        <div class="productSubTitle">${p.subTitle}</div>
        <!--价格区域-->
        <div class="productPrice">
            <div class="juhuasuan">
                <span class="juhuasuanBig">聚划算</span>
                <span>此商品即将参加聚划算,<span class="juhuasuanTime">1天19小时</span>后开始,</span>
            </div>
            <div class="productPriceDiv">
                <div class="gouwujuanDiv">
                    <img height="16px" src="img/site/gouwujuan.png"/><span>全天猫实物商品通用</span>
                </div>
                <div class="originalDiv">
                    <span class="originalPriceDesc">价格</span>
                    <span class="originalPriceYuan">￥</span>
                    <span class="originalPrice">
                        <fmt:formatNumber type="number" value="${p.originalPrice}" minFractionDigits="2"></fmt:formatNumber>
                    </span>
                </div>
                <div class="promotionDiv">
                    <span class="promotionPriceDesc">促销价</span>
                    <span class="promotionPriceYuan">￥</span>
                    <span class="promotionPrice">
                        <fmt:formatNumber type="number" value="${p.promotePrice}" minFractionDigits="2"></fmt:formatNumber>
                    </span>
                </div>
            </div>
        </div>
        <!--销量，评价-->
        <div class="productSaleAndReviewNumber">
            <div>销量<span class="redColor boldWord">${p.saleQuantity}</span></div>
            <div>累计评价<span class="redColor boldWord">${p.reviewQuantity}</span></div>
        </div>
        <!--库存-->
        <div class="productNumber">
            <span>数量</span>
            <span>
                <span class="productNumberSettingSpan">
                    <input type="text" value="1" class="productNumberSetting" name="num"/>
                </span>
                <span class="arrow">
                    <a href="#nowhere" class="increaseNumber">
                        <span class="updown"><img src="img/site/increase.png"/></span>
                    </a>
                    <span class="updownMiddle"></span>
                    <a href="#nowhere" class="decreaseNumber">
                        <span class="updown"><img src="img/site/decrease.png"/></span>
                    </a>
                </span>
            </span>
            <span>库存${p.stock}件</span>
        </div>
        <!--服务承诺-->
        <div class="serviceCommitment">
            <span class="serviceCommitmentDesc">服务承诺</span>
            <span class="serviceCommitmentLink">
                <a href="#nowhere">正品保证</a>
                <a href="#nowhere">急速退款</a>
                <a href="#nowhere">赠运费险</a>
                <a href="#nowhere">七天无理由退换</a>
            </span>
        </div>
        <!--购买按钮-->
        <div class="buyDiv">
            <a class="buyLink" href="forebuyone?pid=${p.id}"><button class="buyButton">立即购买</button></a>
            <a class="addCartLink" href="#nowhere"><button class="addCartButton"><span class="glyphicon glyphicon-shopping-cart"></span>加入购物车</button></a>
        </div>
    </div>
    <div style="clear: both"></div>
</div>
