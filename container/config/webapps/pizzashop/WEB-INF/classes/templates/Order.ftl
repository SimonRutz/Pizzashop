<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="OrderCSS.css">
    <title>PizzaShop Simon</title>
</head>
<body>
<div id="Header">
    Pizzashop
    <form action="Pizza" method="post" id="SearchForm">
        <input type="text" placeholder="Margherita..." id="Searchbar" name="searchBar">
        <input type="submit" value="Search" id="SearchButton" name="searchButton" >
    </form>
    <a href="/pizzashop/Pizza" id="PizzaLink">Pizza Menu </a>
    <a href="/pizzashop/Order" id="OrderLink"> Order</a>
</div>

<div id="OrderPage">

    <form action="Order" method="Post" id="TransactionForm">
        <input type="text" placeholder="  Address" id="AddressForm" name="addressForm">
        <p></p>
        <input type="text" placeholder="  Phonenumber" id="PhonenumberForm" name="phoneNumberForm">
        <p></p>
        <input type="submit" value="Complete" id="TransactionSubmit" name="transactionSubmit">
    </form>

    <div id="OrderList">
        <div id="OrderTitle"> Your Order: </div>
        <#list pizzaList as pizza>
            <div id="Order">
                <img id="Image" src="./images/${pizza.name}.jpg" alt="pizzaBild">
                <div class="OrderTableRows">${pizza.amount} x</div>
                <div class="OrderTableRows">${pizza.name}</div>
                <div class="OrderTableRows">${pizza.price} CHF</div>
                <button id="removeButton" class="OrderTableRows" name="remove"> x </button>
            </div>
        </#list>
        <#if pizzaList?has_content>
            <div id="OrderTitleTotal"></div>
            <div id="total">
                <div class="OrderTableRowsTotal"></div>
                <div class="OrderTableRowsTotal"><b> Total: </b></div>
                <div class="OrderTableRowsTotal"><b>${total} CHF</b></div>
            </div>

            <#else>
                <div id="noItems">No Items</div>
        </#if>
    </div>

</div>

</body>
</html>