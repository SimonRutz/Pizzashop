<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="PizzaSelectionCSS.css">
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

<div id="PizzaMenu">
    <#list pizzaList as pizza>
        <div id="Pizza">
            <img src="/pizzashop/images/${pizza.name}.jpg" id="PizzaImage"/>
            <div id="text"><div id="PizzaName"> ${pizza.name} </div>
                <p></p>
                <div id="PizzaPrice"> Price: ${pizza.price} CHF</div>
                <p></p>
                <form action="Pizza" method="post" id="OrderAddForm">
                    <div>Amount: <input type="number" placeholder="0" min="0" max="10" id="AmountField" name="${pizza.name}Amount"></div>
                    <input type="submit" value="Add to Order" id="cartButton" name="carADD">
                </form>

            </div>
        </div>
    </#list>
</div>

</body>
</html>