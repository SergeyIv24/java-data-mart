const form = document.getElementById('adddataset');
form.addEventListener('submit', handleAddDataset);
console.log(form)

function handleAddDataset(event) {
    event.preventDefault();
    const data = buildBody(form);
    let request = new Request("http://localhost:8080/data-mart/datasets", {
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
        if (pair[1] === "") {
            jsonFormData[pair[0]] = null;
            continue
        }
        jsonFormData[pair[0]] = pair[1];
    }
    return jsonFormData;
}
