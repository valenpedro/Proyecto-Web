let currentIndex = 0;

const carouselContainer = document.querySelector('.carousel-container');
const cards = document.querySelectorAll('.card');
const totalCards = cards.length;

document.querySelector('.carousel-prev').addEventListener('click', () => {
  currentIndex = (currentIndex > 0) ? currentIndex - 1 : totalCards - 1;
  updateCarousel();
});

document.querySelector('.carousel-next').addEventListener('click', () => {
  currentIndex = (currentIndex < totalCards - 1) ? currentIndex + 1 : 0;
  updateCarousel();
});

function updateCarousel() {
  const offset = currentIndex * -340; // Ajusta el valor según el ancho de la carta
  carouselContainer.style.transform = `translateX(${offset}px)`;
}
