/**
 * Created by A.A.MAMUN on 2/25/2020.
 */


$(document).ready(function(){

   //  alert("called viewcontroller.js.......");
    fetchCartItems();

    //searchProduct2();

    websocketEstablishment();

});


function searchProduct(ctx) {

    var sv = $(ctx).val().trim();

    if(sv.length>=3 && sv.length<=15){

        $.get("/fetch-search-product",{name:sv, size:10}, function (data, status) {

            var productList = data.content;

            if(productList.length==0){
                $('.box-results').hide();
            }else{

                searchResult = "";
                for(var i=0;i<productList.length;i++)
                {
                    product = productList[i];
                    searchResult+='<li>';
                    searchResult+='<a href="/product?pid='+product.product_id+'">';
                    searchResult+='<span class="thumbnail"><img style="object-fit: cover; width: 50px; height: 50px" src="'+product.image+'"></span>';
                    searchResult+='<span class="title_name">'+product.name+'</span>';
                    searchResult+='</a>';
                    searchResult+='</li>';
                }

                $('.box-results').empty();
                $('.box-results').append(searchResult);
                $('.box-results').show();
            }
        });

    }else{
        $('.box-results').hide();
        $('.box-results').empty();
    }

}


function fetchCartItems() {

    try{
        $.post("/fetch-cart-items", function(cartList){
            displayMiniCartItems(cartList);
        });
    }catch (e) {
        console.log(e);
    }

}


function displayMiniCartItems(cartList)
{
    miniCartItems = cartList;
    cartLen = cartList.length;
    qtyLen = 0;
    subTotal = 0;

    if(cartLen!=0){

        $(".noItemsHolder").css("display","none");
        $(".cartItemsHolder").css("display","block");

        $("#shopify-section-cart-template").css("display","none");
        $("#shopping-cart-table-holder").css("display","block");

        $("#popup-mycart-no-items").hide();
        $("#popup-mycart-has-items").show();

        cartItems = "";
        popupCartItems = "";

        for(i=0;i<cartLen;i++){

            var item = cartList[i];
            var sizeName = item.sizeName==null?'' : 'Size : '+item.sizeName +' ';
            var colorName = item.colorName==null?'' : 'Color : '+item.colorName;

            qtyLen+=item.quantity;
            subTotal+=item.costPerItem;

            cartItems+='<li class="item ">';
            cartItems+='<div class="product-img-wrap">';
            cartItems+='<a href="/product?pid='+item.product_id+'" class="product-image">';
            cartItems+='<img src="'+item.image+'" alt="item image">';
            cartItems+='</a>';
            cartItems+='<a href="javascript:void(0)" onclick="removeCartItem('+i+')" title="Remove This Item" class="btn-remove">&nbsp;</a>';
            cartItems+='</div>';
            cartItems+='<div class="product-details">';
            cartItems+='<div class="inner-left">';
            cartItems+='<p class="product-name">';
            cartItems+='<a href="/product?pid='+item.product_id+'">'+item.name+'</a>';
            cartItems+='</p>';
            cartItems+='<div class="option">';
            cartItems+='<small>'+ sizeName + colorName+'</small>';
            cartItems+='</div>';
            cartItems+='<div class="product-details-bottom">';
            cartItems+='<span class="title-desc">Quantity : </span>';
            cartItems+='<strong>'+item.quantity+'</strong>';
            cartItems+='</div> </div>';
            cartItems+='<div class="product-price">';
            cartItems+='<span class="price"><span class="money" >৳'+item.price+'</span></span>';
            cartItems+='</div> </div>';
            cartItems+='</li>';

            /* for popup cart */
            popupCartItems+='<tr>';
            popupCartItems+='<td class="text-left first">';
            popupCartItems+='<a href="/product?pid='+item.product_id+'">';
            popupCartItems+='<img style="width:80px; height:80px; object-fit:cover" class="img-thumbnail img-responsive" src="'+item.image+'" alt="'+item.name+'">';
            popupCartItems+='</a>';
            popupCartItems+='</td>';
            popupCartItems+='<td class="text-left">';
            popupCartItems+='<a href="/product?pid='+item.product_id+'">'+item.name+'</a>';

            popupCartItems+='<div class="cart__meta-text">';
            if(item.sizeName!=null)
               popupCartItems+='Size: '+item.sizeName+'<br>';
            if(item.colorName!=null)
            popupCartItems+='Color: '+item.colorName+'<br>';
            popupCartItems+='</div>';

            popupCartItems+='</td>';
            popupCartItems+='<td class="text-right">x&nbsp;'+item.quantity+'</td>';
            popupCartItems+='<td class="text-right total-price"><span class="money">৳'+item.price+'</span></td>';
            popupCartItems+='<td class="text-right last">';
            popupCartItems+='<a href="javascript:void(0);"  onclick="removeCartItem('+i+')" class="btn-remove"><i class="fa fa-trash"></i></a>';
            popupCartItems+='</td>';
            popupCartItems+='</tr>';
        }

        $(".minicart").empty();
        $(".minicart").append(cartItems);

        $("#popup-mycart-table-body").empty();
        $("#popup-mycart-table-body").append(popupCartItems);


    }else{

        $(".cartItemsHolder").css("display","none");
        $(".noItemsHolder").css("display","block");

        $("#shopping-cart-table-holder").css("display","none");
        $("#shopify-section-cart-template").css("display","block");

        $("#popup-mycart-no-items").show();
        $("#popup-mycart-has-items").hide();

    }

    if(qtyLen>9){
        $("#CartCount").text("9+");
    }else{
        $("#CartCount").text(qtyLen);
    }

    $("#cartItemCount").text(qtyLen);

    $(".subTotal").text("৳"+subTotal);
    $(".cart-popup-total").text("Your cart contains "+qtyLen+" items");
}


