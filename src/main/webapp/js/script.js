const demoButton = document.getElementById('demo_button');
const email = document.getElementById('email');
const password = document.getElementById('password');
if (demoButton !== null) {
	demoButton.addEventListener('click', () => {
		email.value = 'demo@demo.com';
		password.value = '=S+tMH2h!ZFa,de';
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
			switchPassword.value = 'see password';
			passwordSpan.textContent = openPassword.value;
		} else {
			passwordSpan.classList.remove('open');
			passwordSpan.classList.add('secret');
			switchPassword.value = 'hide password';
			passwordSpan.textContent = secretPassword.value;
		}
	});
}

const bookBtn = document.getElementById('book_btn');
const checkIn = document.getElementById('check_in');
const checkOut = document.getElementById('check_out');
const checkInError = document.getElementById('check_in_error');
const checkOutError = document.getElementById('check_out_error');
if (bookBtn !== null) {
	bookBtn.addEventListener('click', (event) => {
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
		
		if (checkIn.value === '') {
			checkIn.focus();
		} else if (checkOut.value === '') {
			checkOut.focus();
		} else {
			if (!confirm('Book on these dates?')) {
				event.preventDefault();
			}
		}
	});
}
