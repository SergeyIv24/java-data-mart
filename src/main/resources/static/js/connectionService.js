const form = document.getElementById('addConnection');
form.addEventListener('submit', handleAddConnection);
console.log(form)

function handleAddConnection(event) {
    event.preventDefault();
    console.log("In method")
    const data = buildBody(form);
    let request = new Request("http://localhost:8080/data-mart/connections", {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
    },
    body: JSON.stringify(data)
    });

    fetch(request).then(
        function(response) {
        }
    );
}

function buildBody(form) {
    const jsonFormData = {};
    for(const pair of new FormData(form)) {
        jsonFormData[pair[0]] = pair[1];
    }
    return jsonFormData;
}