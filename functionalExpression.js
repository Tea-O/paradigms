"use strict";

let binOperation = function (f) {
    return function (...args) {
        return function (x, y, z) {
            return f(...args.map((arg) => arg(x, y, z)));
        }
    }
}

let cnst = (function (a) {
    return function () {
        return a;
    }
});

let sinh = binOperation(function (a) {
    return Math.sinh(a);
});

let cosh = binOperation(function (a) {
    return Math.cosh(a);
});

let pi = cnst(Math.PI);

let e = cnst(Math.E);


let add = binOperation(function (a, b) {
    return a + b;
});

let divide = binOperation(function (a, b) {
    return a / b;
});

let variable = function (a) {
    return function (x, y, z) {
        switch (a) {
            case ("x"):
                return x;
            case ("y"):
                return y;
            case ("z"):
                return z;
        }
    }
}


let negate = binOperation(function (a) {
    return -a;
});

let subtract = binOperation(function (a, b) {
    return a - b;
});

let multiply = binOperation(function (a, b) {
    return a * b;
});


