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



$(document).ready(function crearTabla() {
	var content = '<table id="' + order.order_id + '">'
        content += '<tr><th>' + 'Product' + '</th><th>' + 'Quantity' + '</th></tr>'
        for(var i in order.products) {
            content += '<tr><td>' + order.products[i].product + '</td><td>' + order.products[i].quantity + '</td></tr>';
        }
	content += "</table>"
	$('#here_table').append(content);
});

function removeOrderById(id){
    var order = document.getElementById(id);
    
    $(order).remove();
    
	
}