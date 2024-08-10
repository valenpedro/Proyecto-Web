document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("contactForm");

  form.addEventListener("submit", function (e) {
    e.preventDefault();

    // Obtener los valores de los campos
    const nombre = document.getElementById("nombre").value.trim();
    const email = document.getElementById("email").value.trim();
    const asunto = document.getElementById("asunto").value.trim();
    const mensaje = document.getElementById("mensaje").value.trim();

    // Validar los campos
    if (nombre === "" || email === "" || mensaje === "") {
      mostrarError("Por favor, completa todos los campos obligatorios.");
      return;
    }

    if (!validarEmail(email)) {
      mostrarError("Por favor, introduce un email válido.");
      return;
    }

    // Si todo está bien, simular el envío del formulario
    mostrarExito(
      "¡Mensaje enviado con éxito! Nos pondremos en contacto contigo pronto."
    );
    form.reset();
  });

  function validarEmail(email) {
    const re =
      /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email.toLowerCase());
  }

  function mostrarError(mensaje) {
    const errorDiv = document.createElement("div");
    errorDiv.className = "error-mensaje";
    errorDiv.textContent = mensaje;
    form.insertBefore(errorDiv, form.firstChild);

    // Eliminar el mensaje después de 3 segundos
    setTimeout(() => {
      errorDiv.remove();
    }, 3000);
  }

  function mostrarExito(mensaje) {
    const exitoDiv = document.createElement("div");
    exitoDiv.className = "exito-mensaje";
    exitoDiv.textContent = mensaje;
    form.insertBefore(exitoDiv, form.firstChild);

    // Eliminar el mensaje después de 3 segundos
    setTimeout(() => {
      exitoDiv.remove();
    }, 3000);
  }
});
