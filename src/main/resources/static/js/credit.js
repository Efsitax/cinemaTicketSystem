// Get all the input box
const cardNumberInput = document.querySelector(".card-number-input");
const cardHolderInput = document.querySelector(".card-holder-input");
const cardMonthInput = document.querySelector(".card-month-input");
const cardYearInput = document.querySelector(".card-year-input");
const cardCVCInput = document.querySelector(".card-cvc-input");

// Get all the Text box
const cardNumberText = document.querySelector(".card_number");
const cardHolderText = document.querySelector(".card-holder-name");
const cardMonthText = document.querySelector(".expire_month");
const cardYearText = document.querySelector(".expire_year");
const cardCVCText = document.querySelector(".cvc_number");

// Front and Back card
const frontCard = document.querySelector(".Front_card");
const backCard = document.querySelector(".back_card");

let originNum = "0000 0000 0000 0000";
let originMon = "MM";
let originYear = "YY";
let originCVC = "000";

// Number on Card Input
const numberInput = () => {
  cardNumberInput.addEventListener("input", () => {
    if (cardNumberInput.value === "") {
      return (cardNumberText.innerHTML = originNum);
    } else {
      cardNumberText.innerText = cardNumberInput.value
        .toString()
        .replace(/\d{4}(?=.)/g, "$& ");
    }
  });
};
numberInput();

// Name on Card Input
document.querySelector(".card-holder-input").oninput = () => {
  document.querySelector(
    ".card_holder_name"
  ).innerText = document.querySelector(".card-holder-input").value;
};

// Expire Date on Card Input
const expireDate = () => {
  cardMonthInput.addEventListener("input", () => {
    if (cardMonthInput.value === "") {
      return (cardMonthText.innerText = originMon);
    } else {
      cardMonthText.innerText = cardMonthInput.value;
    }
  });

  cardYearInput.addEventListener("input", () => {
    if (cardYearInput.value === "") {
      return (cardYearText.innerText = originYear);
    } else {
      cardYearText.innerText = cardYearInput.value;
    }
  });
};
expireDate();

// Card Flip on mouse control
const cardFlip = () => {
  cardCVCInput.addEventListener("mouseenter", () => {
    frontCard.style.transform = "perspective(1000px) rotateY(-180deg)";
    backCard.style.transform = "perspective(1000px) rotateY(0deg)";
  });
  cardCVCInput.addEventListener("mouseleave", () => {
    frontCard.style.transform = "perspective(1000px) rotateY(0deg)";
    backCard.style.transform = "perspective(1000px) rotateY(180deg)";
  });
};
cardFlip();

// CVC number on Card Input
const cvcNumber = () => {
  cardCVCInput.addEventListener("input", () => {
    if (cardCVCInput.value === "") {
      return (cardCVCText.innerText = originCVC);
    } else {
      cardCVCText.innerText = cardCVCInput.value;
    }
  });
};
cvcNumber();
