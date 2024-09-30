// Функция для открытия модального окна
function openModal() {
    document.getElementById("userModal").style.display = "flex";
}

// Функция для открытия модального окна
function openModalUpdate() {
    document.getElementById("updateModal").style.display = "flex";
}

// Функция для закрытия модального окна
function closeModal() {
    document.getElementById("userModal").style.display = "none";
}

function closeModalUpdate() {
    document.getElementById("updateModal").style.display = "none";
}

/*
// Закрытие модального окна при клике вне контента
window.onclick = function(event) {
    var modal = document.getElementById("userModal");
    if (event.target == modal) {
        closeModal();
    }
}

window.onclick = function(event) {
    var modal = document.getElementById("updateModal");
    if (event.target == modal) {
        closeModal();
    }
}*/
