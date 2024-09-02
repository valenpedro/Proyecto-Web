document.getElementById('contactForm').addEventListener('submit', function(event) {
  event.preventDefault();
  alert('¡Gracias por tu mensaje! Nos pondremos en contacto contigo pronto.');
  this.reset();
});