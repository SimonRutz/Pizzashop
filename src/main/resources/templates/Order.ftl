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
        <div id="OrderTitle"> Ihre Bestellung: </div>
        <#list pizzaList as pizza>
            <div id="Order">
                <img id="Image" src="./images/${pizza.name}.jpg" >
                <div class="OrderTableRows">2 x</div>
                <div class="OrderTableRows">${pizza.name}</div>
                <div class="OrderTableRows">${pizza.price} CHF</div>
            </div>
        </#list>
    </div>

</div>

</body>
</html>