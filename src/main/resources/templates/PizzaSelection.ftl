<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="PizzaSelectionCSS.css">
    <title>PizzaShop Simon</title>
</head>
<body>
<div id="Header">
    Pizzashop

    <input type="text" placeholder="Margherita..." id="Searchbar">
    <button id="SearchButton"> Search </button>
    <a href="/Order" id="OrderLink"> Order</a>
</div>
<!--<div id="Footer">
    Footer
</div>-->
<div id="PizzaMenu">
    <#list pizzaList as pizza>
    <div id="Pizza">
        ${pizza.name}
        ${pizza.price}
    </div>
    </#list>
</div>

</body>
</html>