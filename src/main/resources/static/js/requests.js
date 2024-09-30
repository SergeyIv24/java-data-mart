const form = document.querySelector('form');
const element = document.querySelector('select');
form.addEventListener('submit', handleAddUser);
function handleAddUser(event) {
    const role = element.value;
    event.preventDefault();
    const data = buildBody(form);
    let request = new Request("http://localhost:8080/data-mart/admin/user?role=" + role, {
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

function handleDeleteUser(userId) {
    console.log("userId = " + userId)
    let request = new Request("http://localhost:8080/data-mart/admin/user/" + userId, {
        method: 'DELETE'
    });
    fetch(request).then(
        function(response) {
        }
    );
}
