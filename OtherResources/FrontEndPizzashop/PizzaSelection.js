const PIZZA_URL = 'http://localhost:8080/pizzashop/api/Pizza'
const PIZZA_ORDER_URL = 'http://localhost:8080/pizzashop/api/PizzaOrder'
const searchForm = document.getElementById("SearchForm");

function pizzaMenuTemplate(pizzaData) {
    return `
    <div id="Pizza">
        <img src="./images/${pizzaData.name}.jpg" id="PizzaImage"/>
        <div id="text">
            <div id="PizzaName">${pizzaData.name}</div>
            <p></p>
            <div id="PizzaPrice">${pizzaData.price} CHF</div>
            <p></p>
            <form id="OrderAddForm">
                <div>Amount: <input type="number" placeholder="0" min="0" max="10" class="AmountField" id="${pizzaData.name}Amount"></div>
                <input type="submit" value="Add to Order" id="cartButton" onclick="postData('${pizzaData.name}', '${pizzaData.id}')">
            </form>
        </div>
    </div> `
}

window.onload = async function getPizzaList() {

    let pizzaData;

    console.log(document.getElementById("SearchBar").value);

    if (document.getElementById("SearchBar") != null) {

        let url_string = window.location.href
        let url = new URL(url_string);
        let searchBarValue = url.searchParams.get("SearchValue");

        console.log(searchBarValue);

        if (searchBarValue == "" || searchBarValue == null ) {
            const response = await fetch (PIZZA_URL, {
                method: 'get'
            });
            pizzaData = await response.json();
            console.log(pizzaData);
        } else {
            const response = await fetch (PIZZA_URL + "/search/" + searchBarValue, {
                method: 'get'
            })
            pizzaData = await response.json();
            console.log(pizzaData);
        }

    } else {
        const response = await fetch (PIZZA_URL, {
            method: 'get'
        });
        pizzaData = await response.json();
        console.log(pizzaData);
    }

    const pizzaOrderResponse = await fetch(PIZZA_ORDER_URL, {
        method: 'get'
    });
    const pizzaOrderData = await pizzaOrderResponse.json();
    console.log(pizzaOrderData);

    if (pizzaData.length != 0) {
        document.getElementById("noResults").hidden = true;
    } else {
        document.getElementById("PizzaMenu").hidden = true;
    }

    document.getElementById("PizzaMenu").innerHTML = `${pizzaData.map(pizzaMenuTemplate).join('')}`;

}

function postData(pizzaName, pizzaID) {

    let amount = document.getElementById(pizzaName + "Amount").value;

    if(amount != null) {

        const pizzaOrder = {
            amount: amount,
            pizzaID: pizzaID,
            orderID: null
        }

        fetch(PIZZA_ORDER_URL, {
            method:'post',
            body: JSON.stringify(pizzaOrder),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
            return response.text();
        }).then(function (text) {
            console.log(text);
        }).catch(function (error) {
            console.error(error);
        });

    }
}


