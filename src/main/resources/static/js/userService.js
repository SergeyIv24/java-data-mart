const form = document.getElementById('addUser');
const element = document.getElementById('role');
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

const updateForm = document.getElementById('updateUser');
const elementUp = document.getElementById('roleUpdate');
function handleUpdateUser(event) {
    event.preventDefault();
    const roleUpdate = elementUp.value;
    const updatingData = buildBody(updateForm);
    const but = document.getElementById('updater');
    const userId = but.getAttribute('data-name');

    let request = new Request("http://localhost:8080/data-mart/admin/user/" + userId, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatingData)
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
    let request = new Request("http://localhost:8080/data-mart/admin/user/" + userId, {
        method: 'DELETE'
    });
    fetch(request).then(
        function(response) {
        }
    );
}