function removeCartItem(index)
{
    var cartItem = miniCartItems[index];
    $.post("/remove-cart-item",{cartItem_id:cartItem.cartItem_id}, function(data){
        if(data){

            updateMiniCartItems = [];
            j=0;
            for(i=0;i<miniCartItems.length;i++){
                if(i!=index){
                    updateMiniCartItems[j] = miniCartItems[i];
                    j++;
                }
            }
            $("#tblRowId"+cartItem.cartItem_id).remove();
            displayMiniCartItems(updateMiniCartItems);
        }
    });
}

function fetchRecentViewedProduct() {

    var ca = document.cookie.split(";");
    var recent_viewed = null;
    for(var i=0;i<ca.length;i++){
        var str = ca[i];
        if(str.startsWith("recent_viewed_product=") ||
            str.startsWith(" recent_viewed_product=")){
            recent_viewed = str;
            break;
        }
    }

    var ids = [];

    if(recent_viewed!=null){

        var product_ids = recent_viewed.split("-");
        for(var i=product_ids.length-1;i>0;i--){
            ids.push(parseInt(product_ids[i]));
        }

        //alert(ids);


        $(".ss-loading").show();

        $.get("/fetch-recent-viewed-products",{ids:ids}, function (data, status) {
            //alert(Object.values(data));
            productRow = "";
            for(var i=0;i<data.length;i++)
            {
                var p = data[i];
                productRow+='<div class="product col col-sm-6 col-xs-6">';
                productRow+='<div class="form-box">';
                productRow+='<div class="item">';
                productRow+='<div class="product-thumb transition">';
                productRow+='<div class="image">';
                productRow+='<span class="bt-sale">'+p.discount+'%</span>';
                productRow+='<a href="/product?pid='+p.product_id+'">';
                productRow+='<img style="object-fit: cover; width:200px; height:200px" src="'+p.image+'">';
                productRow+='</a>';
                productRow+='</div>';
                productRow+='<div class="caption">';
                productRow+='<h4><a href="/product?pid='+p.product_id+'" title="Labor occaecat bee">'+p.name+'</a></h4>';
                productRow+='<p class="price">';
                productRow+='<span class="price-new">৳'+p.price+'</span>';
                productRow+='<span class="price-old">৳'+p.old_price+'</span>';
                productRow+='</p>';
                productRow+='</div>';
                productRow+='</div>';
                productRow+='</div>';
                productRow+='</div>';
                productRow+='</div>';
            }

            $("#empty-recent-view").hide();
            $("#recent-viewed-product-container").empty();
            $("#recent-viewed-product-container").append(productRow);

            $(".ss-loading").hide();
            $("#recent-viewed-product-container").show();

        });




    }else{

        $("#recent-viewed-product-container").hide();
        $("#empty-recent-view").show();
    }


}

document.writeln("<script type='text/javascript' src='/webjars/sockjs-client/sockjs.min.js'></script>");
document.writeln("<script type='text/javascript' src='/webjars/stomp-websocket/stomp.min.js'></script>");


var stompClient = null;

function websocketEstablishment() {

    var socket = new SockJS("/websocket-register");
    stompClient = Stomp.over(socket);

    listenItemCarted();
}


function listenItemCarted() {

    stompClient.connect({}, function (frame) {

       // alert("Successfully connected!");
       // console.log("Connected : "+frame);

        stompClient.subscribe('/from-server/listen-item-carted-notification', function (notification) {

            //alert("Message successfully come : "+notification.body);

           // alert(notification.body.productId+"\n"+notification.body.productName+"\n"+notification.body.productImage);

            notify = JSON.parse(notification.body);

            $("#notify_product_image").attr("href","/product?pid="+notify.productId);
            $("#notify_product_image img").attr("src",notify.productImage);
            $("#notify_info").text("Someone carted this item.")
            $("#notify_product_name").attr("href","/product?pid="+notify.productId);
            $("#notify_product_name p").text(notify.productName);
            $("#mps-sales-notification").addClass("sn-activated");

            setTimeout(function(){
                $("#mps-sales-notification").removeClass("sn-activated");
            }, 3000);

        });

    }, function(error){
        //alert("Connection failed!");
        console.log(error);
        websocketEstablishment();
    });

}

function sendItemCartedMessage(customer_name, product_id, product_name, product_image) {

    var notification = JSON.stringify({
        'customerName' : customer_name,
        'productId' : product_id,
        'productName' : product_name,
        'productImage' : product_image
    });

    //alert(notification);

    stompClient.send("/to-server/send-item-carted-message",{}, notification, function (status) {
        console.log("# Status : "+status);
    });

}



function closeNotification() {
    $("#mps-sales-notification").removeClass("sn-activated");
}