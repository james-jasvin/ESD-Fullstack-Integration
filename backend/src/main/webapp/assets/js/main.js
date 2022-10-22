let billList = null;
let paymentDictionary = {};

function onLoginPageLoad() {
    // If user was already logged in and data is retained in localStorage, then directly take user to Bills.html
    if (window.localStorage.hasOwnProperty("studentId")) {
        location.replace("bills.html");
    }

    let form = document.getElementById("loginForm");

    form.onsubmit = function (event) {
        event.preventDefault();
        let email = document.getElementById("email").value;
        let password = document.getElementById("password").value;

        let student = {
            "email": email,
            "password": password
        }

        fetch("http://localhost:8080/Implementation_war/api/student/login", {
            method: "POST",
            body: JSON.stringify(student),
            headers: {
                "Content-type": "application/json; charset=UTF-8",
            }
        })
            .then(response => {
                return response.json();
            })
            .then(student => {
                Swal.fire({
                    title: 'Login',
                    text: 'Login Successful!',
                    icon: 'success',
                    confirmButtonText: 'Okay'
                })
                .then(res => {
                    window.localStorage.setItem("studentId", student.studentId);
                    window.localStorage.setItem("email", email);
                    window.localStorage.setItem("password", password);
                    window.localStorage.setItem("name", student.firstName + " " + student.lastName);

                    // Redirect to Bills page
                    location.replace("bills.html");
                });
            })
            // If an error happened with response.json() => Most likely "response" had empty body => response = null
            // This only happens when login fails!
            .catch(err => {
                Swal.fire({
                    title: 'Login',
                    text: 'Login Failed!',
                    icon: 'error',
                    confirmButtonText: 'Okay'
                });

                form.reset();
            });
    }
}

function onBillsPageLoad() {
    billList = null;
    paymentDictionary = {};

    // If user hasn't logged in and tries to access Bills.html then they'll redirected to Login screen
    if (!window.localStorage.hasOwnProperty("studentId"))
        location.replace("index.html");

    let studentId = parseInt(window.localStorage.getItem("studentId"));
    let name = window.localStorage.getItem("name");

    document.getElementById("welcomeHeading").innerText = "Welcome " + name + ",";

    document.getElementById("totalAmount").innerHTML = "0";

    fetch("http://localhost:8080/Implementation_war/api/bill/get/" + studentId, {
        method: "GET"
    })
        .then((response) => response.json())
        .then(bills => {
            if (bills.length === 0) {
                document.getElementById("billDataTbody").remove();
                document.getElementById("billsHeading").innerText = "No Bills Due";
                document.getElementById("paymentButton").style.display = "none";
            }
            else {
                document.getElementById("billsHeading").innerText = "Bills Due:";

                let tbody = document.getElementById("billDataTbody");

                tbody.innerHTML = "";

                for (let i = 0; i < bills.length; i++) {
                    let curRow = "";
                    let id = bills[i].billId;

                    curRow += '<td>' + id + '</td>';
                    curRow += '<td>' + bills[i].amount + '</td>';
                    curRow += '<td>' + bills[i].billDate + '</td>';
                    curRow += '<td>' + bills[i].description + '</td>';

                    curRow +=
                        '<td> <input type="number" value=0 min=0 max='
                        + bills[i].amount
                        + ' id="payment'
                        + id
                        + '" oninput="calculate('
                        + id
                        + ')" onkeyup="imposeMinMax(this, '
                        + id
                        + ')"/> </td>';

                    tbody.innerHTML += '<tr>' + curRow + '</tr>';
                }

                billList = bills;
            }
        });
}

function calculate(billId) {
    paymentDictionary[billId] = parseInt(document.getElementById("payment" + billId).value);

    // If input field left empty after typing something in it first => NaN or if the user filled in 0 then,
    // delete this bill's key from dictionary
    if (isNaN(paymentDictionary[billId]) || paymentDictionary[billId] === 0)
        delete paymentDictionary[billId];

    let totalAmount = 0;

    for (let i=0; i<billList.length; i++) {
        let id = billList[i].billId;

        if (paymentDictionary.hasOwnProperty(id)) {
            totalAmount += paymentDictionary[id];
        }
    }

    document.getElementById("totalAmount").innerHTML = totalAmount.toString();
}

function imposeMinMax(el, id){
    if(el.value !== ""){
        if(parseInt(el.value) < parseInt(el.min)){
            el.value = el.min;
        }
        if(parseInt(el.value) > parseInt(el.max)){
            el.value = el.max;
        }
    }

    // Recalculate the total amount after considering the change caused by this function
    calculate(id)
}

function payment() {
    let today = new Date().toISOString().slice(0, 10);
    let receiptDictionary = {};

    for (let billId in paymentDictionary) {
        if (paymentDictionary.hasOwnProperty(billId) && paymentDictionary[billId] > 0) {
            receiptDictionary[billId] = {
                "amountPaid": paymentDictionary[billId],
                "dateOfPayment": today
            }
        }
    }

    if (Object.keys(receiptDictionary).length === 0) {
        Swal.fire({
            title: 'Payment Failed',
            text: 'Enter valid amount/s to pay',
            icon: 'error',
            confirmButtonText: 'Okay'
        })
        return;
    }

    fetch("http://localhost:8080/Implementation_war/api/bill/pay", {
        method: "POST",
        body: JSON.stringify(receiptDictionary),
        headers: {
            "Content-type": "application/json; charset=UTF-8",
        },
    })
        .then((response) => response.status)
        .then(status =>
            Swal.fire({
                title: 'Payment',
                text: 'Payment Successful!',
                icon: 'success',
                confirmButtonText: 'Okay'
            })
        )
        .then(val =>
            onBillsPageLoad() // Call the getBills route again to update the payments made)
        );
}

function logout() {
    window.localStorage.clear();

    Swal.fire({
        title: 'Log out',
        text: 'Logged Out Successfully!',
        icon: 'success',
        confirmButtonText: 'Okay'
    })
        .then(res => location.replace("index.html"));
}