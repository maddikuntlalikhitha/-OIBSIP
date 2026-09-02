let display = document.querySelector("#display");

let currentNumber = "";
let previousNumber = "";
let operator = null;


// Number buttons

let numberButtons = document.querySelectorAll(".number");

numberButtons.forEach((button) => {

    button.addEventListener("click", () => {

        let value = button.dataset.value;

        // Prevent multiple decimal points

        if (value === "." && currentNumber.includes(".")) {
            return;
        }

        currentNumber += value;

        display.value = currentNumber;

    });

});


// Operator buttons

let operatorButtons = document.querySelectorAll(".operator");

operatorButtons.forEach((button) => {

    button.addEventListener("click", () => {

        // If first number is empty, don't allow operator

        if (currentNumber === "") {
            return;
        }

        // If an operator already exists, calculate first

        if (previousNumber !== "" && operator !== null) {

            calculate();

        }

        previousNumber = currentNumber;

        operator = button.dataset.value;

        currentNumber = "";

    });

});


// Equal button

let equalButton = document.querySelector("#equal");

equalButton.addEventListener("click", () => {

    calculate();

});


// Calculation function

function calculate() {

    if (
        previousNumber === "" ||
        currentNumber === "" ||
        operator === null
    ) {
        return;
    }

    let num1 = parseFloat(previousNumber);

    let num2 = parseFloat(currentNumber);

    let result;


    switch (operator) {

        case "+":
            result = num1 + num2;
            break;


        case "-":
            result = num1 - num2;
            break;


        case "*":
            result = num1 * num2;
            break;


        case "/":

            if (num2 === 0) {

                display.value = "Cannot divide by zero";

                currentNumber = "";

                previousNumber = "";

                operator = null;

                return;
            }

            result = num1 / num2;

            break;

    }


    display.value = result;

    currentNumber = result.toString();

    previousNumber = "";

    operator = null;

}


// Clear button

let clearButton = document.querySelector("#clear");

clearButton.addEventListener("click", () => {

    currentNumber = "";

    previousNumber = "";

    operator = null;

    display.value = "";

});


// Delete button

let deleteButton = document.querySelector("#delete");

deleteButton.addEventListener("click", () => {

    currentNumber = currentNumber.slice(0, -1);

    display.value = currentNumber;

});