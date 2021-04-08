function validateForm(formId) {
    //    var curForm = document.getElementById(formId);
    // var name = curForm.name.value;
    var name = document.getElementById("name" + formId).value;
    var description = document.getElementById("description" + formId).value;
    var quantity = document.getElementById("quantity" + formId).value;
    var price = document.getElementById("price" + formId).value;


    if (!/[a-z0-9A-Z_\x{00C0}\x{00FF}\x{1EA0}\x{1EFF}\s]{1,50}/.test(name)) {
        alert("Invalid name!! (from 1 ~ 50 chars)");
        return false;
    }

    if (!/[a-z0-9A-Z_\x{00C0}\x{00FF}\x{1EA0}\x{1EFF}\s]{1,200}/.test(description)) {
        alert("Invalid description!! (from 1 ~ 50 chars)");
        return false;
    }

    if (!/^[0-9]{1,15}$/.test(quantity)) {
        alert("Invalid quantity!! (just number)");
        return false;
    }

    if (!/^[1-9]\d{0,15}(\.\d{1,4})?$/.test(price)) {
        alert("Invalid price!! (just number)");
        return false;
    }
    
    alert("Save success!!");
    return true;
}
