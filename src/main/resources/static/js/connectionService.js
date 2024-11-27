const form = document.getElementById('addConnection');
form.addEventListener('submit', handleAddConnection);

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

function handleDeleteConnection(connectionId) {
    let request = new Request("http://localhost:8080/data-mart/connections/" + connectionId, {
        method: 'DELETE'
    });
    fetch(request).then(
        function(response) {
        }
    );
}

const formEdit = document.getElementById('editConnection');
function handleEditConnection(event) {
    event.preventDefault();
    const but = document.getElementById('updater');
    const connectionId = but.getAttribute('data-name');
    const data = buildBody(formEdit);
    let request = new Request("http://localhost:8080/data-mart/connections/" + connectionId, {
        method: 'PATCH',
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
        if (pair[1] === "") {
            jsonFormData[pair[0]] = null;
            continue
        }
        jsonFormData[pair[0]] = pair[1];
    }
    return jsonFormData;
}
