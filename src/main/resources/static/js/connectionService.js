

const form = document.getElementById('addConnection');
form.addEventListener('submit', handleAddUser);
function handleAddConnection(event) {
    event.preventDefault();
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