const PIZZA_URL = 'http://localhost:8080/pizzashop/api/Pizza'
const ORDER_URL = "http://localhost:8080/pizzashop/api/Order"
const PIZZA_ORDER_URL = 'http://localhost:8080/pizzashop/api/PizzaOrder'
let total = 0;

function orderTemplate(pizzaOrderData) { 

    return `
    <div id="Order">
        <img id="PizzaImage" src="./images/${pizzaOrderData.pizza.name}.jpg" alt="pizzaImage">
        <div class="OrderTableRows" id="PizzaAmount">${pizzaOrderData.amount}</div>
        <div class="OrderTableRows" id="PizzaName">${pizzaOrderData.pizza.name}</div>
        <div class="OrderTableRows" id="PizzaPrice">${pizzaOrderData.pizza.price * pizzaOrderData.amount} CHF</div>
    </div>`

}   

async function getData() {

    const pizzaResponse = await fetch (PIZZA_URL, {
        method: 'get'
    });
    const pizzaData = await pizzaResponse.json();
    console.log(pizzaData);

    const pizzaOrderResponse = await fetch(PIZZA_ORDER_URL, {
        method: 'get'
    });
    const pizzaOrderData = await pizzaOrderResponse.json();
    console.log(pizzaOrderData);

    if (pizzaOrderData.length > 0) {
        document.getElementById("Container").innerHTML = `${pizzaOrderData.map(orderTemplate).join('')}`;
        document.getElementById("noItems").hidden = true;

        for(let i = 0; i < pizzaOrderData.length; i++) {
            total += pizzaOrderData[i].pizza.price * pizzaOrderData[i].amount;
        }

        document.getElementById("total").innerHTML = "Total: " + Math.round(total * 100) / 100 + " CHF";
        
    } else {
        document.getElementById("Container").hidden = true;
        document.getElementById("OrderTitleTotal").hidden = true;
        document.getElementById("total").hidden = true;
    }
}

function postData() {

    let phoneNumber = document.getElementById('PhoneNumberForm').value;
    let address = document.getElementById('AddressForm').value;

    if (phoneNumber != "" & address != "" ) {
        const order = {
            phoneNumber: phoneNumber,
            address: address
        }
        
        fetch(ORDER_URL, {
            method:'post',
            body: JSON.stringify(order),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.text();
        }).then(function (text) {
            console.log(text);
        }).catch(function (error) {
            console.error(error);
        })

        fetch(PIZZA_ORDER_URL, {
            method:'put',
        }).then(function (response) {
            return response.text();
        }).then(function (text) {
            console.log(text);
        }).catch(function (error) {
            console.error(error);
        })
    }
}

getData();