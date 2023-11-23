"use strict";

let binOperation = function (f, args) {
    return function (x, y, z) {
        return f(...args.map((arg) => arg.evaluate(x, y, z)));
    }
}

let stringConstruct = function (...args) {
    return function () {
        return (args.map((arg) => arg.toString())).join(" ");
    }
}

let prefixConstruct = function (...args) {
    return (args.map((arg) => arg.prefix())).join(" ");
}

function InvalidDataError(messege) {
    Error.call(this, messege);
}

function Constructor(args, func, type) {
    this.evaluate = binOperation(func, args);
    this.toString = stringConstruct(...args, type);
    this.prefix = function () {
        return `(${type} ${prefixConstruct(...args)})`;
    }
}

function Const(x) {
    this.evaluate = function () {
        return x;
    }
    this.toString = function () {
        return x.toString();
    }
    this.prefix = function () {
        return x.toString();
    }
    this.diff = function () {
        return new Const(0);
    }
}

function Min3(...args) {
    Constructor.call(this, args, (...func) => (Math.min(...func)), "min3");
}

Min3.prototype = Object.create(Constructor.prototype)

function Max5(...args) {
    Constructor.call(this, args, (...func) => (Math.max(...func)), "max5");
}

Max5.prototype = Object.create(Constructor.prototype)

function Negate(...args) {
    Constructor.call(this, args, (func) => -func, "negate");
    this.diff = function (argv) {
        return new Negate(args[0].diff(argv));
    }
}

Negate.prototype = Object.create(Constructor.prototype);

function Add(...args) {
    Constructor.call(this, args, (a, b) => a + b, "+");
    this.diff = function (argv) {
        return new Add(args[0].diff(argv), args[1].diff(argv));
    }
}

Add.prototype = Object.create(Constructor.prototype);

function Sinh(...args) {
    Constructor.call(this, args, (x) => Math.sinh(x), "sinh");
    this.diff = function (argv) {
        return new Multiply(new Cosh(args[0]), args[0].diff(argv));
    }
}

Sinh.prototype = Object.create(Constructor.prototype);

function Cosh(...args) {
    Constructor.call(this, args, (x) => Math.cosh(x), "cosh");
    this.diff = function (argv) {
        return new Multiply(new Sinh(args[0]), args[0].diff(argv));
    }
}

Cosh.prototype = Object.create(Constructor.prototype);

function Subtract(...args) {
    Constructor.call(this, args, (a, b) => a - b, "-");
    this.diff = function (argv) {
        return new Subtract(args[0].diff(argv), args[1].diff(argv));
    }
}

Subtract.prototype = Object.create(Constructor.prototype);

function Multiply(...args) {
    Constructor.call(this, args, (a, b) => a * b, "*");
    this.diff = function (argv) {
        return new Add(new Multiply(args[0].diff(argv), args[1]),
            new Multiply(args[0], args[1].diff(argv))
        );
    }
}

Multiply.prototype = Object.create(Constructor.prototype);

function Divide(...args) {
    Constructor.call(this, args, (a, b) => a / b, "/");
    this.diff = function (argv) {
        return new Divide(new Subtract(new Multiply(args[0].diff(argv), args[1]),
                new Multiply(args[0], args[1].diff(argv))),
            new Multiply(args[1], args[1])
        );
    }
}

Divide.prototype = Object.create(Constructor.prototype);

function Variable(a) {
    this.evaluate = function (x, y, z) {
        switch (a) {
            case ("x"):
                return x;
            case ("y"):
                return y;
            case ("z"):
                return z;
        }
    }
    this.toString = function () {
        return a.toString();
    }
    this.prefix = function () {
        return a.toString();
    }
    this.diff = function (argv) {
        return argv === a ? new Const(1) : new Const(0);
    }
}

function parse(str) {
    let stack = [];
    let expression = str.trim().split(" ").filter(words => words.length >= 1);
    let processing = function (func, ...number) {
        number.reverse();
        let ans = new func(...number);
        stack.push(ans);
    }
    for (let i = 0; i < expression.length; i++) {
        if (expression[i] === "-") {
            processing(Subtract, stack.pop(), stack.pop());
        } else if (expression[i] === "min3") {
            processing(Min3, stack.pop(), stack.pop(), stack.pop());
        } else if (expression[i] === "max5") {
            processing(Max5, stack.pop(), stack.pop(), stack.pop(), stack.pop(), stack.pop());
        } else if (expression[i] === "+") {
            processing(Add, stack.pop(), stack.pop());
        } else if (expression[i] === "*") {
            processing(Multiply, stack.pop(), stack.pop());
        } else if (expression[i] === "/") {
            processing(Divide, stack.pop(), stack.pop());
        } else if (expression[i] === "negate") {
            processing(Negate, stack.pop());
        } else if (expression[i] === "x" || expression[i] === "y" || expression[i] === "z") {
            stack.push(new Variable(expression[i]));
        } else {
            stack.push(new Const(parseInt(expression[i])));
        }
    }
    return stack[0];
}

let pos = 0;
let operation = ["+", "-", "/", "*", "negate", "cosh", "sinh"];

function parsePrefix(str) {
    str = str.replaceAll("(", " ( ");
    str = str.replaceAll(")", " ) ");
    let expression = str.trim().split(" ").filter(words => words.length >= 1);
    pos = 0;
    checkerCBS(str);
    let ans = checkOperation(expression);
    if (pos < expression.length) {
        throw new InvalidDataError("Invalid data");
    }
    return ans;
}

function checkerCBS(str) {
    let balance = 0;
    for (let i = 0; i < str.length; i++) {
        if (str[i] === "(") {
            balance++;
        } else if (str[i] === ")") {
            balance--;
            if (balance < 0) {
                throw new InvalidDataError("it isn't CBS expression");
            }
        }
    }
    if (balance !== 0) {
        throw new InvalidDataError("it isn't CBS expression");
    }
}

function checkOperation(expression) {
    let source = expression[pos++];
    if (source === "(") {
        source = expression[pos++];
        if (!(operation.includes(source))) {
            throw new InvalidDataError("Invalid Operation");
        } else {
            return getOperation(expression, source);
        }
    }
    if (!variableCheck(source) && !isDigit(source)) {
        throw new InvalidDataError("Invalid Args");
    }
    return getOperation(expression, source);
}

function getOperation(str, sin) {
    switch (sin) {
        case ("negate"):
            return new Negate(...getArgs(str, 1));
        case ("+"):
            return new Add(...getArgs(str, 2));
        case ("y"):
        case ("x"):
        case ("z"):
            return new Variable(sin);
        case ("cosh"):
            return new Cosh(...getArgs(str, 1));
        case ("sinh"):
            return new Sinh(...getArgs(str, 1));
        case ("-"):
            return new Subtract(...getArgs(str, 2));
        case ("*"):
            return new Multiply(...getArgs(str, 2));
        case ("/"):
            return new Divide(...getArgs(str, 2));
        default:
            return new Const(parseInt(sin));
    }
}

function getArgs(expr, num) {
    let stack = [];
    let countOfArgs = 0;
    while (expr[pos] !== ")") {
        stack.push(checkOperation(expr));
        countOfArgs++;
    }
    if (countOfArgs !== num) {
        throw new InvalidDataError("Wrong count of arguments")
    }
    pos++;
    return stack;
}

function isDigit(num) {
    return /^-?\d+$/.test(num);
}

function variableCheck(st) {
    return st === "x" || st === "z" || st === "y";
}

