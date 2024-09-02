document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.querySelector('.input-group input');
    const tableRows = document.querySelectorAll('tbody tr');

    // Función para buscar en la tabla
    searchInput.addEventListener('input', () => {
        const searchTerm = searchInput.value.toLowerCase();

        tableRows.forEach(row => {
            const rowContent = row.textContent.toLowerCase();
            const isVisible = rowContent.includes(searchTerm);
            row.style.display = isVisible ? '' : 'none';
        });
    });
});
