const sidebar = document.querySelector('.sidebar');
const toggleBtn = document.querySelector('.home-content i');

toggleBtn.addEventListener('click', () => {
    sidebar.classList.toggle('close');
});