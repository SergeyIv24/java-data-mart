function handleLoginForm(event) {
    event.preventDefault();
    const data = buildBody(form);

    let request = new Request("http://localhost:8080/data-mart/login", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });

    fetch(request).then(
        function(response) {
            console.log(response);
        },
        function(error) {
            console.log(error)
        }
    );
    console.log('Запрос отправляется');
}

function buildBody(form) {
    const jsonFormData = { };
    for(const pair of new FormData(form)) {
        jsonFormData[pair[0]] = pair[1];
    }
    return jsonFormData;
}

const form = document.querySelector('form');
form.addEventListener('submit', handleLoginForm);















/*
function handleLoginForm(event) {
    event.preventDefault();
    const data = buildBody(form);
    const response = sendPostRequest("http://localhost:8080/data-mart/login", data);
    console.log(response)
}

function buildBody(form) {
    const jsonFormData = { };
    for(const pair of new FormData(form)) {
        jsonFormData[pair[0]] = pair[1];
    }
    return jsonFormData;
}

async function sendPostRequest(link, body) {
    try {
        const rawResponse = await fetch(link, {
            mode: 'no-cors',
            method: "POST",
            headers: {'Content-Type': 'application/json;charset=utf-8'},
            body: JSON.stringify(body)
        });
        console.log('sent');
    } catch(error) {
        console.error('Error');
    }
}

const form = document.querySelector('form');
form.addEventListener('submit', handleLoginForm);*/
