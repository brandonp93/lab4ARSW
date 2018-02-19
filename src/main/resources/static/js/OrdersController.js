

var order = {
	"order_id": 2,
	"table_id": 1,
	"products": [{
			"product": "PIZZA",
			"quantity": 3,
		},
		{
			"product": "HOTDOG",
			"quantity": 3
		},
		{
			"product": "COKE",
			"quantity": 4
		}
		
	]
}




$('document').ready(function getOrders(){
    axios.get('/orders/?')
        .then(function (response) {
          var currentOrders = response;
          loadOrdersList(currentOrders);
          //console.log(response);
        })
        .catch(function (error) {
          console.log(error);
          dialog();
        });   
});


  
function removeOrderById(id){
    var order = document.getElementById(id);
    $(order).remove();
}

function dialog() {
    alert("There is a problem with our servers. We apologize for the inconvince, please try again later");
}

function loadOrdersList(currentOrders){
    //var currentFinalOrders =[];
    var indice =[];
    for(var w in currentOrders['data']){
        indice.push(currentOrders['data'][w]);
        axios.get('/orders/'+currentOrders['data'][w])
        .then(function (response) {
            //currentFinalOrders.push(response['data']);          
            response['data'].order_id = indice.shift();
            response['data'].table_id = response['data'].tableNumber;
            delete response['data'].tableNumber;
            response['data'].products = [response['data'].orderAmountsMap];
            delete response['data'].orderAmountsMap;  
            for(var k in response['data'].products){
               for(var g in response['data'].products[k]){
                   response['data'].products.push({"product": g , "quantity":response['data'].products[k][g]});
               }    
            }   
            response['data'].products.splice(0,1);
            var content = '<table id="' +  response['data'].order_id + '">'
            content += '<tr><th>' + 'Product' + '</th><th>' + 'Quantity' + '</th></tr>'
            for(var i in  response['data'].products) {
                content += '<tr><td>' +  response['data'].products[i].product + '</td><td>' +  response['data'].products[i].quantity + '</td></tr>';
            }
            content += "</table>"
            $('#here_table').append(content); 
            console.log(response);
        })
        .catch(function (error) {
          console.log(error);
          dialog();
        });  
    }
    
    
     
        
    
}


