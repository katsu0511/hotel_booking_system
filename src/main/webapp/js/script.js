const demoButton = document.getElementById('demo_button');
const email = document.getElementById('email');
const password = document.getElementById('password');
if (demoButton !== null) {
	demoButton.addEventListener('click', () => {
		email.value = 'demo@demo.com';
		password.value = '=S+tMH2h!ZFa,de';
	});
}

const hotelDemoButton = document.getElementById('hotel_demo_button');
const country = document.getElementById('country');
const number = document.getElementById('number');
if (hotelDemoButton !== null) {
	hotelDemoButton.addEventListener('click', () => {
		country.value = 'Canada';
		number.value = '7787372748';
	});
}

const passwordSpan = document.getElementById('password_span');
const switchPassword = document.getElementById('switch_password');
const openPassword = document.getElementById('open_password');
const secretPassword = document.getElementById('secret_password');
if (switchPassword !== null) {
	switchPassword.addEventListener('click', () => {
		if (passwordSpan.classList.contains('secret')) {
			passwordSpan.classList.remove('secret');
			passwordSpan.classList.add('open');
			switchPassword.value = 'hide password';
			passwordSpan.textContent = openPassword.value;
		} else {
			passwordSpan.classList.remove('open');
			passwordSpan.classList.add('secret');
			switchPassword.value = 'see password';
			passwordSpan.textContent = secretPassword.value;
		}
	});
}

const bookBtn = document.getElementById('book_btn');
const checkIn = document.getElementById('check_in');
const checkOut = document.getElementById('check_out');
const checkInError = document.getElementById('check_in_error');
const checkOutError = document.getElementById('check_out_error');
const datesError = document.getElementById('dates_error');
if (bookBtn !== null) {
	bookBtn.addEventListener('click', (event) => {
		const today = new Date();
		const year = today.getFullYear();
		const month = ("0" + String(today.getMonth() + 1)).slice(-2);
		const day = ("0" + String(today.getDate())).slice(-2);
		const date = year + "-" + month + "-" + day;
		if (checkIn.value === '') {
			checkInError.style.display = 'block';
		} else {
			checkInError.style.display = 'none';
		}
		
		if (checkOut.value === '') {
			checkOutError.style.display = 'block';
		} else {
			checkOutError.style.display = 'none';
		}
		
		if (checkIn.value >= checkOut.value || checkIn.value < date || checkOut.value <= date) {
			datesError.style.display = 'block';
		} else {
			datesError.style.display = 'none';
		}
		
		if (checkIn.value === '') {
			checkIn.focus();
		} else if (checkOut.value === '') {
			checkOut.focus();
		} else if (checkIn.value >= checkOut.value || checkIn.value < date || checkOut.value <= date) {
			event.preventDefault();
		} else {
			if (!confirm('Book on these dates?')) {
				event.preventDefault();
			}
		}
	});
}

const cancelBtn = document.getElementById('cancel_btn');
if (cancelBtn !== null) {
	cancelBtn.addEventListener('click', (event) => {
		if (!confirm('Are you sure to cancel?')) {
			event.preventDefault();
		}
	});
}

const paidBtns = document.getElementsByClassName('paid_btn');
const paidBtnArray = Array.from(paidBtns);
if (paidBtnArray !== null) {
	paidBtnArray.forEach(function(paidBtn) {
		paidBtn.addEventListener('click', function(event) {
			if (!confirm('Change to complete?')) {
				event.preventDefault();
			}
		});
	});
}
