document.querySelector(".cadastrar").addEventListener("click", function (event) {
    event.preventDefault();
    let isValid = true;
    
    // Validar País
    const paisSelect = document.getElementById("pais");
    const paisError = document.getElementById("pais-error");
    if (paisSelect.value === "") {
        paisError.style.display = "block";
        paisSelect.classList.add("error-input");
        isValid = false;
    } else {
        paisError.style.display = "none";
        paisSelect.classList.remove("error-input");
    }

    // Validar Moeda
    const moedaSelect = document.getElementById("moeda");
    const moedaError = document.getElementById("moeda-error");
    if (moedaSelect.value === "") {
        moedaError.style.display = "block";
        moedaSelect.classList.add("error-input");
        isValid = false;
    } else {
        moedaError.style.display = "none";
        moedaSelect.classList.remove("error-input");
    }

    // Validar Segmento
    const segmentoSelect = document.getElementById("segmento");
    const segmentoError = document.getElementById("segmento-error");
    if (segmentoSelect.value == "") {
        segmentoError.style.display = "block";
        segmentoSelect.classList.add("error-input");
        isValid = false;
    } else {
        segmentoError.style.display = "none";
        segmentoSelect.classList.remove("error-input");
    }

    // Só chama pergarClick se o formulário for válido E a caixa de seleção estiver marcada
    if (isValid && document.getElementById("termos").checked) {
        pergarClick();
    }
});

function pergarClick() {
    // Verifica se o objeto Android está disponível (no contexto WebView)
    if (typeof Android !== 'undefined' && Android.gotoWelcoScreen) {
        Android.gotoWelcoScreen();
    } else {
        console.error("Objeto Android ou função gotoWelcoScreen não encontrados. Você está executando em um WebView?");
        alert("Erro: Não foi possível prosseguir. O aplicativo não está em um ambiente Android.");
    }
}

document.getElementById("termos").addEventListener("change", function () {
    document.getElementById("cadastrar").disabled = !this.checked;
});

document.querySelector(".cadastrar").addEventListener("click", function (event) {
    event.preventDefault();
});
